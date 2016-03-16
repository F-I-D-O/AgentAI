/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.info;

import com.fido.dp.BaseLocationInfo;
import com.fido.dp.base.Agent;

/**
 *
 * @author F.I.D.O.
 */
public class EnemyBaseDiscovered extends Info{
	
	private final BaseLocationInfo baseInfo;

	
	
	
	public BaseLocationInfo getBaseInfo() {
		return baseInfo;
	}
	
	
	
	public EnemyBaseDiscovered(Agent recipient, Agent sender, BaseLocationInfo baseInfo) {
		super(recipient, sender);
		this.baseInfo = baseInfo;
	}
	
}
