/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Behaviours;

import JadeWeatherForecast.Agents.WeatherCentralAgent;
import JadeWeatherForecast.Messages.WeatherResponse;
import jade.core.behaviours.SimpleBehaviour;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CalculateTemperatureBehaviour extends SimpleBehaviour {

    private boolean finished = false;
    SimpleDateFormat sdf = new SimpleDateFormat("kk:mm:ss");

    @Override
    public void action() {
        HashMap<Integer, ArrayList<Float>> WeatherConditions = new HashMap<Integer, ArrayList<Float>>();

        for (WeatherResponse response : ((WeatherCentralAgent) myAgent).RegionTemperatures.values()) {
            ArrayList<Float> temperatures;
            if (!WeatherConditions.containsKey(response.getRegion())) {
                temperatures = new ArrayList<Float>();
                temperatures.add(response.getTemperature());
                WeatherConditions.put(response.getRegion(), temperatures);
            } else {
                temperatures = WeatherConditions.get(response.getRegion());
                temperatures.add(response.getTemperature());
                WeatherConditions.put(response.getRegion(), temperatures);
            }
        }
        System.out.println();
        System.out.println(sdf.format(new Date()));
        for (Integer region : WeatherConditions.keySet()) {
            float average = 0;
            int counter;

            System.out.println("Average temperature for region " + region);
            for (counter = 0; counter < WeatherConditions.get(region).size(); counter++) {
                average += WeatherConditions.get(region).get(counter);
                System.out.println("  " + counter + " : " + WeatherConditions.get(region).get(counter));
            }
            System.out.println("  Average : " + (average / counter));
            System.out.println();
            finished = true;
        }
    }

    @Override
    public boolean done() {
        return finished;
    }
}
