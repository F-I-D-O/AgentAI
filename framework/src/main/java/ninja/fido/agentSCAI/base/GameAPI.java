/* 
 * AgentAI
 */
package ninja.fido.agentSCAI.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentSCAI.bwapiCommandInterface.BWAPICommandInterface;
import ninja.fido.agentSCAI.bwapiCommandInterface.DefaultBWAPICommandInterface;
import ninja.fido.agentSCAI.buildingPlacer.BuildingPlacer;
import ninja.fido.agentSCAI.EventEngine;
import ninja.fido.agentSCAI.EventEngineListener;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.mapTools.MapTools;
import ninja.fido.agentSCAI.MorphableUnit;
import ninja.fido.agentSCAI.NonImplementedMorphException;
import ninja.fido.agentSCAI.buildingPlacer.UAlbertaBuildingPlacer;
import ninja.fido.agentSCAI.mapTools.UAlbertaMapTools;
import ninja.fido.agentSCAI.agent.unit.Barracks;
import ninja.fido.agentSCAI.agent.unit.Drone;
import ninja.fido.agentSCAI.agent.unit.Larva;
import ninja.fido.agentSCAI.agent.unit.Marine;
import ninja.fido.agentSCAI.agent.ProductionCommand;
import ninja.fido.agentSCAI.agent.unit.SCV;
import ninja.fido.agentSCAI.agent.UnitCommand;
import ninja.fido.agentSCAI.agent.unit.Hatchery;
import ninja.fido.agentSCAI.agent.unit.HighTemplar;
import ninja.fido.agentSCAI.agent.unit.Overlord;
import ninja.fido.agentSCAI.agent.unit.Probe;
import ninja.fido.agentSCAI.agent.unit.UnitAgent;
import ninja.fido.agentSCAI.agent.unit.Worker;
import ninja.fido.agentSCAI.agent.unit.Zealot;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModule;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
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
import ninja.fido.agentSCAI.GameAPIListener;
import ninja.fido.agentSCAI.NoGameAgentForUnitException;
import ninja.fido.agentSCAI.agent.unit.CommandCenter;
import ninja.fido.agentSCAI.agent.unit.Nexus;
import ninja.fido.agentSCAI.agent.unit.SupplyDepot;
import ninja.fido.agentSCAI.base.exception.ModuleDependencyException;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;
import org.xml.sax.SAXException;

/**
 * Game API. Through this API, you access BWMirror, that accesses BWAPI, that accesses StarCraft.
 * @author david
 */
public final class GameAPI extends DefaultBWListener implements EventEngineListener{
	
	/**
	 * static reference to Commander
	 */
	private static Commander commanderStatic;
	
	/**
	 * UAlbertaBot Map tools
	 */
	private static MapTools mapTools;
	
	/**
	 * UAlbetaBot building placer
	 */
	private static BuildingPlacer buildingPlacer;
	
	/**
	 * Game mirror - BWMirror object
	 */
	private static Mirror mirror;

	/**
	 * Game - BWMirror object
	 */
    private static Game game;
	
	/**
	 * singleton reference
	 */
	private static GameAPI gameAPI;
	
	
	/**
	 * Returns commander.
	 * @return Returns commander.
	 */
	public static Commander getCommander(){
		return commanderStatic;
	}

	/**
	 * Returns map tools.
	 * @return Returns map tools.
	 */
	public static MapTools getMapTools() {
		return mapTools;
	}

	/**
	 * Returns building placer.
	 * @return Returns building placer.
	 */
	public static BuildingPlacer getBuildingPlacer() {
		return buildingPlacer;
	}

	
	
	
	/**
	 * Returns Game. Through Game object you can access BWMirror.
	 * @return Returns Game. Through Game object you can access BWMirror.
	 */
    public static Game getGame() {
        if (game == null) {
            game = mirror.getGame();
        }
        return game;
    }
	
	/**
	 * TODO
	 * @param agent
	 * @param commandAgent 
	 */
	public static void addAgent(Agent agent, CommandAgent commandAgent){
		commandAgent.addCommandedAgent(agent);
        gameAPI.agents.add(agent);
		if(agent instanceof GameAgent){
			GameAgent unitAgent = (GameAgent) agent;
			gameAPI.getGameAgents().add(unitAgent);
			gameAPI.getUnitAgentsMappedByUnit().put(unitAgent.getUnit(), unitAgent);
		}
	}
	
	/**
	 * Returns the position of the start base.
	 * @return Returns the position of the start base.
	 */
	public static Position getStartBasePosition(){
		return getGame().self().getStartLocation().toPosition();
	}
	
