/* 
 * AgentAI
 */
package ninja.fido.agentAI.base;

import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.SubordinateAgentsInfo;
import ninja.fido.agentAI.Tools;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;


/**
 * Command agent type.
 * @author david
 */
public abstract class CommandAgent extends Agent {

	/**
	 * Agents under direct command
	 */
    private final ArrayList<Agent> commandedAgents;
	
	/**
	 * Agents under command.
	 */
	private final SubordinateAgentsInfo subordinateAgentsInfo;
	
	/**
	 * Requests from other agets.
	 */
	protected final Queue<Request> requests;
	
	/**
	 * Completed orders queue
	 */
	private final Queue<Order> completedOrdersQueue;
	
	/**
	 * List of uncompleted orders.
	 */
    private final ArrayList<Order> uncompletedOrders;
	
	/**
	 * Statistics of minerals given to commanded agents.
	 */
	private final Map<Agent,Integer> mineralsGiven;
	
	/**
	 * Statistics of minerals given to commanded agents.
	 */
	private final Map<Agent,Integer> gasGiven;
	
	/**
	 * Statistics of minerals given to commanded agents.
	 */
	private final Map<Agent,Integer> supplyGiven;
	
    

	
	/**
	 * Constructor.
	 * @throws EmptyDecisionTableMapException 
	 */
    public CommandAgent() throws EmptyDecisionTableMapException {
        commandedAgents = new ArrayList<>();
		subordinateAgentsInfo = new SubordinateAgentsInfo(this);
		requests = new ArrayDeque<>();
		completedOrdersQueue = new ArrayDeque<>();
		uncompletedOrders = new ArrayList<>();
		mineralsGiven = new HashMap<>();
		gasGiven = new HashMap<>();
		supplyGiven = new HashMap<>();
    }

	
	
	
	@Override
	protected void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException {
		super.giveResource(receiver, material, amount); 
		countGivenMaterial(receiver, material, amount);
	}
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
    protected final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
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
	
	/**
	 * Detaches commanded agents to other agent.
	 * @param subordinateAgents agent to be detached.
	 * @param newCommand new command agent for agents.
	 */
	protected final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		for (Agent subordinateAgent : subordinateAgents) {
			detachCommandedAgent(subordinateAgent, newCommand);
		}
    }

	/**
	 * Get commanded agent by type. 
	 * @param <T> type of agent we want to get.
	 * @param agentClass class specifying agent type.
	 * @return Returns commanded agent specified by type.
	 */
    protected final <T> T getCommandedAgent(Class<T> agentClass) {
        for (Agent subordinateAgent : commandedAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return (T) subordinateAgent;
            }
        }
