package com.icap.rest_prime;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import junit.framework.Assert;

public abstract class AbstractPrimeCalculatorTestBase {

	private final AbstractPrimeCalculator underTest = generateUnderTest();

	protected abstract AbstractPrimeCalculator generateUnderTest();

	@Test
	public void test_GetPrimesUpToAsString() {
		Assert.assertEquals("[2]", underTest.getPrimesUpToAsString(2));
	}

	@Test
	public void test_GetPrimesUpTo_LessThanTwo() {
		Assert.assertEquals(Collections.emptyList(), underTest.getPrimesUpTo(1));
		Assert.assertEquals(Collections.emptyList(), underTest.getPrimesUpTo(0));
		Assert.assertEquals(Collections.emptyList(), underTest.getPrimesUpTo(-1));
	}

	@Test
	public void test_GetPrimesFromTwoUpTo() {
		Assert.assertEquals(Arrays.asList(2, 3, 5, 7), underTest.getPrimesFromTwoUpTo(10));
		Assert.assertEquals(Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71,
				73, 79, 83, 89, 97), underTest.getPrimesFromTwoUpTo(100));
	}
}
