#"C:\Program Files (x86)\Microsoft Visual Studio 14.0\VC\vcvarsall.bat" amd64
#"C:\Program Files (x86)\IntelSWTools\parallel_studio_xe_2020.0.075\bin\psxevars.bat" intel64
#"C:\Program Files (x86)\IntelSWTools\compilers_and_libraries\windows\mkl\bin\mklvars.bat" intel64
#nmake /f Makefile

CC = icl.exe

JAVA_HOME= C:\Program Files\Java\jdk-12.0.2
CFLAGS  =/I"$(JAVA_HOME)\include" /I"$(JAVA_HOME)\include\win32"
LDFLAGS = mkl_intel_lp64.lib mkl_intel_thread.lib mkl_core.lib libiomp5md.lib 

SOURCES_DIR = ..\..\src\main\c
OBJECTS_DIR = ..\target\obj
EXECUTABLE  =  ..\target\libjmkl.dll

SOURCES = $(SOURCES_DIR)\mkl4j.c
OBJECTS =..\target\obj\mkl4j

all:
	@if not exist $(OBJECTS_DIR) md $(OBJECTS_DIR)
	del /f /s /q "$(EXECUTABLE)" 
	$(CC)  $(CFLAGS) /I"$(SOURCES_DIR)"  "$(SOURCES)"  /c /nologo /Fo"$(OBJECTS)" 
	$(CC) /LD  "$(OBJECTS).obj"  /openmp /Qmkl:parallel /link /nologo /dll   $(LDFLAGS) /out:"$(EXECUTABLE)" 
	del /f /s /q "$(OBJECTS_DIR)\*.*"
	rd /s /q  "$(OBJECTS_DIR)"
	del /f /s /q "mkl*"
