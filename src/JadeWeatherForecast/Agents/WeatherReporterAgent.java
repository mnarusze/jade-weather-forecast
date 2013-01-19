/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Agents;

import JadeWeatherForecast.Behaviours.AnswerTemperatureRequestBehaviour;
import JadeWeatherForecast.Behaviours.CheckTemperatureBehaviour;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

/**
 *
 * @author mnarusze
 */
public class WeatherReporterAgent extends Agent {

    public static final String SERVICE_NAME = "WEATHER_REPORT";
    private String myName;
    private Integer regionID;
    private Float minTemp;
    private Float maxTemp;
    private Float actualTemp;

    public Integer getRegionID() {
        return regionID;
    }

    public Float getActualTemp() {
        return actualTemp;
    }

    public void setActualTemp(Float actualTemp) {
        this.actualTemp = actualTemp;
    }

    @Override
    protected void setup() {
        Object[] args = getArguments();
        if (args.length != 3) {
            throw new IllegalArgumentException("ERROR: Weather reporter needs 3 arguments (regionID,minTemp,maxTemp!");
        } else {
            // regionID,minTemp,maxTemp
            try {
                regionID = Integer.parseInt((String) args[0]);
                minTemp = Float.parseFloat((String) args[1]);
                maxTemp = Float.parseFloat((String) args[2]);
                System.out.println("Region : " + regionID);
                System.out.println("Min temparature : " + minTemp);
                System.out.println("Max temparature : " + maxTemp);
            } catch (NumberFormatException ex) {
                System.err.println("Wrong format of arguments! Needed (positive decimal, real number, real number)");
                System.err.println(ex.getMessage());
            }
            if (minTemp > maxTemp) {
                throw new IllegalArgumentException("ERROR: Second argument (minTemp) needs to be lower than the last one (maxTemp)!");
            }
        }

        actualTemp = (maxTemp - minTemp) / 2;
        myName = getAID().getLocalName();
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(SERVICE_NAME);
        sd.setName(myName);
        dfd.setName(getAID());
        dfd.addServices(sd);
        register(dfd);

        addBehaviour(new CheckTemperatureBehaviour(this, 10000, minTemp, maxTemp));
        addBehaviour(new AnswerTemperatureRequestBehaviour());
    }

    private void register(DFAgentDescription dfd) {
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Succesfully registered " + myName + " in DFS");
    }

    private void deregister() {
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Succesfully deegistered " + myName + " from DFS");
    }

    @Override
    protected void takeDown() {
        deregister();
    }
}
