package com.icap.rest_prime;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;

/**
 * I use a classical Eratosthenes' Sieve to calculate prime numbers.
 * <p/>
 *
 * @author Light
 */
@Path("sieve")
public class EratosthenesSieve extends AbstractPrimeCalculator {
	@Override
	protected List<Integer> getPrimesFromTwoUpTo(int upper) {
		List<Integer> result = new ArrayList<>();
		boolean[] isPrime = new boolean[upper + 1];

		for (int i = 2; i <= upper; i++) {
			isPrime[i] = true;
		}

		for (int i = 2; i <= upper; i++) {
			if (isPrime[i]) {
				result.add(i);
				final int upperDivByI = upper / i;
				for (int j = 2; j <= upperDivByI; j++) {
					isPrime[i * j] = false;
				}
			}
		}

		return result;
	}
}
