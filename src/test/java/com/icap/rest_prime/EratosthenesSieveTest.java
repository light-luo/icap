package com.icap.rest_prime;

public class EratosthenesSieveTest extends AbstractPrimeCalculatorTestBase {

	@Override
	protected AbstractPrimeCalculator generateUnderTest() {
		return new EratosthenesSieve();
	}
}
