@echo off
echo ===================================
echo 🚀 Checking Docker Installation
echo ===================================
docker --version >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo 🚨 Docker is not installed! Please install Docker first from https://www.docker.com/get-started.
    pause
    exit /b
)

echo ===================================
echo 🚀 Checking if Docker is Running
echo ===================================
docker info >nul 2>&1
IF %ERRORLEVEL% NEQ 0 (
    echo 🚨 Docker is not running! Please start Docker and try again.
    pause
    exit /b
)

echo ===================================
echo 🚀 Starting Selenium Grid in Docker
echo ===================================
docker-compose up -d

:: Wait for Selenium Grid to be up
echo Waiting for Selenium Grid to start...
:loop
for /f "tokens=2 delims=: " %%a in ('curl -s http://localhost:4444/status ^| findstr "ready"') do set status=%%a
if "%status%"=="true," (
    echo ✅ Selenium Grid is up and running!
    goto runTests
)
echo ⏳ Waiting for Grid to be ready...
timeout /t 5 /nobreak >nul
goto loop

:runTests
echo ===================================
echo 🛠 Running Selenium TestNG Tests
echo ===================================
mvn clean test

:: Optionally stop Selenium Grid after tests
echo ===================================
echo 🛑 Stopping Selenium Grid
echo ===================================
docker-compose down

echo ✅ All tests executed successfully!
pause
