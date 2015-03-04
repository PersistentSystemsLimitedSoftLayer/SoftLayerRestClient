bdr-cookbook 
=====================
This chef cookbook is used to install and configure <a href="http://www.postgresql.org/" title="BDR User Guide">Postgresql</a> and <a href="http://2ndquadrant.com/en/resources/bdr/" title="BDR User Guide">Bi-directional replication (BDR)</a> on a node

Requirements
------------
#### operating systems
bdr-cookbook supports Ubuntu, RedHat and Centos

Attributes
----------
#### bdr-cookbook::default

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># Packages
required for installing <span class=SpellE>postgresql</span> on Ubuntu<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['prerequisites']['<span
class=SpellE>ubuntu</span>'] = %w(<span class=SpellE>git</span> mc joe <span
class=SpellE>whois</span> curl <span class=SpellE>libreadline-dev</span>
zlib1g-dev bison flex make <span class=SpellE>libssl-dev</span> vim)<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># Array of
entries to be inserted in <span class=SpellE>pg_<span class=GramE>hba.conf</span></span><span
class=GramE><span style='mso-spacerun:yes'>  </span>http</span>://www.postgresql.org/docs/9.4/static/auth-pg-hba-conf.html<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>hba</span>'] = &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># Directory paths
used by <span class=SpellE>Postgresql</span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['home']
= &quot;/home/<span class=SpellE>postgres</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['installation']
= &quot;#{node['<span class=SpellE>postgresql</span>']['directory']['home']}/<span
class=SpellE>bdr</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['bin']
= &quot;#{node['<span class=SpellE>postgresql</span>']['directory']['installation']}/bin&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['<span
class=SpellE>git_clone</span>'] = &quot;#{node['<span class=SpellE>postgresql</span>']['directory']['home']}/2ndquadrant_bdr&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['<span
class=SpellE>contrib</span>/<span class=SpellE>btree_gist</span>'] =
&quot;#{node['postgresql']['directory']['git_clone']}/contrib/btree_gist&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['directory']['<span
class=SpellE>contrib</span>/<span class=SpellE>bdr</span>'] = &quot;#{node['<span
class=SpellE>postgresql</span>']['directory']['<span class=SpellE>git_clone</span>']}/<span
class=SpellE>contrib</span>/<span class=SpellE>bdr</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># <span
class=SpellE>Git</span> repository and revision used for <span class=SpellE>Postgresql+BDR</span>
install<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>git</span>']['repository'] = &quot;<span class=SpellE>git</span>://git.postgresql.org/<span
class=SpellE>git</span>/2ndquadrant_bdr.git&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>git</span>']['revision'] = &quot;<span class=SpellE>bdr</span>/0.7.1&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># Parameters to
be set in <span class=SpellE>postgresql.conf</span> <o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># <span
class=GramE>For</span> detailed explanation of parameters refer
http://www.postgresql.org/docs/9.4/static/runtime-config.html<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># For BDR
configuration refer https://wiki.postgresql.org/wiki/BDR_Administration<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>data_directory</span>'] =
&quot;/opt/<span class=SpellE>pgdata</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>hba_file</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ident_file</span>']=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>external_pid_file</span>']=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>listen_addresses</span>']=<span
style='mso-tab-count:1'> </span>&quot;*&quot;<span style='mso-spacerun:yes'> 
</span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['port'] = 5432<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_connections</span>']= 100 <o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['superuser_reserved_connections']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>unix_socket_directories</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>unix_socket_group</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>unix_socket_permissions</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['bonjour']= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bonjour_name</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>authentication_timeout</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl</span>']= &quot;off&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_ciphers</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_prefer_server_ciphers</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_ecdh_curve</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_renegotiation_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_cert_file</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_key_file</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_ca_file</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>ssl_crl_file</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>password_encryption</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>db_user_namespace</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>krb_server_keyfile</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>krb_caseins_users</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>tcp_keepalives_idle</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>tcp_keepalives_interval</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>tcp_keepalives_count</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>shared_buffers</span>']=
&quot;128MB&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>huge_pages</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>temp_buffers</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_prepared_transactions</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>work_mem</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>maintenance_work_mem</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_work_mem</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_stack_depth</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>dynamic_shared_memory_type</span>']=
&quot;<span class=SpellE>posix</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>temp_file_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_files_per_process</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>shared_preload_libraries</span>']=
&quot;<span class=SpellE>bdr</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_cost_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_cost_page_hit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_cost_page_miss</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_cost_page_dirty</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_cost_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bgwriter_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bgwriter_lru_maxpages</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bgwriter_lru_multiplier</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>effective_io_concurrency</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_worker_processes</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_level</span>']=
&quot;logical&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>fsync</span>']= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>synchronous_commit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_sync_method</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>full_page_writes</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_log_hints</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_buffers</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_writer_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>commit_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>commit_siblings</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>checkpoint_segments</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>checkpoint_timeout</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>checkpoint_completion_target</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>checkpoint_warning</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>archive_mode</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>archive_command</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>archive_timeout</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_wal_senders</span>']= 10<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_keep_segments</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_sender_timeout</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_replication_slots</span>']=
8<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_commit_timestamp</span>']=
&quot;on&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>synchronous_standby_names</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_defer_cleanup_age</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>hot_standby</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_standby_archive_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_standby_streaming_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_receiver_status_interval</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>hot_standby_feedback</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>wal_receiver_timeout</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_bitmapscan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_hashagg</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_hashjoin</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_indexscan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_indexonlyscan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_material</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_mergejoin</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_nestloop</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_seqscan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_sort</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>enable_tidscan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>seq_page_cost</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>random_page_cost</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>cpu_tuple_cost</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>cpu_index_tuple_cost</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>cpu_operator_cost</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>effective_cache_size</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo</span>']= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_threshold</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_effort</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_pool_size</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_generations</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_selection_bias</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>geqo_seed</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_statistics_target</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>constraint_exclusion</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>cursor_tuple_fraction</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>from_collapse_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>join_collapse_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_destination</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>logging_collector</span>']=
&quot;on&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_filename</span>']= &quot;<span
class=SpellE>postgresql</span>-%Y-%m-%d_%H%M%S.log&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_file_mode</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_truncate_on_rotation</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_rotation_age</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_rotation_size</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>syslog_facility</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>syslog_ident</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>event_source</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>client_min_messages</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_min_messages</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_min_error_statement</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_min_duration_statement</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>debug_print_parse</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>debug_print_rewritten</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>debug_print_plan</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>debug_pretty_print</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_checkpoints</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_connections</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_disconnections</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_duration</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_error_verbosity</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_hostname</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_line_prefix</span>']=
&quot;%m&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_lock_waits</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_statement</span>']=
&quot;all&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_temp_files</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_timezone</span>']=
&quot;US/Central&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_activities</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_counts</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_io_timing</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_functions</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>track_activity_query_size</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>update_process_title</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>stats_temp_directory</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_parser_stats</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_planner_stats</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_executor_stats</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_statement_stats</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>log_autovacuum_min_duration</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_max_workers</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_naptime</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_vacuum_threshold</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_analyze_threshold</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['autovacuum_vacuum_scale_factor']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['autovacuum_analyze_scale_factor']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_freeze_max_age</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['autovacuum_multixact_freeze_max_age']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_vacuum_cost_delay</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>autovacuum_vacuum_cost_limit</span>']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>search_path</span>']=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_tablespace</span>'] =
&quot;&quot;<span style='mso-tab-count:1'> </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>temp_tablespaces</span> ']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>check_function_bodies</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_transaction_isolation</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_transaction_read_only</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['default_transaction_deferrable']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>session_replication_role</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>statement_timeout</span>']
=&quot;&quot; <span style='mso-tab-count:3'>            </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lock_timeout</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_freeze_min_age</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>vacuum_freeze_table_age</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['vacuum_multixact_freeze_min_age']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['vacuum_multixact_freeze_table_age']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bytea_output</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>xmlbinary</span>']
=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>xmloption</span>']
=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>intervalstyle</span>']=&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>datestyle</span>'] = &quot;<span
class=SpellE>iso</span>, <span class=SpellE>mdy</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>timezone</span>'] =
&quot;US/Central&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>timezone_abbreviations</span>']
= &quot;&quot;<span style='mso-spacerun:yes'>    </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>extra_float_digits</span>'] =
&quot;&quot;<span style='mso-tab-count:1'> </span><span style='mso-tab-count:
2'>           </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>client_encoding</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lc_messages</span>'] = <span
style='mso-tab-count:1'>    </span>&quot;&quot;<span style='mso-tab-count:2'>         </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lc_monetary</span>'] =
&quot;&quot;<span style='mso-tab-count:3'>             </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lc_numeric</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lc_time</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_text_search_config</span>']
= &quot;<span class=SpellE>pg_catalog.english</span>&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>dynamic_library_path</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>local_preload_libraries</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>session_preload_libraries</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>deadlock_timeout</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>max_locks_per_transaction</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'postgresql']['config']['max_pred_locks_per_transaction']
= &quot;&quot;<span style='mso-tab-count:1'>     </span><o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>array_nulls</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>backslash_quote</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>default_with_oids</span> ']=
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>escape_string_warning</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>lo_compat_privileges</span>
']= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>quote_all_identifiers</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>sql_inheritance</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>standard_conforming_strings</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>synchronize_seqscans</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>transform_null_equals</span>']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>exit_on_error</span> '] =
&quot;&quot; <o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>restart_after_crash</span> ']
= &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>include_dir</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>include_if_exists</span> '] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['include'] = &quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bdr.connections</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>bdr.dsnlist</span>'] =
&quot;&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'><o:p>&nbsp;</o:p></span></p>

