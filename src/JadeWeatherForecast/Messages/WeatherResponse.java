/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Messages;

import java.io.Serializable;

/**
 *
 * @author mnarusze
 */
public class WeatherResponse implements Serializable {

    int region;
    float temperature;

    public int getRegion() {
        return region;
    }

    public void setRegion(int region) {
        this.region = region;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }
}
