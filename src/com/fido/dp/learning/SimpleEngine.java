/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.learning;

import com.fido.dp.GameResult;
import com.fido.dp.Tools;
import com.fido.dp.UnitDecisionSetting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author F.I.D.O.
 */
public class SimpleEngine implements LearningEngine{
	
	private final Map<ArrayList<UnitDecisionSetting>,DecisionTablesMapStatistic> decisionTablesMapStatisticMap;

	public SimpleEngine() {
		decisionTablesMapStatisticMap = new HashMap<>();
	}
	
	

	@Override
	public List<UnitDecisionSetting> learnFromResults(ArrayList<GameResult> gameResults) {
		
		for (GameResult gameResult : gameResults) {
			if(decisionTablesMapStatisticMap.containsKey(gameResult.getUnitDecisionSettings())){
				decisionTablesMapStatisticMap.get(gameResult.getUnitDecisionSettings()).addResult(gameResult);
			}
			else{
				decisionTablesMapStatisticMap.put(gameResult.getUnitDecisionSettings(), 
						new DecisionTablesMapStatistic(gameResult));
			}
		}
		
		List<Entry<ArrayList<UnitDecisionSetting>, DecisionTablesMapStatistic>> sortedList 
				= Tools.getSortedListFromMap(decisionTablesMapStatisticMap, false);
		
		ArrayList<UnitDecisionSetting> unitDecisionSettings = new ArrayList<>();	
		for(UnitDecisionSetting unitDecisionSetting : sortedList.get(0).getKey()){
			unitDecisionSettings.add(unitDecisionSetting);
		}
		
		return unitDecisionSettings;
	}
	
	private class DecisionTablesMapStatistic implements Comparable<DecisionTablesMapStatistic>{
		private int count;
		
		private int scoreTotal;

		public DecisionTablesMapStatistic(GameResult gameResult) {
			count = 1;
			scoreTotal = gameResult.getScore();
		}
		
		public void addResult(GameResult gameResult){
			count++;
			scoreTotal += gameResult.getScore();
		}

		@Override
		public int compareTo(DecisionTablesMapStatistic o) {
			return scoreTotal / count - o.scoreTotal / o.count;
		}
	}
}
