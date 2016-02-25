/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class TestAction extends Action{

	public TestAction(Agent agent) {
		super(agent);
	}

	@Override
	public void performAction() {
		System.out.println("Action run started: " + getClass());
		
		ArrayList<Agent> subordinateAgents = ((CommandAgent) agent).getSubordinateAgents();
		System.out.println("Number of subordinate agents: " + subordinateAgents.size());
		for (Agent subordinateAgent : subordinateAgents) {
			if(subordinateAgent instanceof SCV){
				((SCV) subordinateAgent).commandHarvest();
				System.out.println("Commanded harvest: ");
			}
		}
		
		System.out.println("Action run ended: " + getClass());
	}
	
}
