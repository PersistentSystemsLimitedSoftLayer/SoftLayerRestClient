password = %x( mkpasswd postgres )
password = password.chop

user 'postgres' do
  supports :manage_home => true
  home node['postgresql']['directory']['home']
  shell '/bin/bash'    
  password #{password}
  action :create    
end

[node['postgresql']['config']['data_directory'],node['postgresql']['directory']['git_clone'],node['postgresql']['directory']['installation']].each do |dir_name|
  directory dir_name do
     recursive true
	 action :delete
  end
end

 directory node['postgresql']['config']['data_directory'] do
    mode "0775"
	owner "postgres"
	group "postgres"
	action :create 
	recursive true
 end

git node['postgresql']['directory']['git_clone'] do
  repository node['postgresql']['git']['repository']
  revision node['postgresql']['git']['revision']
  user "postgres"
  action :sync
end

execute "configure" do
	command "./configure --prefix=#{node['postgresql']['directory']['installation']}  --with-openssl"
	user "postgres"
	cwd node['postgresql']['directory']['git_clone']
end
[node['postgresql']['directory']['git_clone'],node['postgresql']['directory']['contrib/btree_gist'],node['postgresql']['directory']['contrib/bdr']].each do |directory|
	bash "do_install" do
		user 'postgres'
		cwd directory
		code <<-EOH
		make
		make install
		EOH
	end
end

