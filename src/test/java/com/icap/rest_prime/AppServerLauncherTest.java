package com.icap.rest_prime;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppServerLauncherTest {

	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// Create the client
		Client c = ClientBuilder.newClient();
		// Find the target
		target = c.target(AppServerLauncher.BASE_URI);
		// Start the server
		new Thread() { @Override public void run() {
			try { AppServerLauncher.main(null); } catch (Exception ex) {
				throw new RuntimeException(ex); }}}.start();
		AppServerLauncher.TESTING_LATCH.await();
	}

	@After
	public void tearDown() throws Exception {
		AppServerLauncher.WAITING_LATCH.countDown();
	}

	@Test
	public void testRequest() throws IOException, InterruptedException {
		String responseMsg = target.path("sieve/5").request().get(String.class);
		assertEquals("[2, 3, 5]", responseMsg);
		responseMsg = target.path("filter/5").request().get(String.class);
		assertEquals("[2, 3, 5]", responseMsg);
	}
}
