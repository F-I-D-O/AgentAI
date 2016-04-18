/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base;

import ninja.fido.agentAI.Log;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A> Agent
 * @param <G> Goal
 * @param <AC> Activity
 */
public abstract class CommandActivity<A extends CommandAgent,G extends Goal, AC extends CommandActivity> 
		extends Activity<A,G,AC> {

	public CommandActivity() {
		
	}

    public CommandActivity(A agent) {
        super(agent);
    }	
	
	protected void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}

	protected void handleCompletedOrder(Order order) {
		Log.log(this, Level.FINE, "{0}: order completed: {1}", this.getClass(), order.getClass());
	}

}
