# Project-Sangraha

Development process
====================
a) docker pull ashsat/activator-1.3.10
b) docker run -it  --name sangraha -v ~/workspace/google-cloud/Project-Sangraha/:/home/app --volumes-from activator_cache -p 8888:8888 -p 9000:9000 ashsat/activator-1.3.10 
c) docker run -it --name activator_cache -v /root/.ivy2 -v /root/.sbt busybox /bin/sh

