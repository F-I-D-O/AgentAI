/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.base;

import ninja.fido.agentai.Log;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 * @param <G>
 */
public abstract class Activity<A extends Agent,G extends Goal> {
    
    private boolean isInitialized;
	
	protected A agent;
    
    private Activity parrentActivity;
    
    private Activity childActivity;
    

	public final A getAgent() {
		return (A) agent;
	}

	
	
	public Activity() {
		
	}


	public Activity(A agent) {
		this.agent = agent;
	}

	
	

	   
    public final void run(){
        Log.log(this, Level.FINE, "{0}: run() START", this.getClass());
        if(childActivity != null){
            childActivity.run();
        }
        else{
            if(!isInitialized){
                init();
				isInitialized = true;
            }
            performAction();
        }
        Log.log(this, Level.FINE, "{0}: run() END", this.getClass());
    }

	@Override
	public abstract boolean equals(Object obj);
	
    
	protected abstract void performAction();
	
	protected abstract void init();
	
    protected final void finish(){
        if(parrentActivity != null){
            parrentActivity.onChildActivityFinish(this);
			parrentActivity.childActivity = null;
        }
        else{
            agent.onActivityFinish(this);
        }
    }
    
    protected void fail(String reason){
        agent.onActionFailed(reason);
    }

    protected void onChildActivityFinish(Activity activity) {
        Log.log(this, Level.FINE, "{0}: Child action finished: {1}", this.getClass(), childActivity);

//        performAction(); not needet, because frame rate is high enough
    }
    
    protected void runChildActivity(Activity childAction){
		if(!childAction.equals(this.childActivity)){
			Log.log(this, Level.FINE, "{0}: Child action replaced. Old: {1}, new: {2}", this.getClass(), 
					this.childActivity, childAction.getClass());
			this.childActivity = childAction;
			childAction.parrentActivity = this;
		}
        Log.log(this, Level.FINE, "{0}: Running child action: {1}", this.getClass(), childAction);
        this.childActivity.run();
    }

	protected void processInfo(Info info) {
		Log.log(this, Level.FINE, "{0}: info received: {1}", this.getClass(), info.getClass());
	}

	protected void onCommandedAgentAdded(Agent commandedAgent) {
		
	}
	
	
}
