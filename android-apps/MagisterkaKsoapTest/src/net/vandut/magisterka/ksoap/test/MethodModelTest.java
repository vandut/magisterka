package net.vandut.magisterka.ksoap.test;

import net.vandut.magisterka.ksoap.data.MethodModel;
import junit.framework.TestCase;

import static net.vandut.magisterka.ksoap.test.DataUtil.chunks;
import static net.vandut.magisterka.ksoap.test.DataUtil.assertEqualMethods;

public class MethodModelTest extends TestCase {
	
	private void marshallUnmarshallCompare(MethodModel m1, String expectedString) {
		String string = m1.marshall();
		assertEquals(expectedString, string);
		MethodModel m2 = MethodModel.unmarshall(null, chunks(string), 0);
		assertEqualMethods(m1, m2);
	}
	
	public void testShouldStoreRestoreEmptyMethod() {
		MethodModel m1 = new MethodModel(null);
		marshallUnmarshallCompare(m1, "|0");
	}
	
	public void testShouldStoreRestoreEmptyMethodWithName() {
		MethodModel m1 = new MethodModel(null);
		m1.setName("xxx");
		marshallUnmarshallCompare(m1, "xxx|0");
	}
	
	public void testShouldStoreRestoreMethodWithNameAnd1Arg() {
		MethodModel m1 = new MethodModel(null);
		m1.setName("xxx");
		m1.addArgument("Argument");
		marshallUnmarshallCompare(m1, "xxx|1|Argument");
	}
	
	public void testShouldStoreRestoreMethodWithNameAnd2Args() {
		MethodModel m1 = new MethodModel(null);
		m1.setName("xxx");
		m1.addArgument("Argument1");
		m1.addArgument("Argument2");
		marshallUnmarshallCompare(m1, "xxx|2|Argument1|Argument2");
	}

}
