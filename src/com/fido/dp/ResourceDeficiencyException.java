/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ResourceDeficiencyException extends Exception{
	
	private final Agent spender;
	
	private final ResourceType resourceType;
	
	private final int amount;
	
	private final int currentAmount;

	
	
	
	public Agent getSpender() {
		return spender;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public int getAmount() {
		return amount;
	}

	public int getCurrentAmount() {
		return currentAmount;
	}

	
	
	
	
	public ResourceDeficiencyException(Agent spender, ResourceType resourceType, int amount,  int currentAmount) {
		this.spender = spender;
		this.resourceType = resourceType;
		this.amount = amount;
		this.currentAmount = currentAmount;
	}
	
	
	
	
	@Override
	public String getMessage() {
		return spender.getClass() + ": Don't have enought " + resourceType + ": " + amount + ". Current amount: " + currentAmount;
	}
}
