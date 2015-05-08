echo "{
  \"name\": \"spark-app-env\",
  \"description\": \"environment for spark app\",
  \"cookbook_versions\": {
        \"spark-cookbook\": \"0.1.0\"
  },
  \"json_class\": \"Chef::Environment\",
  \"chef_type\": \"environment\",
  \"default_attributes\": {

  },
  \"override_attributes\": {
    \"spark\": {
		\"user\": \"root\",
		\"group\": \"root\",
		\"master\": {		
			\"submit\": {
				\"spark_home\": \"/opt/Spark/spark-1.2.1-bin-cdh4\",
				\"app_home\": \"/home/spark-apps/SNDG\",
				\"spark_app_jar\": \"/lib/searchnet-data-generator-1.0.jar\",
				\"other_jars\":  \"/lib/common-util-1.0.jar\",
				\"app_driver_memory\": \"3G\",
				\"app_class\": \"com.kantarmedia.yugo.searchnet.client.SearchnetDataGeneratorClient\",
				\"app_master\": \"spark://$2:7077\",
				\"app_args\": \"2014-06-30\",
				\"executor_memory\": \"4G\",
				\"executor_core\": \"2\"
			}
		}
	}		
  }
}
" > /home/chef-repo/environments/spark-app-env.json
	 
knife environment from file /home/chef-repo/environments/spark-app-env.json

knife bootstrap $2 -E spark-app-env -N $1 -x root -i ~/.ssh/id_rsa -r 'recipe[spark-cookbook::deploy_test]'


