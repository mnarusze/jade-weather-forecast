/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JadeWeatherForecast.Behaviours;

import JadeWeatherForecast.Agents.WeatherReporterAgent;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mnarusze
 */
public class AskForTemperatureBehaviour extends TickerBehaviour {

    long wakeUpTime;
    
    public AskForTemperatureBehaviour(Agent a, long tick) {
        super(a, tick);
    }

    @Override
    public void onStart() {
        wakeUpTime = System.currentTimeMillis() + 8000;
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
            for (int i = 0 ; i < result.length; i++) {
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
                msg.addReceiver(result[i].getName());
                myAgent.send(msg);System.out.println("Asking for " + (i+1) + " temperature!");
            }
            System.out.println("Asking for temperature!");
        }
        long dt = wakeUpTime - System.currentTimeMillis();
        if (dt <= 0) {
            myAgent.addBehaviour(new CalculateTemperatureBehaviour());
        } else {
            block(dt);
        }
    }
}
