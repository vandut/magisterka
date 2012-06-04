package hsoa_2.caller;

import hsoa_2.ServiceSoap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class OpenServlet extends HttpServlet {

	private static final long serialVersionUID = 1490189723287349933L;

	private static final Logger logger = Logger.getLogger(OpenServlet.class);

	// http://localhost:8181/door-status-caller/status

	private static ServiceSoap serviceSoap;

	protected static void setServiceSoap(ServiceSoap person) {
		OpenServlet.serviceSoap = person;
	}

	public void init() throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("Hello from Java Servlet!");
		try {
			action(out);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	public void destroy() {
		super.destroy();
	}

	private void action(PrintWriter out) throws Exception {
		logger.info("Calling serviceSoap.doorOpen()");
		out.println("Calling serviceSoap.doorOpen()");
		logger.info("---> SENDING data");
		out.println("---> SENDING data");
		String returned = serviceSoap.doorOpen();
		logger.info("<--- Returned data: status=" + returned);
		out.println("<--- Returned data: status=" + returned);
	}

}
