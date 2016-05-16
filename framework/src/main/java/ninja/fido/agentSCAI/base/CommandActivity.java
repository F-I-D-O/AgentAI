/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base;

import java.util.ArrayList;
import java.util.List;
import ninja.fido.agentSCAI.Log;
import java.util.logging.Level;

/**
 * Represent command activity, ie activity runned by a command agent
 * @author david_000
 * @param <A> Agent type
 * @param <G> Goal type
 * @param <AC> Activity type
 */
public abstract class CommandActivity<A extends CommandAgent,G extends Goal, AC extends CommandActivity> 
		extends Activity<A,G,AC> {

	/**
	 * Empty constructor. It should be only use to register the activity somewhere. Agent musn't ever choose activity
	 * created by this constructor!
	 */
	public CommandActivity() {
		
	}

	/**
	 * Standard constructor
	 * @param agent Agent who performs this activity.
	 */
    public CommandActivity(A agent) {
        super(agent);
    }	
	
	
	
	
	
	/**
	 * Returns list of agents under direct command of the agent who runs this activity.
	 * @return Returns list of agents under direct command.
	 */
    protected final ArrayList<Agent> getCommandedAgents() {
        return agent.getCommandedAgents();
    }
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
	protected final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
		agent.detachCommandedAgent(subordinateAgent, newCommand);
	}
	
	/**
	 * Detaches commanded agents to other agent.
	 * @param subordinateAgents agent to be detached.
	 * @param newCommand new command agent for agents.
	 */
	protected final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		agent.detachCommandedAgents(subordinateAgents, newCommand);
	}
	
	/**
	 * Get commanded age by type. 
	 * @param <T> type of agent we want to get.
	 * @param agentClass class specifying agent type.
	 * @return Returns commanded agent specified by type.
	 */
    protected final <T> T getCommandedAgent(Class<T> agentClass) {
		return agent.getCommandedAgent(agentClass);
	}
	
	/**
	 * Get commanded agents by type. 
	 * @param <T> Type of agent we want to get.
	 * @param agentClass Class specifying agent type.
	 * @return Returns all commanded agents of specified type.
	 */
    protected final <T> ArrayList<T> getCommandedAgents(Class<T> agentClass) {
		return agent.getCommandedAgents(agentClass);
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
		return agent.getCommandedAgents(agentClass, idleOnly);
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
		return agent.getCommandedAgents(agentClass, count);
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
		return agent.getCommandedAgents(agentClass, count, idleOnly);
	}
	
	/**
	 * Returns nuber of agents under direct command.
	 * @return Returns nuber of agents under direct command.
	 */
	protected final int getNumberOfCommandedAgents(){
		return agent.getNumberOfCommandedAgents();
	}   
	
	/**
	 * Tells whether some specified commanded agent is occupied. IE this agent issueed an order to the agent, and the order 
	 * hasn't been completed yet.
	 * @param agent Agent.
	 * @return True if agent is occupied, faalse otherwise.
	 */
	protected final boolean isCommandedAgentOccupied(Agent agent){
		return this.agent.isCommandedAgentOccupied(agent);
	}
	
	/**
	 * Returns the number of suboordinate agents detached to specified command agent.
	 * @param commandAgent Command aagent.
	 * @param agentClass ssubordinate agent type.
	 * @return Returns the number of suboordinate agents detached to specified command agent.
	 */
	protected final int getSubordinateAgentsDetachedTo(CommandAgent commandAgent, Class<? extends Agent> agentClass){
		return agent.getSubordinateAgentsDetachedTo(commandAgent, agentClass);
	}
	
	/**
	 * Returns amount of minerals given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of minerals given to specified agent.
	 */
	protected final int getMineralsGivenTo(Agent receiver){
		return agent.getMineralsGivenTo(receiver);
	}
	
	/**
	 * Returns amount of gas given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of gas given to specified agent.
	 */
	protected final int getGasGivenTo(Agent receiver){
		return agent.getGasGivenTo(receiver);
	}
	
	/**
	 * Returns amount of supply given to specified agent.
	 * @param receiver Agent.
	 * @return Returns amount of supply given to specified agent.
	 */
	protected final int getSupplyGivenTo(Agent receiver){
		return agent.getSupplyGivenTo(receiver);
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
	
	

}
