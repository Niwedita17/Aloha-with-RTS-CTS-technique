@echo off
setlocal enableextensions enabledelayedexpansion

rem # Usage:
rem #   unet [-cp <classpath-entry>]... [<jvm-options>]... sim <sim-script>
rem #   unet [-cp <classpath-entry>]... [<jvm-options>]... sh [<hostname>] [<container-name>]
rem #   unet [-cp <classpath-entry>]... [<jvm-options>]... csh [<hostname>] [<container-name>]
rem #   unet [-cp <classpath-entry>]... [<jvm-options>]... <script> [<args>]...

rem # figure out home directory
if not "%UNET_HOME%"=="" goto cont0
set _f=%0
for %%F in ("%_f%\..") do set UNET_HOME=%%~dpF
:cont0

rem # figure out lib directory
if exist %UNET_HOME%\lib (
	set UNET_LIB=%UNET_HOME%\lib
) else (
	set UNET_LIB=%UNET_HOME%\app
)

if exist %UNET_HOME%\runtime\bin (
  set JAVA=%UNET_HOME%\runtime\bin\java
) else ( 
  set JAVA=java 
)

rem # compose classpath
set CP=
for %%a in (%UNET_LIB%\*.jar) do set CP=!CP!;%%a
set CP=.!CP!

rem # process command line options
set OPTS=
:loop1
if "%~1"=="" goto cont1
set _S=%~1
set _S=%_S:~0,1%
if not "%_S%"=="-" goto cont1
if "%~1"=="-cp" (
  set CP=%~2;%CP%
  shift
) else (
  set OPTS=%~1 %OPTS%
)
shift & goto loop1
:cont1

rem # process command
set ARGS=
set SIM=
set CMD=%1
shift
if "%CMD%"=="" goto usage
if "%CMD%"=="sim" goto sim
if "%CMD%"=="sh" goto sh
if "%CMD%"=="csh" goto csh
for %%F in ("%CMD%") do set _d=%%~dpF
set CP=%_d%;%CP%
:loop2
if "%~1"=="" goto cont2
set ARGS=%ARGS% -arg:%~1
shift & goto loop2
:cont2
goto run

:sim
if "%~1"=="" goto usage
set CMD=cls://org.arl.unet.sim.initrc %~1
set _d=%~1
for %%F in ("%_d%") do set _d=%%~dpF
set CP=%_d%;%CP%
set SIM=1
goto run

:sh
set CMD=cls://org.arl.unet.shell.initrc
set OPTS=%OPTS% -Dfjage.ip=%~1 -Dfjage.name=%~2 -Dfjage.gui=true
goto run

:csh
set CMD=cls://org.arl.unet.shell.initrc
set OPTS=%OPTS% -Dfjage.ip=%~1 -Dfjage.name=%~2 -Dfjage.gui=false
goto run

rem # run unet
:run
if "%SIM%"=="" (
	if exist %CMD% (
		set /p SHEBANG=<%CMD%
		echo(!SHEBANG! | findstr /i /r "^\/\/\![ ]*sim" > nul
		if !errorlevel! equ 0 (
			for %%F in ("%CMD%") do set CP=%%~dpF;%CP%
			set CMD=cls://org.arl.unet.sim.initrc %CMD%
		)
	)
)

if not exist logs mkdir logs
%JAVA% -cp "%CP%" -Dhome="%UNET_HOME:~0,-1%" %OPTS% org.arl.fjage.shell.GroovyBoot -nocolor %ARGS% %CMD%
goto eof

:usage
echo.
echo Usage:
echo   %_f% sim {sim-script}                       # simulation
echo   %_f% sh [{hostname}] [{container-name}]     # GUI shell
echo   %_f% csh [{hostname}] [{container-name}]    # console shell
echo   %_f% {script}                               # run script
echo.

:eof
