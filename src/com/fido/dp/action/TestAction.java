package com.fido.dp.action;

import com.fido.dp.Log;
import com.fido.dp.agent.Agent;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.SCV;
import java.util.ArrayList;
import java.util.logging.Level;

public class TestAction extends Action {

    private ExplorationCommand explorationCommand;

    public TestAction(Agent agent) {
        super(agent);
    }

    @Override
    public void performAction() {
        if (explorationCommand == null) {
            getExplorationCommand();
            ArrayList<Agent> subordinateAgents = ((CommandAgent) agent).getSubordinateAgents();
            System.out.println("Number of subordinate agents: " + subordinateAgents.size());
            for (Agent subordinateAgent : subordinateAgents) {
                if (subordinateAgent instanceof SCV) {
                    Log.log(this, Level.FINE, "{0}: subordinate agent added to {1}", this.getClass(), explorationCommand.getClass());
                    explorationCommand.addsubordinateAgent(subordinateAgent);
                }
            }
        }
    }

    private void getExplorationCommand() {
        ArrayList<Agent> subordinateAgents = ((CommandAgent) agent).getSubordinateAgents();
        for (Agent subordinateAgent : subordinateAgents) {
            Log.log(this, Level.FINE, "{0}: agent: {1}", this, subordinateAgent.getClass());
            if (subordinateAgent instanceof ExplorationCommand) {
                Log.log(this, Level.FINE, "{0}: INSIDE", this.getClass());
                explorationCommand = (ExplorationCommand) subordinateAgent;
                return;
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
