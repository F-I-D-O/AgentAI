package com.fido.dp.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Race;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.BWAPICommandInterface;
import com.fido.dp.DefaultBWAPICommandInterface;
import com.fido.dp.BuildingPlacer;
import com.fido.dp.EventEngine;
import com.fido.dp.EventEngineListener;
import com.fido.dp.InvalidTilePositionException;
import com.fido.dp.Log;
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
import com.fido.dp.agent.unit.HighTemplar;
import com.fido.dp.agent.unit.Overlord;
import com.fido.dp.agent.unit.Probe;
import com.fido.dp.agent.unit.UnitAgent;
import com.fido.dp.agent.unit.Worker;
import com.fido.dp.agent.unit.Zealot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

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
		if(agent instanceof GameAgent){
			GameAgent unitAgent = (GameAgent) agent;
			gameAPI.getUnitAgents().add(unitAgent);
			gameAPI.getUnitAgentsMappedByUnit().put(unitAgent.getUnit(), unitAgent);
		}
	}
	
	public static Position getStartBasePosition(){
		return getGame().self().getStartLocation().toPosition();
	}
	
	public static int getFrameCount(){
		return gameAPI.frameCount;
	}
	
	public static void build(Worker worker, UnitType buildingType, TilePosition placeToBuildOn){
		gameAPI.commandInterface.build(worker, buildingType, placeToBuildOn);
	}
	
	public static void attackMove(UnitAgent agent, Position target){
		gameAPI.commandInterface.attackMove(agent, target);
	}
	
	public static void train(GameAgent agent, UnitType unitType){
		gameAPI.commandInterface.train(agent, unitType);
	}
	
	public static void move(UnitAgent agent, Position target){
		gameAPI.commandInterface.move(agent, target);
	}
	
	
	
	
	private final EventEngine eventEngine;
	
	private Commander commander;
	
	private final ArrayList<Agent> agents;
	
	private final ArrayList<GameAgent> unitAgents;
	
	private final HashMap<Unit,GameAgent> unitAgentsMappedByUnit;
	
	private int frameCount;
	
	private final Level logLevel;

	private final BWAPICommandInterface commandInterface;
	
	
	private ArrayList<GameAgent> getUnitAgents() {
		return unitAgents;
	}

	private HashMap<Unit, GameAgent> getUnitAgentsMappedByUnit() {
		return unitAgentsMappedByUnit;
	}

	
	
	
	
	public GameAPI() {
		this.frameCount = 0;
		agents = new ArrayList<>();
		unitAgents = new ArrayList<>();
		unitAgentsMappedByUnit = new HashMap<>();
		eventEngine =  new EventEngine();
		eventEngine.addListener(this);
		logLevel = Level.FINE;
		commandInterface = new DefaultBWAPICommandInterface();
	}
	
	
	
	
	@Override
    public void onUnitCreate(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral()){
				return;
			}
//			
//			GameAgent agent = null;
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
			
			if(type.isNeutral() || !unit.getPlayer().equals(getGame().self())){
				return;
			}
			
			GameAgent agent = null;
			
			if(type.equals(UnitType.Terran_SCV)) {
				agent = new SCV(unit);
			}
			else if(type.equals(UnitType.Terran_Marine)) {
				agent = new Marine(unit);
			}
			else if(type.equals(UnitType.Zerg_Larva)){
				agent = new Larva(unit);
			}
			else if(type.equals(UnitType.Zerg_Drone)){
				agent = new Drone(unit);
			}
			else if(type.equals(UnitType.Protoss_Probe)){
				agent = new Probe(unit);
			}
			else if(type.equals(UnitType.Protoss_Zealot)){
				agent = new Zealot(unit);
			}
			else if(type.equals(UnitType.Protoss_High_Templar)){
				agent = new HighTemplar(unit);
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

				GameAgent agent = null;

				if(type.equals(UnitType.Zerg_Drone)){
					agent = new Drone(unit);
				}
				else if(type.equals(UnitType.Zerg_Overlord)){
					agent = new Overlord(unit);
				}

				if(agent == null && !type.isBuilding()){
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
			
			// process queued commands from units that was busz last frame 
			commandInterface.processQueuedCommands();
			
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
			
			// BWTA init
			bwta.BWTA.readMap();
			bwta.BWTA.analyze();

			// game settings
			getGame().setLocalSpeed(10);
			getGame().setFrameSkip(0);
			getGame().enableFlag(bwapi.Flag.Enum.UserInput.getValue());
			
			// tools
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();

			// commander init
//			commander = new Commander();
			if(game.self().getRace() == Race.Zerg){
				commander = new ZergCommander();
			}
			else{
				commander = new FullCommander();
			}
			commanderStatic = commander;
			agents.add(commander);
			
			// other command agents to start the game with
//			addAgent(new ExplorationCommand());
//			addAgent(new ResourceCommand());
//			addAgent(new BuildCommand());
			addAgent(new ProductionCommand());
			addAgent(new UnitCommand());
			
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
		
		GameAgent buildingAgent = null;
		UnitType buildingType = building.getType();
		
		if (buildingType.equals(UnitType.Terran_Barracks)) {
			buildingAgent = new Barracks(building);
		}
		else if(buildingType.equals(UnitType.Zerg_Hatchery)){
			buildingAgent = new Hatchery(building);
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
				for (GameAgent agent : unitAgents) {
					if(agent.canSeeUnit(unit)){
						agent.onEnemyBuildingDiscoverd(unit);
					}
				}
			}
		}
	}
	
	

	
    public void run() {
		Log.init(logLevel);
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    public void addAgent(Agent agent) {
		addAgent(agent, commander);
    }

	private void removeAgent(Unit unit) {
		GameAgent agent = unitAgentsMappedByUnit.remove(unit);
		
		// if the agent has been in the map
		if(agent != null){
			unitAgents.remove(agent);
			agent.getCommandAgent().removeCommandedAgent(agent);
			gameAPI.agents.remove(agent);
		}
	}


	
	
}
