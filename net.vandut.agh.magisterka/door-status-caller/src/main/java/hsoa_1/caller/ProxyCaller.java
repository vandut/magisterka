package hsoa_1.caller;

import hsoa_1.ServiceSoap;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ProxyCaller extends HttpServlet {

	private static final long serialVersionUID = 1490189723287349933L;

	private static final Logger logger = Logger.getLogger(ProxyCaller.class);

	// http://localhost:8181/door-status-caller/status

	private static ServiceSoap serviceSoap;

	protected static void setServiceSoap(ServiceSoap person) {
		ProxyCaller.serviceSoap = person;
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
		logger.info("Calling serviceSoap.doorStatus()");
		out.println("Calling serviceSoap.doorStatus()");
		logger.info("---> SENDING data");
		out.println("---> SENDING data");
		String status = serviceSoap.doorStatus();
		logger.info("<--- Returned data: status=" + status);
		out.println("<--- Returned data: status=" + status);
	}

}
