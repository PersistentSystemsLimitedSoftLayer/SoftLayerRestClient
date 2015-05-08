# 1. installs some linux tools (unzip,net-tools,vim)
# 2. creates a directory for spark_apps on VM (/home/spark-apps)
# 3. copies application.zip  to spark_apps directory
# 4. extracts application.zip using unzip command
# 5. runs the spark-submit script

%w(unzip net-tools vim).each do |package_name|
 package package_name do
  action :install
 end
end

#creates directory to store spark applications
directory "/home/spark-apps" do
  owner 'root'
  group 'root'
  mode '0755'
  action :create
end

#copies the spark application files
cookbook_file "/home/spark-apps/SNDG.zip" do
 source "SNDG.zip"
end

#extract the spark app files
execute 'extract_app' do
  command 'unzip /home/spark-apps/SNDG.zip'
  cwd '/home/spark-apps'
  not_if { File.exists?("/home/spark-apps/SNDG_app") }
end

spark_home = "/opt/Spark/spark-1.2.1-bin-cdh4"
app_home = "/home/spark-apps/SNDG_app"
spark_app_jar = app_home+ "/lib/searchnet-data-generator-1.0.jar"
other_jars = app_home+ "/lib/common-util-1.0.jar"
app_driver_memory = "3G"
app_class = "com.kantarmedia.yugo.searchnet.client.SearchnetDataGeneratorClient"
app_master = "local[2]"
app_args = "2014-06-30"

#run the spark-submit script

bash "run_app" do
  cwd "#{spark_home}/bin"
  code <<-EOH
    export SPARK_HOME=#{spark_home}
   export YUGO_HOME=#{app_home}
   ./spark-submit --driver-memory #{app_driver_memory} --jars #{other_jars}  --class #{app_class} --master #{app_master} #{spark_app_jar} #{app_args} >> #{app_home}/SNDG.log
  EOH
end
