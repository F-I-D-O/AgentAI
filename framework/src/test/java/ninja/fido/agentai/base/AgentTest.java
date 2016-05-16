/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ninja.fido.agentai.base;

import java.util.Map;
import ninja.fido.agentSCAI.base.Resource;
import ninja.fido.agentSCAI.ResourceType;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTable;
import ninja.fido.agentSCAI.modules.decisionMaking.DecisionTablesMapKey;
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
public class AgentTest {
	
	public AgentTest() {
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
	 * Test of getGoal method, of class Agent.
	 */
//	@Test
//	public void testGetGoal() {
//		System.out.println("getGoal");
//		Agent instance = new AgentImpl();
//		Goal expResult = null;
//		Goal result = instance.getGoal();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of IsAssigned method, of class Agent.
//	 */
//	@Test
//	public void testIsAssigned() {
//		System.out.println("IsAssigned");
//		Agent instance = new AgentImpl();
//		boolean expResult = false;
//		boolean result = instance.IsAssigned();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setAssigned method, of class Agent.
//	 */
//	@Test
//	public void testSetAssigned() {
//		System.out.println("setAssigned");
//		boolean assigned = false;
//		Agent instance = new AgentImpl();
//		instance.setAssigned(assigned);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getReceivedMineralsTotal method, of class Agent.
//	 */
//	@Test
//	public void testGetReceivedMineralsTotal() {
//		System.out.println("getReceivedMineralsTotal");
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getReceivedMineralsTotal();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getReceivedGasTotal method, of class Agent.
//	 */
//	@Test
//	public void testGetReceivedGasTotal() {
//		System.out.println("getReceivedGasTotal");
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getReceivedGasTotal();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setCommandAgent method, of class Agent.
//	 */
//	@Test
//	public void testSetCommandAgent() {
//		System.out.println("setCommandAgent");
//		CommandAgent commandAgent = null;
//		Agent instance = new AgentImpl();
//		instance.setCommandAgent(commandAgent);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getCommandAgent method, of class Agent.
//	 */
//	@Test
//	public void testGetCommandAgent() {
//		System.out.println("getCommandAgent");
//		Agent instance = new AgentImpl();
//		CommandAgent expResult = null;
//		CommandAgent result = instance.getCommandAgent();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of addToDecisionTablesMap method, of class Agent.
//	 */
//	@Test
//	public void testAddToDecisionTablesMap() {
//		System.out.println("addToDecisionTablesMap");
//		DecisionTablesMapKey key = null;
//		DecisionTable map = null;
//		Agent instance = new AgentImpl();
//		instance.addToDecisionTablesMap(key, map);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getDecisionTablesMap method, of class Agent.
//	 */
//	@Test
//	public void testGetDecisionTablesMap() {
//		System.out.println("getDecisionTablesMap");
//		Agent instance = new AgentImpl();
//		Map<DecisionTablesMapKey, DecisionTable> expResult = null;
//		Map<DecisionTablesMapKey, DecisionTable> result = instance.getDecisionTablesMap();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of run method, of class Agent.
//	 */
//	@Test
//	public void testRun() throws Exception {
//		System.out.println("run");
//		Agent instance = new AgentImpl();
//		instance.run();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of onActivityFinish method, of class Agent.
//	 */
//	@Test
//	public void testOnActivityFinish() {
//		System.out.println("onActivityFinish");
//		Activity activity = null;
//		Agent instance = new AgentImpl();
//		instance.onActivityFinish(activity);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of onActionFailed method, of class Agent.
//	 */
//	@Test
//	public void testOnActionFailed() {
//		System.out.println("onActionFailed");
//		String reason = "";
//		Agent instance = new AgentImpl();
//		instance.onActionFailed(reason);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of receiveResource method, of class Agent.
//	 */
//	@Test
//	public void testReceiveResource() {
//		System.out.println("receiveResource");
//		Resource resource = null;
//		Agent instance = new AgentImpl();
//		instance.receiveResource(resource);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getOwnedGas method, of class Agent.
//	 */
//	@Test
//	public void testGetOwnedGas() {
//		System.out.println("getOwnedGas");
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getOwnedGas();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getOwnedMinerals method, of class Agent.
//	 */
//	@Test
//	public void testGetOwnedMinerals() {
//		System.out.println("getOwnedMinerals");
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getOwnedMinerals();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getOwnedSupply method, of class Agent.
//	 */
//	@Test
//	public void testGetOwnedSupply() {
//		System.out.println("getOwnedSupply");
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getOwnedSupply();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of giveResource method, of class Agent.
//	 */
//	@Test
//	public void testGiveResource() throws Exception {
//		System.out.println("giveResource");
//		Agent receiver = null;
//		ResourceType material = null;
//		int amount = 0;
//		Agent instance = new AgentImpl();
//		instance.giveResource(receiver, material, amount);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of queInfo method, of class Agent.
//	 */
//	@Test
//	public void testQueInfo() {
//		System.out.println("queInfo");
//		Info info = null;
//		Agent instance = new AgentImpl();
//		instance.queInfo(info);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getMissingMinerals method, of class Agent.
//	 */
//	@Test
//	public void testGetMissingMinerals() {
//		System.out.println("getMissingMinerals");
//		int neededAmount = 0;
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getMissingMinerals(neededAmount);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getMissingGas method, of class Agent.
//	 */
//	@Test
//	public void testGetMissingGas() {
//		System.out.println("getMissingGas");
//		int neededAmount = 0;
//		Agent instance = new AgentImpl();
//		int expResult = 0;
//		int result = instance.getMissingGas(neededAmount);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of requestSended method, of class Agent.
//	 */
//	@Test
//	public void testRequestSended() {
//		System.out.println("requestSended");
//		Request request = null;
//		Agent instance = new AgentImpl();
//		boolean expResult = false;
//		boolean result = instance.requestSended(request);
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of addSendedRequest method, of class Agent.
//	 */
//	@Test
//	public void testAddSendedRequest() {
//		System.out.println("addSendedRequest");
//		Request request = null;
//		Agent instance = new AgentImpl();
//		instance.addSendedRequest(request);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of chooseAction method, of class Agent.
//	 */
//	@Test
//	public void testChooseAction() {
//		System.out.println("chooseAction");
//		Agent instance = new AgentImpl();
//		Activity expResult = null;
//		Activity result = instance.chooseAction();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of routine method, of class Agent.
//	 */
//	@Test
//	public void testRoutine() {
//		System.out.println("routine");
//		Agent instance = new AgentImpl();
//		instance.routine();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of spendSupply method, of class Agent.
//	 */
//	@Test
//	public void testSpendSupply() throws Exception {
//		System.out.println("spendSupply");
//		ResourceType material = null;
//		int amount = 0;
//		Agent instance = new AgentImpl();
//		instance.spendSupply(material, amount);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of processInfo method, of class Agent.
//	 */
//	@Test
//	public void testProcessInfo() {
//		System.out.println("processInfo");
//		Info info = null;
//		Agent instance = new AgentImpl();
//		instance.processInfo(info);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getDefaultGoal method, of class Agent.
//	 */
//	@Test
//	public void testGetDefaultGoal() {
//		System.out.println("getDefaultGoal");
//		Agent instance = new AgentImpl();
//		Goal expResult = null;
//		Goal result = instance.getDefaultGoal();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of addToCommandQueue method, of class Agent.
//	 */
//	@Test
//	public void testAddToCommandQueue() {
//		System.out.println("addToCommandQueue");
//		Order command = null;
//		Agent instance = new AgentImpl();
//		instance.addToCommandQueue(command);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of setGoal method, of class Agent.
//	 */
//	@Test
//	public void testSetGoal() {
//		System.out.println("setGoal");
//		Goal goal = null;
//		Agent instance = new AgentImpl();
//		instance.setGoal(goal);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of decide method, of class Agent.
//	 */
//	@Test
//	public void testDecide() throws Exception {
//		System.out.println("decide");
//		Agent instance = new AgentImpl();
//		Activity expResult = null;
//		Activity result = instance.decide();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of initialize method, of class Agent.
//	 */
//	@Test
//	public void testInitialize() {
//		System.out.println("initialize");
//		Agent instance = new AgentImpl();
//		instance.initialize();
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	/**
//	 * Test of getDefaultDecisionTablesMap method, of class Agent.
//	 */
//	@Test
//	public void testGetDefaultDecisionTablesMap() {
//		System.out.println("getDefaultDecisionTablesMap");
//		Agent instance = new AgentImpl();
//		Map<DecisionTablesMapKey, DecisionTable> expResult = null;
//		Map<DecisionTablesMapKey, DecisionTable> result = instance.getDefaultDecisionTablesMap();
//		assertEquals(expResult, result);
//		// TODO review the generated test code and remove the default call to fail.
//		fail("The test case is a prototype.");
//	}
//
//	public class AgentImpl extends Agent {
//
//		public Activity chooseAction() {
//			return null;
//		}
//
//		public Goal getDefaultGoal() {
//			return null;
//		}
//	}
	
}
