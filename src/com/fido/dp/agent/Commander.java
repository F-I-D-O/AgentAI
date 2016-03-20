package com.fido.dp.agent;

import com.fido.dp.BaseLocationInfo;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.base.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Supply;
import com.fido.dp.base.Action;
import com.fido.dp.action.BBSStrategy;
import com.fido.dp.Material;
import com.fido.dp.base.Agent;
import com.fido.dp.base.Goal;
import com.fido.dp.goal.BBSStrategyGoal;
import com.fido.dp.info.EnemyBaseDiscovered;
import com.fido.dp.info.Info;
import java.util.ArrayList;
import java.util.logging.Level;

public class Commander extends CommandAgent {
	    
    private int reservedMinerals;
    
    private int reservedGas;
	
	private final ArrayList<BaseLocationInfo> enemyBases;

	public ArrayList<BaseLocationInfo> getEnemyBases() {
		return enemyBases;
	}
	
	
	
	

    public Commander() {
		enemyBases = new ArrayList<>();
    }

    @Override
    protected Action chooseAction() {
		if(getGoal() instanceof  BBSStrategyGoal){
			return new BBSStrategy(this);
		}
		return null;
    }
	
	private final void reserveSupply(Supply supply){
		if(supply.getMaterial() == Material.GAS){
            reservedGas += supply.getAmount();
        }
        else{
            reservedMinerals += supply.getAmount();
        }
	}

    public final void removeReservedSupply(Supply supply) {
        if(supply.getMaterial() == Material.GAS){
            reservedGas -= supply.getAmount();
        }
        else{
            reservedMinerals -= supply.getAmount();
        }
    }
    
    public int getFreeGas(){
        return GameAPI.getGame().self().gas() - reservedGas;
    }
    
    public int getFreeMinerals(){
        return GameAPI.getGame().self().minerals() - reservedMinerals;
    }
	
	@Override
	public final void giveSupply(Agent receiver, Material material, int amount){
		if(material == Material.GAS && getOwnedGas() < amount){
			Log.log(this, Level.SEVERE, "Don't have enough gas - requested amount: {0}, current amount: {1}", amount, 
					getOwnedGas());
			return;
		}
		else if(getOwnedMinerals() < amount){
			Log.log(this, Level.SEVERE, "Don't have enough minerals - requested amount: {0}, current amount: {1}",
					amount, getOwnedMinerals());
			return;
		}
		Supply supply = new Supply(this, material, amount);
		receiver.receiveSupply(supply);
		reserveSupply(supply);
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
