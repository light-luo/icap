package com.icap.rest_prime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Path;

/**
 * I use a classical Eratosthenes' Sieve to calculate prime numbers.
 * <p/>
 *
 * @author Light
 */
@Path("sieve")
public class EratosthenesSieve extends AbstractPrimeCalculator {
	private static final int NUMBER_OF_TASKS = 4;
	private static final Logger LOG = Logger.getLogger(EratosthenesSieve.class.getSimpleName());

	private final ExecutorService executor = Executors.newCachedThreadPool();

	@Override
	protected List<Integer> getPrimesFromTwoUpTo(int upper) {
		List<Integer> result = new ArrayList<>();

		// 1 representing composite, and 0 representing prime
		final AtomicIntegerArray isComposite = new AtomicIntegerArray(upper + 1);


		for (int i = 2; i <= upper; i++) {
			if (isComposite.get(i) == 1) continue;
			result.add(i);
			final int numbersPerTask = (upper - i) / NUMBER_OF_TASKS;
			final CountDownLatch latch = new CountDownLatch(NUMBER_OF_TASKS);
			int low = i + 1;
			for (int j = 0; j < NUMBER_OF_TASKS - 1; j++) {
				executor.execute(new EratosthenesSieveWorker(isComposite, latch, i, low, low + numbersPerTask - 1));
				low += numbersPerTask;
			}
			executor.execute(new EratosthenesSieveWorker(isComposite, latch, i, low, upper));
			try {
				latch.await();
			} catch (InterruptedException ex) {
				LOG.log(Level.SEVERE, "Interrupted - not waiting but returning", ex);
			}
		}

		return result;
	}

	private class EratosthenesSieveWorker implements Runnable {

		private final AtomicIntegerArray isComposite;
		private final CountDownLatch latch;
		private final int prime, low, high;

		EratosthenesSieveWorker(AtomicIntegerArray isComposite, CountDownLatch latch, int prime, int low, int high) {
			this.isComposite = isComposite;
			this.latch = latch;
			this.prime = prime;
			this.low = low;
			this.high = high;
		}

		@Override
		public void run() {
			// Find the first number divisible by prime
			int divisee = low;
			for (; divisee <= high && divisee % prime != 0; divisee++);
			// Then set all divisible ones to one (meaning composite)
			for (; divisee <= high; divisee += prime) {
				isComposite.compareAndSet(divisee, 0, 1);
			}
			latch.countDown();
		}
	}
}
