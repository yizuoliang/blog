# 使用批处理方式配置Java环境

## 一、需求点

​	1.公司的现场维护人员配置java环境不熟练，容易配错；

​	2.项目中使用到elasticsearch（2.3.2版本），一定要配置JAVA_HOME;

​	2.项目也是必须要在jdk8以上版本运行；

## 二、解决方案

​	为解决以上问题点，使用批处理来配置环境变量。

​	大致思路分三步：

​		1.安装好JDK；

​		2.判断现在服务器上安装的jdk版本，如果是8以上版本则不配置；

​		3.配置JAVA_HOME和path。

​	注意点：

​		批处理文件需要与jdk放在同级目录，JAVA_HOME /M "%bbd%\jdk1.8.0_144"，这个路径需要手动修改成自己JDK的路径。

```
@echo off
REM 检查JDK环境和NODEJS环境
pushd %~dp0
cd..
set bjava=0
set "bbd=%cd%"
java -version>nul 2>nul
if /i not %errorlevel% == 0 (
set bjava=1
goto ENDJAVA
) else GOTO CHECKJAVA

:CHECKJAVA
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVAVER=%%g
)
set JAVAVER=%JAVAVER:"=%
for /f "delims=. tokens=1-3" %%v in ("%JAVAVER%") do (
    set CURRENTV=%%w
)

if %CURRENTV% LSS 8 (set bjava=1)

:ENDJAVA
if %bjava% equ 1 ( 
	setx JAVA_HOME /M "%bbd%\jdk1.8.0_144"
	setx Path /M "%%JAVA_HOME%%\bin;%PATH%"
)

pause
echo **********************************************
echo             jdk环境已配置好,请按任意键继续!
pause
```

