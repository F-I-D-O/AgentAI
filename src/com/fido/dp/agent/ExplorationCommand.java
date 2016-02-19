/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp.agent;

import com.fido.dp.action.Action;
import com.fido.dp.action.StrategicExplorationAction;

/**
 *
 * @author david_000
 */
public class ExplorationCommand extends CommandAgent {

    @Override
    protected Action chooseAction() {
        return new StrategicExplorationAction(this);
    }
    
}
