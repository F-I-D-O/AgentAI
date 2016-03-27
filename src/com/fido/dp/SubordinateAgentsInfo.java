/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;
import java.util.HashMap;

/**
 *
 * @author F.I.D.O.
 */
public class SubordinateAgentsInfo {
	
	private final HashMap<CommandAgent, HashMap<Class<? extends Agent>,Integer>> table;
	
	private final CommandAgent commandAgent;
	
	public SubordinateAgentsInfo(CommandAgent commandAgent) {
		table = new HashMap<>();
		this.commandAgent = commandAgent;
	}
	
	public void onSubordinateAgentAdded(Agent agent){
		if(!table.containsKey(commandAgent)){
			table.put(commandAgent, new HashMap<>());
		}
		HashMap<Class<? extends Agent>,Integer> innerTable = table.get(commandAgent);
		if(innerTable.containsKey(agent.getClass())){
			innerTable.put(agent.getClass(), innerTable.get(agent.getClass()) + 1);
		}
		else{
			innerTable.put(agent.getClass(), 1);
		}
	}
	
	public void onSubordinateAgentDetached(Agent agent, CommandAgent newCommand){
		HashMap<Class<? extends Agent>,Integer> innerTable = table.get(commandAgent);
		innerTable.put(agent.getClass(), innerTable.get(agent.getClass()) - 1);
		if(!table.containsKey(newCommand)){
			table.put(newCommand, new HashMap<>());
		}
		innerTable = table.get(newCommand);
		if(innerTable.containsKey(agent.getClass())){
			innerTable.put(agent.getClass(), innerTable.get(agent.getClass()) + 1);
		}
		else{
			innerTable.put(agent.getClass(), 1);
		}
	}
	
	public void onSubordinateAgentDetachedBack(Agent agent){
		HashMap<Class<? extends Agent>,Integer> innerTable = table.get(commandAgent);
		innerTable.put(agent.getClass(), innerTable.get(agent.getClass()) - 1);
	}
	
	public int getSubordinateAgentsDetachedTo(CommandAgent commandAgent, Class<? extends Agent> agentClass){
		return table.get(commandAgent).get(agentClass);
	}
	
	
}
