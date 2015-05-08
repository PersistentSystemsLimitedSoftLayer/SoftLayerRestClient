directory "/root/.ssh" do
  owner 'root'
  group 'root'
  mode '0700'
  action :create
  not_if { File.exists?("/root/.ssh") }
end

cookbook_file '/root/.ssh/id_rsa' do
  owner 'root'
  group 'root'
  source 'id_rsa'
  mode 0700
  action :create
end

cookbook_file '/root/.ssh/id_rsa.pub' do
  owner 'root'
  group 'root'
  source 'id_rsa.pub'
  mode 0700
  action :create
end

