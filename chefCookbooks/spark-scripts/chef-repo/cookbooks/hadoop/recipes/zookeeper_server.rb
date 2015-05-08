#
# Cookbook Name:: hadoop
# Recipe:: zookeeper_server
#
# Copyright © 2013-2015 Cask Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

include_recipe 'hadoop::repo'
include_recipe 'hadoop::zookeeper'
pkg = 'zookeeper-server'

package pkg do
  action :nothing
end

# Hack to prevent auto-start of services, see COOK-26
ruby_block "package-#{pkg}" do
  block do
    begin
      Chef::Resource::RubyBlock.send(:include, Hadoop::Helpers)
      policy_rcd('disable') if node['platform_family'] == 'debian'
      resources("package[#{pkg}]").run_action(:install)
    ensure
      policy_rcd('enable') if node['platform_family'] == 'debian'
    end
  end
end

# HDP 2.0.11.0 (maybe others) doesn't create zookeeper group
group 'zookeeper' do
  action :create
end

zookeeper_conf_dir = "/etc/zookeeper/#{node['zookeeper']['conf_dir']}"

# Setup zoo.cfg
if node['zookeeper'].key?('zoocfg')

  # We need to create a data directory, if it exists
  zookeeper_data_dir =
    if node['zookeeper']['zoocfg'].key?('dataDir')
      node['zookeeper']['zoocfg']['dataDir']
    else
      '/var/lib/zookeeper'
    end
  zookeeper_datalog_dir =
    if node['zookeeper']['zoocfg'].key?('dataLogDir')
      node['zookeeper']['zoocfg']['dataLogDir']
    else
      '/var/lib/zookeeper'
    end
  zookeeper_client_port =
    if node['zookeeper']['zoocfg'].key?('clientPort')
      node['zookeeper']['zoocfg']['clientPort']
    else
      '2181'
    end

  node.default['zookeeper']['zoocfg']['dataDir'] = zookeeper_data_dir
  node.default['zookeeper']['zoocfg']['dataLogDir'] = zookeeper_datalog_dir
  node.default['zookeeper']['zoocfg']['clientPort'] = zookeeper_client_port
  my_vars = { :properties => node['zookeeper']['zoocfg'] }

  directory zookeeper_data_dir do
    owner 'zookeeper'
    group 'hadoop'
    mode '0755'
    recursive true
    action :create
  end

  unless zookeeper_datalog_dir == zookeeper_data_dir
    directory zookeeper_datalog_dir do
      owner 'zookeeper'
      group 'hadoop'
      mode '0755'
      recursive true
      action :create
      only_if { node['zookeeper']['zoocfg'].key?('dataLogDir') }
    end
  end

  template "#{zookeeper_conf_dir}/zoo.cfg" do
    source 'generic.properties.erb'
    owner 'root'
    group 'root'
    mode '0644'
    action :create
    variables my_vars
  end

  # Try and find the current node in the list of configured servers. If the node was found then write the myid file
  myid = nil
  1.upto(255) do |index|
    server = node['zookeeper']['zoocfg']["server.#{index}"]
    next if server.nil?
    next unless server.start_with?("#{node['fqdn']}:") || server.start_with?("#{node['ipaddress']}:") || server.start_with?("#{node['hostname']}:")
    myid = index
    break
  end

  template "#{node['zookeeper']['zoocfg']['dataDir']}/myid" do
    source 'zookeeper-myid.erb'
    owner 'root'
    group 'root'
    mode '0644'
    action :create
    variables(
      :myid => myid
    )
    not_if { myid.nil? }
  end
end # End zoo.cfg

# Setup zookeeper-env.sh
if node['zookeeper'].key?('zookeeper_env')
  my_vars = { :options => node['zookeeper']['zookeeper_env'] }

  zookeeper_log_dir =
    if node['zookeeper']['zookeeper_env'].key?('zookeeper_log_dir')
      node['zookeeper']['zookeeper_env']['zookeeper_log_dir']
    else
      '/var/log/zookeeper'
    end

  directory zookeeper_log_dir do
    owner 'zookeeper'
    group 'zookeeper'
    mode '0755'
    action :create
    recursive true
    only_if { node['zookeeper']['zookeeper_env'].key?('zookeeper_log_dir') }
  end

  template "#{zookeeper_conf_dir}/zookeeper-env.sh" do
    source 'generic-env.sh.erb'
    mode '0755'
    owner 'root'
    group 'root'
    action :create
    variables my_vars
  end

  unless zookeeper_log_dir == '/var/log/zookeeper'
    # Delete default directory, if we aren't set to it
    directory '/var/log/zookeeper' do
      action :delete
      recursive true
      not_if 'test -L /var/log/zookeeper'
    end
    # symlink
    link '/var/log/zookeeper' do
      to zookeeper_log_dir
    end
  end
end # End zookeeper-env.sh

# Setup log4j.properties
if node['zookeeper'].key?('log4j')
  my_vars = { :properties => node['zookeeper']['log4j'] }

  template "#{zookeeper_conf_dir}/log4j.properties" do
    source 'generic.properties.erb'
    mode '0644'
    owner 'root'
    group 'root'
    action :create
    variables my_vars
  end
end # End log4j.properties

# Hack to work around broken Hortonworks release engineering
if node['hadoop']['distribution'] == 'hdp' &&
   (node['hadoop']['distribution_version'].to_f == 2.1 || node['hadoop']['distribution_version'].to_s == '2')
  log 'hdp-2.1 release engineering fix' do
    level :warn
    message 'Performing workaround for broken zookeeper-server init script on HDP 2.1'
  end
  directory '/usr/lib/bigtop-utils' do
    action :create
  end
  file '/usr/lib/bigtop-utils/bigtop-detect-javahome' do
    action :touch
    not_if 'test -e /usr/lib/bigtop-utils/bigtop-detect-javahome'
  end
end # HDP 2.1 hack

service pkg do
  status_command "service #{pkg} status"
  supports [:restart => true, :reload => false, :status => true]
  action :nothing
end

# Update alternatives to point to our configuration
execute 'update zookeeper-conf alternatives' do
  command "update-alternatives --install /etc/zookeeper/conf zookeeper-conf /etc/zookeeper/#{node['zookeeper']['conf_dir']} 50"
  not_if "update-alternatives --display zookeeper-conf | grep best | awk '{print $5}' | grep /etc/zookeeper/#{node['zookeeper']['conf_dir']}"
end
