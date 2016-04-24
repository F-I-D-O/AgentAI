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
    }

	
	
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
    final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
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
	final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		for (Agent subordinateAgent : subordinateAgents) {
			detachCommandedAgent(subordinateAgent, newCommand);
		}
    }

	/**
	 * Get commanded age by type. 
	 * @param <T>
	 * @param agentClass
	 * @return 
	 */
    final <T> T getCommandedAgent(Class<T> agentClass) {
        for (Agent subordinateAgent : commandedAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return (T) subordinateAgent;
            }
        }
//        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
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
	
	/**
	 * Returns list of agents under direct command.
	 * @return Returns list of agents under direct command.
	 */
    final ArrayList<Agent> getCommandedAgents() {
        return (ArrayList<Agent>) commandedAgents.clone();
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
