# Project-Sangraha

**drishit**
-----------

Development process
===================
a) docker pull ashsat/activator-1.3.10
b) One time only create directories /tmp/.ivy2 and /tmp/.sbt
c) docker run -it --name activator_cache -v /tmp/.ivy2:/root/.ivy2 -v /tmp/.sbt:/root/.sbt busybox /bin/sh
d) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/drishti/:/home/app \
                   --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10 
e) got to http://localhost:8888 on your browser and u can use the activator
f) This can be used by debugging and running using the activator UI


Create Docker Image
===================
a) docker pull ashsat/activator-1.3.10
b) One time only create directories /tmp/.ivy2 and /tmp/.sbt
c) docker run -it --name activator_cache -v /tmp/.ivy2:/root/.ivy2 -v /tmp/.sbt:/root/.sbt busybox /bin/sh
d) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/drishti:/home/app \
                   --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10 docker:stage
e) cd target/docker/stage
f) docker build -t ashsat/dristi .
g) docker run -p 9000:9000 ashsat/dristi


**smara**
=========
a) docker pull ashsat/activator-1.3.10
b) One time only create directories /tmp/.ivy2 and /tmp/.sbt
c) docker run -it --name activator_cache -v /tmp/.ivy2:/root/.ivy2 -v /tmp/.sbt:/root/.sbt busybox /bin/sh
d) One time only create directory /tmp/smara
e) docker run --name smaraDb  -e MYSQL_ROOT_PASSWORD="G0od0ldPa55w0rd" -e MYSQL_DATABASE="smara"  -v /tmp/smara:/var/lib/mysql mysql:latest
f) One time only connect to the db and create the tables and seed values -- TBD 
g) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/smara/:/home/app \
                   --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10

Using activator UI of compiling and running
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
d) got to http://localhost:8888 on your browser and u can use the activator
e) This can be used by debugging and running using the activator UI

Using activator promot
~~~~~~~~~~~~~~~~~~~~~~ 
d) docker ps
e) note the CONTAINER ID for the activator container (cid)
f) docker exec -it cid bash
g) the above command will give a bash prompt in the docker container
h) set the activator path export PATH=/opt/activator/bin/$PATH
i) activator
