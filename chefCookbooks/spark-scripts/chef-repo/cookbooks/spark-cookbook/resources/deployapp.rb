actions :deploy
default_action :deploy
attribute :app_class           , :kind_of => String
attribute :master          , :kind_of => String
attribute :executor_memory , :kind_of => String
attribute :total_executor_cores , :kind_of => Fixnum
attribute :jar_file             , :kind_of => String
attribute :application_arguments , :kind_of => String
