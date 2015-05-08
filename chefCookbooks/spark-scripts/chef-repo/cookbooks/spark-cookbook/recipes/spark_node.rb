#installs jdk and spark on a node
%w(unzip net-tools vim).each do |package_name|
 package package_name do
  action :install
 end
end
 
directory "/opt/JDK" do
  owner 'root'
  group 'root'
  mode '0755'
  action :create
end

cookbook_file "/opt/JDK/jdk-8u20-linux-x64.rpm" do
 source "jdk-8u20-linux-x64.rpm"
end

package "jdk-8u20-linux-x64.rpm" do
 source "/opt/JDK/jdk-8u20-linux-x64.rpm"
end

cookbook_file "/opt/spark/spark-1.2.1-bin-cdh4.tgz" do
 source "spark-1.2.1-bin-cdh4.tgz"
end

execute "extract_spark" do
 command "tar -xvf spark-1.2.1-bin-cdh4.tgz"
 cwd "/opt/spark"
end

#cookbook_file "/home/SNDG.zip" do
# source "SNDG.zip"
#end


#execute "unzip_SNDG" do
# command "unzip SNDG.zip"
# cwd "/home"
#end

