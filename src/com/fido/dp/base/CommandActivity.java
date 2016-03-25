/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.base;

import com.fido.dp.Log;
import com.fido.dp.request.Request;
import java.util.logging.Level;

/**
 *
 * @author david_000
 * @param <T>
 * @param <G>
 */
public abstract class CommandActivity<T extends CommandAgent,G extends Goal> extends Activity<T,G>{

    public CommandActivity(T agent) {
        super(agent);
    }
	
	public void handleRequest(Request request) {
		Log.log(this, Level.FINE, "{0}: request received: {1}", this.getClass(), request.getClass());
	}

	void handleCompletedOrder(Order order) {
		Log.log(this, Level.FINE, "{0}: order completed: {1}", this.getClass(), order.getClass());
	}

}
