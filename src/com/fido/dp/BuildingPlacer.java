/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.TilePosition;

/**
 *
 * @author F.I.D.O.
 */
public interface BuildingPlacer {
	public TilePosition getBuildingLocation(final Building building);
}
