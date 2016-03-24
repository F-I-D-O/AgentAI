/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.Action;
import com.fido.dp.base.Goal;
import java.util.HashMap;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionMap {
	private final Goal goal;
	
	private final HashMap<Double,Action> probabilities;

	public DecisionMap(Goal goal, HashMap<Double, Action> probabilities) {
		this.goal = goal;
		this.probabilities = probabilities;
	}
	
	
	
	public Action chooseAction(Goal goal){
		double rand = Math.random();
		
		return probabilities.get(rand);
	}
	
}
