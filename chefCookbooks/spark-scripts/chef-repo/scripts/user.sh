#!/bin/bash
echo "{
  \"name\": \"spark-user-env\",
  \"description\": \"environment for creation of spark user\",
  \"cookbook_versions\": {
        \"apache_spark\": \"1.0.5\"
  },
  \"json_class\": \"Chef::Environment\",
  \"chef_type\": \"environment\",
  \"default_attributes\": {

  },
  \"override_attributes\": {
    \"apache_spark\": {
      \"user\": \"$1\" ,
      \"group\": \"$2\" , 
      \"user_password\": \"$3\"
    }
  }
}
" > /home/chef-repo/environments/spark-user-env.json
	 
knife environment from file /home/chef-repo/environments/spark-user-env.json


	 
