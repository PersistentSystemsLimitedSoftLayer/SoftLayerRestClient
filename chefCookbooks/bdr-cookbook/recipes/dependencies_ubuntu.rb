execute "apt-get update" do
	command "apt-get update"
end


node['postgresql']['prerequisites']['ubuntu'].each do |package_name|
  package package_name do
    action :install
  end
end

package "whois" do
  action :nothing
end.run_action(:install)