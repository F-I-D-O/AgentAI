/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.Log;
import com.fido.dp.agent.Agent;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Action {
	
	protected Agent agent;
    
    protected Action parrentAction;
    
    protected Action childAction;
    

	public Agent getAgent() {
		return agent;
	}

	public Action(Agent agent) {
		this.agent = agent;
	}
	
	

	public abstract void performAction();
    
    public final void run(){
        Log.log(this, Level.FINE, "{0}: run() START", this);
        if(childAction != null){
            childAction.run();
        }
        else{
            performAction();
        }
        Log.log(this, Level.FINE, "{0}: run() END", this);
    }
    
    protected final void finish(){
        if(parrentAction != null){
            parrentAction.onChildActionFinish();
        }
        else{
            agent.onActionFinish();
        }
    }
    
    protected void fail(String reason){
        agent.onActionFailed(reason);
    }

    protected void onChildActionFinish() {
        Log.log(this, Level.FINE, "{0}: Child action finished: {1}", this, childAction);
        childAction = null;
        performAction();
    }
    
    protected void runChildAction(Action childAction){
        Log.log(this, Level.FINE, "{0}: Running child action: {1}", this, childAction);
        this.childAction = childAction;
        this.childAction.run();
    }
	
	
}
