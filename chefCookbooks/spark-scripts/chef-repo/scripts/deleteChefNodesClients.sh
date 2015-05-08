#!/bin/bash

knife bootstrap $2 -x root -i ~/.ssh/id_rsa -N $1 -r 'recipe[apache_spark::remove-client]' 
knife node delete $1 -y  #deletes chef node
knife client delete $1	-y #deletes chef client
ssh-keygen -R $2	#removes entry from known hosts file