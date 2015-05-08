#
# Cookbook Name:: hadoop
# Recipe:: repo
#
# Copyright © 2013-2014 Cask Data, Inc.
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

major_platform_version = node['platform_version'].to_i
key = 'RPM-GPG-KEY'

# Ensure that we have the proper LWRPs available
case node['platform_family']
when 'rhel'
  include_recipe 'yum'
when 'debian'
  include_recipe 'apt'
end

# Set defaults for version, based on distribution
node.default['hadoop']['distribution_version'] =
  if node['hadoop']['distribution'] == 'hdp'
    '2.1'
  elsif node['hadoop']['distribution'] == 'cdh'
    '5'
  elsif node['hadoop']['distribution'] == 'bigtop'
    '0.8.0'
  end

case node['hadoop']['distribution']
when 'hdp'
  case node['hadoop']['distribution_version']
  when '2.0.5.0', '2.0.6.0', '2.0.6.1', '2.0.10.0', '2.0.11.0', '2.0.12.0', '2.0.13.0'
    hdp_version = '2.0.4.0'
    hdp_update_version = node['hadoop']['distribution_version']
  when '2.0'
    hdp_version = '2.0.4.0'
    hdp_update_version = '2.0.13.0'
  when '2.1.1.0', '2.0.4.0'
    hdp_version = node['hadoop']['distribution_version']
    hdp_update_version = nil
  when '2.1.2.0', '2.1.2.1', '2.1.3.0', '2.1.4.0', '2.1.5.0', '2.1.7.0'
    hdp_version = '2.1.1.0'
    hdp_update_version = node['hadoop']['distribution_version']
  when '2.1'
    hdp_version = '2.1.1.0'
    hdp_update_version = '2.1.7.0'
  when '2.2.0.0', '2.2', '2'
    hdp_version = '2.2.0.0'
    hdp_update_version = nil
  else
    Chef::Application.fatal!('This cookbook only supports HDP 2.x')
  end

  hdp_utils_version = '1.1.0.20'

  case node['platform_family']
  when 'rhel'
    yum_base_url = 'http://public-repo-1.hortonworks.com/HDP'
    if major_platform_version == 5
      os = "centos#{major_platform_version}"
    else
      os = 'centos6'
    end

    yum_repo_url = node['hadoop']['yum_repo_url'] ? node['hadoop']['yum_repo_url'] : "#{yum_base_url}/#{os}/2.x/GA/#{hdp_version}"
    yum_repo_key_url = node['hadoop']['yum_repo_key_url'] ? node['hadoop']['yum_repo_key_url'] : "#{yum_base_url}/#{os}/#{key}/#{key}-Jenkins"

    yum_repository 'hdp' do
      name 'HDP-2.x'
      description 'Hortonworks Data Platform Version - HDP-2.x'
      url yum_repo_url
      gpgkey yum_repo_key_url
      action :add
    end
    if hdp_update_version.nil?
      yum_repository 'hdp-updates' do
        name 'Updates-HDP-2.x'
        description 'Updates for Hortonworks Data Platform Version - HDP-2.x'
        url "#{yum_base_url}/#{os}/2.x/updates"
        gpgkey yum_repo_key_url
        action :add
      end
    else
      yum_repository 'hdp-updates' do
        name 'Updates-HDP-2.x'
        description 'Updates for Hortonworks Data Platform Version - HDP-2.x'
        url "#{yum_base_url}/#{os}/2.x/updates/#{hdp_update_version}"
        gpgkey yum_repo_key_url
        action :add
      end
    end
    yum_repository 'hdp-utils' do
      name "HDP-UTILS-#{hdp_utils_version}"
      description "Hortonworks Data Platform Utils Version - HDP-UTILS-#{hdp_utils_version}"
      url "#{yum_base_url}-UTILS-#{hdp_utils_version}/repos/#{os}"
      gpgkey yum_repo_key_url
      action :add
    end

  when 'debian'
    apt_base_url = 'http://public-repo-1.hortonworks.com/HDP'
    # HDP only supports Debian 6 and Ubuntu 12
    case node['platform']
    when 'debian'
      os = "#{node['platform']}6"
    else
      os = "#{node['platform']}12"
    end
    hdp_update_version = hdp_version if hdp_update_version.nil?
    apt_repo_url = node['hadoop']['apt_repo_url'] ? node['hadoop']['apt_repo_url'] : "#{apt_base_url}/#{os}/#{hdp_update_version}"
    # Hortonworks don't know how to provide a key, but we do
    apt_repo_key_url = node['hadoop']['apt_repo_key_url'] ? node['hadoop']['apt_repo_key_url'] : "#{apt_base_url}/centos6/#{key}/#{key}-Jenkins"

    apt_repository 'hdp' do
      uri apt_repo_url
      key apt_repo_key_url
      distribution 'HDP'
      trusted true
      components ['main']
      action :add
    end
    apt_repository 'hdp-utils' do
      uri "#{apt_base_url}-UTILS-#{hdp_utils_version}/repos/#{os}"
      key apt_repo_key_url
      distribution 'HDP-UTILS'
      components ['main']
      action :add
    end
  end # End hdp

