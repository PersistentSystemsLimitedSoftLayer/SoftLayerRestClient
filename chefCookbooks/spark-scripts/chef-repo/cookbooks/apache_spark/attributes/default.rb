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

# Installation mode. This could be "package" or "tarball".
default['apache_spark']['install_mode'] = 'tarball'

default['apache_spark']['download_url'] =
  'http://d3kbcqa49mib13.cloudfront.net/spark-1.2.1-bin-cdh4.tgz'
# The SHA256 checksum of the downloaded tarball.
default['apache_spark']['checksum'] = '8e618cf67b3090acf87119a96e5e2e20e51f6266c44468844c185122b492b454'

# These options are used with the "package" installation mode.
default['apache_spark']['pkg_name'] = 'spark'
default['apache_spark']['pkg_version'] = '1.2.1'

default['apache_spark']['install_dir'] = '/usr/share/spark'
default['apache_spark']['install_base_dir'] = '/opt/spark'
default['apache_spark']['user'] = 'root'
default['apache_spark']['group'] = 'root'
default['apache_spark']['user_password'] = "" #Should be supplied in role

default['apache_spark']['standalone']['master_host'] = nil

# The IP address for Spark master to bind to. This should be set to an IP address or host name
# accessible by the entire cluster.
default['apache_spark']['standalone']['master_bind_ip'] = '158.85.156.38'

# This is the host clients will try to connect to.
default['apache_spark']['standalone']['master_host'] = '158.85.156.38'

default['apache_spark']['standalone']['master_port'] = 7077
default['apache_spark']['standalone']['master_webui_port'] = 8080

default['apache_spark']['standalone']['worker_bind_ip'] = '0.0.0.0'
default['apache_spark']['standalone']['worker_webui_port'] = 8081

default['apache_spark']['standalone']['worker_ips'] =  ["169.55.14.180"]

default['apache_spark']['standalone']['job_dir_days_retained'] = 7
default['apache_spark']['standalone']['job_dir_num_retained'] = 16
default['apache_spark']['standalone']['worker_dir_cleanup_log'] = '/var/log/clean_spark_worker_dir.log'

# Run this many worker threads per worker by default. If nil, the # of CPU cores (hyper-threads) on
# the host.
default['apache_spark']['standalone']['worker_cores'] = nil

# Spark worker's directory for storing logs and jars.
# (/var/run is mounted on tmpfs and has very little capacity on some systems.)
default['apache_spark']['standalone']['worker_work_dir'] = '/var/spark-standalone-worker'

# Total memory a worker will report as available, MBs.
default['apache_spark']['standalone']['worker_memory_mb'] = 1024

# Default amount of memory allocated for a Sparky application per executor.
default['apache_spark']['standalone']['default_executor_mem_mb'] = 256

default['apache_spark']['standalone']['log_dir'] = '/var/log/spark-standalone'
default['apache_spark']['standalone']['daemon_root_logger'] = 'INFO,DRFA'
default['apache_spark']['standalone']['max_num_open_files'] = 65536

# Java debugging for Spark
default['apache_spark']['standalone']['java_debug_enabled'] = true
default['apache_spark']['standalone']['default_debug_port'] = 23000
default['apache_spark']['standalone']['master_debug_port'] = 23010
default['apache_spark']['standalone']['worker_debug_port'] = 23020
default['apache_spark']['standalone']['standalone_executor_debug_port'] = 23030

# Extra classpath items for both driver and executor processesses of Spark apps.
default['apache_spark']['common_extra_classpath_items'] = []

# Default configuration options for Spark.
default['apache_spark']['conf']['spark.akka.frameSize'] = 100

# Uniformly spread the load across all Spark worker nodes.
default['apache_spark']['conf']['spark.deploy.spreadOut'] = true
default['apache_spark']['conf']['spark.executor.extraLibraryPath '] = '/usr/lib/hadoop/lib/native'

#Write master's hostname to /etc/hosts file.
default['spark']['ip']=""
default['spark']['hostname']=""
default['spark']['nodename']=""


