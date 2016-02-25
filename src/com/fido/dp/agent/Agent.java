/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.Log;
import com.fido.dp.State;
import com.fido.dp.action.Action;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Agent {
	
	protected Action commandedAction;
    
    protected Action chosenAction;

    
    
    
    public void setCommandedAction(Action commandedAction) {
        this.commandedAction = commandedAction;
    }
    
    
    
    
	
	public void start(){
		System.out.println("Agent started: " + getClass());
	}
	
	protected State currentState;
	
	public void run(){
		System.out.println("Agent run started: " + getClass());
		chosenAction = chooseAction(); 
		if(chosenAction != null){
			System.out.println("ChosenAction: " + chosenAction.getClass());
			chosenAction.run();
		}
		System.out.println("Agent run ended: " + getClass());
	}

	protected Action chooseAction(){
        return commandedAction;
    }
    
    public void onActionFinish(){
        Log.log(this, Level.FINE, "Action finished: {0}", chosenAction);
        run();
    }
    
    public void onActionFailed(String reason){
        Log.log(this, Level.WARNING, "Action {0} failed: {1}", chosenAction, reason);
        run();
    }
}
