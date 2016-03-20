package com.icap.rest_prime;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Path;

/**
 * I filter out any non-primes and leave only the primes in the result list.
 * <p/>
 *
 * @author Light
 */
@Path("filter")
public class FilteringPrimeCalculator extends AbstractPrimeCalculator {

	@Override
	protected List<Integer> getPrimesFromTwoUpTo(int upper) {
		List<Integer> inputList = new ArrayList<>(upper);
		for (int i = 2; i <= upper; i++) {
			inputList.add(i);
		}

		return inputList.parallelStream().filter(i -> isPrime(i)).collect(Collectors.toList());
	}

	private boolean isPrime(Integer input) {
		final int sqrt = (int) Math.sqrt(input);
		for (int divisor = 2; divisor <= sqrt; divisor++) {
			if (input % divisor == 0) {
				return false;
			}
		}
		return true;
	}
}
