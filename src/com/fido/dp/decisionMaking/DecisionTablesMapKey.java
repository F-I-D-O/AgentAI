/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Agent;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionTablesMapKey {

	public static DecisionTablesMapKey createKeyBasedOnCurrentState(Agent agent, DecisionTablesMapKey referenceKey) {
		DecisionTablesMapKey key = new DecisionTablesMapKey();
		for (Entry<Class<DecisionTablesMapParametr>,DecisionTablesMapParametr> entry : 
				referenceKey.getKeyParametrs().entrySet()) {
			key.addParameter(entry.getValue().getCurrentParameter(agent));
		}
		return key;
	}
	
	
	
	
	
	private final HashMap<Class<DecisionTablesMapParametr>,DecisionTablesMapParametr> keyParametrs;

	public HashMap<Class<DecisionTablesMapParametr>, DecisionTablesMapParametr> getKeyParametrs() {
		return keyParametrs;
	}
	
	
	

	public DecisionTablesMapKey() {
		this.keyParametrs = new HashMap<>();
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
		final DecisionTablesMapKey other = (DecisionTablesMapKey) obj;
		if (!Objects.equals(this.keyParametrs, other.keyParametrs)) {
			return false;
		}
		return true;
	}
	
	public void addParameter(DecisionTablesMapParametr parametr){
		keyParametrs.put((Class<DecisionTablesMapParametr>) parametr.getClass(), parametr);
	}

	@Override
	public String toString() {
		String[] content = new String[keyParametrs.size()];
		int i = 0;
		for (Entry<Class<DecisionTablesMapParametr>, DecisionTablesMapParametr> entry : keyParametrs.entrySet()) {
			content[i] += entry.getValue().toString();
			i++;
		}
		
		return "{" + String.join(", ", content) + '}';
	}
	
	

}
