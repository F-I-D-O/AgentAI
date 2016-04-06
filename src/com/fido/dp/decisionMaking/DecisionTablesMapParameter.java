/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.decisionMaking;

import com.fido.dp.base.Agent;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 * @param <A>
 * @param <V>
 * @param <P>
 */
public abstract class DecisionTablesMapParameter<A extends Agent,V,P extends DecisionTablesMapParameter> {
	
	private V value;

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}
	
	
	

	public DecisionTablesMapParameter(V value) {
		this.value = value;
	}
	
	
	
	public abstract P getCurrentParameter(A agent);

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
		final DecisionTablesMapParameter<?,?,?> other = (DecisionTablesMapParameter<?,?,?>) obj;
		
		return this.value.equals(other.value);
	}

	@Override
	public String toString() {
		return getClass() + ": " + value.toString(); //To change body of generated methods, choose Tools | Templates.
	}
	
	public abstract Element getXml(Document document);
	
	public abstract P createFromXml(Element element);

	public abstract String getId();
	
}
