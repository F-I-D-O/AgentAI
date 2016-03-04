/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.BuildingConstructionCommand;
import com.fido.dp.agent.CommandAgent;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.SCV;
import java.util.List;

/**
 *
 * @author david_000
 */
public class BBSStrategy extends CommandAction{
    
    private final ResourceCommand resourceCommand;
    
    private final ExplorationCommand explorationCommand;
    
    private final BuildingConstructionCommand buildCommand;
    
    private int targetNumberOfScouts;

    public BBSStrategy(CommandAgent agent) {
        super(agent);
        
        resourceCommand = (ResourceCommand) getAgent().getSubordinateAgent(ResourceCommand.class);
        explorationCommand = (ExplorationCommand) getAgent().getSubordinateAgent(ExplorationCommand.class);
        buildCommand = (BuildingConstructionCommand) getAgent().getSubordinateAgent(BuildingConstructionCommand.class);
        
        targetNumberOfScouts = 1;
    }

    @Override
    public void performAction() {
        resourceCommand.commandHarvest(1.0);
        buildCommand.commandBBCBuild();
        
        List<SCV> scvs = getAgent().getSubordinateAgents(SCV.class);
        
        if(!scvs.isEmpty()){           
            for (SCV scv : scvs) {
                if(buildCommand.needWorkes()){
                    getAgent().detachSubordinateAgent(scv, buildCommand);
                }
                if(explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachSubordinateAgent(scv, explorationCommand);
                }
                else{
                    getAgent().detachSubordinateAgent(scv, resourceCommand);
                }
            }
        }
        
        
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BBSStrategy other = (BBSStrategy) obj;
        return true;
    }

    
}