	/**
	 * Returns current frame count.
	 * @return Returns current frame count.
	 */
	public static int getFrameCount(){
		return gameAPI.frameCount;
	}
	
	/**
	 * Build command wrapper. Handles busy error automaticaly.
	 * @param worker Worker that will be constructing the building.
	 * @param buildingType Building type.
	 * @param placeToBuildOn Place where the building will be build.
	 */
	public static void build(Worker worker, UnitType buildingType, TilePosition placeToBuildOn){
		gameAPI.commandInterface.build(worker, buildingType, placeToBuildOn);
	}
	
	/**
	 * Attack move wrapper. Handles busy error automaticaly.
	 * @param agent Agent.
	 * @param target Attack target
	 */
	public static void attackMove(UnitAgent agent, Position target){
		gameAPI.commandInterface.attackMove(agent, target);
	}
	
	/**
	 * Train command wrapper. Handles busy error automaticaly.
	 * @param agent Agent.
	 * @param unitType Unit type to train.
	 */
	public static void train(GameAgent agent, UnitType unitType){
		gameAPI.commandInterface.train(agent, unitType);
	}
	
	/**
	 * Move command wrapper. Handles busy error automaticaly.
	 * @param agent Agent.
	 * @param target Move target.
	 */
	public static void move(UnitAgent agent, Position target){
		gameAPI.commandInterface.move(agent, target);
	}

	/**
	 * Returns decision table map for agent type.
	 * @param agentClass Agent type.
	 * @return Returns decision table map for agent type.
	 */
	public static Map<DecisionTablesMapKey,DecisionTable> getDecisionTablesMap(Class<? extends Agent> agentClass){
		DecisionModule decisionModule;
		if((decisionModule = (DecisionModule) gameAPI.getRegisteredModule(DecisionModule.class)) == null){
			return null;
		}
		return decisionModule.getDecisionTablesMap(agentClass);
	}
	
	/**
	 * Determines if decision module is turned on for agent type.
	 * @param agent Agent type.
	 * @return True if decision module is turned on and agent type is registered to it.
	 */
	public static boolean isDecisionMakingOn(Agent agent){
		DecisionModule decisionModule;
		if((decisionModule = (DecisionModule) gameAPI.getRegisteredModule(DecisionModule.class)) == null){
			return false;
		}
		return decisionModule.isDecisionMakingOn(agent);
	}
	
	/**
	 * Adds new simple decision map.
	 * @param agentClass Agent type.
	 * @param simpleDecisionsMap New simple decision map for agent type.
	 */
	public static void addSimpleDecisionMap(
			Class<? extends Agent> agentClass, Map<Class<? extends Goal>,Activity> simpleDecisionsMap){
		gameAPI.agentSimpleDecisions.put(agentClass, simpleDecisionsMap);
	}
	
	/**
	 * Returns simple decision map for agent type.
	 * @param agentClass Agent type.
	 * @return Returns simple decision map for agent type.
	 */
	public static Map<Class<? extends Goal>,Activity> getSimpleDecisionsMap(Class<? extends Agent> agentClass){
		return gameAPI.agentSimpleDecisions.get(agentClass);
	}
	
	/**
	 * Determines if game API is ready.
	 * @return True if game API is ready, false otherwwise.
	 */
	public static boolean ready(){
		return gameAPI != null;
	}
	
	
	
	
	
	
	
	/**
	 * Event engine. Can generate events that are not generated by BWAPI.
	 */
	private final EventEngine eventEngine;
	
	/**
	 * Reference to commander.
	 */
	private Commander commander;
	
	/**
	 * List of all agents.
	 */
	private ArrayList<Agent> agents;
	
	/**
	 * List of all game agents.
	 */
	private List<GameAgent> gameAgents;
	
	/**
	 * List of all game agents mapped by unit.
	 */
	private HashMap<Unit,GameAgent> gameAgentsMappedByUnit;
	
	/**
	 * Frame count.
	 */
	private int frameCount;
	
	/**
	 * Game count. Used when there are multiple game in row.
	 */
	private int gameCount;
	
	/**
	 * Log level. Use standard java levels.
	 */
	private final Level logLevel;
	
	/**
	 * Game speeed.
	 */
	private final int gameSpeed;
	
	/**
	 * Frame skip. Determines how many logical franes will be skipped on screen (logical to screen frame ratio). 
	 */
	private final int frameSkip;

	/**
	 * Command interface.
	 */
	private final BWAPICommandInterface commandInterface;
	
	/**
	 * List of registered modules.
	 */
	private final List<GameAPIModule> registeredModules;
	
