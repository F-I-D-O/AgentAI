package ninja.fido.agentai.agent;

import ninja.fido.agentai.BaseLocationInfo;
import ninja.fido.agentai.base.CommandAgent;
import ninja.fido.agentai.base.GameAPI;
import ninja.fido.agentai.Resource;
import ninja.fido.agentai.ResourceDeficiencyException;
import ninja.fido.agentai.base.Activity;
import ninja.fido.agentai.activity.terran.BBSStrategy;
import ninja.fido.agentai.ResourceType;
import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Goal;
import ninja.fido.agentai.goal.BBSStrategyGoal;
import ninja.fido.agentai.info.EnemyBaseDiscovered;
import ninja.fido.agentai.base.Info;
import java.util.ArrayList;
import ninja.fido.agentai.base.exception.CommanderNotCreatedException;
import ninja.fido.agentai.base.exception.MultipleCommandersException;

public class Commander extends CommandAgent {
	
	private static Class<? extends Commander> commanderClass;
	
	private static Commander commander;
	
	public static Commander create(String name) throws MultipleCommandersException{
		commander = new Commander(name);
		return commander;
	}
	
	public static Commander get() throws CommanderNotCreatedException{
		if(commander == null){
			throw new CommanderNotCreatedException(Commander.class);
		}
		return commander;
	}
	
	
	    
    private int reservedMinerals;
    
    private int reservedGas;
	
	private int reservedSupply;
	
	private final ArrayList<BaseLocationInfo> enemyBases;
	
	private final String name;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	

    protected Commander(String name) throws MultipleCommandersException {
		enemyBases = new ArrayList<>();
		this.name = name;
		if(commanderClass != null){
			throw new MultipleCommandersException(commanderClass, this.getClass());
		}
		commanderClass = this.getClass();
    }
	
	
	

    @Override
    protected Activity chooseAction() {
		if(getGoal() instanceof  BBSStrategyGoal){
			return new BBSStrategy(this);
		}
		return null;
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
		return new BBSStrategyGoal(this, null);
	}


	
	
}
