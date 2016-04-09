package ninja.fido.agentai.base;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Position;
import bwapi.Race;
import bwapi.TilePosition;
import bwapi.Unit;
import bwapi.UnitType;
import ninja.fido.agentai.BWAPICommandInterface;
import ninja.fido.agentai.DefaultBWAPICommandInterface;
import ninja.fido.agentai.BuildingPlacer;
import ninja.fido.agentai.decisionStorage.DecisionStorageModule;
import ninja.fido.agentai.EventEngine;
import ninja.fido.agentai.EventEngineListener;
import ninja.fido.agentai.learning.LearningModule;
import ninja.fido.agentai.Log;
import ninja.fido.agentai.MapTools;
import ninja.fido.agentai.MorphableUnit;
import ninja.fido.agentai.NonImplementedMorphException;
import ninja.fido.agentai.UAlbertaBuildingPlacer;
import ninja.fido.agentai.UAlbertaMapTools;
import ninja.fido.agentai.activity.ASAPSquadAttackMove;
import ninja.fido.agentai.activity.NormalSquadAttackMove;
import ninja.fido.agentai.activity.Wait;
import ninja.fido.agentai.agent.unit.Barracks;
import ninja.fido.agentai.agent.Commander;
import ninja.fido.agentai.agent.unit.Drone;
import ninja.fido.agentai.agent.FullCommander;
import ninja.fido.agentai.agent.unit.Larva;
import ninja.fido.agentai.agent.unit.Marine;
import ninja.fido.agentai.agent.ProductionCommand;
import ninja.fido.agentai.agent.SquadCommander;
import ninja.fido.agentai.agent.unit.SCV;
import ninja.fido.agentai.agent.UnitCommand;
import ninja.fido.agentai.agent.ZergCommander;
import ninja.fido.agentai.agent.unit.Hatchery;
import ninja.fido.agentai.agent.unit.HighTemplar;
import ninja.fido.agentai.agent.unit.Overlord;
import ninja.fido.agentai.agent.unit.Probe;
import ninja.fido.agentai.agent.unit.UnitAgent;
import ninja.fido.agentai.agent.unit.Worker;
import ninja.fido.agentai.agent.unit.Zealot;
import ninja.fido.agentai.decisionMaking.DecisionModule;
import ninja.fido.agentai.decisionMaking.DecisionTable;
import ninja.fido.agentai.decisionMaking.DecisionTablesMapKey;
import ninja.fido.agentai.decisionMaking.GoalParameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
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

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, 
			ClassNotFoundException, TransformerException, TransformerConfigurationException, XPathExpressionException {
//		String line;
//		ObjectMapper mapper = new ObjectMapper();
//		try (
//			InputStream fis = new FileInputStream("result.json");
//			InputStreamReader isr = new InputStreamReader(fis);
//			BufferedReader br = new BufferedReader(isr);
//		)
//		{
//			while ((line = br.readLine()) != null) {
//				GameResult gameResult = mapper.readValue(line, GameResult.class);
//				gameResults.add(gameResult);
//			}
//		}


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
	
	public static boolean loadDecisionsFromStoregeOn(){
		return gameAPI.loadDecisionsFromStorege;
	}
	
	public static Map<DecisionTablesMapKey,DecisionTable> getDecisionTablesMap(Class<? extends Agent> agentClass){
		return gameAPI.decisionModule.getDecisionTablesMap(agentClass);
	}
	
	public static boolean isDecisionMakingOn(Agent agent){
		return gameAPI.decisionModule.isDecisionMakingOn(agent);
	}
	
	
	
	private final EventEngine eventEngine;
	
	private Commander commander;
	
	private ArrayList<Agent> agents;
	
	private ArrayList<GameAgent> unitAgents;
	
	private HashMap<Unit,GameAgent> unitAgentsMappedByUnit;
	
	private int frameCount;
	
	private final Level logLevel;

	private final BWAPICommandInterface commandInterface;
	
	private final DecisionStorageModule decisionStorageModule;
	
	private final LearningModule learningModule;
	
	private boolean loadDecisionsFromStorege;
	
	private boolean learningOn;
	
	private final DecisionModule decisionModule;
	
	
	private ArrayList<GameAgent> getUnitAgents() {
		return unitAgents;
	}

	private HashMap<Unit, GameAgent> getUnitAgentsMappedByUnit() {
		return unitAgentsMappedByUnit;
	}

	
	
	
	
	public GameAPI() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, 
			TransformerException, TransformerConfigurationException, XPathExpressionException {
		this.frameCount = 0;
		eventEngine =  new EventEngine();
		eventEngine.addListener(this);
		logLevel = Level.FINE;
		commandInterface = new DefaultBWAPICommandInterface();
		decisionModule = new DecisionModule();
		decisionStorageModule = new DecisionStorageModule(decisionModule);
		learningModule = new LearningModule(this, decisionStorageModule);
	}
	
	
	
	
	@Override
    public void onUnitCreate(Unit unit) {
		try{
			UnitType type = unit.getType();
			
			if(type.isNeutral()){
				return;
			}

			// Construction started event
			if(type.isBuilding()){
				Unit builderUnit = unit.getBuildUnit();
				SCV builderAgent = (SCV) unitAgentsMappedByUnit.get(builderUnit);
				
				// units obtained on start has no event
				if(builderAgent != null){
					builderAgent.onConstructionStarted();
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
			getGame().setLocalSpeed(GAME_SPEED);
			getGame().setFrameSkip(0);
			getGame().enableFlag(bwapi.Flag.Enum.UserInput.getValue());
			
			// tools
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();
			
			// collections
			agents = new ArrayList<>();
			unitAgents = new ArrayList<>();
			unitAgentsMappedByUnit = new HashMap<>();

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

	@Override
	public void onEnd(boolean isWinner) {
		try{
			int score = getGame().self().getKillScore();
			
			learningModule.saveResult(isWinner, score);
			
			
//			XMLOutputFactory xMLOutputFactory = XMLOutputFactory.newInstance();
//			XMLStreamWriter writer = xMLOutputFactory.createXMLStreamWriter(new FileWriter("result.json", true));
//			
//			if(newFile){
//				writer.writeStartDocument("UTF-8", "1.0");
//				writer.writeStartElement("results");
//			}
//			
//			
//			if(newFile){
//				writer.writeEndElement();
//				writer.writeEndDocument();
//			}	
//			
			
			

//			ObjectMapper mapper = new ObjectMapper();
//			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
//			File file = new File("result.json");
//			FileWriter writer = new FileWriter("result.json", true);
//			String jsonInString = mapper.writeValueAsString(gameResult);
//			try {
//				mapper.writeValue(file, gameResult);
//				writer.append(System.getProperty("line.separator"));
//				writer.append(jsonInString);
//				writer.close();
				
//			} catch (IOException ex) {
////				 Log.log(this, Level.SEVERE, "Cannot write to file! Path: {0}", writer.getAbsolutePath());
//				 ex.printStackTrace();
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
		Log.init(logLevel);
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
		gameAPI.init();
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

	private void registerActivities() {
		decisionStorageModule.registerActivity(new Wait());
		decisionStorageModule.registerActivity(new ASAPSquadAttackMove());
		decisionStorageModule.registerActivity(new NormalSquadAttackMove());
	}

	private void registerDecisionParameters() {
		decisionStorageModule.registerParameter(new GoalParameter(null));
	}

	private void registerDecisionMakingAgentTypes() {
		decisionModule.registerAgentClass(new FullCommander());
		decisionModule.registerAgentClass(new SquadCommander());
	}

	private void init() throws SAXException, IOException, ParserConfigurationException, ClassNotFoundException, 
			TransformerException, TransformerConfigurationException, XPathExpressionException {
		registerDecisionMakingAgentTypes();
		
		loadDecisionsFromStorege = true;
		if(loadDecisionsFromStorege){
			registerActivities();
			registerDecisionParameters();
			decisionStorageModule.loadSettings();
		}
		
		learningOn = true;
		if(learningOn){
			learningModule.processResults();		
		}
	}
	
	
}
