@echo off

IF "%JAVA_HOME%" == "" (
    	echo "Add meg a JAVA_HOME path-t (pl: c:\Program Files\Java\jdk1.6)!"
    	set /p JAVA_HOME=
)

set PATH=%JAVA_HOME%\bin;%PATH%

java view/MainWindow