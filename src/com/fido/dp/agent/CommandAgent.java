package com.fido.dp.agent;

import com.fido.dp.Log;
import com.fido.dp.Supply;
import com.fido.dp.Material;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class CommandAgent extends Agent {

    protected ArrayList<Agent> subordinateAgents;
    
    private Supply minerals;
    
    private Supply gas;
    
    

    public ArrayList<Agent> getSubordinateAgents() {
        return subordinateAgents;
    }

    public CommandAgent() {
        this.subordinateAgents = new ArrayList();
    }

    public void addsubordinateAgent(Agent subordinateAgent) {
        subordinateAgents.add(subordinateAgent);
    }

    public void detachSubordinateAgent(Agent subordinateAgent, CommandAgent newCommand) {
        newCommand.addsubordinateAgent(subordinateAgent);
        subordinateAgents.remove(this);
    }

    public Agent getSubordinateAgent(Class agentClass) {
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                return subordinateAgent;
            }
        }
        Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        return null;
    }

    public List getSubordinateAgents(Class<SCV> agentClass) {
        ArrayList<Agent> agents = new ArrayList();
        for (Agent subordinateAgent : subordinateAgents) {
            if (agentClass.isInstance(subordinateAgent)) {
                agents.add(subordinateAgent);
            }
        }
        if (agents.isEmpty()) {
            Log.log(this, Level.WARNING, "{0}: No subordinate agents of type: {1}", this.getClass(), agentClass);
        }
        return agents;
    }
    
    public final void receiveSupply(Supply supply){
        if(supply.getMaterial() == Material.GAS){
            gas.merge(supply);
        }
        else{
            minerals.merge(supply);
        }
    }
    
    public int getOwnedGas(){
        return gas.getAmount();
    } 
    
    public int getOwnedMinerals(){
        return minerals.getAmount();
    }
}
