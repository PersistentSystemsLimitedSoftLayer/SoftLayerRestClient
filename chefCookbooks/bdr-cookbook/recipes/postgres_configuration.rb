ruby_block "update path in .bashrc" do
  block do
    file = Chef::Util::FileEdit.new("#{node['postgresql']['directory']['home']}/.bashrc")
    file.insert_line_if_no_match("export PATH=$PATH:#{node['postgresql']['directory']['bin']}","export PATH=$PATH:#{node['postgresql']['directory']['bin']}")
    file.write_file
  end
  action :run
end

execute "initdb" do
	command "#{node['postgresql']['directory']['bin']}/initdb -D #{node['postgresql']['config']['data_directory']} -U postgres --auth-host=md5 --auth-local=peer"
	user "postgres"
end

template node['postgresql']['postgresql.conf'] do
  source "postgresql.conf.erb"
end
template node['postgresql']['pg_hba.conf'] do
  source "pg_hba.conf.erb"
end

ruby_block "copy directory contents" do
  block do
    FileUtils.cp_r("#{node['postgresql']['directory']['contrib/bdr']}/.","#{node['postgresql']['directory']['installation']}/share/extension")
  end
  action :run
end

