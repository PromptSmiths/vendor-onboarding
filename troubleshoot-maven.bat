@echo off
echo === Maven Troubleshooting Script ===
echo.

echo 1. Checking Java installation...
java -version
if errorlevel 1 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 11 or higher
    goto :end
)
echo.

echo 2. Checking JAVA_HOME...
if "%JAVA_HOME%"=="" (
    echo WARNING: JAVA_HOME is not set
) else (
    echo JAVA_HOME: %JAVA_HOME%
)
echo.

echo 3. Checking Maven wrapper...
if exist mvnw.cmd (
    echo Maven wrapper found: mvnw.cmd
) else (
    echo ERROR: Maven wrapper not found
    goto :end
)
echo.

echo 4. Testing Maven wrapper...
.\mvnw.cmd --version
if errorlevel 1 (
    echo ERROR: Maven wrapper failed to run
    goto :end
)
echo.

echo 5. Running clean compile...
.\mvnw.cmd clean compile -X
if errorlevel 1 (
    echo ERROR: Build failed
    goto :end
)

echo.
echo SUCCESS: Build completed successfully!

:end
echo.
echo Press any key to exit...
pause > nul
