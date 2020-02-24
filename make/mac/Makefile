# vim:noet
#export JAVA_HOME=/Library/Java/JavaVirtualMachines/adoptopenjdk-13.jdk/Contents/Home
#source /opt/intel/mkl/bin/mklvars.sh  intel64
#source /opt/intel/compilers_and_libraries/mac/bin/compilervars.sh intel64
SUFFIX = dylib

CC = icc
CFLAGS = -I ${JAVA_HOME}/include/darwin -c -I ${JAVA_HOME}/include -I  /Library/Developer/CommandLineTools/SDKs/MacOSX10.15.sdk/usr/include -std=c99 \
                  -fPIC -fstack-protector-all
LDFLAGS = -Wall -ldl -liomp5 -shared -mkl=parallel -qopenmp \
                   -static-intel -no-intel-extensions

SOURCES_DIR = ../../src/main/c
OBJECTS_DIR = ../target/obj
EXECUTABLE  = ../target/libjmkl.$(SUFFIX)

SOURCES=$(SOURCES_DIR)/mkl4j.c
OBJECTS=$(patsubst $(SOURCES_DIR)/%.c, $(OBJECTS_DIR)/%.o, $(SOURCES))

all: $(EXECUTABLE)

$(EXECUTABLE): $(OBJECTS)
		$(CC) $(LDFLAGS) $(OBJECTS) -lm -o $@
		rm -rf $(OBJECTS_DIR)


$(OBJECTS): $(SOURCES)
		mkdir -p $(OBJECTS_DIR)
		$(CC) -I$(SOURCES_DIR)  $(CFLAGS)  $< -o $@

clean:
		rm -rf $(OBJECTS_DIR) $(EXECUTABLE)