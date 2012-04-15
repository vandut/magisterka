package org.apache.servicemix.samples.wsdl_first;

import javax.xml.ws.Holder;

public class PersonProxy implements Person {

	private transient Person person;

	public void setPerson(Person person) {
		System.out.println("Setting out person="+person);
		this.person = person;
	}

	public void getPerson(Holder<String> personId, Holder<String> ssn,
			Holder<String> name) throws UnknownPersonFault {
		System.out.println("PersonProxy: calling getPerson() on proxy object");
		System.out.println("--> person.getPerson(personId.value="+personId.value+", ssn.value="+ssn.value+", name.value="+name.value+");");
		person.getPerson(personId, ssn, name);
		personId.value = null;
		System.out.println("<-- person.getPerson(personId.value="+personId.value+", ssn.value="+ssn.value+", name.value="+name.value+");");
	}

}
