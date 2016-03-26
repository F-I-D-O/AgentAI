package com.fido.dp.base;

import com.fido.dp.Log;
import com.fido.dp.request.Request;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.logging.Level;

public abstract class CommandAgent extends Agent {

    private final ArrayList<Agent> subordinateAgents;
	
	protected final Queue<Request> requests;
	
	private final Queue<Order> completedOrdersQueue;
	
    private final ArrayList<Order> uncompletedOrders;
	
    

    public ArrayList<Agent> getSubordinateAgents() {
        return (ArrayList<Agent>) subordinateAgents.clone();
    }
	
	
    public CommandAgent() {
        subordinateAgents = new ArrayList<>();
		requests = new ArrayDeque<>();
		completedOrdersQueue = new ArrayDeque<>();
		uncompletedOrders = new ArrayList<>();
    }

    public final void detachSubordinateAgent(Agent subordinateAgent, CommandAgent newCommand) {
		subordinateAgent.setAssigned(false);
        newCommand.addSubordinateAgent(subordinateAgent);
        subordinateAgents.remove(subordinateAgent);
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
	
	public final int getNumberOfSubordinateAgents(){
		return subordinateAgents.size();
	}   
	
	public void queRequest(Request request){
		requests.add(request);
	}	
	
	public final boolean isSubordinateAgentOccupied(Agent agent){
		for (Order order : uncompletedOrders) {
			if(order.getTarget().equals(agent)){
				return true;
			}
		}
		return false;
	}
	

	protected void onSubordinateAgentAdded(Agent subordinateAgent) {
		Log.log(this, Level.INFO, "{0}: Subordinate agent added: {1}", this.getClass(), subordinateAgent);
	}
	
	protected void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}
	
	protected void handleCompletedOrder(Order order) {
		Log.log(this, Level.FINE, "{0}: order completed: {1}", this.getClass(), order.getClass());
	}

	@Override
	protected void routine() {
		super.routine(); 
		handleRequests();
		handleCompletedOrders();
	}
	

	final void addUncompletedOrder(Order order){
		uncompletedOrders.add(order);
	}
	
    final void addSubordinateAgent(Agent subordinateAgent) {
        subordinateAgents.add(subordinateAgent);
		subordinateAgent.setCommandAgent(this);
		onSubordinateAgentAdded(subordinateAgent);
    }
	
	final void reportOrderCompleted(Order order) {
		completedOrdersQueue.add(order);
	}
		
	
	private void handleRequests(){
		Request request;
		while((request = requests.poll()) != null){
			handleRequest(request);
			((CommandActivity) chosenAction).handleRequest(request);
		}
	}

	private void handleCompletedOrders(){
		Order order;
		while((order = completedOrdersQueue.poll()) != null){
			handleCompletedOrder(order);
			((CommandActivity) chosenAction).handleCompletedOrder(order);
			uncompletedOrders.remove(order);
		}
	}

	void removeSubordinateAgent(UnitAgent agent) {
		subordinateAgents.remove(agent);
	}

}
