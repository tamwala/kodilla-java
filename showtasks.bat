call runcrud.bat
if not "%ERRORLEVEL%" == "0" goto fail
start "" http://localhost:8080/crud/v1/tasks
goto end

:fail
echo.
echo Errors while running runcrud.bat script

:end
echo.
echo showtasks.bat has finished work