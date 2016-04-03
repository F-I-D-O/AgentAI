package com.fido.dp.base;

import com.fido.dp.Log;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.SubordinateAgentsInfo;
import com.fido.dp.Tools;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;

public abstract class CommandAgent extends Agent {

    private final ArrayList<Agent> commandedAgents;
	
	private final SubordinateAgentsInfo subordinateAgentsInfo;
	
	protected final Queue<Request> requests;
	
	private final Queue<Order> completedOrdersQueue;
	
    private final ArrayList<Order> uncompletedOrders;
	
	private final Map<Agent,Integer> mineralsGiven;
	
    

    public ArrayList<Agent> getCommandedAgents() {
        return (ArrayList<Agent>) commandedAgents.clone();
    }
	
	
	
	
    public CommandAgent() {
        commandedAgents = new ArrayList<>();
		subordinateAgentsInfo = new SubordinateAgentsInfo(this);
		requests = new ArrayDeque<>();
		completedOrdersQueue = new ArrayDeque<>();
		uncompletedOrders = new ArrayList<>();
		mineralsGiven = new HashMap<>();
    }

	
	
	
    public final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
		subordinateAgent.setAssigned(false);
        newCommand.addCommandedAgent(subordinateAgent);
        commandedAgents.remove(subordinateAgent);
		if(getCommandAgent() == newCommand){
			subordinateAgentsInfo.onSubordinateAgentDetachedBack(subordinateAgent);
		}
		else{
			subordinateAgentsInfo.onSubordinateAgentDetached(subordinateAgent, newCommand);
		}
    }
	
	public final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		for (Agent subordinateAgent : subordinateAgents) {
			detachCommandedAgent(subordinateAgent, newCommand);
		}
    }

    public final <T> T getCommandedAgent(Class<T> agentClass) {
        for (Agent subordinateAgent : commandedAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return (T) subordinateAgent;
            }
        }
        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        return null;
    }

    public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass) {
		return CommandAgent.this.getCommandedAgents(agentClass, Integer.MAX_VALUE);
    }
	
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, boolean idleOnly) {
		return getCommandedAgents(agentClass, Integer.MAX_VALUE, idleOnly);
    }
	
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count) {
		return getCommandedAgents(agentClass, count, false);
	}
	
	public final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count, boolean idleOnly) {
        ArrayList<T> agents = new ArrayList();
        for (Agent subordinateAgent : commandedAgents) {
            if (agentClass.isInstance(subordinateAgent) && (!idleOnly || ((GameAgent) subordinateAgent).isIdle())) {
                agents.add((T) subordinateAgent);
				if(agents.size() == count){
					break;
				}
            }
        }
        if (agents.isEmpty() && count != Integer.MAX_VALUE) {
            Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        }
		if (count != Integer.MAX_VALUE && agents.size() < count) {
            Log.log(this, Level.WARNING, "{0}: Not enough subordinate agents of type: {1}", this.getClass(), agentClass);
        }
        return agents;
    }
	
	public final int getNumberOfCommandedAgents(){
		return commandedAgents.size();
	}   
	
	public void queRequest(Request request){
		requests.add(request);
	}	
	
	public final boolean isCommandedAgentOccupied(Agent agent){
		for (Order order : uncompletedOrders) {
			if(order.getTarget().equals(agent)){
				return true;
			}
		}
		return false;
	}
	
	public int getSubordinateAgentsDetachedTo(CommandAgent commandAgent, Class<? extends Agent> agentClass){
		return subordinateAgentsInfo.getSubordinateAgentsDetachedTo(commandAgent, agentClass);
	}

	@Override
	public void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException {
		super.giveResource(receiver, material, amount); 
		countGivenMaterial(receiver, material, amount);
	}
	
	public int getMineralsGivenTo(Agent receiver){
		return mineralsGiven.containsKey(receiver) ? mineralsGiven.get(receiver) : 0;
	}
	

	protected void onCommandedAgentAdded(Agent subordinateAgent) {
		Log.log(this, Level.INFO, "{0}: Subordinate agent added: {1}", this.getClass(), subordinateAgent.getClass());
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
	
    final void addCommandedAgent(Agent commandedAgent) {
        commandedAgents.add(commandedAgent);
		commandedAgent.setCommandAgent(this);
		subordinateAgentsInfo.onSubordinateAgentAdded(commandedAgent);
		onCommandedAgentAdded(commandedAgent);
		if(chosenAction != null){
			chosenAction.onCommandedAgentAdded(commandedAgent);
		}
    }
	
	final void reportOrderCompleted(Order order) {
		completedOrdersQueue.add(order);
	}
		
	
	private void handleRequests(){
		Request request;
		int requestCount = requests.size();
		for (int i = 0; i < requestCount; i++) {
			request = requests.poll();
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

	void removeCommandedAgent(GameAgent agent) {
		commandedAgents.remove(agent);
	}
	
	private void countGivenMaterial(Agent receiver, ResourceType material, int amount){
		switch(material){
			case MINERALS:
				Tools.incrementMapValue(mineralsGiven, receiver, amount);
		}
	}
	

}
