ruby_block "update path in .bashrc" do
  block do
    file = Chef::Util::FileEdit.new("/home/postgres/.bashrc")
    file.insert_line_if_no_match("export PATH=$PATH:/home/postgres/bdr/bin","export PATH=$PATH:/home/postgres/bdr/bin")
    file.write_file
  end
  action :run
end

execute "initdb" do
	command "/home/postgres/bdr/bin/initdb -D #{node['postgresql']['config']['data_directory']} -U postgres --auth-host=md5 --auth-local=peer"
	user "postgres"
end

template "#{node['postgresql']['config']['data_directory']}/postgresql.conf" do
  source "postgresql.conf.erb"
end
template "#{node['postgresql']['config']['data_directory']}/pg_hba.conf" do
  source "pg_hba.conf.erb"
end

ruby_block "copy directory contents" do
  block do
    FileUtils.cp_r("/home/postgres/2ndquadrant_bdr/contrib/bdr/.","/home/postgres/bdr/share/extension")
  end
  action :run
end

