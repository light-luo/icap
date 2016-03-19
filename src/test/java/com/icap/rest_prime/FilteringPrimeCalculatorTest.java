package com.icap.rest_prime;

public class FilteringPrimeCalculatorTest extends AbstractPrimeCalculatorTestBase {

	@Override
	protected AbstractPrimeCalculator generateUnderTest() {
		return new FilteringPrimeCalculator();
	}
}
