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

include_recipe 'monit'

# Ensure monit daemon is running. This may not happen on its own on Docker.
bash 'start-monit' do
  code '/etc/init.d/monit start'
end

chef_gem 'waitutil'

template '/usr/local/bin/start_stop_service_from_monit.sh' do
  source 'start_stop_service_from_monit.sh.erb'
  owner 'root'
  group 'root'
  mode '0744'
  variables timeout_sec: node['monit_wrapper']['start_stop_timeout_sec']
end
