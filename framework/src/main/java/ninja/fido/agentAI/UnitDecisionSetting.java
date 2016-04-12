package ninja.fido.agentAI;

import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

	@Override
	public int hashCode() {
		int hash = 5;
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
		final UnitDecisionSetting other = (UnitDecisionSetting) obj;
		if (!Objects.equals(this.agentClass, other.agentClass)) {
			return false;
		}
		if (!Objects.equals(this.decisionTablesMap, other.decisionTablesMap)) {
			return false;
		}
		return true;
	}
	
	

	public UnitDecisionSetting(Class<? extends Agent> agentClass, 
			Map<DecisionTablesMapKey, DecisionTable> decisionTablesMap) {
		this.agentClass = agentClass;
		this.decisionTablesMap = decisionTablesMap;
	}
	
	
}
