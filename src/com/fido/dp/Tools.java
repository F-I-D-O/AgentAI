/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fido.dp;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
	
	public static <K,V extends Comparable> List<Map.Entry<K, V>> getSortedListFromMap(final Map<K,V> map, final boolean order){
		List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
			@Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });
		
		return list;
	}
	
}
