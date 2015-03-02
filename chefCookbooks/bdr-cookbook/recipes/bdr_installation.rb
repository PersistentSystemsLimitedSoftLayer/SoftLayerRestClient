password = %x( mkpasswd postgres )
password = password.chop

user 'postgres' do
  supports :manage_home => true
  home '/home/postgres'
  shell '/bin/bash'    
  password #{password}
  action :create    
end

%w( #{node['postgresql']['config']['data_directory']} /home/postgres/2ndquadrant_bdr /home/bdr).each do |dir_name|
  directory dir_name do
     recursive true
	 action :delete
  end
end

 directory "#{node['postgresql']['config']['data_directory']}" do
    mode "0775"
	owner "postgres"
	group "postgres"
	action :create 
	recursive true
 end

git "/home/postgres/2ndquadrant_bdr" do
  repository "git://git.postgresql.org/git/2ndquadrant_bdr.git"
  revision "bdr/0.7.1"
  user "postgres"
  action :sync
end

bash 'install_something' do
  user 'postgres'
  cwd '/home/postgres/2ndquadrant_bdr'
  code <<-EOH
  ./configure --prefix=/home/postgres/bdr  --with-openssl
  make
  make install
  cd /home/postgres/2ndquadrant_bdr/contrib/btree_gist && make && make install && cd ../../contrib/bdr && make && make install
  EOH
end
