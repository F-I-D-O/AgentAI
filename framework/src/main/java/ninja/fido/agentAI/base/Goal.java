/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.base;

/**
 * This class represents Agent's goal.
 * @author F.I.D.O.
 */
public abstract class Goal {
	
	/**
	 * Agent that has this goal
	 */
	protected final Agent agent;
	
	/**
	 * Order that resulted in this goal. If the goal does note come from an order (for example default goal), this will 
	 * be null.
	 */
	private GoalOrder order;
	
	/**
	 * This property can store the information whether this goal is completed. Note that goal can be completed without 
	 * changing this property, what matters is the result of the call to {@link #isCompleted()} 
	 */
	protected boolean completed;

	
	/**
	 * Sets the completed property. Note that goal can be completed without changing this property, what matters is the
	 * result of the call to {@link #isCompleted()} 
	 * @param completed future value of completed property
	 */
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	
	
	/**
	 * Default constructor.
	 * @param agent Agent that has this goal.
	 * @param order Order that resulted in this goal. If the goal does note come from an order 
	 * (for example default goal), this will be null.
	 */
	public Goal(Agent agent, GoalOrder order) {
		this.agent = agent;
		this.order = order;
	}
	
	
	
	/**
	 * Reports to the order that the goal is completed.
	 */
	public final void reportCompleted(){
		if(isOrdered()){
			order.reportCompleted();
		}
	}
	
	/**
	 * Tells wheter this goal is completed. Correct implementation of this method is essential to prevent agents of
	 * being stuck.
	 * @return true if the goal is completed, false otherwise.
	 */
	public abstract boolean isCompleted();

	
	/**
	 * Determines if this goal was ordered or not.
	 * @return true if the goal was ordered, false otherwise.
	 */
	final boolean isOrdered() {
		return order != null;
	}
	
	/**
	 * Sets the order. Ist used only by {@link UniversalGoalOrder), because reference to other orders are passed through
	 * constructor 
	 * @param order universalGoalOrder
	 */
	final void setOrder(UniversalGoalOrder order){
		this.order = order;
	}
}
