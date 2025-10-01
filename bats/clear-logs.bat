@echo off
setlocal

REM === CONFIGURE AQUI O CAMINHO DO PERFIL DO WEBSHERE ===
set LOG_DIR=C:\Program Files\IBM\WebSphere\AppServer\profiles\AppSrv01\logs

echo Limpando arquivos .log em %LOG_DIR% ...

for /R "%LOG_DIR%" %%F in (*.log) do (
    echo. > "%%F"
    echo Limpo: %%F
)

echo.
echo Todos os arquivos .log foram esvaziados com sucesso.
pause
