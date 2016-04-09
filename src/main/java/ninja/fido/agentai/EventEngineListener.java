/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import bwapi.Unit;

/**
 *
 * @author F.I.D.O.
 */
public interface EventEngineListener {

	public void onBuildingConstructionFinished(Unit building);
	
}
