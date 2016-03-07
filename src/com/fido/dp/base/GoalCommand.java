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
public abstract class GoalCommand extends Command {

	public GoalCommand(Agent target, CommandAgent commandAgent) {
		super(target, commandAgent);
	}
	
	protected final void setGoal(Goal goal){
		Agent target = getTarget();
		target.setGoal(goal);
	}
}
