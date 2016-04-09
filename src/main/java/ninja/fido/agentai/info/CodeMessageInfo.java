/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.info;

import ninja.fido.agentai.base.Agent;
import ninja.fido.agentai.base.Info;

/**
 *
 * @author F.I.D.O.
 */
public class CodeMessageInfo extends Info{
	
	public enum Code{
		OVERLORD_MORPHED
	}
	
	private final Code code;

	
	
	
	public Code getCode() {
		return code;
	}

	
	
	
	public CodeMessageInfo(Code code, Agent recipient, Agent sender) {
		super(recipient, sender);
		this.code = code;
	}

	
}
