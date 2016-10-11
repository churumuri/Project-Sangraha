# Project-Sangraha

**drishit**
-----------

Development process
===================
a) docker pull ashsat/activator-1.3.10
b) docker run -it --name activator_cache -v /root/.ivy2 -v /root/.sbt busybox /bin/sh
c) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/drishti/:/home/app \
                   --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10 
d) got to http://localhost:8888 on your browser and u can use the activator
e) This can be used by debugging and running using the activator UI


Create Docker Image
===================
a) docker pull ashsat/activator-1.3.10
b) docker run -it --name activator_cache -v /root/.ivy2 -v /root/.sbt busybox /bin/sh
c) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/drishti:/home/app \
                   --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10 docker:stage
d) cd target/docker/stage
e) docker build -t ashsat/dristi .
f) docker run -p 9000:9000 ashsat/dristi


**smara**
=========