<p class=MsoNoSpacing><span style='font-family:"Courier New"'># <span
class=SpellE>Postgresql</span> <span class=SpellE>config</span> file paths<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>postgresql.conf</span>'] =
&quot;#{node['postgresql']['config']['data_directory']}/postgresql.conf&quot;<o:p></o:p></span></p>

<p class=MsoNoSpacing><span class=GramE><span style='font-family:"Courier New"'>default[</span></span><span
style='font-family:"Courier New"'>'<span class=SpellE>postgresql</span>']['<span
class=SpellE>pg_hba.conf</span>'] = &quot;#{node['<span class=SpellE>postgresql</span>']['<span
class=SpellE>config</span>']['<span class=SpellE>data_directory</span>']}/<span
class=SpellE>pg_hba.conf</span>&quot;<o:p></o:p></span></p>

Usage
-----
#### bdr-cookbook::default
1] Create a role having the runlist used below.


```json

{
  "name": "bdrserver",
  "description": "",
  "json_class": "Chef::Role",
  "default_attributes": {
  },
  "override_attributes": {
  },
  "chef_type": "role",
  "run_list": [
    "recipe[bdr-cookbook::dependencies]",
    "recipe[bdr-cookbook::bdr_installation]",
    "recipe[bdr-cookbook::postgres_configuration]",
    "recipe[bdr-cookbook::first_run]"
  ],
  "env_run_lists": {
  }

```


2] Create an environment and overide the attributes as shown below

```json
{
  "name": "bdr_environment",
  "description": "",
  "cookbook_versions": {
    "bdr-cookbook": "0.1.0"
  },
  "json_class": "Chef::Environment",
  "chef_type": "environment",
  "default_attributes": {

  },
  "override_attributes": {
    "postgresql": {
      "config": {
        "bdr.dsnlist": [
          dbname=thedb host=the-target-hostname user=bdr password=dbpassword port=5432"
        ],
        "bdr.connections": "connection1"
      },
      "hba": [
        "host  replication     postgres        IP/CIDR_of_node       md5"
      ]
    }
  }
}

```
3] Make use of the above created environment while running the role.

License and Authors
-------------------
Authors: TODO: List authors
