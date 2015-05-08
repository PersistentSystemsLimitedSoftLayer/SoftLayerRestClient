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

include Chef::MonitWrapper::Wait
require 'chef/mixin/shell_out'

# Creates a configuration file for a new Monit-monitored service.
action :create do  # ~FC017
  # We disable FC017 (LWRP does not notify when updated) because we are using
  # notifying_action_wrapper that notifies all subscribers when the template is regenerated.
  template_resource_name = "#{node['monit']['includes_dir']}/#{new_resource.name}.monitrc"
  notifying_action_wrapper(
    allow_updates_from: "template[#{template_resource_name}]",
    verbose: true
  ) do

    wait_for_host_port(new_resource.wait_for_host_port)

    variables = (new_resource.variables || {}).to_hash.clone

    variables[:service_name] = new_resource.name
    variables[:pid_file] = new_resource.pid_file if new_resource.pid_file
    variables[:pattern] = new_resource.pattern if new_resource.pattern
    if new_resource.java_class
      variables[:pattern] = "^[^ ]*java .* #{new_resource.java_class}($| .*)"
    end
    if [new_resource.pid_file,
        new_resource.pattern,
        new_resource.java_class].compact.size > 1
      raise 'No more than one of pid file, pattern, or Java class can be specified. ' +
            "Variables: #{variables.inspect}"
    end

    Chef::Log.info(
      "Creating Monit configuration file #{template_resource_name} " \
      "for service #{new_resource.name}"
    )
    template template_resource_name do
      owner 'root'
      group 'root'
      mode  '0644'
      source new_resource.template_source || 'service_wrapper.monitrc.erb'
      cookbook new_resource.template_cookbook || 'monit_wrapper'
      variables variables
      action :create
    end

    Chef::Log.info("Reloading Monit configuration and waiting for service #{new_resource.name}")
    monit_wrapper_reload_and_wait new_resource.name
  end
end

action :delete do
  conf_file = "#{node['monit']['includes_dir']}/#{new_resource.name}.monitrc"
  if ::File.exists?(conf_file)
    file conf_file do
      action :delete
    end

    bash "monit-reload-after-removing-#{new_resource.name}" do
      code 'monit reload'
    end

    new_resource.updated_by_last_action(true)
  end
end
