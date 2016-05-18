/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI.base;

import ninja.fido.agentSCAI.BaseLocationInfo;
import ninja.fido.agentSCAI.ResourceDeficiencyException;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.info.EnemyBaseDiscovered;
import java.util.ArrayList;
import ninja.fido.agentSCAI.base.exception.MultipleCommandersException;
import ninja.fido.agentSCAI.modules.decisionMaking.EmptyDecisionTableMapException;

/**
 * Commander.
 * @author david
 */
public class Commander extends CommandAgent {
	
	/**
	 * Class of current commander. Also control field that ensures that there is no more than one commander.
	 */
	private static Class<? extends Commander> commanderClass;
	
	/**
	 * Reset commander class on game start. This is neede for repeated game.
	 */
	static void onStart(){
		commanderClass = null;
	}
	
	
	
	
	
	
	
	/**
	 * Total minerals owned by all agents.
	 */
    private int reservedMinerals;
    
	/**
	 * Total gas owned by all agents.
	 */
    private int reservedGas;
	
	/**
	 * Total supply owned by all agents.
	 */
	private int reservedSupply;
	
	/**
	 * List of basic informations about enemy bases.
	 */
	private final ArrayList<BaseLocationInfo> enemyBases;
	
	/**
	 * Commander's name.
	 */
	protected final String name;
	
	/**
	 * Comander's iniitial goal
	 */
	protected final Goal initialGoal;

	
	
		
	/**
	 * Returns list of basic informations about enemy bases
	 * @return 
	 */
	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	
	
	/**
	 * onstructor.
	 * @param name Name for the commander.
	 * @param initialGoal Initaial goal for the commander.
	 * @throws MultipleCommandersException If multiple commanders has been created in one game.
	 * @throws EmptyDecisionTableMapException If the decision tables map is not initialized.
	 */
    public Commander(String name, Goal initialGoal) throws MultipleCommandersException, EmptyDecisionTableMapException {
		enemyBases = new ArrayList<>();
		this.name = name;
		if(commanderClass != null){
			throw new MultipleCommandersException(commanderClass, this.getClass());
		}
		commanderClass = this.getClass();
		this.initialGoal = initialGoal;
    }
	
	
	
	
	/**
	 * Creates new instace of the commander.
	 * @return Returns new instace of the commander.
	 * @throws MultipleCommandersException If multiple commanders has been created in one game.
	 * @throws EmptyDecisionTableMapException If the decision tables map is not initialized.
	 */
	protected Commander create() throws MultipleCommandersException, EmptyDecisionTableMapException {
		return new Commander(name, initialGoal);
	}
	
	@Override
	protected final void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException{
		if(material == ResourceType.GAS && getOwnedGas() < amount){
			throw new ResourceDeficiencyException(this, ResourceType.GAS, amount, getOwnedGas());
		}
		else if(material == ResourceType.MINERALS && getOwnedMinerals() < amount){
			throw new ResourceDeficiencyException(this, ResourceType.MINERALS, amount, getOwnedMinerals());
		}
		else if(material == ResourceType.SUPPLY && getOwnedSupply() < amount){
			throw new ResourceDeficiencyException(this, ResourceType.SUPPLY, amount, getOwnedSupply());
		}
		Resource supply = new Resource(this, this, material, amount);
		receiver.receiveResource(supply);
		reserveResource(supply);
	}
	
	@Override
    protected int getOwnedGas(){
        return getFreeGas();
    } 
    
	@Override
    protected int getOwnedMinerals(){
        return getFreeMinerals();
    }

	@Override
	protected int getOwnedSupply() {
		return getFreeSupply();
	}
	
	@Override
	protected void processInfo(Info info) {
		if(info instanceof EnemyBaseDiscovered){
			BaseLocationInfo baseInfo = ((EnemyBaseDiscovered) info).getBaseInfo();
			enemyBases.add(baseInfo);
		}
	}

	@Override
	protected Goal getDefaultGoal() {
		return initialGoal;
	}

	
	/**
	 * Remove reserervation on some resources. Used when some agent spend resources.
	 * @param resource Resources.
	 */
	final void removeReservedResource(Resource resource) {
		switch(resource.getResourceType()){
			case GAS:
				reservedGas -= resource.getAmount();
				break;
			case MINERALS:
				reservedMinerals -= resource.getAmount();
				break;
			case SUPPLY:
				reservedSupply -= resource.getAmount();
				break;
		}
    }
	
	
	/**
	 * Reserve resources. Used hen resources are given to some agent.
	 * @param resource Resources.
	 */
	private void reserveResource(Resource resource){
		switch(resource.getResourceType()){
			case GAS:
				reservedGas += resource.getAmount();
				break;
			case MINERALS:
				reservedMinerals += resource.getAmount();
				break;
			case SUPPLY:
				reservedSupply += resource.getAmount();
				break;
		}
	}

    /**
	 * Returns amount of free gas.
	 * @return Returns amount of free gas.
	 */
    private int getFreeGas(){
        return GameAPI.getGame().self().gas() - reservedGas;
    }
    
	/**
	 * Returns amount of free minerals.
	 * @return Returns amount of free minerals.
	 */
    private int getFreeMinerals(){
        return GameAPI.getGame().self().minerals() - reservedMinerals;
    }
	
	/**
	 * Returns amount of free supply.
	 * @return Returns amount of free ssupply.
	 */
	private int getFreeSupply(){
		return GameAPI.getGame().self().supplyTotal() - GameAPI.getGame().self().supplyUsed() - reservedSupply;
	}
	
}
