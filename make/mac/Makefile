# vim:noet
include ../Makefile.head

SUFFIX = dylib

ifeq ($(PLATFORM), MACOS)
	override CC = icc
	override CFLAGS = -I ${JAVA_HOME}/include/darwin -c -I ${JAVA_HOME}/include -I  /Library/Developer/CommandLineTools/SDKs/MacOSX10.15.sdk/usr/include -std=c99 \
	                  -fPIC -fstack-protector-all
	override LDFLAGS = -Wall -ldl -liomp5 -shared -mkl=parallel -qopenmp \
	                   -static-intel -no-intel-extensions
	include ../Makefile.tail
endif
