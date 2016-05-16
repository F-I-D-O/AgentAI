/* 
 * AgentSCAI
 */
package ninja.fido.agentSCAI;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * This class contains static helper methods that don't belog to any specific part of the framework.
 * @author F.I.D.O.
 */
public class Tools {
	
	/**
	 * Increment map entry by 1.
	 * @param <K> map key type
	 * @param map map to be changed
	 * @param key key pointing to the value to be incremented
	 */
	public static <K> void incrementMapValue(final Map<K,Integer> map, final K key){
		incrementMapValue(map, key, 1);
	}
	
	/**
	 * Increment map entry by specified value.
	 * @param <K> map key type
	 * @param map map to be changed
	 * @param key key pointing to the value to be incremented
	 * @param increment the number by which the value will be increased
	 */
	public static <K> void incrementMapValue(final Map<K,Integer> map, final K key, final int increment){
		if(map.containsKey(key)){
			map.put(key, Math.addExact(map.get(key), increment));
		}
		else{
			map.put(key, increment);
		}
	}
	
	/**
	 * Sort map by value.
	 * @param <K> key type
	 * @param <V> value type
	 * @param map map to be sorted
	 * @param order sort order. True means ascending order, false descending
	 * @return list with sorted entries from map.
	 */
	public static <K,V extends Comparable> List<Map.Entry<K, V>> getSortedListFromMap(final Map<K,V> map, 
			final boolean order){
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
