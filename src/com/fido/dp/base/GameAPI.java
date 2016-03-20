package com.fido.dp.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.EventEngine;
import com.fido.dp.EventEngineListener;
import com.fido.dp.Log;
import com.fido.dp.LogFormater;
import com.fido.dp.MapTools;
import com.fido.dp.UAlbertaBuildingPlacer;
import com.fido.dp.UAlbertaMapTools;
import com.fido.dp.agent.Barracks;
import com.fido.dp.base.Agent;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.Marine;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.base.UnitAgent;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.SCV;
import com.fido.dp.agent.UnitCommand;
import com.fido.dp.base.CommandAgent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameAPI extends DefaultBWListener implements EventEngineListener{
	
	private static Commander commanderStatic;
	
	private static MapTools mapTools;
	
	private static BuildingPlacer buildingPlacer;
	
	private static Mirror mirror;

    private static Game game;
	
	private static GameAPI gameAPI;
	
	
	
	public static Commander getCommander(){
		return commanderStatic;
	}

	public static MapTools getMapTools() {
		return mapTools;
	}

	public static BuildingPlacer getBuildingPlacer() {
		return buildingPlacer;
	}

    public static Mirror getMirror() {
        return mirror;
    }

	
	
	
    public static Game getGame() {
        if (game == null) {
            game = mirror.getGame();
        }
        return game;
    }

	public static void main(String[] args) {
		gameAPI = new GameAPI();
        gameAPI.run();
    }
	
	public static void addAgent(Agent agent, CommandAgent commandAgent){
		commandAgent.addSubordinateAgent(agent);
        gameAPI.agents.add(agent);
	}
	
	
	
	
	private final EventEngine eventEngine;
	
	private Commander commander;
	
	private final ArrayList<Agent> agents;
	
	private final ArrayList<UnitAgent> unitAgents;
	
	private final HashMap<Unit,UnitAgent> unitAgentsMappedByUnit;
	
	private int frameCount;
	
	private final Level logLevel;

	
	
	public GameAPI() {
		this.frameCount = 0;
		agents = new ArrayList<>();
		unitAgents = new ArrayList<>();
		unitAgentsMappedByUnit = new HashMap<>();
		eventEngine =  new EventEngine();
		eventEngine.addListener(this);
		logLevel = Level.INFO;
	}
	
	
	
	
	@Override
    public void onUnitCreate(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral()){
				return;
			}
			
			UnitAgent agent = null;
			
			// Construction started event
			if(type.isBuilding()){
				Unit builderUnit = unit.getBuildUnit();
				SCV builderAgent = (SCV) unitAgentsMappedByUnit.get(builderUnit);
				
				// units obtained on start has no event
				if(builderAgent != null){
					builderAgent.onConstructionStarted();
				}
			}
			
			if (type.equals(UnitType.Terran_SCV)) {
				agent = new SCV(unit);
			}
			
			if (type.equals(UnitType.Terran_Marine)) {
				agent = new Marine(unit);
			}
			
			
			
			// check beacause we not handle all units creation
			if(agent != null){
				addAgent(agent);
				unitAgents.add(agent);
				unitAgentsMappedByUnit.put(unit, agent);
			}
		}
		catch (Exception exception) {
            Log.log(this, Level.SEVERE, "EXCEPTION!");
            exception.printStackTrace();
        } 
		catch (Error error) {
            Log.log(this, Level.SEVERE, "ERROR!");
            error.printStackTrace();
        }
    }

    @Override
    public void onFrame() {
        try {
			frameCount++;
            Log.log(this, Level.INFO, "OnFrame start - frame {0}", frameCount);
			
			eventEngine.run();
			
            Log.log(this, Level.FINE, "Number of agents: {0}", agents.size());
            for (Agent agent : agents) {
                agent.run();
            }
            Log.log(this, Level.INFO, "OnFrame end");
        } 
		catch (Exception exception) {
            Log.log(this, Level.SEVERE, "EXCEPTION!");
            exception.printStackTrace();
        } 
		catch (Error error) {
            Log.log(this, Level.SEVERE, "ERROR!");
            error.printStackTrace();
        }
    }

    @Override
    public void onStart() {
		try{
			Log.log(this, Level.FINE, "OnStart START");
			Logger rootLog = Logger.getLogger("");
			bwta.BWTA.readMap();
			bwta.BWTA.analyze();
			rootLog.setLevel(logLevel);
			rootLog.getHandlers()[0].setLevel(logLevel);
			rootLog.getHandlers()[0].setFormatter(new LogFormater());
			
			getGame().setLocalSpeed(10);
			getGame().setFrameSkip(0);
			getGame().enableFlag(1);

			commander = new Commander();

			commanderStatic = commander;
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();

			agents.add(commander);
			addAgent(new ExplorationCommand());
			addAgent(new ResourceCommand());
			addAgent(new BuildCommand());
			addAgent(new ProductionCommand());
			addAgent(new UnitCommand());
			
//			buildingPlacer.getBuildingLocation(new Building(GameAPI.getGame().self().getStartLocation().toPosition(),
//					UnitType.Terran_Barracks, null, false));
			Log.log(this, Level.FINE, "OnStart END");
		}
		catch (Exception exception) {
            Log.log(this, Level.SEVERE, "EXCEPTION!");
            exception.printStackTrace();
        } 
		catch (Error error) {
            Log.log(this, Level.SEVERE, "ERROR!");
            error.printStackTrace();
        }
    }
	
	@Override
	public void onBuildingConstructionFinished(Unit building) {
		Unit builderUnit = building.getBuildUnit();
		SCV builderAgent = (SCV) unitAgentsMappedByUnit.get(builderUnit);
		
		// units obtained on start has no event
		if(builderAgent != null){
			builderAgent.onConstructionFinished();
		}
		
		UnitAgent buildingAgent = null;
		UnitType buildingType = building.getType();
		
		if (buildingType.equals(UnitType.Terran_Barracks)) {
			buildingAgent = new Barracks(building);
		}
		
		if(buildingAgent != null){
			addAgent(buildingAgent);
			unitAgentsMappedByUnit.put(building, buildingAgent);
		}
	}

	@Override
	public void onUnitDiscover(Unit unit) {
		
		// we are only interested in enemz units
		if(unit.getPlayer().isEnemy(getGame().self())){
			UnitType type = unit.getType();
			
			// buildings
			if(type.isBuilding()){
				for (UnitAgent agent : unitAgents) {
					if(agent.canSeeUnit(unit)){
						agent.onEnemyBuildingDiscoverd(unit);
					}
				}
			}
		}
	}
	
	

	
    public void run() {
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    public void addAgent(Agent agent) {
		addAgent(agent, commander);
    }

	
	
	
}
