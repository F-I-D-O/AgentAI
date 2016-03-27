/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.request;

import com.fido.dp.base.Request;
import com.fido.dp.agent.ExplorationCommand;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class ExpansionInfoRequest extends Request{
	
	public ExpansionInfoRequest(ExplorationCommand recipient, Agent sender) {
		super(recipient, sender);
	}
	
}
