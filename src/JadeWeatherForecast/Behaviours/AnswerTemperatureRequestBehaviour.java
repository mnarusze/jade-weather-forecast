/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Behaviours;

import JadeWeatherForecast.Agents.WeatherReporterAgent;
import JadeWeatherForecast.Messages.WeatherResponse;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnarusze
 */
public class AnswerTemperatureRequestBehaviour extends CyclicBehaviour {
    
    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
        if (msg != null) {
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            WeatherResponse response = new WeatherResponse();
            response.setRegion(((WeatherReporterAgent) myAgent).getRegionID());
            response.setTemperature(((WeatherReporterAgent) myAgent).getActualTemp());
            try {
                reply.setContentObject(response);
                myAgent.send(reply);
                System.out.println(myAgent.getName() + " sent temperature");
            } catch (IOException ex) {
                Logger.getLogger(AnswerTemperatureRequestBehaviour.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
