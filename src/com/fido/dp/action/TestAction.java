/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.Log;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class TestAction extends Action{
    
    private ExplorationCommand explorationCommand;

	public TestAction(Agent agent) {
		super(agent);
	}

	@Override
	public void performAction() {
//		System.out.println("Action run started: " + getClass());
        
        if(explorationCommand == null){
            getExplorationCommand();
            ArrayList<Agent> subordinateAgents = ((CommandAgent) agent).getSubordinateAgents();
            System.out.println("Number of subordinate agents: " + subordinateAgents.size());
            for (Agent subordinateAgent : subordinateAgents) {
                if(subordinateAgent instanceof SCV){
    //				((SCV) subordinateAgent).commandHarvest();
    //				System.out.println("Commanded harvest: ");
                    Log.log(this, Level.FINE, "{0}: subordinate agent added to {1}", this.getClass(), explorationCommand.getClass());
                    explorationCommand.addsubordinateAgent(subordinateAgent);
                }
            }
        }
		
		
		
//		System.out.println("Action run ended: " + getClass());
	}
    
    private void getExplorationCommand(){
        ArrayList<Agent> subordinateAgents = ((CommandAgent) agent).getSubordinateAgents();
        for (Agent subordinateAgent : subordinateAgents) {
            Log.log(this, Level.FINE, "{0}: agent: {1}", this, subordinateAgent.getClass());
			if(subordinateAgent instanceof ExplorationCommand){
                Log.log(this, Level.FINE, "{0}: INSIDE", this.getClass());
                explorationCommand = (ExplorationCommand) subordinateAgent;
                return ;
			}
		}
    }
        
	
}
