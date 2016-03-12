/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.Log;
import com.fido.dp.Material;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.SCV;
import com.fido.dp.command.BBSBuildCommand;
import com.fido.dp.command.DeatchBack;
import com.fido.dp.command.HarvestCommand;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <T>
 */
public class BBSStrategy<T extends Commander> extends CommandAction<T>{
	
	private static final int MINERALS_FOR_BUILDING_CONSTRUCTION = 400;
	
    
    private final ResourceCommand resourceCommand;
    
    private final ExplorationCommand explorationCommand;
    
    private final BuildCommand buildCommand;
    
    private final int targetNumberOfScouts;
	
	private boolean unitsDetachedFromBuildCommand;
	
	

    public BBSStrategy(T agent) {
        super(agent);
        
        resourceCommand = agent.getSubordinateAgent(ResourceCommand.class);
        explorationCommand = agent.getSubordinateAgent(ExplorationCommand.class);
        buildCommand = agent.getSubordinateAgent(BuildCommand.class);
        
        targetNumberOfScouts = 1;
		unitsDetachedFromBuildCommand = true;
    }

    @Override
    public void performAction() {
		List<SCV> scvs = agent.getSubordinateAgents(SCV.class);
		if(buildCommand.getMissingCrystalForFirsItem() == 0){
			Log.log(this, Level.FINER, "{0}: Missing crystal - NO", this.getClass());
			if(buildCommand.needWorkes()){
				Log.log(this, Level.FINER, "{0}: Need workers - YES", this.getClass());
				if(scvs.isEmpty()){
					Log.log(this, Level.FINER, "{0}: Have workers - NO", this.getClass());
					new DeatchBack(resourceCommand, agent, SCV.class, 1).issueCommand();
				}
				else {
					Log.log(this, Level.FINER, "{0}: Have workers - YES", this.getClass());
					getAgent().detachSubordinateAgent(scvs.get(0), buildCommand);
					unitsDetachedFromBuildCommand = false;
				}
			}	
			else {
				Log.log(this, Level.FINER, "{0}: Need workers - NO", this.getClass());
			}
		}	
		else{
			Log.log(this, Level.FINER, "{0}: Missing crystal - YES", this.getClass());
			Log.log(this, Level.FINEST, "{0}: Owned crystal: {1}", this.getClass(), getAgent().getOwnedMinerals());
			if(buildCommand.getMissingCrystalForFirsItem() <= getAgent().getOwnedMinerals()){
				Log.log(this, Level.FINER, "{0}: Have crystal to give - YES", this.getClass());
				getAgent().giveSupply(buildCommand, Material.MINERALS, buildCommand.getMissingCrystalForFirsItem());
			}
			else{
				Log.log(this, Level.FINER, "{0}: Have crystal to give - NO", this.getClass());
				Log.log(this, Level.FINER, "{0}: Missing amount of crystal: {1}", this.getClass(), 
						buildCommand.getMissingCrystalForFirsItem() - getAgent().getOwnedMinerals());
				if(unitsDetachedFromBuildCommand){
					getAgent().detachSubordinateAgents(scvs, buildCommand);
				}
				else{
					new DeatchBack(buildCommand, this.getAgent(), SCV.class).issueCommand();
					unitsDetachedFromBuildCommand = true;
				}
			}
		}
        
//        if(!scvs.isEmpty()){           
//            for (SCV scv : scvs) {
//				
//					if(buildCommand.needWorkes()){
//						getAgent().detachSubordinateAgent(scv, buildCommand);
//					}
//				
//                if(explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
//                    getAgent().detachSubordinateAgent(scv, explorationCommand);
//                }
//                else{
//                    getAgent().detachSubordinateAgent(scv, resourceCommand);
//                }
//            }
//        }
        
        
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

	@Override
	protected void init() {
		new HarvestCommand(resourceCommand, this.getAgent(), 1.0).issueCommand();
		new BBSBuildCommand(buildCommand, this.getAgent()).issueCommand();
	}
	
	

    
}
