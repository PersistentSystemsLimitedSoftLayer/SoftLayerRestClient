# This is a Chef recipe file. It can be used to specify resources which will
# apply configuration to a server.

log "Welcome to Chef, #{node["starter_name"]}!" do
  level :info
end

 bash "deploy-application" do
 cwd '/root/spark-1.2.1-bin-cdh4/bin/'
 code <<-EOH
 ./spark-submit \
  --class org.apache.spark.examples.SparkPi \
  --master spark://169.54.255.84:7077 \
   /root/spark-1.2.1-bin-cdh4/lib/spark-examples-1.2.1-hadoop2.0.0-mr1-cdh4.2.0.jar \
  1000
 EOH
end
# For more information, see the documentation: https://docs.chef.io/essentials_cookbook_recipes.html
