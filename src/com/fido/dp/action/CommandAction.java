/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.Log;
import com.fido.dp.base.CommandAgent;
import com.fido.dp.request.Request;
import java.util.Queue;
import java.util.logging.Level;

/**
 *
 * @author david_000
 */
public abstract class CommandAction extends Action{

    public CommandAction(CommandAgent agent) {
        super(agent);
    }


    @Override
    public CommandAgent getAgent() {
        return (CommandAgent) agent;
    }
    
    protected void handleRequests(){
		Queue<Request> requests = getAgent().getRequests();
		Request request;
		while((request = requests.poll()) != null){
			handleRequest(request);
		}
	}

	private void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}
    

    
    
}
