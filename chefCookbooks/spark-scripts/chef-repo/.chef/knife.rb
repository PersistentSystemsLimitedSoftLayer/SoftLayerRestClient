# See https://docs.chef.io/config_rb_knife.html for more information on knife configuration options

current_dir = File.dirname(__FILE__)
log_level                :info
log_location             STDOUT
node_name                "prajakta_bhatekar"
client_key               "#{current_dir}/prajakta_bhatekar.pem"
validation_client_name   "prajakta_bhatekar-validator"
validation_key           "#{current_dir}/prajakta_bhatekar-validator.pem"
chef_server_url          "https://api.opscode.com/organizations/prajakta_bhatekar"
syntax_check_cache_path  "#{ENV['HOME']}/.chef/syntaxcache"
cookbook_path            ["#{current_dir}/../cookbooks"]
knife[:softlayer_username] = "psl_kantar"
knife[:softlayer_api_key]  = "76a3569f534824995097d9fc682026fab1bcabc256f2add5cab5d909bdc18429"

