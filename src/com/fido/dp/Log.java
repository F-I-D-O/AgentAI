/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author david_000
 */
public class Log {
    public static void log(Object object, Level level, String message){ 
        Logger log = Logger.getLogger(object.getClass().getName());
        log.log(level, message);
    }
    
    public static void log(Object object, Level level, String message, Object... params){
        Logger log = Logger.getLogger(object.getClass().getName());
        log.log(level, message, params);
    }
}
