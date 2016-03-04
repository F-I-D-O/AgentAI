/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.agent.Commander;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public class Supply {
    
    private Commander commander;
    
    private Material material;
    
    private int amount;
    
    
    

    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }

    
    
    
    public Supply(Commander commander, Material material, int amount) {
        this.commander = commander;
        this.material = material;
        this.amount = amount;
    }
    
    
    
    
    public void merge(Supply supply){
        if(material != supply.getMaterial()){
            Log.log(this, Level.SEVERE, "Canot merge {0} to {1}", supply.getMaterial(), material);
        }
        else{
            amount += supply.getAmount();
        }
    }
    
    public Supply split(int amount){
        if(amount > this.amount){
            Log.log(this, Level.SEVERE, "Don|t have enough supply to split - requested amount: {0}, current amount: {1}",
                    amount, this.amount);
            return null;
        }
        
        this.amount -= amount;
        return new Supply(commander, material, amount);
    }
    
    public void spend(int amount){
        commander.removeReservedSupply(this);
    }
    
}
