execute "start_bdr" do
     command "/home/postgres/bdr/bin/pg_ctl -D #{node['postgresql']['config']['data_directory']} start"
     user "postgres"
end
ruby_block "waiting for database to start" do
  block do
    sleep(10)
  end
  action :run
end

execute "query" do
	command "echo \"\"\"
alter user postgres with password 'postgres'
\"\"\" | /home/postgres/bdr/bin/psql"
	user "postgres"
end

bash 'restart_bdr' do
  user 'postgres'
  code <<-EOH
  /home/postgres/bdr/bin/pg_ctl -D #{node['postgresql']['config']['data_directory']} stop -m immediate
  /home/postgres/bdr/bin/pg_ctl -D #{node['postgresql']['config']['data_directory']} start
  EOH
end