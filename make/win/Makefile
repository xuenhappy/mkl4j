#"C:\Program Files (x86)\Microsoft Visual Studio 14.0\VC\vcvarsall.bat" amd64
#"C:\Program Files (x86)\IntelSWTools\parallel_studio_xe_2020.0.075\bin\psxevars.bat" intel64
#"C:\Program Files (x86)\IntelSWTools\compilers_and_libraries\windows\mkl\bin\mklvars.bat" intel64
#nmake /f Makefile

CC = icl.exe
LD = xilink.exe

JAVA_HOME= C:\Program Files\Java\jdk-12.0.2
CFLAGS  = /EHsc /LD /I $(JAVA_HOME)\include /I $(JAVA_HOME)\include\win32
LDFLAGS = /DLL mkl_intel_lp64.lib mkl_intel_thread.lib mkl_core.lib libiomp5md.lib

SOURCES_DIR = ..\..\src\main\c
OBJECTS_DIR = ..\target\obj
EXECUTABLE  =  ..\target\libjmkl.dll

SOURCES = $(SOURCES_DIR)
OBJECTS = $(patsubst $(SOURCES_DIR)\\%.c, $(OBJECTS_DIR)\\%.obj, $(SOURCES))

all:
	@if not exist $(OBJECTS_DIR) md $(OBJECTS_DIR)
	$(CC) $(CFLAGS) -IC:$(SOURCES_DIR) /FoC:$(strip $(OBJECTS)) -c $(SOURCES)
	$(LD) $(LDFLAGS) /out:$(EXECUTABLE) $(OBJECTS)
	del /f /s /q "$(OBJECTS_DIR)\*.*"
	rd /s /q  "$(OBJECTS_DIR)"
