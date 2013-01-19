set CLASSPATH="C:\Users\mnarusze\Documents\NetBeansProjects\JadeWeatherForecast\dist\JadeWeatherForecast.jar;C:\Program Files\Java\jade\lib\jade.jar"

set AGENTS_AMOUNT=3
set REGIONS_AMOUNT=2

for /L %%i IN (1 1 %AGENTS_AMOUNT%) DO (
	SET /A "REGION = %%i %% %REGIONS_AMOUNT%"
 	start java -cp %CLASSPATH% jade.Boot -container -container-name station_%%i station_%%i_agent:JadeWeatherForecast.Agents.WeatherReporterAgent(%REGION%,0.0,10.0)
)