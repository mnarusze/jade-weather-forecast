@echo OFF

set AGENTS_AMOUNT=5
set REGIONS_AMOUNT=3

for /L %%i IN (1 1 %AGENTS_AMOUNT%) DO (
	SET /A "REGION = %%i %% %REGIONS_AMOUNT%"
	echo %REGION%
)