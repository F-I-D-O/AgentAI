/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.base;

/**
 *
 * @author david_000
 * @param <T>
 */
public abstract class CommandAction<T extends CommandAgent> extends Action<T>{

    public CommandAction(T agent) {
        super(agent);
    }

    
}
