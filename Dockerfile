FROM dockerfile/java:oracle-java8
MAINTAINER Martin Chalupa <chalimartines@gmail.com>

ADD build/distributions/twitter-service-1.0.tar /usr/local

CMD /usr/local/twitter-service-1.0/bin/twitter-service