/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Behaviours;

import JadeWeatherForecast.Agents.WeatherCentralAgent;
import JadeWeatherForecast.Agents.WeatherReporterAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnarusze
 */
public class AskForTemperatureBehaviour extends TickerBehaviour {

    public AskForTemperatureBehaviour(Agent a, long tick) {
        super(a, tick);
    }

    @Override
    protected void onTick() {

        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        DFAgentDescription[] result = {};
        sd.setType(WeatherReporterAgent.SERVICE_NAME);
        dfd.addServices(sd);
        try {
            result = DFService.search(myAgent, dfd);
        } catch (FIPAException ex) {
            Logger.getLogger(AskForTemperatureBehaviour.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (result != null) {
            ((WeatherCentralAgent)myAgent).setResponseCounter(0);
            ((WeatherCentralAgent)myAgent).setResponseLimit(result.length);
            ArrayList<ACLMessage> messages = new ArrayList<ACLMessage>();
            for (int i = 0; i < result.length; i++) {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(result[i].getName());
                messages.add(msg);
            }
            for (ACLMessage msg : messages) {
                myAgent.send(msg);
            }
        }
    }
}
