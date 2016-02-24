/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.Agent;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Action {
	
	protected Agent agent;

	public Agent getAgent() {
		return agent;
	}

	public Action(Agent agent) {
		this.agent = agent;
	}
	
	

	public abstract void run();
    
    public void finish(){
        agent.onActionFinish();
    }
    
    public void fail(String reason){
        agent.onActionFailed(reason);
    }
	
	
}
