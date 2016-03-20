/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import java.util.Map;

/**
 *
 * @author F.I.D.O.
 */
public class Tools {
	
	public static <K> void incrementMapValue(Map<K,Integer> map, K key){
		incrementMapValue(map, key, 1);
	}
	
	public static <K> void incrementMapValue(Map<K,Integer> map, K key, int increment){
		if(map.containsKey(key)){
			map.put(key, map.get(key) + increment);
		}
		else{
			map.put(key, increment);
		}
	}
	
	
}
