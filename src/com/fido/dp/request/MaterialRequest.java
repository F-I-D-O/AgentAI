/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.request;

import com.fido.dp.base.Request;
import com.fido.dp.ResourceType;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;

/**
 *
 * @author F.I.D.O.
 */
public class MaterialRequest extends Request {
	
	
	private final int mineralAmount;
	
	private final int gasAmount;
	
	private boolean processed;

	


	public int getMineralAmount() {
		return mineralAmount;
	}

	public int getGasAmount() {
		return gasAmount;
	}

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	
	
	
	public MaterialRequest(CommandAgent recipient, Agent sender, int mineralAmount, int gasAmount) {
		super(recipient, sender);
		this.mineralAmount = mineralAmount;
		this.gasAmount = gasAmount;
		processed = false;
	}
	
}
