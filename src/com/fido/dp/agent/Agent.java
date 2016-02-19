/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.State;
import com.fido.dp.action.Action;

/**
 *
 * @author F.I.D.O.
 */
public abstract class Agent {
	
	protected Action commandedAction;
    
    protected Action chosenAction;
	
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

	protected abstract Action chooseAction();
    
    public void onActionFinish(){
        run();
    }
}
