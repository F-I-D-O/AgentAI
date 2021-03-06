/* 
 * AgentSCAI - Demo
 */
package ninja.fido.agentSCAI.demo.activity.terran;

import bwapi.UnitType;
import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.base.CommandActivity;
import ninja.fido.agentSCAI.Log;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.agent.unit.Barracks;
import ninja.fido.agentSCAI.agent.BuildCommand;
import ninja.fido.agentSCAI.base.Commander;
import ninja.fido.agentSCAI.agent.ExplorationCommand;
import ninja.fido.agentSCAI.agent.unit.Marine;
import ninja.fido.agentSCAI.agent.ProductionCommand;
import ninja.fido.agentSCAI.agent.ResourceCommand;
import ninja.fido.agentSCAI.agent.unit.SCV;
import ninja.fido.agentSCAI.agent.UnitCommand;
import ninja.fido.agentSCAI.base.Goal;
import ninja.fido.agentSCAI.info.EnemyBaseDiscovered;
import ninja.fido.agentSCAI.info.EnemyBasesInfo;
import ninja.fido.agentSCAI.base.Info;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionModuleActivity;
import ninja.fido.agentSCAI.demo.order.BBSAttackOrder;
import ninja.fido.agentSCAI.demo.order.BBSBuildOrder;
import ninja.fido.agentSCAI.demo.order.BBSProductionOrder;
import ninja.fido.agentSCAI.order.DetachBack;
import ninja.fido.agentSCAI.order.HarvestOrder;
import ninja.fido.agentSCAI.order.StrategicExplorationOrder;
import java.util.List;
import java.util.logging.Level;
import ninja.fido.agentSCAI.base.exception.ChainOfCommandViolationException;

/**
 *
 * @author david_000
 * @param <A>
 */
public class BBSStrategy<A extends Commander> extends CommandActivity<A,Goal,BBSStrategy> 
		implements DecisionModuleActivity<A,Goal,BBSStrategy>{
	
	private static final int MINERALS_FOR_BUILDING_CONSTRUCTION = 400;
	
	private static final int MINERALS_FOR_BARRACKS = 300;
	
	private static final int NUMBER_OF_BARRACKS = 2;
	
    
    private ResourceCommand resourceCommand;
    
    private ExplorationCommand explorationCommand;
    
    private BuildCommand buildCommand;
	
	private ProductionCommand productionCommand;
	
	private UnitCommand unitCommand;
    
    private int targetNumberOfScouts;
	
//	private boolean unitsDetachedFromBuildCommand;
	
	public BBSStrategy() {
	}

    public BBSStrategy(A agent) {
        super(agent);

        targetNumberOfScouts = 1;
//		unitsDetachedFromBuildCommand = true;
    }
	
	
	
	

    @Override
    public void performAction() throws ChainOfCommandViolationException {
		new DetachBack(buildCommand, this.getAgent(), SCV.class, true).issueOrder();
		
		SCV scv;
		while((scv = getCommandedAgent(SCV.class)) != null 
				&& explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
			detachCommandedAgent(scv, explorationCommand);
		}
		
		List<SCV> scvs = getCommandedAgents(SCV.class);
		List<Barracks> barracks = getCommandedAgents(Barracks.class);
		List<Marine> marines = getCommandedAgents(Marine.class);
		
		detachCommandedAgents(barracks, productionCommand);
		detachCommandedAgents(marines, unitCommand);
		
		if(buildCommand.getNumberOfConstructionStarted(UnitType.Terran_Barracks) >= NUMBER_OF_BARRACKS
				&& (!productionCommand.isSupplyMissing() || getOwnedSupply() > productionCommand.getMissingSupply())
				&& productionCommand.isMineralsMissing()){
			if(productionCommand.getMissingMinerals() <= getOwnedMinerals() 
						&& productionCommand.getMissingSupply() <= getOwnedSupply()){
				try {
					giveResource(productionCommand, ResourceType.MINERALS, productionCommand.getMissingMinerals());
					giveResource(productionCommand, ResourceType.SUPPLY, productionCommand.getMissingSupply());
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
						detachCommandedAgent(scvs.get(0), buildCommand);
//						unitsDetachedFromBuildCommand = false;
					}
				}	
				else {
					Log.log(this, Level.FINER, "{0}: Need workers - NO", this.getClass());
				}
			}	
			else{
				Log.log(this, Level.FINER, "{0}: Missing crystal - YES", this.getClass());
				Log.log(this, Level.FINEST, "{0}: Owned crystal: {1}", this.getClass(), getOwnedMinerals());
				if(buildCommand.getMissingMineralForFirsItem() <= getOwnedMinerals()){
					Log.log(this, Level.FINER, "{0}: Have crystal to give - YES", this.getClass());
					try {
						giveResource(buildCommand, ResourceType.MINERALS, buildCommand.getMissingMineralForFirsItem());
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
	protected void init() throws ChainOfCommandViolationException {
		resourceCommand = getCommandedAgent(ResourceCommand.class);
        explorationCommand = getCommandedAgent(ExplorationCommand.class);
        buildCommand = getCommandedAgent(BuildCommand.class);
		productionCommand = getCommandedAgent(ProductionCommand.class);
		unitCommand = getCommandedAgent(UnitCommand.class);
		
		List<SCV> scvs = getCommandedAgents(SCV.class);
		if(!scvs.isEmpty()){           
            for (SCV scv : scvs) {
                if(explorationCommand.getNumberOfScouts() < targetNumberOfScouts){
                    detachCommandedAgent(scv, explorationCommand);
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
				buildCommand.getMissingMineralForFirsItem() - getOwnedMinerals());
//		if(unitsDetachedFromBuildCommand){
			if(!scvs.isEmpty()){
				detachCommandedAgents(scvs, resourceCommand);
			}
//		}
//		else{
//			
////			unitsDetachedFromBuildCommand = true;
//		}
	}

	@Override
	public BBSStrategy create(A agent, Goal goal) {
		return new BBSStrategy(agent);
	}


	
	

    
}
