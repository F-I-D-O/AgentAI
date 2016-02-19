/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.Vulture;
import java.util.ArrayList;

/**
 *
 * @author david_000
 */
public class StrategicExplorationAction extends Action {
    
    private ArrayList<Vulture> scouts;

    public StrategicExplorationAction(CommandAgent agent) {
        super(agent);
        scouts = new ArrayList<>();
        for (Agent subordinateAgent : agent.getSubordinateAgents()) {
            if(subordinateAgent instanceof Vulture){
                scouts.add((Vulture) subordinateAgent);
            }
        }
    }

    @Override
    public void run() {
        for (Vulture scout : scouts) {
            
        }
    }
    
}
