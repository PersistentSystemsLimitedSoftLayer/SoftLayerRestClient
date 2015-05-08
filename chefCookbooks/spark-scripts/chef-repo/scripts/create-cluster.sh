#!/bin/bash

knife cookbook upload apache_spark
knife cookbook upload spark-cookbook

image_id=""
ssh_keys=""
cores=""
ram=""
datacenter=""
no_of_nodes=""

rm /tmp/spark_cluster
touch /tmp/spark_cluster

if [ $# -lt 6 ] 
then
   echo " Please Pass The required arguments  -i <image-id> -s <ssh-keys> -c <cores> -r <ram> -d <datacenter> -n <no-of-nodes>"
else
	while getopts ":i:s:c:r:d:n:" opt; do
  		case $opt in
			 i)
     			image_id=$OPTARG
     		 ;;
    		 s)
     			ssh_keys=$OPTARG
      		 ;;
    		 c)
      			cores=$OPTARG
      		 ;;
    		 r)
      			ram=$OPTARG
      		 ;;
    		 d)
      			datacenter=$OPTARG
      		 ;;
			 n)
      			no_of_nodes=$OPTARG
      		 ;;	 
    		\?)
      			echo "Invalid option: -$OPTARG" >&2
      		 ;;
    		 :)
     			echo "Option -$OPTARG requires an argument." >&2
      			exit 1
      		 ;;
  		esac
	done

	echo "image-id= $image_id , ssh-keys=$ssh_keys , cores=$cores , ram=$ram , datacenter=$datacenter "
	index=$no_of_nodes
	i=1

	user=spark
	group=spark
	password=psl
	
	masterIp=0
	masterNodename=""
	declare -a workers
	declare -a workersIps

	./user.sh $user $group $password
	
	while [ $i -le $index ]
	do   
		node_name=spark
		if [ $i == 1 ] 
		then
			node_name=spark-master-node$(date '+%d-%m-%Y-%H-%M-%S')
		else
			j=$((i-1))
			node_name=spark-worker$(date '+%d-%m-%Y-%H-%M-%S')-$j
		fi
	
		knife softlayer server create \
		--image-id ${image_id} \
		--ssh-keys ${ssh_keys} \
		--hostname ${node_name} \
		--domain persistent.com \
		--cores ${cores} \
		--ram ${ram} \
		--datacenter ${datacenter} \
		--node-name ${node_name} \
		-x root \
		-i ~/.ssh/id_rsa

		list=($(slcli vs list | grep ${node_name} ))

		echo "${list[2]}"
		serverIp=${list[2]}
	
	
		echo "Created ${node_name} at $serverIp"		

		if [ $i == 1 ] 
		then
			masterIp=$serverIp
			masterNodename=${node_name}

		 	echo "{
  				\"name\": \"spark-master-env\",
 		       	\"description\": \"environment for spark master\",
  					\"cookbook_versions\": {
       				\"apache_spark\": \"1.0.5\"
  					},
  				\"json_class\": \"Chef::Environment\",
  				\"chef_type\": \"environment\",
  				\"default_attributes\": {
  				},
  				\"override_attributes\": {
    					\"apache_spark\": {
      						\"standalone\": {
        						\"master_host\": \"$serverIp\",
        						\"master_bind_ip\": \"$serverIp\"
      						}
    					}
  				}
			}
			" > /home/chef-repo/environments/spark-master-env.json
	 
			knife environment from file /home/chef-repo/environments/spark-master-env.json
	
      			echo "create spark master ${node_name} at $serverIp "
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-master-env -r 'recipe[apache_spark::spark-master]'
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-user-env -r 'recipe[apache_spark::spark-user]'
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -r 'recipe[apache_spark::setup_master_ssh]'

		else
	
			./write-hosts.sh ${masterIp} ${masterNodename}
			
			echo "create spark worker ${node_name} at $serverIp "
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-master-env -r 'recipe[apache_spark::spark-worker]'
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-hosts-env -r 'recipe[apache_spark::hosts]'
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-user-env -r 'recipe[apache_spark::spark-user]'
			knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -r 'recipe[apache_spark::setup_slave_ssh]'


			./write-hosts.sh ${serverIp} ${node_name}
			knife bootstrap $masterIp -x root -i ~/.ssh/id_rsa -N ${masterNodename} -E spark-hosts-env -r 'recipe[apache_spark::hosts]'

			if [ ${#workers[@]} -ne 0 ]
			then 
				x=0
				while [ $x -lt ${#workers[@]} ]
			    do
					list=($(slcli vs list | grep ${workers[x]} ))
					echo "${list[2]}"
					workerIp=${list[2]}
					
					#writes recently created worker's ip to hosts file of previously created worker
					./write-hosts.sh ${serverIp} ${node_name}
					knife bootstrap $workerIp -x root -i ~/.ssh/id_rsa -N ${workers[x]} -E spark-hosts-env -r 'recipe[apache_spark::hosts]'
					
					#writes previously created worker's ip to hosts file of recently created worker
					./write-hosts.sh ${workerIp} ${workers[x]}
					knife bootstrap $serverIp -x root -i ~/.ssh/id_rsa -N ${node_name} -E spark-hosts-env -r 'recipe[apache_spark::hosts]'
					
					x=$((x+1))
				done
			fi
					
			
			./deleteChefNodesClients.sh ${node_name} $serverIp
			workers=("${workers[@]}" "$node_name")
			workersIps=("${workersIps[@]}" "$serverIp")
		fi 
		
		echo "$serverIp $node_name.persistent.com $node_name " >> /tmp/spark_cluster
		i=$((i+1))
	done


#Writing to add_worker.sh

cat /tmp/add_worker_intermediate > /home/Other/add_worker.sh
echo "cd /opt/Spark/spark-1.2.1-bin-cdh4/bin && ./spark-class org.apache.spark.deploy.worker.Worker spark://$masterIp:7077 >/home/workerStartLog.log 2>&1 & " >> /home/Other/add_worker.sh
chmod +x /home/Other/add_worker.sh
	
	./app.sh $masterNodename $masterIp 
	./deleteChefNodesClients.sh $masterNodename $masterIp
fi

