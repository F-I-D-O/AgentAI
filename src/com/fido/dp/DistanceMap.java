/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.Position;
import bwapi.TilePosition;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class DistanceMap {
	
	private final int cols;
	
	private final int rows;
	
	private int startRow;
	
	private int startColumn;
	
	private final ArrayList<Integer> distances;
	
	private final ArrayList<Character> moveTo;
	
	private ArrayList<TilePosition> sorted;

	
	
	
	public ArrayList<TilePosition> getSortedTiles()
    {
        return sorted;
    }
	
	
	
	
	public DistanceMap() {
		distances = new ArrayList<>(GameAPI.getGame().mapWidth() * GameAPI.getGame().mapHeight());
		moveTo = new ArrayList<>(GameAPI.getGame().mapWidth() * GameAPI.getGame().mapHeight());
		rows = GameAPI.getGame().mapHeight();
		cols = GameAPI.getGame().mapWidth();
		startRow = -1;
		startColumn = -1; 
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
		return distances.get(getIndex(position.getY() / 32, position.getX() / 32));
	}
	
	public int getDistance(int index){
		return distances.get(index);
	}
	
	public void setDistance(int index, int distance){
		distances.set(index, distance);
	}
	
	public void setStartPosition(int startRow, int startColumn){
		this.startRow = startRow;
		this.startColumn = startColumn;
	}
	
	public void setMoveTo(int index,char val) {
		moveTo.set(index, val);
	}
	
	
}
