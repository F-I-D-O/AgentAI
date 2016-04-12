/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentAI.request;

import ninja.fido.agentAI.base.Request;
import ninja.fido.agentAI.base.Agent;
import ninja.fido.agentAI.base.CommandAgent;

/**
 *
 * @author F.I.D.O.
 */
public class ResourceRequest extends Request {
	
	
	private final int mineralAmount;
	
	private final int gasAmount;
	
	private final int supplyAmount;
	
	private boolean processed;

	


	public int getMineralAmount() {
		return mineralAmount;
	}

	public int getGasAmount() {
		return gasAmount;
	}

	public int getSupplyAmount() {
		return supplyAmount;
	}
	
	

	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	
	
	
	public ResourceRequest(CommandAgent recipient, Agent sender, int mineralAmount, int gasAmount, int supplyAmount) {
		super(recipient, sender);
		this.mineralAmount = mineralAmount;
		this.gasAmount = gasAmount;
		this.supplyAmount = supplyAmount;
		processed = false;
	}
	
}
