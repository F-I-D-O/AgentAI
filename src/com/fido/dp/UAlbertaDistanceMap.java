/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Position;
import bwapi.TilePosition;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author F.I.D.O.
 */
public class UAlbertaDistanceMap {
	
	public static final int DISTANCE_UNEXPLORED = -1;
	
	public static final char MOVE_TO_DEFAULT = 'X';
	
	
	private final int cols;
	
	private final int rows;
	
	private int startRow;
	
	private int startColumn;
	
	private final int[] distances;
	
	private final char[] moveTo;
	
	private final ArrayList<TilePosition> sorted;

	
	
	
	public ArrayList<TilePosition> getSortedTiles()
    {
        return sorted;
    }
	
	
	
	
	public UAlbertaDistanceMap() {
		distances = new int[GameAPI.getGame().mapWidth() * GameAPI.getGame().mapHeight()];
		Arrays.fill(distances, DISTANCE_UNEXPLORED);
		
		moveTo = new char[GameAPI.getGame().mapWidth() * GameAPI.getGame().mapHeight()];
		Arrays.fill(moveTo, MOVE_TO_DEFAULT);
		
		rows = GameAPI.getGame().mapHeight();
		cols = GameAPI.getGame().mapWidth();
		startRow = -1;
		startColumn = -1; 
		sorted = new ArrayList<>();
	}
	
	
	
	
	public void addSorted(TilePosition tilePosition)
    {
        sorted.add(tilePosition);
    }
	
	private int getIndex(int row, int col)
	{
		return row * cols + col;
	}
	
	public int getDistanceTo(Position position){
		return distances[getIndex(position.getY() / 32, position.getX() / 32)];
	}
	
	public int getDistance(int index){
		return distances[index];
	}
	
	public void setDistance(int index, int distance){
		distances[index] = distance;
	}
	
	public void setStartPosition(int startRow, int startColumn){
		this.startRow = startRow;
		this.startColumn = startColumn;
	}
	
	public void setMoveTo(int index,char val) {
		moveTo[index] = val;
	}
	
	
}
