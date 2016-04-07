/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity;

import com.fido.dp.base.Activity;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.decisionStorage.StorableDecisionModuleActivity;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author F.I.D.O.
 */
public class Wait extends Activity<Agent,Goal> implements StorableDecisionModuleActivity<Agent, Goal, Wait>{

	public Wait() {
	}
	
	public Wait(Agent agent) {
		super(agent);
	}

	public Wait(Agent agent, Goal goal) {
		super(agent, goal);
	}
	
	

	@Override
	public boolean equals(Object obj) {
		return this == obj || obj instanceof Wait;
	}
	
	
	
	

	@Override
	protected void performAction() {
		
	}

	@Override
	protected void init() {
		
	}

	@Override
	public void initialize(Agent agent, Goal goal) {
		super.initialize(agent,goal);
	}

	@Override
	public Element getXml(Document document) {
		Element parameter = document.createElement("wait");
		
		return parameter;
	}

	@Override
	public String getId() {
		return "wait";
	}

	@Override
	public Wait create(Agent agent, Goal goal) {
		return new Wait(agent, goal);
	}
	
	
	
}
