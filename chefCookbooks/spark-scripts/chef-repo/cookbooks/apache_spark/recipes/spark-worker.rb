#configures a spark node as a spark worker
spark_home = "/opt/Spark/spark-1.2.1-bin-cdh4"
master_ip = node['apache_spark']['standalone']['master_host'] 
master_port = "7077"
master_url = "spark://" + master_ip + ":" + master_port
template "#{spark_home}/conf/spark-env.sh" do
  source "spark-env.sh.erb"
  action :create 
end

template "#{spark_home}/conf/slaves" do
  source "slaves.erb"
  action :create 
end

  execute "make-executable" do
   command "chmod +x #{spark_home}/conf/spark-env.sh "
  end

bash 'install_something' do
  cwd  "#{spark_home}/bin"
  code <<-EOH
  ./spark-class org.apache.spark.deploy.worker.Worker #{master_url} > /home/workerStartLog.log 2>&1 & 
  EOH
end
