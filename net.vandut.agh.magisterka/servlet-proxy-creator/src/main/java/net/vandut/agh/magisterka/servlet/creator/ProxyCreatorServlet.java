package net.vandut.agh.magisterka.servlet.creator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.vandut.agh.magisterka.proxy_creator.ProxyCreator;

import org.apache.commons.io.FileUtils;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.ServletUtils;

public class ProxyCreatorServlet extends HttpServlet {

	private final static String WSDL_FILE_REGEXP = ".*\\.wsdl";

	private static final long serialVersionUID = 1L;

	// private static final Logger logger =
	// Logger.getLogger(ProxyCreator.class);

	// http://localhost:8181/servlet-proxy-creator/upload.html

	@Override
	public void init() throws ServletException {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		File tempDir = getTempDir();
		File uniqueDir = getUniqueTempDir();

		MultipartRequest mR = new MultipartRequest(request, tempDir.getAbsolutePath(), 500000);
		String wsdl_location = mR.getParameter("wsdl_location");
		String portToUse = mR.getParameter("port");
		
		if (portToUse == null || "".equals(portToUse)) {
			throw new ServletException("You must provide port");
		}

		if (wsdl_location == null || "".equals(wsdl_location)) {
			throw new ServletException("wsdl_location form parameter missing");
		}

		File wsdlFile;
		if ("upload".equals(wsdl_location)) {
			wsdlFile = getFileFromUploadForm(mR);
		} else {
			wsdlFile = getFileFromInternet(mR.getParameter("url"), tempDir);
		}

		ProxyCreator proxyCreator = new ProxyCreator();
		String bundleJarLocation = createProxyBundleFromForm(proxyCreator, uniqueDir, wsdlFile, portToUse);
		
		File bundleFile = new File(bundleJarLocation);
		File deployDir = new File(Activator.getKarafBase() + "/deploy");
		if(!deployDir.exists() || !deployDir.isDirectory()) {
			throw new IOException(deployDir.getAbsolutePath() + " expected to be existing directory");
		}

		FileUtils.copyFileToDirectory(bundleFile, deployDir);
		
//		PrintWriter out = response.getWriter();
//		out.println("Deployed generated bundle: "+bundleFile.getName());
//		out.println("Use this IP address: "+ConnectionUtils.getRegisteredIPAddress());
//		out.println("Use this port for ECF connection: "+ConnectionUtils.getGeneratedECFPort());
//		out.println("Source location: "+proxyCreator.getSourceLocation());
		
		returnFileToUser(response, proxyCreator.getClientZipFile(),
				"application/zip, application/octet-stream");
	}

	private File getFileFromInternet(String urlString, File tempDir) throws IOException {
		URL url = new URL(urlString);
		File file = new File(tempDir, "service.wsdl");
		FileUtils.copyURLToFile(url, file);
		return file;
	}

	private String createProxyBundleFromForm(ProxyCreator proxyCreator, File uniqueDir, File wsdlFile, String portToUse) throws IOException, ServletException {
		proxyCreator.setWsdlLocation(wsdlFile.getAbsolutePath());
		proxyCreator.setOutputLocation(uniqueDir.getAbsolutePath());
		proxyCreator.setPortToUse(portToUse);

		String bundleJarLocation = null;
		try {
			bundleJarLocation = proxyCreator.generateProxyBundle();
		} catch (Exception e) {
			throw new ServletException(e);
		}

		return bundleJarLocation;
	}

	private void returnFileToUser(HttpServletResponse response, String fileLocation, String mime) throws IOException {
		response.setContentType(mime);
		File file = new File(fileLocation);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\";");
		ServletOutputStream out = response.getOutputStream();
		ServletUtils.returnFile(fileLocation, out);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	public File getTempDir() {
		return (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
	}

	public File getUniqueTempDir() {
		File tempDir = getTempDir();
		UUID uuid = UUID.randomUUID();
		File uniqueDir = new File(tempDir, uuid.toString());
		uniqueDir.mkdir();
		uniqueDir.deleteOnExit();
		return uniqueDir;
	}

	private File getFileFromUploadForm(MultipartRequest mR) throws IOException, ServletException {
		Enumeration<?> files = mR.getFileNames();
		if (!files.hasMoreElements()) {
			throw new ServletException("No file uploaded");
		}

		String name = (String) files.nextElement();
		String filename = mR.getFilesystemName(name);
		File file = mR.getFile(name);

		if (!filename.matches(WSDL_FILE_REGEXP)) {
			throw new ServletException("Uploaded file is not a WSDL file!");
		}

		if (file == null) {
			throw new IOException("No file selected, please try again!");
		}

		return file;
	}

}
