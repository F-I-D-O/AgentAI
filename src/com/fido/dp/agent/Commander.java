package com.fido.dp.agent;

import com.fido.dp.GameAPI;
import com.fido.dp.Supply;
import com.fido.dp.action.Action;
import com.fido.dp.action.BBSStrategy;
import com.fido.dp.Material;

public class Commander extends CommandAgent {
    
    private int reservedMinerals;
    
    private int reservedGas;

    public Commander() {
    }

    @Override
    protected Action chooseAction() {
        return new BBSStrategy(this);
    }

    public void removeReservedSupply(Supply supply) {
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
}
