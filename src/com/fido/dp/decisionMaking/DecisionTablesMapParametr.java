/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 * @param <V>
 * @param <P>
 */
public abstract class DecisionTablesMapParametr<A extends Agent,V,P extends DecisionTablesMapParametr> {
	
	private final V value;
	
	
	

	public DecisionTablesMapParametr(V value) {
		this.value = value;
	}
	
	
	
	public abstract P getCurrentParameter(Agent agent);

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
		final DecisionTablesMapParametr<?,?,?> other = (DecisionTablesMapParametr<?,?,?>) obj;
		
		return this.value.equals(other.value);
	}

	@Override
	public String toString() {
		return getClass() + ": " + value.toString(); //To change body of generated methods, choose Tools | Templates.
	}
	
	
}
