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

# Start the given Monit service.
action :start do  # ~FC017
  # We disable FC017 because notifying_action_wrapper takes care of notifications.
  service_name = new_resource.name
  notifying_action_wrapper do
    if monit_service_running?(service_name)
      Chef::Log.info("Service #{service_name} is already running, skipping start action")
    elsif monit_service_registered?(service_name)
      wait_for_host_port(new_resource.wait_for_host_port)
      bash "monit-start-#{new_resource.name}" do
        user 'root'
        code "monit start #{new_resource.name}"
        not_if { monit_service_running?(new_resource.name, :verbose => true) }
        action :run
      end
    elsif new_resource.fallback_to_regular_service
      wait_for_host_port(new_resource.wait_for_host_port)
      service service_name do
        action :start
      end
    else
      raise "Monit does not know about #{service_name} and fallback_to_regular_service is disabled"
    end
  end
end

# Stop the given Monit service.
action :stop do  # ~FC017
  # We disable FC017 because notifying_action_wrapper takes care of notifications.
  service_name = new_resource.name
  notifying_action_wrapper do
    if monit_service_registered?(service_name)
      bash "monit-stop-#{service_name}" do
        user 'root'
        code "monit stop #{service_name}"
        action :run
      end
    elsif new_resource.fallback_to_regular_service
      service service_name do
        action :stop
      end
    else
      raise "Monit does not know about #{service_name} and fallback_to_regular_service is disabled"
    end
  end
end

# Restart the given Monit service.
action :restart do  # ~FC017
  # We disable FC017 because notifying_action_wrapper takes care of notifications.
  if monit_service_running?(new_resource.name, :verbose => true)
    command = 'restart'
  else
    command = 'start'
  end
  wait_for_host_port(new_resource.wait_for_host_port)
  notifying_action_wrapper do
    bash "monit-#{command}-#{new_resource.name}" do
      user 'root'
      code "monit #{command} #{new_resource.name}"
      action :run
    end
  end
end
