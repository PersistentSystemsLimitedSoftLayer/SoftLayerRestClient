default['spark']['application_list'] = "Spark-pi-app.jar" #%w(Spark-pi-app)
default['spark']['master']['ip']
default['spark']['master']['spark_home'] = "/root/spark-1.2.1-bin-cdh4/"
default['spark']['master']['application_dir'] = "/root"

default['spark']['user']=""
default['spark']['group']=""
default['spark']['master']['submit']['spark_home']=""
default['spark']['master']['submit']['app_home'] = ""
default['spark']['master']['submit']['spark_app_jar'] = ""
default['spark']['master']['submit']['app_jar'] = node['spark']['master']['submit']['app_home'] + node['spark']['master']['submit']['spark_app_jar']
default['spark']['master']['submit']['other_jars'] = ""
default['spark']['master']['submit']['jars'] = node['spark']['master']['submit']['app_home'] + node['spark']['master']['submit']['other_jars']
default['spark']['master']['submit']['app_driver_memory'] = ""
default['spark']['master']['submit']['app_class'] = ""
default['spark']['master']['submit']['app_master'] = ""
default['spark']['master']['submit']['app_args'] = ""
default['spark']['master']['submit']['executor_memory'] = ""
default['spark']['master']['submit']['executor_core'] = ""