	/**
	 * Map of workers mapped by unfinished buildings.
	 */
	private final Map<Unit,Worker> unfinishedBuildings;
	
	/**
	 * Simple decision tables maped by agent type.
	 */
	private final Map<Class<? extends Agent>,Map<Class<? extends Goal>,Activity>> agentSimpleDecisions;
	
	/**
	 * Registered game agents mapped by BWAPI unit types.
	 */
	private final Map<UnitType,GameAgent> registeredGameAgents;
	
	/**
	 * Game API listeners.
	 */
	private final ArrayList<GameAPIListener> listeners;
	
	
	
	/**
	 * Returns list of all game agents.
	 * @return Returns list of all game agents.
	 */
	private List<GameAgent> getGameAgents() {
		return gameAgents;
	}

	/**
	 * Return map of game agents mapped by unit.
	 * @return Return map of game agents mapped by unit.
	 */
	private HashMap<Unit, GameAgent> getUnitAgentsMappedByUnit() {
		return gameAgentsMappedByUnit;
	}

	/**
	 * Returs log level.
	 * @return Returs log level.
	 */
	public Level getLogLevel() {
		return logLevel;
	}

	
//	public void setLogLevel(Level logLevel) {
//		this.logLevel = logLevel;
//	}
	
	
	
	
	/**
	 * Constructor.
	 * @param logLevel Standard java log level.
	 * @param gameSpeed Game speed. Max speed is 0, standard speed is arround TODO.
	 * @param frameSkip Frame skip. Determines how many logical franes will be skipped on screen (logical to screen 
	 * frame ratio). 
	 * @param commander Commander.
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws ClassNotFoundException
	 * @throws TransformerException
	 * @throws TransformerConfigurationException
	 * @throws XPathExpressionException 
	 */
	public GameAPI(final Level logLevel, int gameSpeed, int frameSkip, Commander commander) throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, 
			TransformerException, TransformerConfigurationException, XPathExpressionException {
		frameCount = 1;
		gameCount = 1;
		registeredModules = new ArrayList<>();
		unfinishedBuildings = new HashMap<>();
		agentSimpleDecisions = new HashMap<>();
		registeredGameAgents = new HashMap<>();
		listeners = new ArrayList<>();
		
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
		
//		// register default game agents
//		registerDefaultGameAgents();
		
		// static access
		gameAPI = this;
	}
	
	/**
	 * Returns registered module by class.
	 * @param moduleType Module type.
	 * @return Returns registered module by class.
	 */
	public GameAPIModule getRegisteredModule(Class<? extends GameAPIModule> moduleType){
		for (GameAPIModule module : registeredModules) {
			if(moduleType.isInstance(module)){
				return module;
			}
		}
		return null;
	}
	
