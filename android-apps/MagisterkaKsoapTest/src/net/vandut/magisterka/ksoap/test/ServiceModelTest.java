package net.vandut.magisterka.ksoap.test;

import static net.vandut.magisterka.ksoap.test.DataUtil.assertEqualServices;
import static net.vandut.magisterka.ksoap.test.DataUtil.chunks;
import junit.framework.TestCase;
import net.vandut.magisterka.ksoap.data.MethodModel;
import net.vandut.magisterka.ksoap.data.ServiceModel;

public class ServiceModelTest extends TestCase {

	private void marshallUnmarshallCompare(ServiceModel s1,
			String expectedString) {
		String string = s1.marshall();
		assertEquals(expectedString, string);
		ServiceModel s2 = ServiceModel.unmarshall(chunks(string), 0);
		assertEqualServices(s1, s2);
	}

	public void testShouldStoreRestoreEmptyService() {
		ServiceModel s = new ServiceModel();
		marshallUnmarshallCompare(s, "||||0");
	}

	public void testShouldStoreRestoreSimpleService() {
		ServiceModel s = new ServiceModel()
				.setName("Name")
				.setPort("8080")
				.setPath("Path")
				.setNamespace("Namespace");
		marshallUnmarshallCompare(s, "Name|8080|Path|Namespace|0");
	}

	public void testShouldStoreRestoreServiceMethod() {
		ServiceModel s = new ServiceModel();
		s.setName("Name")
		 .setPort("8080")
		 .setPath("Path")
		 .setNamespace("Namespace")
		 .addMethod(new MethodModel(s).setName("m1").addArgument("m1a1").addArgument("m1a2"))
		 .addMethod(new MethodModel(s).setName("m2").addArgument("m2a1"));
		marshallUnmarshallCompare(s, "Name|8080|Path|Namespace|2|m1|2|m1a1|m1a2|m2|1|m2a1");
	}

}
