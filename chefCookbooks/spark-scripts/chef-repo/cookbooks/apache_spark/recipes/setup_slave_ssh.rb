directory "/root/.ssh" do
  owner 'root'
  group 'root'
  mode '0700'
  action :create
  not_if { File.exists?("/root/.ssh") }
end

cookbook_file "authorized_keys" do
  owner 'root'
  group 'root'
  path "/root/.ssh/authorized_keys"
  mode 0700
  action :create_if_missing
end

cookbook_file "create_temp_file" do
  owner 'root'
  group 'root'
  source "id_rsa.pub"
  path "/tmp/id_rsa.pub"
  mode 0700
  action :create_if_missing
end

bash 'install_something' do
  user 'root'
  code <<-EOH
  cat /tmp/id_rsa.pub >> /root/.ssh/authorized_keys
  rm -rf /tmp/id_rsa.pub
  echo "StrictHostKeyChecking no" >>/etc/ssh/ssh_config
  echo "UserKnownHostsFile=/dev/null" >>/etc/ssh/ssh_config
  EOH
end