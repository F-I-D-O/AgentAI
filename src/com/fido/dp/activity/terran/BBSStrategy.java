/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.activity.terran;

import bwapi.UnitType;
import com.fido.dp.BaseLocationInfo;
import com.fido.dp.base.CommandActivity;
import com.fido.dp.Log;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.ResourceType;
import com.fido.dp.agent.unit.Barracks;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.unit.Marine;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.agent.UnitCommand;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.info.EnemyBasesInfo;
import com.fido.dp.base.Info;
import com.fido.dp.order.BBSAttackOrder;
import com.fido.dp.order.BBSBuildOrder;
import com.fido.dp.order.BBSProductionOrder;
import com.fido.dp.order.DetachBack;
import com.fido.dp.order.HarvestOrder;
import com.fido.dp.order.StrategicExplorationOrder;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <A>
 */
public class BBSStrategy<A extends Commander> extends CommandActivity<A,Goal>{
	
	private static final int MINERALS_FOR_BUILDING_CONSTRUCTION = 400;
	
	private static final int MINERALS_FOR_BARRACKS = 300;
	
	private static final int NUMBER_OF_BARRACKS = 2;
	
    
    private ResourceCommand resourceCommand;
    
    private ExplorationCommand explorationCommand;
    
    private BuildCommand buildCommand;
	
	private ProductionCommand productionCommand;
	
	private UnitCommand unitCommand;
    
    private final int targetNumberOfScouts;
	
//	private boolean unitsDetachedFromBuildCommand;
	
	

    public BBSStrategy(A agent) {
        super(agent);

        targetNumberOfScouts = 1;
//		unitsDetachedFromBuildCommand = true;
    }

    @Override
    public void performAction() {
		new DetachBack(buildCommand, this.getAgent(), SCV.class, true).issueOrder();
		
		SCV scv;
		while((scv = agent.getCommandedAgent(SCV.class)) != null 
				&& explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
			getAgent().detachCommandedAgent(scv, explorationCommand);
		}
		
		List<SCV> scvs = agent.getCommandedAgents(SCV.class);
		List<Barracks> barracks = agent.getCommandedAgents(Barracks.class);
		List<Marine> marines = agent.getCommandedAgents(Marine.class);
		
		agent.detachCommandedAgents(barracks, productionCommand);
		agent.detachCommandedAgents(marines, unitCommand);
		
		if(buildCommand.getNumberOfConstructionStarted(UnitType.Terran_Barracks) >= NUMBER_OF_BARRACKS
				&& productionCommand.isMineralsMissing()){
			if(productionCommand.getMissingMinerals() <= getAgent().getOwnedMinerals() 
						&& productionCommand.getMissingSupply() <= agent.getOwnedSupply()){
				try {
					getAgent().giveResource(productionCommand, ResourceType.MINERALS, productionCommand.getMissingMinerals());
					getAgent().giveResource(productionCommand, ResourceType.SUPPLY, productionCommand.getMissingSupply());
				} catch (ResourceDeficiencyException ex) {
					ex.printStackTrace();
				}
			}
			else{
				focusOnHarvest(scvs);
			}
		}
		else{
			if(buildCommand.getMissingMineralForFirsItem() == 0){
				Log.log(this, Level.FINER, "{0}: Missing crystal - NO", this.getClass());
				if(buildCommand.needWorkes()){
					Log.log(this, Level.FINER, "{0}: Need workers - YES", this.getClass());
					if(scvs.isEmpty()){
						Log.log(this, Level.FINER, "{0}: Have workers - NO", this.getClass());
						new DetachBack(resourceCommand, agent, SCV.class, 1).issueOrder();
					}
					else {
						Log.log(this, Level.FINER, "{0}: Have workers - YES", this.getClass());
						getAgent().detachCommandedAgent(scvs.get(0), buildCommand);
//						unitsDetachedFromBuildCommand = false;
					}
				}	
				else {
					Log.log(this, Level.FINER, "{0}: Need workers - NO", this.getClass());
				}
			}	
			else{
				Log.log(this, Level.FINER, "{0}: Missing crystal - YES", this.getClass());
				Log.log(this, Level.FINEST, "{0}: Owned crystal: {1}", this.getClass(), getAgent().getOwnedMinerals());
				if(buildCommand.getMissingMineralForFirsItem() <= getAgent().getOwnedMinerals()){
					Log.log(this, Level.FINER, "{0}: Have crystal to give - YES", this.getClass());
					try {
						getAgent().giveResource(buildCommand, ResourceType.MINERALS, buildCommand.getMissingMineralForFirsItem());
					} catch (ResourceDeficiencyException ex) {
						ex.printStackTrace();
					}
				}
				else{
					focusOnHarvest(scvs);
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

	@Override
	public void initialize(A agent, Goal goal) {
		super.initialize(agent,goal);
		
	}
	
	

	@Override
	protected void init() {
		resourceCommand = agent.getCommandedAgent(ResourceCommand.class);
        explorationCommand = agent.getCommandedAgent(ExplorationCommand.class);
        buildCommand = agent.getCommandedAgent(BuildCommand.class);
		productionCommand = agent.getCommandedAgent(ProductionCommand.class);
		unitCommand = agent.getCommandedAgent(UnitCommand.class);
		
		List<SCV> scvs = agent.getCommandedAgents(SCV.class);
		if(!scvs.isEmpty()){           
            for (SCV scv : scvs) {
                if(explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachCommandedAgent(scv, explorationCommand);
                }
            }
        }
		new HarvestOrder(resourceCommand, this.getAgent(), 1.0).issueOrder();
		new BBSBuildOrder(buildCommand, this.getAgent()).issueOrder();
		new BBSProductionOrder(productionCommand, agent).issueOrder();
		new BBSAttackOrder(unitCommand, agent).issueOrder();
		new StrategicExplorationOrder(explorationCommand, agent).issueOrder();
	}
	
	@Override
	protected void processInfo(Info info) {
		if(info instanceof EnemyBaseDiscovered){
			BaseLocationInfo baseInfo = ((EnemyBaseDiscovered) info).getBaseInfo();
			new EnemyBasesInfo(unitCommand, agent, agent.getEnemyBases()).send();
		}
	}

	private void focusOnHarvest(List<SCV> scvs) {
		Log.log(this, Level.FINER, "{0}: Have crystal to give - NO", this.getClass());
		Log.log(this, Level.FINER, "{0}: Missing amount of crystal: {1}", this.getClass(), 
				buildCommand.getMissingMineralForFirsItem() - getAgent().getOwnedMinerals());
//		if(unitsDetachedFromBuildCommand){
			if(!scvs.isEmpty()){
				getAgent().detachCommandedAgents(scvs, resourceCommand);
			}
//		}
//		else{
//			
////			unitsDetachedFromBuildCommand = true;
//		}
	}
	
	

    
}
