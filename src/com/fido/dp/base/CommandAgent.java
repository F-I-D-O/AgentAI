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
	
	protected final Queue<Request> requests;
    
    

    public ArrayList<Agent> getSubordinateAgents() {
        return subordinateAgents;
    }

	public Queue<Request> getRequests(){
		return requests;
	}
	
	
    public CommandAgent() {
        subordinateAgents = new ArrayList<>();
		requests = new ArrayDeque<>();
    }

    public void addsubordinateAgent(Agent subordinateAgent) {
        subordinateAgents.add(subordinateAgent);
    }

    public final void detachSubordinateAgent(Agent subordinateAgent, CommandAgent newCommand) {
		subordinateAgent.setAssigned(false);
        newCommand.addsubordinateAgent(subordinateAgent);
        subordinateAgents.remove(subordinateAgent);
    }
	
	public final void detachSubordinateAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
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
	
	   
	
	public void handleRequest(Request request){
		requests.add(request);
	}
	
//	protected void commanderSetGas(Supply gas){
//		this.gas = gas;
//	}
}
