# vim:noet
#export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
#source  compilervars.sh -arch intel64 -platform linux
include ../Makefile.head

CFLAGS = -I ${JAVA_HOME}/include/linux 
SUFFIX = so

all:
ifeq ($(PLATFORM), LINUX)
include ../Makefile.tail
endif
