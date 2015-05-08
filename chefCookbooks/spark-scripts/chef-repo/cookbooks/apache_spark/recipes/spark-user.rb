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

spark_user = node['apache_spark']['user']
spark_group = node['apache_spark']['group']

group spark_group


package "openssl" do
  action :nothing
end.run_action(:install)
spark_user_password = node['apache_spark']['user_password']
spark_password = %x( openssl passwd -1 #{spark_user_password})
spark_password = spark_password.chop

user spark_user do
   gid spark_group
   shell "/bin/bash"
   home "/home/#{spark_user}"
   password spark_password
   supports :manage_home => true
   action :create
end

directory "/home/#{spark_user}" do
  owner spark_user
  group spark_group
  mode 0755
end