when 'cdh'
  cdh_release = node['hadoop']['distribution_version'].to_i
  if node['hadoop']['distribution_version'].to_f >= 5.3 && node.key?('java') && node['java'].key?('jdk_version') && node['java']['jdk_version'] < 7
    Chef::Application.fatal!('CDH 5.3 and above require Java 7 or higher')
  end
  case node['platform_family']
  when 'rhel'
    yum_base_url = "http://archive.cloudera.com/cdh#{cdh_release}/redhat"
    yum_repo_url = node['hadoop']['yum_repo_url'] ? node['hadoop']['yum_repo_url'] : "#{yum_base_url}/#{major_platform_version}/#{node['kernel']['machine']}/cdh/#{node['hadoop']['distribution_version']}"
    yum_repo_key_url = node['hadoop']['yum_repo_key_url'] ? node['hadoop']['yum_repo_key_url'] : "#{yum_base_url}/#{major_platform_version}/#{node['kernel']['machine']}/cdh/#{key}-cloudera"

    yum_repository "cloudera-cdh#{cdh_release}" do
      name "cloudera-cdh#{cdh_release}"
      description "Cloudera's Distribution for Hadoop, Version #{cdh_release}"
      url yum_repo_url
      gpgkey yum_repo_key_url
      action :add
    end

  when 'debian'
    codename = node['lsb']['codename']

    # rubocop: disable Metrics/BlockNesting
    case codename
    when 'raring', 'saucy', 'trusty'
      Chef::Log.warn('This version of Ubuntu is unsupported by Cloudera! Bug reports should include patches.')
      codename = 'precise'
    when 'trusty'
      unless cdh_release >= 5
        Chef::Log.warn('This version of Ubuntu is unsupported by Cloudera! Bug reports should include patches.')
        codename = 'precise'
      end
    end
    # rubocop: enable Metrics/BlockNesting

    apt_base_url = "http://archive.cloudera.com/cdh#{cdh_release}/#{node['platform']}"
    apt_repo_url = node['hadoop']['apt_repo_url'] ? node['hadoop']['apt_repo_url'] : "#{apt_base_url}/#{codename}/amd64/cdh"
    apt_repo_key_url = node['hadoop']['apt_repo_key_url'] ? node['hadoop']['apt_repo_key_url'] : "#{apt_base_url}/#{codename}/amd64/cdh/archive.key"

    apt_repository "cloudera-cdh#{cdh_release}" do
      uri apt_repo_url
      key apt_repo_key_url
      distribution "#{codename}-cdh#{node['hadoop']['distribution_version']}"
      components ['contrib']
      arch 'amd64'
      action :add
    end
  end # End cdh

when 'bigtop'
  bigtop_release = node['hadoop']['distribution_version']

  # allow a developer mode for use when developing against bigtop, see https://issues.cask.co/browse/COOK-1
  if bigtop_release.downcase == 'develop' && !(node['hadoop'].key?('yum_repo_url') || node['hadoop'].key?('apt_repo_url'))
    Chef::Application.fatal!("You must set node['hadoop']['yum_repo_url'] or node['hadoop']['apt_repo_url'] when specifying node['hadoop']['distribution_version'] == 'develop'")
  end

  # do not validate gpg repo keys when in develop mode
  validate_repo_key =  bigtop_release.downcase == 'develop' ? false : true
  Chef::Log.warn('Allowing install of unsigned binaries') unless validate_repo_key

  case node['platform_family']
  when 'rhel'

    case major_platform_version
    when 5, 6
      yum_platform_version = major_platform_version
    when 2014 # Amazon linux, point to redhat/6 bigtop repo
      yum_platform_version = 6
    else
      Chef::Log.warn('Unsupported platform detected, use at your own risk')
      yum_platform_version = major_platform_version
    end

    yum_base_url = "http://bigtop.s3.amazonaws.com/releases/#{bigtop_release}/redhat"
    yum_repo_url = node['hadoop']['yum_repo_url'] ? node['hadoop']['yum_repo_url'] : "#{yum_base_url}/#{yum_platform_version}/#{node['kernel']['machine']}"
    yum_repo_key_url = node['hadoop']['yum_repo_key_url'] ? node['hadoop']['yum_repo_key_url'] : 'http://archive.apache.org/dist/bigtop/KEYS'

    yum_repository "bigtop-#{bigtop_release}" do
      name "bigtop-#{bigtop_release}"
      description "Apache Bigtop Distribution for Hadoop, Version #{bigtop_release}"
      url yum_repo_url
      gpgkey yum_repo_key_url
      gpgcheck validate_repo_key
      action :add
    end

  when 'debian'
    # for bigtop, we do not validate codename, to support developing against custom repositories
    codename = node['lsb']['codename']

    apt_base_url = "http://bigtop.s3.amazonaws.com/releases/#{bigtop_release}/#{node['platform']}"
    apt_repo_url = node['hadoop']['apt_repo_url'] ? node['hadoop']['apt_repo_url'] : "#{apt_base_url}/#{codename}/#{node['kernel']['machine']}"
    apt_repo_key_url = node['hadoop']['apt_repo_key_url'] ? node['hadoop']['apt_repo_key_url'] : 'http://archive.apache.org/dist/bigtop/KEYS'

    apt_repository "bigtop-#{bigtop_release}" do
      uri apt_repo_url
      key apt_repo_key_url
      trusted !validate_repo_key
      distribution 'bigtop'
      components ['contrib']
      action :add
    end
  end
else
  # COOK-25 fail fast
  Chef::Application.fatal!("Invalid node['hadoop']['distribution'] (#{node['hadoop']['distribution']}) specified!")
end
