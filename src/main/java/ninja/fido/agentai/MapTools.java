/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import bwapi.Position;
import bwapi.TilePosition;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public interface MapTools {
	
	public ArrayList<TilePosition> getClosestTilesTo(Position position);
	
	public TilePosition getNextExpansion();
}
