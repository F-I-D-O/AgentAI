/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.base;

import com.fido.dp.Log;
import com.fido.dp.info.Info;
import com.fido.dp.request.Request;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 * @param <T>
 */
public abstract class Action <T extends Agent> {
    
    private boolean isInitialized;
	
	protected T agent;
    
    private Action parrentAction;
    
    private Action childAction;
    

	public final T getAgent() {
		return (T) agent;
	}

	public Action(T agent) {
		this.agent = agent;
	}
	
	

	protected abstract void performAction();
	
	protected abstract void init();
    
    public final void run(){
        Log.log(this, Level.FINE, "{0}: run() START", this.getClass());
        if(childAction != null){
            childAction.run();
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
        Log.log(this, Level.FINE, "{0}: Child action finished: {1}", this.getClass(), childAction);
        childAction = null;
//        performAction(); not needet, because frame rate is high enough
    }
    
    protected void runChildAction(Action childAction){
        Log.log(this, Level.FINE, "{0}: Running child action: {1}", this.getClass(), childAction);
        this.childAction = childAction;
		childAction.parrentAction = this;
        this.childAction.run();
    }

	protected void processInfo(Info info) {
		Log.log(this, Level.FINE, "{0}: info received: {1}", this.getClass(), info.getClass());
	}
	
	
}
