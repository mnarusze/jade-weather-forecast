set AGENTS_AMOUNT=3
set REGIONS_AMOUNT=2

for /L %%i IN (1 1 %AGENTS_AMOUNT%) DO (
	SET /A "REGION=%REGIONS_AMOUNT% %% %%i"
 	start java jade.Boot -container -container-name station_%%i station_%%i_agent:JadeWeatherForecast.Agents.WeatherReporterAgent"(%REGION%,0.0,10.0)")