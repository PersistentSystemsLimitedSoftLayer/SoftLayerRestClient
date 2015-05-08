#
# Cookbook Name:: spark-cookbook
# Recipe:: default
#
# Copyright 2015, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#


app_class = "org.apache.spark.examples.SparkPi"
master = "spark://169.54.255.2020202020202020202020202020202020202020:7077"
total_executor_cores = 1

master_app_store = "/home/spark_apps" #directory where application jars to be run on spark cluster are stored

jar_file = node['spark']['application_list'];
jar_file_path = master_app_store + "/" + jar_file

application_arguments = "1000"

spark_home = node['spark']['master']['spark_home']


directory master_app_store do
  owner 'root'
  group 'root'
  mode '0755'
  action :create
end

cookbook_file jar_file_path do
  source jar_file
  mode '0644'
end

bash "run_spark-submit" do
 cwd spark_home
 code <<-EOH
 ./bin/spark-submit \
  --class #{app_class} \
  --master  #{master} \
  --total-executor-cores #{total_executor_cores} \
   #{jar_file_path} \
  #{application_arguments}
  EOH
 end
