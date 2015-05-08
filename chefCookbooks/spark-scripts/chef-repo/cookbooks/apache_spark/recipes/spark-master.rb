#configures a spark node as a spark master
spark_home = "/opt/Spark/spark-1.2.1-bin-cdh4"

template "#{spark_home}/conf/spark-env.sh" do
  source "spark-env.sh.erb"
  action :create 
end

template "#{spark_home}/conf/slaves" do
  source "slaves.erb"
  action :create 
end

bash 'start_master' do
  cwd  "#{spark_home}"
  code <<-EOH
  ./sbin/start-master.sh > /home/masterStartLog.log 2>&1 & 
  EOH
end

