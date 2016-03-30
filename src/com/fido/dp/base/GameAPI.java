package com.fido.dp.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Race;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.EventEngine;
import com.fido.dp.EventEngineListener;
import com.fido.dp.Log;
import com.fido.dp.LogFormater;
import com.fido.dp.MapTools;
import com.fido.dp.MorphableUnit;
import com.fido.dp.NonImplementedMorphException;
import com.fido.dp.UAlbertaBuildingPlacer;
import com.fido.dp.UAlbertaMapTools;
import com.fido.dp.agent.unit.Barracks;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.unit.Drone;
import com.fido.dp.agent.FullCommander;
import com.fido.dp.agent.unit.Larva;
import com.fido.dp.agent.unit.Marine;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.agent.UnitCommand;
import com.fido.dp.agent.ZergCommander;
import com.fido.dp.agent.unit.Hatchery;
import com.fido.dp.agent.unit.Probe;
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
		commandAgent.addCommandedAgent(agent);
        gameAPI.agents.add(agent);
		if(agent instanceof UnitAgent){
			UnitAgent unitAgent = (UnitAgent) agent;
			gameAPI.getUnitAgents().add(unitAgent);
			gameAPI.getUnitAgentsMappedByUnit().put(unitAgent.getUnit(), unitAgent);
		}
	}
	
	public static Position getStartBasePosition(){
		return getGame().self().getStartLocation().toPosition();
	}
	
	
	private final EventEngine eventEngine;
	
	private Commander commander;
	
	private final ArrayList<Agent> agents;
	
	private final ArrayList<UnitAgent> unitAgents;
	
	private final HashMap<Unit,UnitAgent> unitAgentsMappedByUnit;
	
	private int frameCount;
	
	private final Level logLevel;

	private ArrayList<UnitAgent> getUnitAgents() {
		return unitAgents;
	}

	private HashMap<Unit, UnitAgent> getUnitAgentsMappedByUnit() {
		return unitAgentsMappedByUnit;
	}

	
	
	
	
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
//			
//			UnitAgent agent = null;
//			
			// Construction started event
			if(type.isBuilding()){
				Unit builderUnit = unit.getBuildUnit();
				SCV builderAgent = (SCV) unitAgentsMappedByUnit.get(builderUnit);
				
				// units obtained on start has no event
				if(builderAgent != null){
					builderAgent.onConstructionStarted();
				}
			}
//			
//			if (type.equals(UnitType.Terran_SCV)) {
//				agent = new SCV(unit);
//			}
//			
//			if (type.equals(UnitType.Terran_Marine)) {
//				boolean test = unit.isCompleted();
//				agent = new Marine(unit);
//			}
//			
//			
//			
//			// check beacause we not handle all units creation
//			if(agent != null){
//				addAgent(agent);
//				unitAgents.add(agent);
//				unitAgentsMappedByUnit.put(unit, agent);
//			}
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
	public void onUnitComplete(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral()){
				return;
			}
			
			UnitAgent agent = null;
			
			if(type.equals(UnitType.Terran_SCV)) {
				agent = new SCV(unit);
			}
			if(type.equals(UnitType.Terran_Marine)) {
				agent = new Marine(unit);
			}
			if(type.equals(UnitType.Zerg_Larva)){
				agent = new Larva(unit);
			}
			if(type.equals(UnitType.Zerg_Drone)){
				agent = new Drone(unit);
			}
			if(type.equals(UnitType.Protoss_Probe)){
				agent = new Probe(unit);
			}
			
			
			
			// check beacause we not handle all units creation
			if(agent != null){
				addAgent(agent);
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
	public void onUnitMorph(Unit unit) {
		try{
			UnitType type = unit.getType();

			if(type.isNeutral()){
				return;
			}

			// we skip the egg stadium
			if(type.equals(UnitType.Zerg_Egg)){
				return;
			}

			MorphableUnit formerUnitAgent = (MorphableUnit) unitAgentsMappedByUnit.get(unit);

			// units obtained on start has no event
			if(formerUnitAgent != null){

				UnitAgent agent = null;

				if(type.equals(UnitType.Zerg_Drone)){
					agent = new Drone(unit);
				}
				if(type.equals(UnitType.Zerg_Hatchery)){
					agent = new Hatchery(unit);
				}

				if(agent == null){
					throw new NonImplementedMorphException(formerUnitAgent, type);
				}

				//onMorphFinished event
				formerUnitAgent.onMorphFinished();
				removeAgent(unit);

				// check beacause we not handle all units creation
				if(agent != null){
					addAgent(agent);
				}
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
			
            for (Agent agent : (ArrayList<Agent>) agents.clone()) {
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
			
			// tools
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();

//			commander = new Commander();
			if(game.self().getRace() == Race.Zerg){
				commander = new ZergCommander();
			}
			else{
				commander = new FullCommander();
			}

			commanderStatic = commander;
			

			agents.add(commander);
//			addAgent(new ExplorationCommand());
//			addAgent(new ResourceCommand());
//			addAgent(new BuildCommand());
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

	private void removeAgent(Unit unit) {
		UnitAgent agent = unitAgentsMappedByUnit.remove(unit);
		
		// if the agent has been in the map
		if(agent != null){
			unitAgents.remove(agent);
			agent.getCommandAgent().removeCommandedAgent(agent);
			gameAPI.agents.remove(agent);
		}
	}


	
	
}