	/**
	 * Returns true if module is registered.
	 * @param moduleType Module type.
	 * @return Returns true if module is registered.
	 */
	public boolean moduleRegistered(Class<? extends GameAPIModule> moduleType){
		for (GameAPIModule module : registeredModules) {
			if(moduleType.isInstance(module)){
				return true;
			}
		}
		return false;
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
				SCV builderAgent = (SCV) gameAgentsMappedByUnit.get(builderUnit);
				
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
			
			GameAgent agent = getRegisteredAgent(type, unit, null);
			
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

			MorphableUnit formerUnitAgent = (MorphableUnit) gameAgentsMappedByUnit.get(unit);

			// units obtained on start has no event
			if(formerUnitAgent != null && !type.isBuilding()){

				GameAgent agent = getRegisteredAgent(type, unit, formerUnitAgent);

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
			
			// process queued commands from units that was busy last frame 
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
			
			// register default game agents
			registerDefaultGameAgents();
			
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
			gameAgents = new ArrayList<>();
			gameAgentsMappedByUnit = new HashMap<>();

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
			for (GameAPIModule module : registeredModules) {
				module.onStart(gameCount);
			}	
			
			// listeners
			for (GameAPIListener listener : listeners) {
				listener.onStart();
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
		try {
			Log.log(this, Level.INFO, "Building construction finished: {0}", building.getBuildType());
			
			Worker builderAgent = unfinishedBuildings.get(building);
			
			// units obtained on start has no event
			if(builderAgent != null){
				builderAgent.onConstructionFinished();
			}
			
			UnitType buildingType = building.getType();
			GameAgent buildingAgent = getRegisteredAgent(buildingType, building, null);
			
			if(buildingAgent != null){
				addAgent(buildingAgent);
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
	public void onUnitDiscover(Unit unit) {
		
		// we are only interested in enemz units
		if(unit.getPlayer().isEnemy(getGame().self())){
			UnitType type = unit.getType();
			
			// buildings
			if(type.isBuilding()){
				for (GameAgent agent : gameAgents) {
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
			for (GameAPIModule module : registeredModules) {
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
	
	/**
	 * Start method. Call this when gameAPI is ready and you want to start the AI.
	 * @throws SAXException
	 * @throws XPathExpressionException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws ClassNotFoundException
	 * @throws TransformerException 
	 */
    public void run() throws SAXException, XPathExpressionException, IOException, ParserConfigurationException, 
			ClassNotFoundException, TransformerException {
		
		//log
		Log.init(logLevel);
		
		// mirror
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
		
		//modules
		for (GameAPIModule module : registeredModules) {
			module.onRun();
		}	
		
        mirror.startGame();
    }
	
	/**
	 * Returns agent by type.
	 * @param agentClass Agent type.
	 * @return Returns agent by type.
	 */
	public Agent getAgent(Class<? extends Agent> agentClass) {
        for (Agent agent : agents) {
            if (agentClass.isInstance(agent)) {
                return agent;
            }
        }
        return null;
    }
	
	/**
	 * Register module to gameAPI. 
	 * @param module Module.
	 * @throws ModuleDependencyException 
	 */
	public void registerModule(GameAPIModule module) throws ModuleDependencyException{
		List<Class<? extends GameAPIModule>> dependencies = module.getDependencies();
		if(dependencies != null){
			for (Class<? extends GameAPIModule> dependency : dependencies) {
				boolean moduleRegistered = false;
				for(GameAPIModule registeredModule : registeredModules){
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

	
	/**
	 * Adds agent to the system.
	 * @param agent Agent.
	 */
    private void addAgent(Agent agent) {
		addAgent(agent, commander);
    }

	/**
	 * Removes game agent from the system. Used when unit dies.
	 * @param unit Unit.
	 */
	private void removeAgent(Unit unit) {
		GameAgent agent = gameAgentsMappedByUnit.remove(unit);
		
		// if the agent has been in the map
		if(agent != null){
			gameAgents.remove(agent);
			agent.getCommandAgent().removeCommandedAgent(agent);
			gameAPI.agents.remove(agent);
		}
	}

	/**
	 * Registers default game agents.
	 */
	private void registerDefaultGameAgents() {
		try {
			registerGameAgent(UnitType.Terran_Barracks, new Barracks());
			registerGameAgent(UnitType.Terran_SCV, new SCV());
			registerGameAgent(UnitType.Terran_Marine, new Marine());
			registerGameAgent(UnitType.Terran_Command_Center, new CommandCenter());
			registerGameAgent(UnitType.Terran_Supply_Depot, new SupplyDepot());
			registerGameAgent(UnitType.Zerg_Drone, new Drone());
			registerGameAgent(UnitType.Zerg_Overlord, new Overlord());
			registerGameAgent(UnitType.Zerg_Larva, new Larva());
			registerGameAgent(UnitType.Zerg_Hatchery, new Hatchery());
			registerGameAgent(UnitType.Protoss_Probe, new Probe());
			registerGameAgent(UnitType.Protoss_Zealot, new Zealot());
			registerGameAgent(UnitType.Protoss_High_Templar, new HighTemplar());
			registerGameAgent(UnitType.Protoss_Nexus, new Nexus());
		} catch (EmptyDecisionTableMapException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Registers new listerner in the API.
	 * @param listener Listener.
	 */
	public void registerListener(GameAPIListener listener){
		listeners.add(listener);
	}
	
	/**
	 * Register game agent to BWAPI. Each game agent is map to one unit. If more than one game agents is map to one 
	 * unit, the latest agent is used.
	 * @param unitType BWAPI unit type.
	 * @param agent Game agent.
	 */
	public void registerGameAgent(UnitType unitType, GameAgent agent){
		registeredGameAgents.put(unitType, agent);
	}
	
	/**
	 * Returns registered game agent for BWAPI unit type.
	 * @param type BWAPI unit type.
	 * @param unit BWAPI unit.
	 * @return Returns registered game agent for BWAPI unit type.
	 */
	private GameAgent getRegisteredAgent(UnitType type, Unit unit, MorphableUnit formerUnitAgent) 
			throws EmptyDecisionTableMapException, NonImplementedMorphException, NoGameAgentForUnitException {
		GameAgent template = registeredGameAgents.get(type);
		
		if(template == null){
			if(formerUnitAgent == null){
				throw new NoGameAgentForUnitException(type);
			}
			else{
				throw new NonImplementedMorphException(formerUnitAgent, type);
			}
		}
		
		return template.create(unit);
	}

}
