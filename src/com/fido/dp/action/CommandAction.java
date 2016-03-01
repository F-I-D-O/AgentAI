/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.action;

import com.fido.dp.agent.CommandAgent;

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
    
    
    

    
    
}
