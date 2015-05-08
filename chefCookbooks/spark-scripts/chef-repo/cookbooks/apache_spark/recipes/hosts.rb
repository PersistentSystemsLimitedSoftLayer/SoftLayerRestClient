#Write the hostname of master to slave and vice versa
bash "append_to_hosts" do
   user "root"
   code <<-EOF
      echo "#{node['spark']['ip']}	#{node['spark']['hostname']}	#{node['spark']['nodename']}" >> /etc/hosts
   EOF
end