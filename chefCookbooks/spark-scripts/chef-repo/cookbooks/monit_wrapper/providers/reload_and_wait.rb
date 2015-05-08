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

# Reload Monit configuration and wait for it to become aware of the given service.
action :reload_and_wait do
  script 'monit-reload' do
    interpreter 'bash'
    user 'root'
    code 'monit reload'
  end

  ruby_block "wait-for-monit-reload-#{new_resource.name}" do
    block do
      require 'waitutil'
      WaitUtil.wait_for_condition(
        "#{new_resource.name} to show up in the output of 'monit status'",
        :delay_sec => 0.2,
        :timeout_sec => 30
      ) do
        p = shell_out('/usr/bin/monit status')
        if p.exitstatus != 0
          Chef::Log.fatal("Command '#{p.command}' failed\n" +
                          "stdout:\n#{p.stdout}\nstderr:\n#{p.stderr}")
          raise
        end
        [p.stdout.split("\n").include?("Process '#{new_resource.name}'"),
         "stdout:\n#{p.stdout}\nstderr:#{p.stderr}"]
      end
    end
  end

  new_resource.updated_by_last_action(true)
end
