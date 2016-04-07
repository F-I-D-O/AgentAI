/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import java.util.Objects;
import java.util.TreeMap;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionTable {
	
	private TreeMap<Double,DecisionModuleActivity> probabilities;

	public TreeMap<Double, DecisionModuleActivity> getProbabilities() {
		return probabilities;
	}

	public void setProbabilities(TreeMap<Double, DecisionModuleActivity> probabilities) {
		this.probabilities = probabilities;
	}
	
	

	public DecisionTable(TreeMap<Double, DecisionModuleActivity> probabilities) {
		this.probabilities = probabilities;
	}
	
	
	
	public DecisionModuleActivity chooseAction(){
		double rand = Math.random();
//		Activity chosenAction = probabilities.get(rand);
		return probabilities.higherEntry(rand).getValue();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final DecisionTable other = (DecisionTable) obj;
		if (!Objects.equals(this.probabilities, other.probabilities)) {
			return false;
		}
		return true;
	}
	
	
}
