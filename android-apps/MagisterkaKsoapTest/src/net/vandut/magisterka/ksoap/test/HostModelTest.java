package net.vandut.magisterka.ksoap.test;

import static net.vandut.magisterka.ksoap.test.DataUtil.assertEqualHosts;
import junit.framework.TestCase;
import net.vandut.magisterka.ksoap.data.HostModel;
import net.vandut.magisterka.ksoap.data.ServiceModel;

public class HostModelTest extends TestCase {

	private void marshallUnmarshallCompare(HostModel h1,
			String expectedString) {
		String string = h1.marshall();
		System.out.println("string="+string);
		assertEquals(expectedString, string);
		HostModel h2 = HostModel.unmarshall(string);
		assertEqualHosts(h1, h2);
	}

	public void testShouldStoreRestoreEmptyService() {
		HostModel h = new HostModel();
		marshallUnmarshallCompare(h, "||0");
	}

	public void testShouldStoreRestoreSimpleHost() {
		HostModel h = new HostModel()
				.setName("Hathor")
				.setIpAddress("192.168.1.3");
		marshallUnmarshallCompare(h, "Hathor|192.168.1.3|0");
	}

	public void testShouldStoreRestoreHostService() {
		HostModel h = new HostModel()
		 .setName("Hathor")
		 .setIpAddress("192.168.1.3")
		 .addService(new ServiceModel()
			.setName("Name")
			.setPort("8080")
			.setPath("Path")
			.setNamespace("Namespace"));
		marshallUnmarshallCompare(h, "Hathor|192.168.1.3|1|Name|8080|Path|Namespace|0");
	}

	public void testShouldStoreRestoreMultipleHostService() {
		HostModel h = new HostModel()
		 .setName("Hathor")
		 .setIpAddress("192.168.1.3")
		 .addService(new ServiceModel()
			.setName("Name1")
			.setPort("8081")
			.setPath("Path1")
			.setNamespace("Namespace1"))
		 .addService(new ServiceModel()
			.setName("Name2")
			.setPort("8082")
			.setPath("Path2")
			.setNamespace("Namespace2"));
		marshallUnmarshallCompare(h, "Hathor|192.168.1.3|2|Name1|8081|Path1|Namespace1|0|Name2|8082|Path2|Namespace2|0");
	}

}