//        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        return null;
    }
	
	
	/**
	 * Returns list of all agents under direct command.
	 * @return Returns list of agents under direct command.
	 */
    protected final ArrayList<Agent> getCommandedAgents() {
        return (ArrayList<Agent>) commandedAgents.clone();
    }

	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @return Returns all commanded agents of specified type.
	 */
    protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass) {
		return getCommandedAgents(agentClass, Integer.MAX_VALUE);
    }
	
	/**
	 * Get all commanded agents of scpecified type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @param idleOnly By this parameter, you can specify to return only agents that are not assigned. true means not 
	 * assigned only, false means all.
	 * @return Returns all commanded agents of specified type. If {@code idleOnly} is true, this method returns only 
	 * agents that are not assigned.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, boolean idleOnly) {
		return getCommandedAgents(agentClass, Integer.MAX_VALUE, idleOnly);
    }
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent you want to get.
	 * @param agentClass Class specifying agent type.
	 * @param count Number of agents you need.
	 * @return Returns commanded agents of specified type {@code T}. Number of agents returned is specified by 
	 * {@code count}.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count) {
		return getCommandedAgents(agentClass, count, false);
	}
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent you want to get.
	 * @param agentClass Class specifying agent type.
	 * @param count Number of agents you need.
	 * @param idleOnly By this parameter, you can specify to return only agents that are not assigned. true means not 
	 * assigned only, false means all.
	 * @return Returns commanded agents of specified type {@code T}. Number of agents returned is specified by 
	 * {@code count}. If {@code idleOnly} is true, this method returns only agents that are not assigned.
	 */
	protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass, int count, boolean idleOnly) {
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
	
	/**
	 * Returns nuber of agents under direct command.
	 * @return Returns nuber of agents under direct command.
	 */
	protected final int getNumberOfCommandedAgents(){
		return commandedAgents.size();
	}   
	
	/**
	 * Tells whether some specified commanded agent is occupied. IE this agent issueed an order to the agent, and the order 
	 * hasn't been completed yet.
	 * @param agent Agent.
	 * @return True if agent is occupied, faalse otherwise.
	 */
	protected final boolean isCommandedAgentOccupied(Agent agent){
		for (Order order : uncompletedOrders) {
			if(order.getTarget().equals(agent)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the number of suboordinate agents detached to specified command agent.
	 * @param commandAgent Command aagent.
	 * @param agentClass ssubordinate agent type.
	 * @return Returns the number of suboordinate agents detached to specified command agent.
	 */
	protected final int getSubordinateAgentsDetachedTo(CommandAgent commandAgent, Class<? extends Agent> agentClass){
		return subordinateAgentsInfo.getSubordinateAgentsDetachedTo(commandAgent, agentClass);
	}
	
	/**
	 * Returns amount of minerals given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of minerals given to specified agent.
	 */
	protected int getMineralsGivenTo(Agent receiver){
		return mineralsGiven.containsKey(receiver) ? mineralsGiven.get(receiver) : 0;
	}
	
	/**
	 * Returns amount of gas given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of gas given to specified agent.
	 */
	protected int getGasGivenTo(Agent receiver){
		return gasGiven.containsKey(receiver) ? gasGiven.get(receiver) : 0;
	}
	
	/**
	 * Returns amount of supply given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of supply given to specified agent.
	 */
	protected int getSupplyGivenTo(Agent receiver){
		return supplyGiven.containsKey(receiver) ? supplyGiven.get(receiver) : 0;
	}
	
	
	
	/**
	 * Called when new agent is detached under the dirct command of this agent.
	 * @param subordinateAgent New commanded agent.
	 */
	protected void onCommandedAgentAdded(Agent subordinateAgent) {
		Log.log(this, Level.INFO, "{0}: Subordinate agent added: {1}", this.getClass(), subordinateAgent.getClass());
	}
	
	/**
	 * Handles sisngle request from request queue.
	 * @param request Request.
	 */
	protected void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}
	
	/**
	 * Called when order from commanded agent is completed
	 * @param order Completed orde.
	 */
	protected void handleCompletedOrder(Order order) {
		Log.log(this, Level.FINE, "{0}: order completed: {1}", this.getClass(), order.getClass());
	}
	
	@Override
	protected void routine() {
		super.routine(); 
		handleRequests();
		handleCompletedOrders();
	}
	
	
	/**
	 * Adds new request to request queue.
	 * @param request new request.
	 */
	final void queRequest(Request request){
		requests.add(request);
	}	

	/**
	 * Add order issued to some agent to the list of uncompleted orders.
	 * @param order Order.
	 */
	final void addUncompletedOrder(Order order){
		uncompletedOrders.add(order);
	}
	
	/**
	 * Adds some agent under direct command of this agent.
	 * @param commandedAgent Agent.
	 */
    final void addCommandedAgent(Agent commandedAgent) {
        commandedAgents.add(commandedAgent);
		commandedAgent.setCommandAgent(this);
		subordinateAgentsInfo.onSubordinateAgentAdded(commandedAgent);
		onCommandedAgentAdded(commandedAgent);
		if(chosenAction != null){
			chosenAction.onCommandedAgentAdded(commandedAgent);
		}
    }
	
	/**
	 * Called when some order issued by this agent was completed.
	 * @param order Completed order.
	 */
	final void reportOrderCompleted(Order order) {
		completedOrdersQueue.add(order);
	}
	
	/**
	 * This is called when agent is removed from the game. 
	 * @param agent Removed agent.
	 */
	final void removeCommandedAgent(GameAgent agent) {
		commandedAgents.remove(agent);
	}
		
	
	/**
	 * Processes the request queue
	 */
	private void handleRequests(){
		Request request;
		int requestCount = requests.size();
		for (int i = 0; i < requestCount; i++) {
			request = requests.poll();
			handleRequest(request);
			((CommandActivity) chosenAction).handleRequest(request);
		}
	}

	/**
	 * processes completed orders.
	 */
	private void handleCompletedOrders(){
		Order order;
		while((order = completedOrdersQueue.poll()) != null){
			handleCompletedOrder(order);
			((CommandActivity) chosenAction).handleCompletedOrder(order);
			uncompletedOrders.remove(order);
		}
	}

	/**
	 * Counts given material.
	 * @param receiver Agent that received the mmmaterial.
	 * @param material Material type.
	 * @param amount Material amount.
	 */
	private void countGivenMaterial(Agent receiver, ResourceType material, int amount){
		switch(material){
			case MINERALS:
				Tools.incrementMapValue(mineralsGiven, receiver, amount);
			case GAS:
				Tools.incrementMapValue(gasGiven, receiver, amount);
			case SUPPLY:
				Tools.incrementMapValue(supplyGiven, receiver, amount);
		}
	}

}
