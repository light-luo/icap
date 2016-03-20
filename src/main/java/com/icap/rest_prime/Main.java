package com.icap.rest_prime;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * I am the main class to launch the application.
 * <p/>
 *
 * @author Light
 */
public class Main {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/rest_prime/";

    static final CountDownLatch WAITING_LATCH = new CountDownLatch(1);

    // I'm for testing purpose only to coordinate main and testing threads
    static final CountDownLatch TESTING_LATCH = new CountDownLatch(1);

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
     * @return Grizzly HTTP server.
     */
    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig().packages("com.icap.rest_prime");

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    @SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, InterruptedException {
        final HttpServer server = startServer();
        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
        new Thread(new TearDownWaitingWorker(), "Teardown-Waiting-Thread").start();
        TESTING_LATCH.countDown();
        WAITING_LATCH.await();
        server.stop();
    }

    private static class TearDownWaitingWorker implements Runnable {
    	@Override
    	public void run() {
    		try {
    			System.in.read();
    		} catch (IOException ex) {
    			throw new RuntimeException(ex);
    		}
    		WAITING_LATCH.countDown();
    	}
    }
}
