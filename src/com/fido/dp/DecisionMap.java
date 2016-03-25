/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Goal;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionMap {
	private final Goal goal;
	
	private final TreeMap<Double,Activity> probabilities;

	public DecisionMap(Goal goal, TreeMap<Double, Activity> probabilities) {
		this.goal = goal;
		this.probabilities = probabilities;
	}
	
	
	
	public Activity chooseAction(){
		double rand = Math.random();
//		Activity chosenAction = probabilities.get(rand);
		return probabilities.higherEntry(rand).getValue();
	}
	
}
