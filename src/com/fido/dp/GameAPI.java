package com.fido.dp;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.base.Agent;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.base.LeafAgent;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameAPI extends DefaultBWListener {
	
	private static Commander commanderStatic;
	
	private static MapTools mapTools;
	
	private static BuildingPlacer buildingPlacer;
	
	
	
	public static Commander getCommander(){
		return commanderStatic;
	}

	public static MapTools getMapTools() {
		return mapTools;
	}

	public static BuildingPlacer getBuildingPlacer() {
		return buildingPlacer;
	}
	
	
	
	
	
	public static void main(String[] args) {
        new GameAPI().run();
    }
	
	
	
	

    private static Mirror mirror;

    private static Game game;
	
	private Commander commander;
	
	private ArrayList<Agent> agents;
		
		
		

    public static Mirror getMirror() {
        return mirror;
    }

    public static Game getGame() {
        if (game == null) {
            game = mirror.getGame();
        }
        return game;
    }
	



    public void run() {
        agents = new ArrayList();
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    @Override
    public void onUnitCreate(Unit unit) {
		try{
			UnitType type = unit.getType();
			if (type.equals(UnitType.Terran_SCV)) {
				LeafAgent agent = new SCV(unit);
				commander.addsubordinateAgent(agent);
				agents.add(agent);
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
            Log.log(this, Level.FINE, "OnFrame start");
            Log.log(this, Level.FINE, "Number of agents: {0}", agents.size());
            for (Agent agent : agents) {
                agent.run();
            }
            Log.log(this, Level.FINE, "OnFrame end");
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
			rootLog.setLevel(Level.FINEST);
			rootLog.getHandlers()[0].setLevel(Level.FINEST);
			rootLog.getHandlers()[0].setFormatter(new LogFormater());

			commander = new Commander();

			commanderStatic = commander;
			mapTools = new UAlbertaMapTools();
			buildingPlacer = new UAlbertaBuildingPlacer();

			agents.add(commander);
			mirror.getGame().setLocalSpeed(42);
			addAgent(new ExplorationCommand());
			addAgent(new ResourceCommand());
			addAgent(new BuildCommand());
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

    public void addAgent(Agent agent) {
        commander.addsubordinateAgent(agent);
        agents.add(agent);
    }
}
