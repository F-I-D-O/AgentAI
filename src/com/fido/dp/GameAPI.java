/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.LeafAgent;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;

/**
 *
 * @author F.I.D.O.
 */
public class GameAPI extends DefaultBWListener {
	
//	public static final Map<UnitType, Double> unitTyps = new Map();
	
	/*
	* STATIC
	*/
	
	private static Mirror mirror;
	
	private static Game game;
	
	public static Mirror getMirror(){
		return mirror;
	}
	
	public static Game getGame(){
		if(game == null){
			game = mirror.getGame();
		}
		return game;
	}
	
	public static void main(String[] args) {
        new GameAPI().run();
    }
	
	
	/*
	* INSTANCE
	*/
	
	private Commander commander;
	
	private ArrayList<Agent> agents;
			
	
	public void run() {
		agents = new ArrayList<>();
		mirror = new Mirror();
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

	@Override
	public void onUnitCreate(Unit unit) {
//		System.out.println("New unit " + unit.getType());
		UnitType type = unit.getType();
		if(type.equals(UnitType.Terran_SCV)){
			LeafAgent agent = new SCV(unit);
			commander.addsubordinateAgent(agent);
			agents.add(agent);
		}
	}

	@Override
	public void onFrame() {
		try{
			System.out.println("OnFrame start");
			System.out.println("Number of agents: " + agents.size());
			for (Agent agent : agents) {
				agent.run();
			}
			System.out.println("OnFrame end");
		}
		catch(Exception exception){
			System.out.println("EXCEPTION!");
			exception.printStackTrace();
		}
		
	}

	@Override
	public void onStart() {
		commander = new Commander();
		agents.add(commander);
		mirror.getGame().setLocalSpeed(42);
	}
	
	
}
