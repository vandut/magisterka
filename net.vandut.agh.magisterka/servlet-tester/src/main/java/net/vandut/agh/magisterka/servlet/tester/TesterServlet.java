package net.vandut.agh.magisterka.servlet.tester;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.framework.ServiceReference;

public class TesterServlet extends HttpServlet {

	private static final long serialVersionUID = 6394143880850074960L;

	// private static final Logger logger =
	// Logger.getLogger(TesterServlet.class);

	// http://localhost:8181/servlet-tester/tester

	public void init() throws ServletException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println("Hello from Java TesterServlet!");
	}

	public void destroy() {
		super.destroy();
	}

}
