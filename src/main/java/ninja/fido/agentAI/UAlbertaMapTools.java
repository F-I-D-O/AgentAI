/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI;

import ninja.fido.agentAI.base.GameAPI;
import bwapi.Player;
import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwta.BaseLocation;
import bwta.BWTA;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 *
 * @author F.I.D.O.
 */
public class UAlbertaMapTools implements MapTools{
	
	/**
	 * a cache of already computed distance maps
	 */
	private final Map<Position,UAlbertaDistanceMap> allMaps;
	
	/**
	 * the map stored at TilePosition resolution, values are 0/1 for walkable or not walkable
	 */
	private final boolean[] map;
	
	/**
	 * the fringe vector which is used as a sort of 'open list'
	 */
	private final int[] fringe;
	
	private final int rows;
	
	private final int cols;

	
	
	public UAlbertaMapTools() {
		allMaps = new HashMap<>();
		rows = GameAPI.getGame().mapHeight();
		cols = GameAPI.getGame().mapWidth();
		map = new boolean[rows * cols];
		fringe = new int[rows * cols];

		setBWAPIMapData();
	}

	
	
	@Override
	public ArrayList<TilePosition> getClosestTilesTo(Position position){
		
		// make sure the distance map is calculated with pos as a destination
		int a = getGroundDistance(GameAPI.getGame().self().getStartLocation().toPosition(), position);

		return allMaps.get(position).getSortedTiles();
	}
	
	@Override
	public TilePosition getNextExpansion()
	{
		return getNextExpansion(GameAPI.getGame().self());
	}
	
	
	private TilePosition getNextExpansion(Player player)
	{
		BaseLocation closestBase = null;
		double minDistance = 100000;

		TilePosition homeTile = player.getStartLocation();

		// for each base location
		for (BaseLocation base : BWTA.getBaseLocations())
		{
			// if the base has gas
			if (!base.isMineralOnly() && !(base == BWTA.getStartLocation(player)))
			{
				// get the tile position of the base
				TilePosition baseTile = base.getTilePosition();
				boolean buildingInTheWay = false;

				for (int x = 0; x < GameAPI.getGame().self().getRace().getCenter().tileWidth(); ++x)
				{
					for (int y = 0; y < GameAPI.getGame().self().getRace().getCenter().tileHeight(); ++y)
					{
						TilePosition tilePosition = new TilePosition(baseTile.getX() + x, baseTile.getY() + y);

						for (Unit unit : GameAPI.getGame().getUnitsOnTile(tilePosition))
						{
							if (unit.getType().isBuilding() && !unit.isFlying())
							{
								buildingInTheWay = true;
								break;
							}
						}
					}
				}

				if (buildingInTheWay)
				{
					continue;
				}

				// the base's distance from our main nexus
				Position myBasePosition = player.getStartLocation().toPosition();
				Position thisTile = baseTile.toPosition();
				double distanceFromHome = getGroundDistance(thisTile, myBasePosition);

				// if it is not connected, continue
				if (!BWTA.isConnected(homeTile, baseTile) || distanceFromHome < 0)
				{
					continue;
				}

				if (closestBase == null || distanceFromHome < minDistance)
				{
					closestBase = base;
					minDistance = distanceFromHome;
				}
			}

		}

		if (closestBase != null)
		{
			return closestBase.getTilePosition();
		}
		else
		{
			return TilePosition.None;
		}
	}
	
	/**
	 * computes walk distance from @Position position to all other points on the map
	 * @param distanceMap
	 * @param position 
	 */
	private void computeDistance(UAlbertaDistanceMap distanceMap, Position position)
	{
		search(distanceMap, position.getY() / 32, position.getX() / 32);
	}
	
	private int getGroundDistance(Position origin, Position destination){
		
		// if we have too many maps, reset our stored maps in case we run out of memory
		if (allMaps.size() > 20){
			allMaps.clear();

			Log.log(this, Level.FINE, "Cleared stored distance map cache");
		}

		// if we haven't yet computed the distance map to the destination
		if (allMaps.get(destination) == null)
		{
			// if we have computed the opposite direction, we can use that too
			if (allMaps.get(origin) != null)
			{
				return allMaps.get(origin).getDistanceTo(destination);
			}

			// add the map and compute it
			allMaps.put(destination,new UAlbertaDistanceMap());
			computeDistance(allMaps.get(destination), destination);
		}

		// get the distance from the map
		return allMaps.get(destination).getDistanceTo(origin);
	}
	
