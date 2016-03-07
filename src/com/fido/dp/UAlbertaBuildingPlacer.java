/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Position;
import bwapi.Race;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import bwta.BWTA;
import bwta.BaseLocation;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class UAlbertaBuildingPlacer implements BuildingPlacer {
	
	private static final int BUILDING_SPACING = 1;
	
	private static final int PYLON_SPACING = 3;
	
	
	private ArrayList<ArrayList<Boolean>> reserveMap;
	
	private int boxTop;
    private int boxBottom;
    private int boxLeft;
    private int boxRight;
	
	
	
	
	@Override
	public TilePosition getBuildingLocation(final Building building){
		int numPylons = GameAPI.getGame().self().completedUnitCount(UnitType.Protoss_Pylon);

//		if (building.isGasSteal()){
//			BaseLocation enemyBaseLocation = InformationManager::Instance().getMainBaseLocation(BWAPI::Broodwar->enemy());
//			UAB_ASSERT(enemyBaseLocation,"Should have enemy base location before attempting gas steal");
//			UAB_ASSERT(enemyBaseLocation->getGeysers().size() > 0,"Should have spotted an enemy geyser");
//
//			for (auto & unit : enemyBaseLocation->getGeysers())
//			{
//				BWAPI::TilePosition tp(unit->getInitialTilePosition());
//				return tp;
//			}
//		}

		if (building.getType().requiresPsi() && numPylons == 0)
		{
			return TilePosition.None;
		}

		if (building.getType().isRefinery())
		{
			return getRefineryPosition();
		}

		if (building.getType().isResourceDepot())
		{
			// get the location 
			TilePosition tilePosition = GameAPI.getMapTools().getNextExpansion();

			return tilePosition;
		}

		// set the building padding specifically
		int distance = building.getType() == UnitType.Protoss_Photon_Cannon ? 0 : BUILDING_SPACING;
		if (building.getType() == UnitType.Protoss_Pylon && (numPylons < 3))
		{
			distance = PYLON_SPACING;
		}

		// get a position within our region
		return getBuildLocationNear(building, distance, false);
	}
	
	private TilePosition getBuildLocationNear(Building building, int buildDistance, boolean horizontalOnly){
		
		// get the precomputed vector of tile positions which are sorted closes to this location
		ArrayList<TilePosition> closestPositions = 
				GameAPI.getMapTools().getClosestTilesTo(building.getDesiredPosition());
		
//		double ms1 = t.getElapsedTimeInMilliSec();

		// special easy case of having no pylons
		int numPylons = GameAPI.getGame().self().completedUnitCount(UnitType.Protoss_Pylon);
		if (building.getType().requiresPsi() && numPylons == 0)
		{
			return TilePosition.None;
		}

		// iterate through the list until we've found a suitable location
		for (int i = 0; i < closestPositions.size(); ++i)
		{
			if (canBuildHereWithSpace(closestPositions.get(i), building, buildDistance, horizontalOnly))
			{
//				double ms = t.getElapsedTimeInMilliSec();
				//BWAPI::Broodwar->printf("Building Placer Took %d iterations, lasting %lf ms @ %lf iterations/ms, %lf setup ms", i, ms, (i / ms), ms1);

				return closestPositions.get(i);
			}
		}

//		double ms = t.getElapsedTimeInMilliSec();
		//BWAPI::Broodwar->printf("Building Placer Took %lf ms", ms);

		return  TilePosition.None;
	}

	//returns true if we can build this type of unit here with the specified amount of space.
	//space value is stored in this->buildDistance.
	private boolean canBuildHereWithSpace(
			TilePosition position, Building building, int buildDistance, boolean horizontalOnly){
		UnitType buildingType = building.getType();

		//if we can't build here, we of course can't build here with space
		if (!canBuildHere(position,building))
		{
			return false;
		}

		// height and width of the building
		int width = building.getType().tileWidth();
		int height = building.getType().tileHeight();

		//make sure we leave space for add-ons. These types of units can have addons:
		if (building.getType().equals(UnitType.Terran_Command_Center) ||
			building.getType().equals(UnitType.Terran_Factory) ||
			building.getType().equals(UnitType.Terran_Starport) ||
			building.getType().equals(UnitType.Terran_Science_Facility))
		{
			width += 2;
		}

		// define the rectangle of the building spot
		int startx = position.getX() - buildDistance;
		int starty = position.getY() - buildDistance;
		int endx   = position.getX() + width + buildDistance;
		int endy   = position.getY() + height + buildDistance;

		if (building.getType().isAddon())
		{
			UnitType builderType = buildingType.whatBuilds().first;

			TilePosition builderTile = new TilePosition(
					position.getX() - builderType.tileWidth(), position.getY() + 2 - builderType.tileHeight());

			startx = builderTile.getX() - buildDistance;
			starty = builderTile.getY() - buildDistance;
			endx = position.getX() + width + buildDistance;
			endy = position.getY() + height + buildDistance;
		}

		if (horizontalOnly)
		{
			starty += buildDistance;
			endy -= buildDistance;
		}

		// if this rectangle doesn't fit on the map we can't build here
		if (startx < 0 || starty < 0 || endx > GameAPI.getGame().mapWidth() || endx < position.getX() + width
				|| endy > GameAPI.getGame().mapHeight())
		{
			return false;
		}

		// if we can't build here, or space is reserved, or it's in the resource box, we can't build here
		for (int x = startx; x < endx; x++)
		{
			for (int y = starty; y < endy; y++)
			{
				if (!building.getType().isRefinery())
				{
					if (!buildable(building, x, y) || reserveMap.get(x).get(y) || (
							(building.getType() != UnitType.Protoss_Photon_Cannon) && isInResourceBox(x, y)))
					{
						return false;
					}
				}
			}
		}

		return true;
	}
	
	/**
	 * makes final checks to see if a building can be built at a certain location
	 * @param position
	 * @param building
	 * @return 
	 */
	private boolean canBuildHere(TilePosition position, Building building){
		
		//returns true if we can build this type of unit here. Takes into account reserved tiles.
		if (!GameAPI.getGame().canBuildHere(position, building.getType(), building.getBuilderUnit()))
		{
			return false;
		}

		// check the reserve map
		for (int x = position.getX(); x < position.getX() + building.getType().tileWidth(); x++)
		{
			for (int y = position.getY(); y < position.getY() + building.getType().tileHeight(); y++)
			{
				if (reserveMap.get(x).get(y))
				{
					return false;
				}
			}
		}

		// if it overlaps a base location return false
		if (tileOverlapsBaseLocation(position, building.getType()))
		{
			return false;
		}

		return true;
	}
	
	private boolean tileOverlapsBaseLocation(TilePosition tilePosition, UnitType type){
		
		// if it's a resource depot we don't care if it overlaps
		if (type.isResourceDepot())
		{
			return false;
		}

		// dimensions of the proposed location
		int tx1 = tilePosition.getX();
		int ty1 = tilePosition.getY();
		int tx2 = tx1 + type.tileWidth();
		int ty2 = ty1 + type.tileHeight();

		// for each base location
		for (BaseLocation base : BWTA.getBaseLocations())
		{
			// dimensions of the base location
			int bx1 = base.getTilePosition().getX();
			int by1 = base.getTilePosition().getY();
			int bx2 = bx1 + GameAPI.getGame().self().getRace().getCenter().tileWidth();
			int by2 = by1 + GameAPI.getGame().self().getRace().getCenter().tileHeight();

			// conditions for non-overlap are easy
			boolean noOverlap = (tx2 < bx1) || (tx1 > bx2) || (ty2 < by1) || (ty1 > by2);

			// if the reverse is true, return true
			if (!noOverlap)
			{
				return true;
			}
		}

		// otherwise there is no overlap
		return false;
	}
	
	private boolean buildable(final Building building, int x, int y){
		TilePosition tilePosition = new TilePosition(x, y);

		//returns true if this tile is currently buildable, takes into account units on tile
		if (!GameAPI.getGame().isBuildable(x,y))
		{
			return false;
		}

		if ((GameAPI.getGame().self().getRace() == Race.Terran) && tileBlocksAddon(tilePosition))
		{
			return false;
		}

		for (Unit unit : GameAPI.getGame().getUnitsOnTile(x, y))
		{
			if ((building.getBuilderUnit() != null) && (unit != building.getBuilderUnit()))
			{
				return false;
			}
		}

		if (!tilePosition.isValid())
		{
			return false;
		}

		return true;
	}
	
	private boolean tileBlocksAddon(TilePosition position)
	{
		for (int i = 0; i <= 2; ++i)
		{
			for (Unit unit : GameAPI.getGame().getUnitsOnTile(position.getX() - i,position.getY()))
			{
				if (unit.getType().equals(UnitType.Terran_Command_Center) ||
					unit.getType().equals(UnitType.Terran_Factory) ||
					unit.getType().equals(UnitType.Terran_Starport) ||
					unit.getType().equals(UnitType.Terran_Science_Facility))
				{
					return true;
				}
			}
		}

		return false;
	}
	
	private boolean isInResourceBox(int x, int y)
	{
		int posX = x * 32;
		int posY = y * 32;

		return (posX >= boxLeft) && (posX < boxRight) && (posY >= boxTop) && (posY < boxBottom);
	}
	
	TilePosition getRefineryPosition(){
		TilePosition closestGeyser = TilePosition.None;
		double minGeyserDistanceFromHome = Double.MAX_VALUE;
		Position homePosition = GameAPI.getGame().self().getStartLocation().toPosition();

		// for each geyser
		for (Unit geyser : GameAPI.getGame().getStaticGeysers())
		{
			if (geyser.getType() != UnitType.Resource_Vespene_Geyser)
			{
				continue;
			}

			Position geyserPosition = geyser.getInitialPosition();

			// check to see if it's next to one of our depots
			boolean nearDepot = false;
			for (Unit unit : GameAPI.getGame().self().getUnits())
			{
				if (unit.getType().isResourceDepot() && unit.getDistance(geyserPosition) < 300)
				{
					nearDepot = true;
				}
			}

			if (nearDepot)
			{
				double homeDistance = geyser.getDistance(homePosition);

				if (homeDistance < minGeyserDistanceFromHome)
				{
					minGeyserDistanceFromHome = homeDistance;
					closestGeyser = geyser.getInitialTilePosition();
				}
			}
		}

		return closestGeyser;
	}
}