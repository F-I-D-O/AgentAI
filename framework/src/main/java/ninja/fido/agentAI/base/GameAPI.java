package ninja.fido.agentAI.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentAI.bwapiCommandInterface.BWAPICommandInterface;
import ninja.fido.agentAI.bwapiCommandInterface.DefaultBWAPICommandInterface;
import ninja.fido.agentAI.buildingPlacer.BuildingPlacer;
import ninja.fido.agentAI.EventEngine;
import ninja.fido.agentAI.EventEngineListener;
import ninja.fido.agentAI.Log;
import ninja.fido.agentAI.mapTools.MapTools;
import ninja.fido.agentAI.MorphableUnit;
import ninja.fido.agentAI.NonImplementedMorphException;
import ninja.fido.agentAI.buildingPlacer.UAlbertaBuildingPlacer;
import ninja.fido.agentAI.mapTools.UAlbertaMapTools;
import ninja.fido.agentAI.agent.unit.Barracks;
import ninja.fido.agentAI.agent.unit.Drone;
import ninja.fido.agentAI.agent.unit.Larva;
import ninja.fido.agentAI.agent.unit.Marine;
import ninja.fido.agentAI.agent.ProductionCommand;
import ninja.fido.agentAI.agent.unit.SCV;
import ninja.fido.agentAI.agent.UnitCommand;
import ninja.fido.agentAI.agent.unit.Hatchery;
import ninja.fido.agentAI.agent.unit.HighTemplar;
import ninja.fido.agentAI.agent.unit.Overlord;
import ninja.fido.agentAI.agent.unit.Probe;
import ninja.fido.agentAI.agent.unit.UnitAgent;
import ninja.fido.agentAI.agent.unit.Worker;
import ninja.fido.agentAI.agent.unit.Zealot;
import ninja.fido.agentAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentAI.modules.decisionMaking.DecisionTablesMapKey;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import ninja.fido.agentAI.base.exception.ModuleDependencyException;
import ninja.fido.agentAI.modules.decisionMaking.EmptyDecisionTableMapException;
import org.xml.sax.SAXException;

public class GameAPI extends DefaultBWListener implements EventEngineListener{
	
	private static final int GAME_SPEED = 0;
	
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

	
	public static Map<DecisionTablesMapKey,DecisionTable> getDecisionTablesMap(Class<? extends Agent> agentClass){
		DecisionModule decisionModule;
		if((decisionModule = (DecisionModule) gameAPI.getRegisteredModule(DecisionModule.class)) == null){
			return null;
		}
		return decisionModule.getDecisionTablesMap(agentClass);
	}
	
	public static boolean isDecisionMakingOn(Agent agent){
		DecisionModule decisionModule;
		if((decisionModule = (DecisionModule) gameAPI.getRegisteredModule(DecisionModule.class)) == null){
			return false;
		}
		return decisionModule.isDecisionMakingOn(agent);
	}
	
	public static void addSimpleDecisionMap(
			Class<? extends Agent> agentClass, Map<Class<? extends Goal>,Activity> simpleDecisionsMap){
		gameAPI.agentSimpleDecisions.put(agentClass, simpleDecisionsMap);
	}
	
	public static Map<Class<? extends Goal>,Activity> getSimpleDecisionsMap(Class<? extends Agent> agentClass){
		return gameAPI.agentSimpleDecisions.get(agentClass);
	}
	
	public static boolean ready(){
		return gameAPI != null;
	}
	
	
	
	
	
	public GameApiModule getRegisteredModule(Class<? extends GameApiModule> moduleType){
		for (GameApiModule module : registeredModules) {
			if(moduleType.isInstance(module)){
				return module;
			}
		}
		return null;
	}
	
	public boolean moduleRegistered(Class<? extends GameApiModule> moduleType){
		for (GameApiModule module : registeredModules) {
			if(moduleType.isInstance(module)){
				return true;
			}
		}
		return false;
	}
	
	
	
	private final EventEngine eventEngine;
	
	private Commander commander;
	
	private ArrayList<Agent> agents;
	
	private List<GameAgent> unitAgents;
	
	private HashMap<Unit,GameAgent> unitAgentsMappedByUnit;
	
	private int frameCount;
	
	private int gameCount;
	
	private final Level logLevel;
	
	private final int gameSpeed;
	
	private final int frameSkip;

	private final BWAPICommandInterface commandInterface;
	
	private final List<GameApiModule> registeredModules;
	
	private final Map<Unit,Worker> unfinishedBuildings;
	
	private final Map<Class<? extends Agent>,Map<Class<? extends Goal>,Activity>> agentSimpleDecisions;
	
	
	
	
	private List<GameAgent> getUnitAgents() {
		return unitAgents;
	}

