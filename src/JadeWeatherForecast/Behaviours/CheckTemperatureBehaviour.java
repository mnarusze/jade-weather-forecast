/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Behaviours;

import JadeWeatherForecast.Agents.WeatherReporterAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

/**
 *
 * @author mnarusze
 */
public class CheckTemperatureBehaviour extends TickerBehaviour {

    Float minTemp, maxTemp;

    public CheckTemperatureBehaviour(Agent a, long period, Float minTemp, Float maxTemp) {
        super(a, period);
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
    }

    @Override
    public void onTick() {
        ((WeatherReporterAgent) myAgent).setActualTemp((float) (Math.random() < 0.5 ? ((1 - Math.random())
                * (maxTemp - minTemp) + minTemp) : (Math.random() * (maxTemp - minTemp) + minTemp)));
        System.out.println("New temperature registered: " + ((WeatherReporterAgent) myAgent).getActualTemp());
    }
}
