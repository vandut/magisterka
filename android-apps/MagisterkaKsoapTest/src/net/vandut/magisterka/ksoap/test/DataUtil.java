package net.vandut.magisterka.ksoap.test;

import junit.framework.TestCase;
import net.vandut.magisterka.ksoap.data.HostModel;
import net.vandut.magisterka.ksoap.data.MethodModel;
import net.vandut.magisterka.ksoap.data.ServiceModel;

public class DataUtil extends TestCase {
	
	public static String[] chunks(String marshalled) {
		return marshalled.split("\\|");
	}
	
	public static void assertEqualStrings(String s1, String s2) {
		if(s1 == null) {
			assertTrue(s1 == s2);
		} else {
			assertEquals(s1, s2);
		}
	}
	
	public static void assertEqualMethods(MethodModel m1, MethodModel m2) {
		assertEqualStrings(m1.getName(), m2.getName());
		assertEquals(m1.getArgumentCount(), m2.getArgumentCount());
		for(int i = 0; i < m1.getArgumentCount(); i++) {
			assertEqualStrings(m1.getArguments().get(i), m2.getArguments().get(i));
		}
	}
	
	public static void assertEqualServices(ServiceModel s1, ServiceModel s2) {
		assertEqualStrings(s1.getName(), s2.getName());
		assertEqualStrings(s1.getPath(), s2.getPath());
		assertEqualStrings(s1.getNamespace(), s2.getNamespace());
		assertEquals(s1.getMethodCount(), s2.getMethodCount());
		for(int i = 0; i < s1.getMethodCount(); i++) {
			assertEqualMethods(s1.getMethods().get(i), s2.getMethods().get(i));
		}
	}
	
	public static void assertEqualHosts(HostModel h1, HostModel h2) {
		assertEqualStrings(h1.getName(), h2.getName());
		assertEqualStrings(h1.getIpAddress(), h2.getIpAddress());
		assertEquals(h1.getServiceCount(), h2.getServiceCount());
		for(int i = 0; i < h1.getServiceCount(); i++) {
			assertEqualServices(h1.getServices().get(i), h2.getServices().get(i));
		}
	}

}
