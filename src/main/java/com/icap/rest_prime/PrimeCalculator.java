package com.icap.rest_prime;

import java.util.List;

/**
 * I calculate the list of all primes up to a give number.
 * <p/>
 *
 * @author Light
 */
public interface PrimeCalculator {
	public List<Integer> getPrimesUpTo(int upper);
	public String getPrimesUpToAsString(int upper);
}
