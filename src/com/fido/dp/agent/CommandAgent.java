/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public abstract class CommandAgent extends Agent
{
	protected ArrayList<Agent> subordinateAgents;

	public ArrayList<Agent> getSubordinateAgents() {
		System.out.println("getting subordinate agents: " + getClass());
		return subordinateAgents;
	}

	public CommandAgent() {
		this.subordinateAgents = new ArrayList<>();
	}
	
	
	
	
	
	public void addsubordinateAgent(Agent subordinateAgent){
		subordinateAgents.add(subordinateAgent);
	}
}
