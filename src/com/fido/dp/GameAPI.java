package com.fido.dp;

import bwapi.DefaultBWListener;
import bwapi.Game;
import bwapi.Mirror;
import bwapi.Unit;
import bwapi.UnitType;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.LeafAgent;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;
import java.util.logging.Level;
import static java.util.logging.Level.FINER;
import java.util.logging.Logger;

public class GameAPI extends DefaultBWListener {

    private static Mirror mirror;

    private static Game game;

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
        new GameAPI().run();
    }

    private Commander commander;

    private ArrayList<Agent> agents;

    public void run() {
        agents = new ArrayList();
        mirror = new Mirror();
        mirror.getModule().setEventListener(this);
        mirror.startGame();
    }

    @Override
    public void onUnitCreate(Unit unit) {
        UnitType type = unit.getType();
        if (type.equals(UnitType.Terran_SCV)) {
            LeafAgent agent = new SCV(unit);
            commander.addsubordinateAgent(agent);
            agents.add(agent);
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
        } catch (Exception exception) {
            Log.log(this, Level.SEVERE, "EXCEPTION!");
            exception.printStackTrace();
        } catch (Error error) {
            Log.log(this, Level.SEVERE, "ERROR!");
            error.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        Logger rootLog = Logger.getLogger("");
        bwta.BWTA.readMap();
        bwta.BWTA.analyze();
        rootLog.setLevel(Level.FINE);
        rootLog.getHandlers()[0].setLevel(Level.FINE);
        commander = new Commander();
        agents.add(commander);
        mirror.getGame().setLocalSpeed(42);
        addAgent(new ExplorationCommand());
        addAgent(new ResourceCommand());
        addAgent(new BuildCommand());
    }

    public void addAgent(Agent agent) {
        commander.addsubordinateAgent(agent);
        agents.add(agent);
    }
}