	/**
	 * return the index of the 1D array from (row,col)
	 * @param row
	 * @param col
	 * @return 
	 */
	private int getIndex(int row, int col)
	{
		return row * cols + col;
	}

	private TilePosition getTilePosition(int index){
		return new TilePosition(index % cols, index / cols);
	}
	
	private void resetFringe(){
		for (Integer integer : fringe) {
			integer = 0;
		}
	}
	
	/**
	 * does the dynamic programming search
	 * @param distanceMap
	 * @param startRow
	 * @param startColumn 
	 */
	private void search(UAlbertaDistanceMap distanceMap, int startRow, int startColumn){
		
		// reset the internal variables
		resetFringe();

		// set the starting position for this search
		distanceMap.setStartPosition(startRow, startColumn);

		// set the distance of the start cell to zero
		distanceMap.setDistance(getIndex(startRow, startColumn), 0);

		// set the fringe variables accordingly
		int fringeSize = 1;
		int fringeIndex = 0;
		fringe[0] = getIndex(startRow, startColumn);
		distanceMap.addSorted(getTilePosition(fringe[0]));

		// temporary variables used in search loop
		int currentIndex;
		int nextIndex;
		int newDistance;

		// the size of the map
		int size = rows * cols;

		// while we still have things left to expand
		while (fringeIndex < fringeSize)
		{
			// grab the current index to expand from the fringe
			currentIndex = fringe[fringeIndex++];
			newDistance = distanceMap.getDistance(currentIndex) + 1;

			// search up
			nextIndex = (currentIndex > cols) ? (currentIndex - cols) : -1;
			if (unexplored(distanceMap, nextIndex))
			{
				// set the distance based on distance to current cell
				distanceMap.setDistance(nextIndex, newDistance);
				distanceMap.setMoveTo(nextIndex, 'D');
				distanceMap.addSorted(getTilePosition(nextIndex));

				// put it in the fringe
				fringe[fringeSize++] = nextIndex;
			}

			// search down
			nextIndex = (currentIndex + cols < size) ? (currentIndex + cols) : -1;
			if (unexplored(distanceMap, nextIndex))
			{
				// set the distance based on distance to current cell
				distanceMap.setDistance(nextIndex, newDistance);
				distanceMap.setMoveTo(nextIndex, 'U');
				distanceMap.addSorted(getTilePosition(nextIndex));

				// put it in the fringe
				fringe[fringeSize++] = nextIndex;
			}

			// search left
			nextIndex = (currentIndex % cols > 0) ? (currentIndex - 1) : -1;
			if (unexplored(distanceMap, nextIndex))
			{
				// set the distance based on distance to current cell
				distanceMap.setDistance(nextIndex, newDistance);
				distanceMap.setMoveTo(nextIndex, 'R');
				distanceMap.addSorted(getTilePosition(nextIndex));

				// put it in the fringe
				fringe[fringeSize++] = nextIndex;
			}

			// search right
			nextIndex = (currentIndex % cols < cols - 1) ? (currentIndex + 1) : -1;
			if (unexplored(distanceMap, nextIndex))
			{
				// set the distance based on distance to current cell
				distanceMap.setDistance(nextIndex, newDistance);
				distanceMap.setMoveTo(nextIndex, 'L');
				distanceMap.addSorted(getTilePosition(nextIndex));

				// put it in the fringe
				fringe[fringeSize++] = nextIndex;
			}
		}
	}
	
	/**
	 * Determines if TilePosition is unexplored by search. 
	 * @param distanceMap UAlbertaDistanceMap object
	 * @param index intex to distances array
	 * @return 
	 */
	private boolean unexplored(UAlbertaDistanceMap distanceMap, int index){
		return (index != -1) && distanceMap.getDistance(index) == UAlbertaDistanceMap.DISTANCE_UNEXPLORED && map[index];
	}

	/**
	 * eads in the map data from bwapi and stores it in our map format
	 */
	private void setBWAPIMapData(){
		
		// for each row and column
		for (int row = 0; row < rows; ++row)
		{
			for (int column = 0; column < cols; ++column)
			{
				boolean clear = true;

				// check each walk tile within this TilePosition
				for (int i = 0; i < 4; ++i)
				{
					for (int j = 0; j < 4; ++j)
					{
						if (!GameAPI.getGame().isWalkable(column * 4 + i,row * 4 + j))
						{
							clear = false;
							break;
						}

						if (clear)
						{
							break;
						}
					}
				}

				// set the map as binary clear or not
				map[getIndex(row, column)] =  clear;
			}
		}
	}
}
