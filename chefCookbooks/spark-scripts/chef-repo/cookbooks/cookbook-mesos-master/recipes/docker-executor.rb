#
# Cookbook Name:: mesos
# Recipe:: docker-executor
#

version = node[:mesos][:version]
platform_version = node['platform_version']

# tricks for "0.19.0" only.
if version == "0.19.0" then
  version = "0.19.0_rc2"
end

if version >= "0.18.2"
  egg_name = "mesos-#{version}-py2.7-linux-x86_64"
else
  egg_name = "mesos_#{version}-amd64"
end

# this doesn't work. so we have to install docker manually outside. I can't figure out why.
include_recipe "docker"

package "python-setuptools" do
  action :install
end

remote_file "#{Chef::Config[:file_cache_path]}/#{egg_name}.egg" do
  source "http://downloads.mesosphere.io/master/ubuntu/#{platform_version}/#{egg_name}.egg"
  mode   "0755"
  not_if { File.exists?("#{Chef::Config[:file_cache_path]}/#{egg_name}.egg")==true }
  notifies :run,  "execute[install-mesos-python-binding]"
end

execute "install-mesos-python-binding" do
  command "easy_install #{Chef::Config[:file_cache_path]}/#{egg_name}.egg"
  not_if { ::File.exists?("/usr/local/lib/python2.7/dist-packages/#{egg_name}.egg") }
end

directory '/var/lib/mesos/executors' do
  owner 'root'
  group 'root'
  mode "0755"
  recursive true
  action :create
end

remote_file "/var/lib/mesos/executors/docker" do
  source "https://raw.github.com/mesosphere/mesos-docker/master/bin/mesos-docker"
  mode "0755"
  not_if { File.exists?("/var/lib/mesos/executor/docker")==true }
end
