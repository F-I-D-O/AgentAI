/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base;

import java.util.ArrayList;
import java.util.List;
import ninja.fido.agentAI.Log;
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
    public final ArrayList<Agent> getCommandedAgents() {
        return agent.getCommandedAgents();
    }
	
	/**
	 * Detaches commanded agent to other agent.
	 * @param subordinateAgent agent to be detached.
	 * @param newCommand new command agent for agent.
	 */
	public final void detachCommandedAgent(Agent subordinateAgent, CommandAgent newCommand) {
		agent.detachCommandedAgent(subordinateAgent, newCommand);
	}
	
	/**
	 * Detaches commanded agents to other agent.
	 * @param subordinateAgents agent to be detached.
	 * @param newCommand new command agent for agents.
	 */
	public final void detachCommandedAgents(List<? extends Agent> subordinateAgents, CommandAgent newCommand) {
		agent.detachCommandedAgents(subordinateAgents, newCommand);
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
	 * @param order 
	 */
	protected void handleCompletedOrder(Order order) {
		Log.log(this, Level.FINE, "{0}: order completed: {1}", this.getClass(), order.getClass());
	}

}
