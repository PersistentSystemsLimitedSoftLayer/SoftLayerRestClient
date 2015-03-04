execute "start_bdr" do
     command "#{node['postgresql']['directory']['bin']}/pg_ctl -D #{node['postgresql']['config']['data_directory']} start"
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
\"\"\" | #{node['postgresql']['directory']['bin']}/psql"
	user "postgres"
	retry_delay 10
end

bash 'restart_bdr' do
  user 'postgres'
  code <<-EOH
  #{node['postgresql']['directory']['bin']}/pg_ctl -D #{node['postgresql']['config']['data_directory']} stop -m immediate
  #{node['postgresql']['directory']['bin']}/pg_ctl -D #{node['postgresql']['config']['data_directory']} start
  EOH
end