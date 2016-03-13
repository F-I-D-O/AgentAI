package com.fido.dp.base;

import com.fido.dp.Log;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class CommandAgent extends Agent {

    protected final ArrayList<Agent> subordinateAgents;
	
	
    
    

    public ArrayList<Agent> getSubordinateAgents() {
        return subordinateAgents;
    }
	
	
    public CommandAgent() {
        subordinateAgents = new ArrayList<>();
    }

    public void addSubordinateAgent(Agent subordinateAgent) {
        subordinateAgents.add(subordinateAgent);
    }

    public final void detachSubordinateAgent(Agent subordinateAgent, CommandAgent newCommand) {
		subordinateAgent.setAssigned(false);
        newCommand.addSubordinateAgent(subordinateAgent);
        subordinateAgents.remove(subordinateAgent);
		subordinateAgent.setCommandAgent(newCommand);
    }
	
	public final void detachSubordinateAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		for (Agent subordinateAgent : subordinateAgents) {
			detachSubordinateAgent(subordinateAgent, newCommand);
		}
    }

    public final <T> T getSubordinateAgent(Class<T> agentClass) {
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return (T) subordinateAgent;
            }
        }
        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        return null;
    }

    public final <T> List<T> getSubordinateAgents(Class<T> agentClass) {
		return getSubordinateAgents(agentClass, Integer.MAX_VALUE);
    }
	
	public final <T> List<T> getSubordinateAgents(Class<T> agentClass, boolean idleOnly) {
		return getSubordinateAgents(agentClass, Integer.MAX_VALUE, idleOnly);
    }
	
	public final <T> List<T> getSubordinateAgents(Class<T> agentClass, int count) {
		return getSubordinateAgents(agentClass, count, false);
	}
	
	public final <T> List<T> getSubordinateAgents(Class<T> agentClass, int count, boolean idleOnly) {
        ArrayList<T> agents = new ArrayList();
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent) && (!idleOnly || ((UnitAgent) subordinateAgent).isIdle())) {
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
	
	   
	
	
	
	
}
