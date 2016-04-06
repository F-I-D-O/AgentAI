/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Activity;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionTable {
	
	private TreeMap<Double,Activity> probabilities;

	public TreeMap<Double, Activity> getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(TreeMap<Double, Activity> probabilities) {
		this.probabilities = probabilities;
	}
	
	

	public DecisionTable(TreeMap<Double, Activity> probabilities) {
		this.probabilities = probabilities;
	}
	
	
	
	public Activity chooseAction(){
		double rand = Math.random();
//		Activity chosenAction = probabilities.get(rand);
		return probabilities.higherEntry(rand).getValue();
	}
	
}
