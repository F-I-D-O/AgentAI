/* 
 * AgentAI
 */
package ninja.fido.agentAI;

import bwapi.Race;

/**
 * BWAPI tools.
 * @author F.I.D.O.
 */
public class BWAPITools {
	
	/**
	 * Transform string to Race enum.
	 * @param raceString string representing the race.
	 * @return 
	 */
	public static Race raceFromString(String raceString) {
		Race race = null;
		switch(raceString){
			case "Terran":
				race = Race.Terran;
				break;
			case "Protoss":
				race = Race.Protoss;
				break;
			case "Zerg":
				race = Race.Zerg;
				break;
		}
		return race;
	}
}
