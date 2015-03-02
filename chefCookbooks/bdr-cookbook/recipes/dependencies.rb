case node.platform
when "redhat", "centos"
  include_recipe "bdr-cookbook::dependencies_redhat"
when "ubuntu"
  include_recipe "bdr-cookbook::dependencies_ubuntu"
end