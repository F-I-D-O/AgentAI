package com.fido.dp.agent;

import com.fido.dp.BaseLocationInfo;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Resource;
import com.fido.dp.ResourceDeficiencyException;
import com.fido.dp.base.Activity;
import com.fido.dp.activity.terran.BBSStrategy;
import com.fido.dp.ResourceType;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.BBSStrategyGoal;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.base.Info;
import java.util.ArrayList;
import java.util.logging.Level;

public class Commander extends CommandAgent {
	    
    private int reservedMinerals;
    
    private int reservedGas;
	
	private int reservedSupply;
	
	private final ArrayList<BaseLocationInfo> enemyBases;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	
	

    public Commander() {
		enemyBases = new ArrayList<>();
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