	private HashMap<Unit, GameAgent> getUnitAgentsMappedByUnit() {
		return unitAgentsMappedByUnit;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	
//	public void setLogLevel(Level logLevel) {
//		this.logLevel = logLevel;
//	}

	public void setCommander(Commander commander) {
		this.commander = commander;
	}

	
	
	
	
	public GameAPI(final Level logLevel, int gameSpeed, int frameSkip, Commander commander) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, 
			TransformerException, TransformerConfigurationException, XPathExpressionException {
		frameCount = 1;
		gameCount = 1;
		registeredModules = new ArrayList<>();
		unfinishedBuildings = new HashMap<>();
		agentSimpleDecisions = new HashMap<>();
		this.logLevel = logLevel;
		this.gameSpeed = gameSpeed;
		this.frameSkip = frameSkip;
		
		// commander
		this.commander = commander;
		
		// event engine
		eventEngine =  new EventEngine();
		eventEngine.addListener(this);
		
		// command interface
		commandInterface = new DefaultBWAPICommandInterface();
		
		// static access
		gameAPI = this;
	}
	
	
	
	
	@Override
    public void onUnitCreate(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral()){
				return;
			}

			// Construction started event and other
			if(type.isBuilding()){
				Unit builderUnit = unit.getBuildUnit();
				SCV builderAgent = (SCV) unitAgentsMappedByUnit.get(builderUnit);
				
				// units obtained on start has no event
				if(builderAgent != null){
					builderAgent.onConstructionStarted();
					unfinishedBuildings.put(unit, builderAgent);
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
	public void onUnitComplete(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral() || !unit.getPlayer().equals(getGame().self())){
				return;
			}
			
			// building finished
			if(type.isBuilding()){
				onBuildingConstructionFinished(unit);
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
            Log.log(this, Level.INFO, "OnFrame start - game: {0}, frame: {1}", gameCount, frameCount);
			
			// process queued commands from units that was busz last frame 
			commandInterface.processQueuedCommands();
			
//			eventEngine.run();
			
            Log.log(this, Level.FINE, "Number of agents: {0}", agents.size());
			
            for (Agent agent : (ArrayList<Agent>) agents.clone()) {
                agent.run();
            }
            Log.log(this, Level.INFO, "OnFrame end - game: {0}, frame: {1}", gameCount, frameCount);
			frameCount++;
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
			
			// BWTA onInitialize
			bwta.BWTA.readMap();
			bwta.BWTA.analyze();

			// game settings
			getGame().setLocalSpeed(gameSpeed);
			getGame().setFrameSkip(frameSkip);
			getGame().enableFlag(bwapi.Flag.Enum.UserInput.getValue());
			
			// mapTools
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();
			
			// collections
			agents = new ArrayList<>();
			unitAgents = new ArrayList<>();
			unitAgentsMappedByUnit = new HashMap<>();

			// commander onInitialize
			Commander.onStart();
			commander = commander.create();
//			commander = new Commander();
//			if(game.self().getRace() == Race.Zerg){
//				commander = ZergCommander.create("Zerg");
//			}
//			else{
//				commander = FullCommander.create("Full");
//			}
			commanderStatic = commander;
			agents.add(commander);
			
			// other command agents to start the game with
//			addAgent(new ExplorationCommand());
//			addAgent(new ResourceCommand());
//			addAgent(new BuildCommand());
			addAgent(new ProductionCommand());
			addAgent(new UnitCommand());
			
			//modules
			for (GameApiModule module : registeredModules) {
				module.onStart(gameCount);
			}	
			
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
		Log.log(this, Level.INFO, "Building construction finished: {0}", building.getBuildType());

		Worker builderAgent = unfinishedBuildings.get(building);
		
		// units obtained on start has no event
		if(builderAgent != null){
			builderAgent.onConstructionFinished();
		}
		
		GameAgent buildingAgent = null;
		UnitType buildingType = building.getType();
		
		if (buildingType.equals(UnitType.Terran_Barracks)) {
			try {
				buildingAgent = new Barracks(building);
			} catch (EmptyDecisionTableMapException ex) {
				ex.printStackTrace();
			}
		}
		else if(buildingType.equals(UnitType.Zerg_Hatchery)){
			try {
				buildingAgent = new Hatchery(building);
			} catch (EmptyDecisionTableMapException ex) {
				ex.printStackTrace();
			}
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

	@Override
	public void onEnd(boolean isWinner) {
		try{
			int score = getGame().self().getKillScore();
			
			//modules
			for (GameApiModule module : registeredModules) {
				module.onEnd(isWinner, score);
			}
			
			gameCount++;
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
	public void onUnitDestroy(Unit unit) {
		try{
			removeAgent(unit);
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
	
	
	
	

	
    public void run() throws SAXException, XPathExpressionException, IOException, ParserConfigurationException, 
			ClassNotFoundException, TransformerException {
		
		//log
		Log.init(logLevel);
		
		// mirror
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
		
		//modules
		for (GameApiModule module : registeredModules) {
			module.onRun();
		}	
		
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

	public Agent getAgent(Class<? extends Agent> agentClass) {
        for (Agent agent : agents) {
            if (agentClass.isInstance(agent)) {
                return agent;
            }
        }
        return null;
    }
	
	public void registerModule(GameApiModule module) throws ModuleDependencyException{
		List<Class<? extends GameApiModule>> dependencies = module.getDependencies();
		if(dependencies != null){
			for (Class<? extends GameApiModule> dependency : dependencies) {
				boolean moduleRegistered = false;
				for(GameApiModule registeredModule : registeredModules){
					if(dependency.isInstance(registeredModule)){
						moduleRegistered = true;
						break;
					}
				}
				if(!moduleRegistered){
					throw new ModuleDependencyException(module.getClass(), dependency);
				}
			}
		}
		registeredModules.add(module);
	}
	

	
}
