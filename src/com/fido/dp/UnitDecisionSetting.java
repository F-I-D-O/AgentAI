package com.fido.dp;

import com.fido.dp.base.Agent;
import com.fido.dp.decisionMaking.DecisionTable;
import com.fido.dp.decisionMaking.DecisionTablesMapKey;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author F.I.D.O.
 */
public class UnitDecisionSetting {
	private Class<? extends Agent> agentClass;
	
	private Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap;

	public Class<? extends Agent> getAgentClass() {
		return agentClass;
	}

	public void setAgentClass(Class<? extends Agent> agentClass) {
		this.agentClass = agentClass;
	}

	public Map<DecisionTablesMapKey, DecisionTable> getDecisionTablesMap() {
		return decisionTablesMap;
	}

	public void setDecisionTablesMap(HashMap<DecisionTablesMapKey, DecisionTable> decisionTablesMap) {
		this.decisionTablesMap = decisionTablesMap;
	}
	
	

	public UnitDecisionSetting(Class<? extends Agent> agentClass, 
			Map<DecisionTablesMapKey, DecisionTable> decisionTablesMap) {
		this.agentClass = agentClass;
		this.decisionTablesMap = decisionTablesMap;
	}
	
	
}
