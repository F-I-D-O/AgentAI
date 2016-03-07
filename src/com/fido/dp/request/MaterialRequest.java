/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.request;

import com.fido.dp.Material;
import com.fido.dp.base.Agent;
import com.fido.dp.base.CommandAgent;

/**
 *
 * @author F.I.D.O.
 */
public class MaterialRequest extends Request {
	
	private Material material;
	
	private int amount;
	
	public MaterialRequest(CommandAgent recipient, Agent sender, Material material, int amount) {
		super(recipient, sender);
	}
	
}
