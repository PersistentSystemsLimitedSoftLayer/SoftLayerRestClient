# Copyright © 2015 ClearStory Data, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

include_recipe 'apache_spark::find-free-port'
include_recipe 'apache_spark::spark-user'

spark_user = node['apache_spark']['user']
spark_group = node['apache_spark']['group']

install_mode = node['apache_spark']['install_mode']

spark_install_dir = node['apache_spark']['install_dir']
spark_conf_dir = ::File.join(spark_install_dir, 'conf')

case install_mode
when 'package'
  package node['apache_spark']['pkg_name'] do
    version node['apache_spark']['pkg_version']
  end
when 'tarball'
  install_base_dir = node['apache_spark']['install_base_dir']
  directory install_base_dir do
    user spark_user
    group spark_group
  end
  tarball_basename = ::File.basename(URI.parse(node['apache_spark']['download_url']).path)
  downloaded_tarball_path = ::File.join(Chef::Config[:file_cache_path], tarball_basename)
  tarball_url = node['apache_spark']['download_url']
  Chef::Log.warn("#{tarball_url} will be downloaded to #{downloaded_tarball_path}")
  remote_file downloaded_tarball_path do
    source tarball_url
  end

  extracted_dir_name = tarball_basename.sub(/[.](tar[.]gz|tgz)$/, '')

  Chef::Log.warn("#{downloaded_tarball_path} will be extracted in #{install_base_dir}")
  actual_install_dir = ::File.join(install_base_dir, extracted_dir_name)
  tar_extract downloaded_tarball_path do
    action :extract_local
    target_dir install_base_dir
    creates actual_install_dir
    user spark_user
    group spark_group
  end

  link spark_install_dir do
    to actual_install_dir
  end
else
  raise "Invalid Apache Spark installation mode: #{install_mode}. 'package' or 'tarball' required."
end


([spark_install_dir,
  spark_conf_dir,
  node['apache_spark']["standalone"]["log_dir"],
  node['apache_spark']["standalone"]["worker_work_dir"]] + local_dirs).each do |dir|
  directory dir do
    mode 0755
    owner spark_user
    group spark_group
    action :create
    recursive true
  end
end
=begin
template "#{spark_conf_dir}/spark-env.sh" do
  source    "spark-env.sh.erb"
  mode      0644
  owner     spark_user
  group     spark_group
  variables node['apache_spark']["standalone"]
end
=end
bash "Change ownership of Spark installation directory" do
  user "root"
  code "chown -R #{spark_user}:#{spark_group} #{spark_install_dir}"
end

=begin
template "#{spark_conf_dir}/log4j.properties" do
  source    'spark_log4j.properties.erb'
  mode      0644
  owner     spark_user
  group     spark_group
  variables node['apache_spark']['standalone']
end
=end




