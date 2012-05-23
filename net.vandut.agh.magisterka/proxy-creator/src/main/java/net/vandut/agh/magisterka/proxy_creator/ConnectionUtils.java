package net.vandut.agh.magisterka.proxy_creator;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ConnectionUtils {

	public static final int DEFAULT_ECF_PORT = 3787;

	private static int generatedECFPort = DEFAULT_ECF_PORT;

	private ConnectionUtils() {
		throw new AssertionError();
	}

	public static String getRegisteredIPAddress() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public static void generateECFPort() {
		generatedECFPort = DEFAULT_ECF_PORT + (int) (Math.random() * 1000);
	}

	public static int getGeneratedECFPort() {
		return generatedECFPort;
	}

}
