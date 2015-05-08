echo "{
 	\"name\": \"spark-hosts-env\",
 	\"description\": \"environment for writing to hosts file\",
  	\"cookbook_versions\": {
       		\"apache_spark\": \"1.0.5\"
  		},
  	\"json_class\": \"Chef::Environment\",
  	\"chef_type\": \"environment\",
  	\"default_attributes\": {
  		},
  	\"override_attributes\": {
    		\"spark\": {
      			\"ip\":\"$1\",
				\"hostname\":\"$2.persistent.com\",
				\"nodename\":\"$2\"
    			}
  		}
	}
" > /home/chef-repo/environments/spark-hosts-env.json
	 
knife environment from file /home/chef-repo/environments/spark-hosts-env.json