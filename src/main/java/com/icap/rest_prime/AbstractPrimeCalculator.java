package com.icap.rest_prime;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * I expose the REST parameter to the user, and also protects my subclasses from
 * calculating "prime numbers" less than two.
 * <p/>
 *
 * @author Light
 */
public abstract class AbstractPrimeCalculator implements PrimeCalculator {
	@Override
	@GET
	@Path("/{upper}")
	@Produces(MediaType.TEXT_PLAIN)
	public String getPrimesUpToAsString(@PathParam("upper") int upper) {
		return getPrimesUpTo(upper).toString();
	}

	@Override
	public List<Integer> getPrimesUpTo(int upper) {
		if (upper <= 1) return Collections.emptyList();
		return getPrimesFromTwoUpTo(upper);
	}

	protected abstract List<Integer> getPrimesFromTwoUpTo(int upper);
}
