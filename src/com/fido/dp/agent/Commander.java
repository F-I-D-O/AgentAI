package com.fido.dp.agent;

import com.fido.dp.base.CommandAgent;
import com.fido.dp.GameAPI;
import com.fido.dp.Log;
import com.fido.dp.Supply;
import com.fido.dp.base.Action;
import com.fido.dp.action.BBSStrategy;
import com.fido.dp.Material;
import com.fido.dp.base.Agent;
import java.util.logging.Level;

public class Commander extends CommandAgent {
	
//	private static Commander comander;
	
//	private static Commander get(){
//		if(comander == null){
//			comander = new Commander();
//		}
//		return comander;
//	}
	
	
	
    
    private int reservedMinerals;
    
    private int reservedGas;

    public Commander() {
    }

    @Override
    protected Action chooseAction() {
        return new BBSStrategy(this);
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

//	@Override
//	protected void routine() {
//		gas = 
//	}
	
	
}
