@echo off
chcp 65001
echo ========================================
echo 无人机管理系统启动脚本
echo ========================================
echo.
echo 正在启动应用程序...
echo 请稍候，首次启动可能需要较长时间...
echo.

cd /d %~dp0
apache-maven-3.8.8\bin\mvn.cmd spring-boot:run

pause
