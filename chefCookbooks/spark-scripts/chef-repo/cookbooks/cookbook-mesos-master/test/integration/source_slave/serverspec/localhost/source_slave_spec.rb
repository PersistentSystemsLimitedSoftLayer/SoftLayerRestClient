# encoding: utf-8

require 'spec_helper'

describe 'mesos::slave' do
  it_behaves_like 'an installation from source'

  it_behaves_like 'a configuration of a slave node'
end
