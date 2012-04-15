package net.vandut.agh.magisterka.servlet.caller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Holder;

import org.apache.log4j.Logger;
import org.apache.servicemix.samples.wsdl_first.Person;
import org.apache.servicemix.samples.wsdl_first.UnknownPersonFault;

public class ProxyCaller extends HttpServlet {
	
	private static final long serialVersionUID = 6394143880850074960L;

	private static final Logger logger = Logger.getLogger(ProxyCaller.class);
	
	// http://localhost:8181/servlet-client/ProxyCaller
	
	private static Person person;
	
	protected static void setPerson(Person person) {
		ProxyCaller.person = person;
	}
	
	public void init() throws ServletException {
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("Hello from Java Servlet!");
		try {
			action(out);
		} catch (UnknownPersonFault e) {
			throw new ServletException(e);
		}
	}
	
	public void destroy() {
		super.destroy();
	}
	
	private void action(PrintWriter out) throws UnknownPersonFault {
		Holder<String> personId = new Holder<String>("Person from Servlet");
		Holder<String> ssn = new Holder<String>("caller");
		Holder<String> name = new Holder<String>("caller");

		logger.info("Calling person.getPerson()");
		logger.info("---> SENDING data: personId=" + personId.value + " ssn=" + ssn.value + " name=" + name.value);
		out.println("---> SENDING data: personId=" + personId.value + " ssn=" + ssn.value + " name=" + name.value);
		person.getPerson(personId, ssn, name);
		logger.info("<--- Returned data: personId=" + personId.value + " ssn=" + ssn.value + " name=" + name.value);
		out.println("<--- Returned data: personId=" + personId.value + " ssn=" + ssn.value + " name=" + name.value);
	}

}
