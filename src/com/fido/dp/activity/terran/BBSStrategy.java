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
import com.fido.dp.Material;
import com.fido.dp.agent.unit.Barracks;
import com.fido.dp.agent.BuildCommand;
import com.fido.dp.agent.Commander;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.agent.unit.Marine;
import com.fido.dp.agent.ProductionCommand;
import com.fido.dp.agent.ResourceCommand;
import com.fido.dp.agent.unit.SCV;
import com.fido.dp.agent.UnitCommand;
import com.fido.dp.base.Goal;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.info.EnemyBasesInfo;
import com.fido.dp.info.Info;
import com.fido.dp.order.BBSAttackOrder;
import com.fido.dp.order.BBSBuildOrder;
import com.fido.dp.order.BBSProductionOrder;
import com.fido.dp.order.DeatchBack;
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
	
    
    private final ResourceCommand resourceCommand;
    
    private final ExplorationCommand explorationCommand;
    
    private final BuildCommand buildCommand;
	
	private final ProductionCommand productionCommand;
	
	private final UnitCommand unitCommand;
    
    private final int targetNumberOfScouts;
	
//	private boolean unitsDetachedFromBuildCommand;
	
	

    public BBSStrategy(A agent) {
        super(agent);
        
        resourceCommand = agent.getSubordinateAgent(ResourceCommand.class);
        explorationCommand = agent.getSubordinateAgent(ExplorationCommand.class);
        buildCommand = agent.getSubordinateAgent(BuildCommand.class);
		productionCommand = agent.getSubordinateAgent(ProductionCommand.class);
		unitCommand = agent.getSubordinateAgent(UnitCommand.class);
        
        targetNumberOfScouts = 1;
//		unitsDetachedFromBuildCommand = true;
    }

    @Override
    public void performAction() {
		new DeatchBack(buildCommand, this.getAgent(), SCV.class, true).issueOrder();
		
		SCV scv;
		while((scv = agent.getSubordinateAgent(SCV.class)) != null 
				&& explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
			getAgent().detachSubordinateAgent(scv, explorationCommand);
		}
		
		List<SCV> scvs = agent.getSubordinateAgents(SCV.class);
		List<Barracks> barracks = agent.getSubordinateAgents(Barracks.class);
		List<Marine> marines = agent.getSubordinateAgents(Marine.class);
		
		agent.detachSubordinateAgents(barracks, productionCommand);
		agent.detachSubordinateAgents(marines, unitCommand);
		
		if(buildCommand.getNumberOfConstructionStarted(UnitType.Terran_Barracks) >= NUMBER_OF_BARRACKS
				&& productionCommand.isMineralsMissing()){
			if(productionCommand.getMissingMinerals() <= getAgent().getOwnedMinerals()){
				getAgent().giveSupply(productionCommand, Material.MINERALS, productionCommand.getMissingMinerals());
			}
			else{
				focusOnHarvest(scvs);
			}
		}
		else{
			if(buildCommand.getMissingCrystalForFirsItem() == 0){
				Log.log(this, Level.FINER, "{0}: Missing crystal - NO", this.getClass());
				if(buildCommand.needWorkes()){
					Log.log(this, Level.FINER, "{0}: Need workers - YES", this.getClass());
					if(scvs.isEmpty()){
						Log.log(this, Level.FINER, "{0}: Have workers - NO", this.getClass());
						new DeatchBack(resourceCommand, agent, SCV.class, 1).issueOrder();
					}
					else {
						Log.log(this, Level.FINER, "{0}: Have workers - YES", this.getClass());
						getAgent().detachSubordinateAgent(scvs.get(0), buildCommand);
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
				if(buildCommand.getMissingCrystalForFirsItem() <= getAgent().getOwnedMinerals()){
					Log.log(this, Level.FINER, "{0}: Have crystal to give - YES", this.getClass());
					getAgent().giveSupply(buildCommand, Material.MINERALS, buildCommand.getMissingCrystalForFirsItem());
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
	protected void init() {
		List<SCV> scvs = agent.getSubordinateAgents(SCV.class);
		if(!scvs.isEmpty()){           
            for (SCV scv : scvs) {
                if(explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    getAgent().detachSubordinateAgent(scv, explorationCommand);
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
				buildCommand.getMissingCrystalForFirsItem() - getAgent().getOwnedMinerals());
//		if(unitsDetachedFromBuildCommand){
			if(!scvs.isEmpty()){
				getAgent().detachSubordinateAgents(scvs, resourceCommand);
			}
//		}
//		else{
//			
////			unitsDetachedFromBuildCommand = true;
//		}
	}
	
	

    
}
