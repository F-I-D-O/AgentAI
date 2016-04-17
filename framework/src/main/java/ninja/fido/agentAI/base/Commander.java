package ninja.fido.agentAI.base;

import ninja.fido.agentAI.BaseLocationInfo;
import ninja.fido.agentAI.Resource;
import ninja.fido.agentAI.ResourceDeficiencyException;
import ninja.fido.agentAI.ResourceType;
import ninja.fido.agentAI.info.EnemyBaseDiscovered;
import java.util.ArrayList;
import ninja.fido.agentAI.base.exception.MultipleCommandersException;

public class Commander extends CommandAgent {
	
	private static Class<? extends Commander> commanderClass;
	
	static void onStart(){
		commanderClass = null;
	}
	
	
	    
    private int reservedMinerals;
    
    private int reservedGas;
	
	private int reservedSupply;
	
	private final ArrayList<BaseLocationInfo> enemyBases;
	
	protected final String name;
	
	protected final Goal initialGoal;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	protected Commander create() throws MultipleCommandersException{
		return new Commander(name, initialGoal);
	}
	

    public Commander(String name, Goal initialGoal) throws MultipleCommandersException {
		enemyBases = new ArrayList<>();
		this.name = name;
		if(commanderClass != null){
			throw new MultipleCommandersException(commanderClass, this.getClass());
		}
		commanderClass = this.getClass();
		this.initialGoal = initialGoal;
    }
	
	
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

    public final void removeReservedResource(Resource resource) {
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
    
    public int getFreeGas(){
        return GameAPI.getGame().self().gas() - reservedGas;
    }
    
    public int getFreeMinerals(){
        return GameAPI.getGame().self().minerals() - reservedMinerals;
    }
	
	public int getFreeSupply(){
		return GameAPI.getGame().self().supplyTotal() - GameAPI.getGame().self().supplyUsed() - reservedSupply;
	}
	
	@Override
	public final void giveResource(Agent receiver, ResourceType material, int amount) throws ResourceDeficiencyException{
		if(material == ResourceType.GAS && getOwnedGas() < amount){
//			Log.log(this, Level.SEVERE, "Don''t have enough gas - requested amount: {0}, current amount: {1}", amount, 
//					getOwnedGas());
//			return;
			throw new ResourceDeficiencyException(this, ResourceType.GAS, amount, getOwnedGas());
		}
		else if(material == ResourceType.MINERALS && getOwnedMinerals() < amount){
//			Log.log(this, Level.SEVERE, "Don''t have enough minerals - requested amount: {0}, current amount: {1}",
//					amount, getOwnedMinerals());
//			return;
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
    public int getOwnedGas(){
        return getFreeGas();
    } 
    
	@Override
    public int getOwnedMinerals(){
        return getFreeMinerals();
    }

	@Override
	public int getOwnedSupply() {
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


	
	
}
