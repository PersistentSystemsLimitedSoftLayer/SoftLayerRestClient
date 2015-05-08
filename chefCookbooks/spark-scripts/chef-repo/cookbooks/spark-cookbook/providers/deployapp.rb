action :deploy do
 app_class = new_resource.appclass
 master = new_resource.master
 executor_memory = new_resource.executor_memory
 total_executor_cores = new_resource.total_executor_cores
 jar_file = new_resource.jar_file
 application_arguments = new_resource.application_arguments
 
 
 cookbook_file '#{node[\'spark\'][\'master\'][\'application-dir\']}/#{node[\'spark\'][\'application-list\']}' do
  source '/#{node[\'spark\'][\'application-list\']}'
  mode '0644'
 end
 
 bash "deploy-app" do
 cwd '#{node[\'spark\'][\'master\'][\'spark_home\']}'
 code <<-EOH
 ./bin/spark-submit \
  --class #{appclass} \
  --master #{master} \
  --total-executor-cores #{total_executor_cores} \
   #{jar_file} \
  #{application_arguments}
  EOH
 end
end
