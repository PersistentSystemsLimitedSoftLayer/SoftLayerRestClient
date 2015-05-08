cookbook_file "/etc/hosts" do
	source "spark_cluster"
	action :create
end
 