/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.decisionMaking;

import ninja.fido.agentai.UnitDecisionSetting;
import ninja.fido.agentai.base.Agent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author F.I.D.O.
 */
public class DecisionModule {
	private final Set<Class<? extends Agent>> registeredAgents;
	
	private final Map<Class<? extends Agent>,Map<DecisionTablesMapKey,DecisionTable>> decisionSettings;

	public DecisionModule() {
		decisionSettings = new HashMap<>();
		registeredAgents = new HashSet<>();
	}
	
		
	public Map<DecisionTablesMapKey,DecisionTable> getDecisionTablesMap(Class<? extends Agent> agentClass){
		return decisionSettings.get(agentClass);
	}
	
	public void addDecisionTablesMap(Class<? extends Agent> agentClass,
			Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap){
		if(decisionSettings.containsKey(agentClass)){
			mergeDecisionTablesMaps(agentClass, decisionTablesMap);
		}
		else{
			decisionSettings.put(agentClass, decisionTablesMap);
		}
	}
	
	public void addDecisionTablesMap(UnitDecisionSetting unitDecisionSetting){
		addDecisionTablesMap(unitDecisionSetting.getAgentClass(), unitDecisionSetting.getDecisionTablesMap());
	}
	
	public boolean isDecisionMakingOn(Agent agent){
		return registeredAgents.contains(agent.getClass());
	}
	
	public void registerAgentClass(Agent agent){
		registeredAgents.add(agent.getClass());
		Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap = agent.getDefaultDecisionTablesMap();
		if(decisionTablesMap != null){
			addDecisionTablesMap(agent.getClass(), decisionTablesMap);
		}
	}
	
	private void mergeDecisionTablesMaps(Class<? extends Agent> agentClass,
			Map<DecisionTablesMapKey,DecisionTable> decisionTablesMap){
		Map<DecisionTablesMapKey,DecisionTable> oldDecisionTablesMap = decisionSettings.get(agentClass);
		
		for (Map.Entry<DecisionTablesMapKey, DecisionTable> entry : decisionTablesMap.entrySet()) {
			DecisionTablesMapKey decisionTablesMapKey = entry.getKey();
			DecisionTable decisionTable = entry.getValue();
			oldDecisionTablesMap.put(decisionTablesMapKey, decisionTable);
		}
	}
}
