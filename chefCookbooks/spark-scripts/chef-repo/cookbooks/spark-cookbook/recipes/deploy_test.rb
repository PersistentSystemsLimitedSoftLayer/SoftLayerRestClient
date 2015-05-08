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
  owner "#{node['spark']['user']}"
  group "#{node['spark']['group']}"
  mode '0755'
  action :create
end

#copies the spark application files
cookbook_file "/home/spark-apps/SNDG.zip" do
 source "SNDG.zip"
 owner "#{node['spark']['user']}"
end

#extract the spark app files
execute 'extract_app' do
  command 'unzip /home/spark-apps/SNDG.zip'
  cwd '/home/spark-apps'
  user "#{node['spark']['user']}"
  not_if { File.exists?("/home/spark-apps/SNDG") }
end


#run the spark-submit script

bash "run_app" do
  cwd "#{node['spark']['master']['submit']['spark_home']}/bin"
  user "#{node['spark']['user']}"
  code <<-EOH
    export SPARK_HOME=#{node['spark']['master']['submit']['spark_home']}
   export YUGO_HOME=#{node['spark']['master']['submit']['app_home']}
   ./spark-submit --executor-memory #{node['spark']['master']['submit']['executor_memory']} --executor-cores #{node['spark']['master']['submit']['executor_core']} --driver-memory #{node['spark']['master']['submit']['app_driver_memory']} --jars #{node['spark']['master']['submit']['jars']}  --class #{node['spark']['master']['submit']['app_class']} --master #{node['spark']['master']['submit']['app_master']} #{node['spark']['master']['submit']['app_jar']} #{node['spark']['master']['submit']['app_args']} >> #{node['spark']['master']['submit']['app_home']}/SNDG.log 2>&1 &
  EOH
end

