/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.alexhicks.psychicpotodo;

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
public class Vector2Test {
	
	public Vector2Test() {
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
	 * Test of copy method, of class Vector2.
	 */
	@Test
	public void testCopy() {
		System.out.println("copy");
		Vector2 instance = new Vector2(4, 4);
		Vector2 expResult = new Vector2(4, 4);
		Vector2 result = instance.copy();
		instance.x = 3;
		instance.y = 3;
		assertEquals(expResult, result);
	}

	/**
	 * Test of abs method, of class Vector2.
	 */
	@Test
	public void testAbs() {
		System.out.println("abs");
		Vector2 instance = new Vector2(-3, -4);
		Vector2 expResult = new Vector2(3, 4);
		Vector2 result = instance.abs();
		assertEquals(expResult, result);
	}

	/**
	 * Test of add method, of class Vector2.
	 */
	@Test
	public void testAdd_Vector2() {
		System.out.println("add");
		Vector2 other = new Vector2(3, 4);
		Vector2 instance = new Vector2(5, 6);
		Vector2 expResult = new Vector2(8, 10);
		Vector2 result = instance.add(other);
		assertEquals(expResult, result);
	}

	/**
	 * Test of add method, of class Vector2.
	 */
	@Test
	public void testAdd_int() {
		System.out.println("add");
		int both = 4;
		Vector2 instance = new Vector2(3, 7);
		Vector2 expResult = new Vector2(7, 11);
		Vector2 result = instance.add(both);
		assertEquals(expResult, result);
	}

	/**
	 * Test of sub method, of class Vector2.
	 */
	@Test
	public void testSub_Vector2() {
		System.out.println("sub");
		Vector2 other = new Vector2(4, 5);
		Vector2 instance = new Vector2(8, 2);
		Vector2 expResult = new Vector2(4, -3);
		Vector2 result = instance.sub(other);
		assertEquals(expResult, result);
	}

	/**
	 * Test of sub method, of class Vector2.
	 */
	@Test
	public void testSub_int() {
		System.out.println("sub");
		int both = 3;
		Vector2 instance = new Vector2(2, 4);
		Vector2 expResult = new Vector2(-1, 1);
		Vector2 result = instance.sub(both);
		assertEquals(expResult, result);
	}

	/**
	 * Test of mul method, of class Vector2.
	 */
	@Test
	public void testMul_Vector2() {
		System.out.println("mul");
		Vector2 other = new Vector2(2, 3);
		Vector2 instance = new Vector2(3, 4);
		Vector2 expResult = new Vector2(6, 12);
		Vector2 result = instance.mul(other);
		assertEquals(expResult, result);
	}

	/**
	 * Test of mul method, of class Vector2.
	 */
	@Test
	public void testMul_int() {
		System.out.println("mul");
		int both = 4;
		Vector2 instance = new Vector2(2, 3);
		Vector2 expResult = new Vector2(8, 12);
		Vector2 result = instance.mul(both);
		assertEquals(expResult, result);
	}

	/**
	 * Test of div method, of class Vector2.
	 */
	@Test
	public void testDiv_Vector2() {
		System.out.println("div");
		Vector2 other = new Vector2(4, 5);
		Vector2 instance = new Vector2(20, 10);
		Vector2 expResult = new Vector2(5, 2);
		Vector2 result = instance.div(other);
		assertEquals(expResult, result);
	}

	/**
	 * Test of div method, of class Vector2.
	 */
	@Test
	public void testDiv_int() {
		System.out.println("div");
		int both = 3;
		Vector2 instance = new Vector2(12, 9);
		Vector2 expResult = new Vector2(4, 3);
		Vector2 result = instance.div(both);
		assertEquals(expResult, result);
	}

	/**
	 * Test of equals method, of class Vector2.
	 */
	@Test
	public void testEquals() {
		System.out.println("equals");
		Object other = new Vector2(4, 4);
		Vector2 instance = new Vector2(4, 4);
		boolean expResult = true;
		boolean result = instance.equals(other);
		assertEquals(expResult, result);
	}

	/**
	 * Test of hashCode method, of class Vector2.
	 */
	@Test
	public void testHashCode() {
		System.out.println("hashCode");
		Vector2 instance = new Vector2(2, -1);
		int expResult = 14150;
		int result = instance.hashCode();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getDirectionComponents method, of class Vector2.
	 */
	@Test
	public void testGetDirectionComponents() {
		System.out.println("getDirectionComponents");
		Vector2 other = new Vector2(2, 2);
		Vector2 instance = new Vector2(1, 1);
		Direction[] expResult = new Direction[] { Direction.RIGHT, Direction.DOWN };
		Direction[] result = instance.getDirectionComponents(other);
		assertArrayEquals(expResult, result);
	}

	/**
	 * Test of getTopLeft method, of class Vector2.
	 */
	@Test
	public void testGetTopLeft() {
		System.out.println("getTopLeft");
		Vector2 other = new Vector2(3, 2);
		Vector2 instance = new Vector2(2, 3);
		Vector2 expResult = new Vector2(2, 2);
		Vector2 result = instance.getTopLeft(other);
		assertEquals(expResult, result);
	}
	
}
