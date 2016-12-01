/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.alexhicks.psychicpotodo.network;

import net.alexhicks.psychicpotodo.Vector2;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alex
 */
public class ProtocolStatementTest {
	
	public ProtocolStatementTest() {
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
	 * Test of set method, of class ProtocolStatement.
	 */
	@Test
	public void testSet_String_String() {
		System.out.println("set");
		String name = "abc";
		String value = "123";
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set(name, value);
		String expResult = "test abc=123";
		assertEquals(expResult, instance.toString());
	}

	/**
	 * Test of set method, of class ProtocolStatement.
	 */
	@Test
	public void testSet_String_double() {
		System.out.println("set");
		String name = "abc";
		double value = 4.2;
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set(name, value);
		String expResult = "test abc=4.2";
		assertEquals(expResult, instance.toString());
	}

	/**
	 * Test of set method, of class ProtocolStatement.
	 */
	@Test
	public void testSet_String_float() {
		System.out.println("set");
		String name = "abc";
		float value = 3.2F;
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set(name, value);
		String expResult = "test abc=3.2";
		assertEquals(expResult, instance.toString());
	}

	/**
	 * Test of set method, of class ProtocolStatement.
	 */
	@Test
	public void testSet_String_int() {
		System.out.println("set");
		String name = "abc";
		int value = 6;
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set(name, value);
		String expResult = "test abc=6";
		assertEquals(expResult, instance.toString());
	}

	/**
	 * Test of set method, of class ProtocolStatement.
	 */
	@Test
	public void testSet_String_ObjectArr() {
		System.out.println("set");
		String name = "abc";
		Object[] value = new Vector2[] { new Vector2(2, 3) };
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set(name, value);
		String expResult = "test abc=(2,3)";
		assertEquals(expResult, instance.toString());
	}

	/**
	 * Test of toString method, of class ProtocolStatement.
	 */
	@Test
	public void testToString() {
		System.out.println("toString");
		ProtocolStatement instance = new ProtocolStatement("test");
		instance.set("abc", "245");
		String expResult = "test abc=245";
		String result = instance.toString();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getAction method, of class ProtocolStatement.
	 */
	@Test
	public void testGetAction() {
		System.out.println("getAction");
		ProtocolStatement instance = new ProtocolStatement("test");
		String expResult = "test";
		String result = instance.getAction();
		assertEquals(expResult, result);
	}

	/**
	 * Test of fromNetworkString method, of class ProtocolStatement.
	 */
	@Test
	public void testFromNetworkString() {
		System.out.println("fromNetworkString");
		String raw = "test abc=32.4";
		ProtocolStatement expResult = new ProtocolStatement("test");
		expResult.set("abc", 32.4);
		ProtocolStatement result = ProtocolStatement.fromNetworkString(raw);
		assertEquals(expResult, result);
	}
	
}
