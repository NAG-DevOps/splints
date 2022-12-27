FROM anapsix/alpine-java:jdk8

ENV HOME /home/netbeans

RUN adduser -D netbeans && \
    apk update && \
    apk add libxext libxtst libxrender && \
    rm -rf /tmp/* && \
    rm -rf /var/cache/apk/*

RUN mkdir -m 700 /data && \
    mkdir -m 700 $HOME/.netbeans && \
    mkdir -m 700 $HOME/NetBeansProjects && \
    chown -R netbeans:netbeans /data $HOME/.netbeans $HOME/NetBeansProjects

VOLUME /data
VOLUME ~/.netbeans
VOLUME ~/NetBeansProjects

USER netbeans

RUN wget http://download.netbeans.org/netbeans/8.2/final/zip/netbeans-8.2-2016093
00101-javaee.zip -O ~/netbeans.zip -q && \
    unzip ~/netbeans.zip -q -d ~ && \
    rm ~/netbeans.zip

WORKDIR /data
CMD ~/netbeans/bin/netbeans