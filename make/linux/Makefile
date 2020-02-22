# vim:noet
#export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64
#source  compilervars.sh -arch intel64 -platform linux
CC	=	icc
SUFFIX  = so
CFLAGS  = -c ${JAVA_HOME}/include/linux -I ${JAVA_HOME}/include -I/opt/intel/mkl/include -std=c99 \
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


$(OBJECTS): $(SOURCES)
		mkdir -p $(OBJECTS_DIR)
		$(CC) -I$(SOURCES_DIR)  $(CFLAGS)  $< -o $@

clean:
		rm -rf $(OBJECTS_DIR) $(EXECUTABLE)
