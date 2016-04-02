/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.base;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Goal {
	
	protected final Agent agent;
	
	private GoalOrder order;
	
	protected boolean completed;

	
	
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	

	public Goal(Agent agent, GoalOrder order) {
		this.agent = agent;
		this.order = order;
	}
	
	public final void reportCompleted(){
		order.reportCompleted();
	}
	
	public abstract boolean isCompleted();

	final boolean isOrdered() {
		return order != null;
	}
	
	final void setOrder(UniversalGoalOrder order){
		this.order = order;
	}
}
