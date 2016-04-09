/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author F.I.D.O.
 */
public class ToolsTest {
	
	public ToolsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_Map_GenericType() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, 0);
		resultMap.put(key, 1);
		
		Tools.incrementMapValue(testMap, key);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}

	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_3args() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, 1);
		resultMap.put(key, 2);
		
		Tools.incrementMapValue(testMap, key, 1);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}
	
	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_3argsEmptyTest() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		resultMap.put(key, 1);

		Tools.incrementMapValue(testMap, key, 1);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}
	
	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_3argsZeroInc() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, 0);
		resultMap.put(key, 0);
		
		Tools.incrementMapValue(testMap, key, 0);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}
	
	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_3argsNumberInc() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, 3);
		resultMap.put(key, 14);
		
		Tools.incrementMapValue(testMap, key, 11);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}
	
	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test (expected = ArithmeticException.class)
	public void testIncrementMapValue_3argsOverflow() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, Integer.MAX_VALUE);
		
		Tools.incrementMapValue(testMap, key, 5);
	}
	
	/**
	 * Test of incrementMapValue method, of class Tools.
	 */
	@Test
	public void testIncrementMapValue_3argsDecr() {
		System.out.println("incrementMapValue");
		Map<String,Integer> testMap = new HashMap<>();
		Map<String,Integer> resultMap = new HashMap<>();
		
		String key = "Test";
		
		testMap.put(key, 3);
		resultMap.put(key, -4);
		
		Tools.incrementMapValue(testMap, key, -7);
		
		assertEquals("Must return incremented map", resultMap, testMap);
	}
	

	/**
	 * Test of getSortedListFromMap method, of class Tools.
	 */
	@Test
	public void testGetSortedListFromMap() {
		System.out.println("getSortedListFromMap");
		
		Map<String,Double> testMap = new HashMap<>();
		String key1 = "a";
		String key2 = "b";
		String key3 = "c";
		String key4 = "d";
		testMap.put(key1, 0.1);
		testMap.put(key2, 6.0);
		testMap.put(key3, 158.256987);
		testMap.put(key4, 158.1649);
		List<Map.Entry<String, Double>> expResult = new ArrayList<>();
		expResult.add(new TestEntry<>(key1, 0.1));
		expResult.add(new TestEntry<>(key2, 6.0));
		expResult.add(new TestEntry<>(key4, 158.1649));
		expResult.add(new TestEntry<>(key3, 158.256987));
		
		List<Map.Entry<String, Double>> result = Tools.getSortedListFromMap(testMap, true);
		assertEquals(result.get(0), expResult.get(0));
	}
	
	/**
	 * Test of getSortedListFromMap method, of class Tools.
	 */
	@Test
	public void testGetSortedListFromMapEmpty() {
		System.out.println("getSortedListFromMap");
		
		Map<String,Double> testMap = new HashMap<>();
		List<Map.Entry<String, Double>> expResult = new ArrayList<>();
		
		List<Map.Entry<String, Double>> result = Tools.getSortedListFromMap(testMap, true);
		assertEquals(expResult, result);
	}
	
	/**
	 * Test of getSortedListFromMap method, of class Tools.
	 */
	@Test
	public void testGetSortedListFromMapDesc() {
		System.out.println("getSortedListFromMap");
		
		Map<String,Double> testMap = new HashMap<>();
		String key1 = "a";
		String key2 = "b";
		String key3 = "c";
		String key4 = "d";
		testMap.put(key1, 0.1);
		testMap.put(key2, 6.0);
		testMap.put(key3, 158.256987);
		testMap.put(key4, 158.1649);
		List<Map.Entry<String, Double>> expResult = new ArrayList<>();
		expResult.add(new TestEntry<>(key3, 158.256987));
		expResult.add(new TestEntry<>(key4, 158.1649));
		expResult.add(new TestEntry<>(key2, 6.0));
		expResult.add(new TestEntry<>(key1, 0.1));
		
		List<Map.Entry<String, Double>> result = Tools.getSortedListFromMap(testMap, false);
		assertEquals(result.get(0), expResult.get(0));
	}
	
	
	
	final class TestEntry<K, V> implements Entry<K, V> {
		private final K key;
		private V value;

		public TestEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}

		@Override
		public int hashCode() {
			int hash = 5;
			return hash;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (!(obj instanceof Entry)) {
				return false;
			}
			final Entry<?, ?> other = (Entry<?, ?>) obj;
			if (!Objects.equals(this.key, other.getKey())) {
				return false;
			}
			if (!Objects.equals(this.value, other.getValue())) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return "<" + key + "=" + value + ">";
		}
		
		
	}
	
}