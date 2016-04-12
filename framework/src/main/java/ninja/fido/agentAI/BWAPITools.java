/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import bwapi.Race;

/**
 *
 * @author F.I.D.O.
 */
public class BWAPITools {
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
