/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Agents;

import JadeWeatherForecast.Behaviours.AskForTemperatureBehaviour;
import JadeWeatherForecast.Behaviours.CalculateTemperatureBehaviour;
import JadeWeatherForecast.Messages.WeatherResponse;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnarusze
 */
public class WeatherCentralAgent extends Agent {

    public HashMap<AID, WeatherResponse> RegionTemperatures;
    private int responseCounter;
    private int responseLimit;
    
    @Override
    protected void setup() {
        RegionTemperatures = new HashMap<AID, WeatherResponse>();

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(WeatherReporterAgent.SERVICE_NAME);
        dfd.addServices(sd);
        addBehaviour(new AskForTemperatureBehaviour(this, 20000));
        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                ACLMessage message = receive();
                if (message != null && ! message.getSender().getLocalName().equals("df")) {
                    try {
                        if (message.getContentObject() != null) {
                            WeatherResponse response = (WeatherResponse) message.getContentObject();

                            if (response != null) {
                                ((WeatherCentralAgent) myAgent).RegionTemperatures.put(message.getSender(), response);
                                responseCounter++;
                                if (responseCounter >= responseLimit) {
                                    myAgent.addBehaviour(new CalculateTemperatureBehaviour());
                                    responseCounter = 0;
                                    responseLimit = 0;
                                }
                            }
                        }
                    } catch (UnreadableException ex) {
                        Logger.getLogger(WeatherCentralAgent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    public int getResponseCounter() {
        return responseCounter;
    }

    public void setResponseCounter(int responseCounter) {
        this.responseCounter = responseCounter;
    }

    public int getResponseLimit() {
        return responseLimit;
    }

    public void setResponseLimit(int responseLimit) {
        this.responseLimit = responseLimit;
    }
}
