package com.fido.dp.base;

import com.fido.dp.base.Agent;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Supply;
import com.fido.dp.Material;
import com.fido.dp.request.Request;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

public abstract class CommandAgent extends Agent {

    protected final ArrayList<Agent> subordinateAgents;
    
    private final Supply minerals;
    
    private final Supply gas;
	
	protected final Queue<Request> requests;
    
    

    public ArrayList<Agent> getSubordinateAgents() {
        return subordinateAgents;
    }

	public Queue<Request> getRequests(){
		return requests;
	}
	
	
    public CommandAgent() {
        subordinateAgents = new ArrayList<>();
		minerals = new Supply(GameAPI.getCommander(), Material.MINERALS, 0);
		gas = new Supply(GameAPI.getCommander(), Material.GAS, 0);
		requests = new ArrayDeque<>();
    }

    public void addsubordinateAgent(Agent subordinateAgent) {
        subordinateAgents.add(subordinateAgent);
    }

    public void detachSubordinateAgent(Agent subordinateAgent, CommandAgent newCommand) {
		subordinateAgent.setAssigned(false);
        newCommand.addsubordinateAgent(subordinateAgent);
        subordinateAgents.remove(subordinateAgent);
    }
	
	public void detachSubordinateAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		for (Agent subordinateAgent : subordinateAgents) {
			detachSubordinateAgent(subordinateAgent, newCommand);
		}
    }

    public final <T> T getSubordinateAgent(Class agentClass) {
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return (T) subordinateAgent;
            }
        }
        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        return null;
    }

    public final <T> List<T> getSubordinateAgents(Class agentClass) {
//        ArrayList<T> agents = new ArrayList();
//        for (Agent subordinateAgent : subordinateAgents) {
//            if (agentClass.isInstance(subordinateAgent)) {
//                agents.add((T) subordinateAgent);
//            }
//        }
//        if (agents.isEmpty()) {
//            Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
//        }
//        return agents;
		return getSubordinateAgents(agentClass, Integer.MAX_VALUE);
    }
	
	public final <T> List<T> getSubordinateAgents(Class agentClass, int count) {
        ArrayList<T> agents = new ArrayList();
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                agents.add((T) subordinateAgent);
				if(agents.size() == count){
					break;
				}
            }
        }
        if (agents.isEmpty()) {
            Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        }
		if (count != Integer.MAX_VALUE && agents.size() < count) {
            Log.log(this, Level.WARNING, "{0}: Not enough subordinate agents of type: {1}", this.getClass(), agentClass);
        }
        return agents;
    }
    
    public final void receiveSupply(Supply supply){
        if(supply.getMaterial() == Material.GAS){
            gas.merge(supply);
        }
        else{
            minerals.merge(supply);
        }
    }
	
	public void giveSupply(CommandAgent receiver, Material material, int amount){
		if(material == Material.GAS){
			receiver.receiveSupply(gas.split(amount));
		}
		else{
			receiver.receiveSupply(gas.split(amount));
		}
	}
    
    public int getOwnedGas(){
        return gas.getAmount();
    } 
    
    public int getOwnedMinerals(){
        return minerals.getAmount();
    }
	
	public void handleRequest(Request request){
		requests.add(request);
	}
	
//	protected void commanderSetGas(Supply gas){
//		this.gas = gas;
//	}
}
