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
public class Resource {
    
    private final Commander commander;
    
    private final ResourceType resourceType;
    
    private int amount;
    
    
    

    public ResourceType getResourceType() {
        return resourceType;
    }

    public int getAmount() {
        return amount;
    }

    
    
    
    public Resource(Commander commander, ResourceType resourceType, int amount) {
        this.commander = commander;
        this.resourceType = resourceType;
        this.amount = amount;
    }
    
    
    
    
    public void merge(Resource supply){
        if(resourceType != supply.getResourceType()){
            Log.log(this, Level.SEVERE, "Canot merge {0} to {1}", supply.getResourceType(), resourceType);
        }
        else{
            amount += supply.getAmount();
        }
    }
    
    public Resource split(int amount){
        if(amount > this.amount){
            Log.log(this, Level.SEVERE, "Don't have enough supply to split - requested amount: {0}, current amount: {1}",
                    amount, this.amount);
            return null;
        }
        
        this.amount -= amount;
        return new Resource(commander, resourceType, amount);
    }
    
    public void spend(int amount){
        commander.removeReservedResource(this);
    }
    
}
