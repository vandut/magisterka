package net.vandut.agh.magisterka.gui.thread;

import java.io.File;
import java.net.URL;
import java.util.UUID;

import net.vandut.agh.magisterka.gui.panel.CreatorPane;

import org.apache.commons.io.FileUtils;

public class UrlProxyCreatorThread extends ProxyCreatorThread {

	private final String wsdlUrlLocation;

	public UrlProxyCreatorThread(CreatorPane creatorPane, String wsdlUrlLocation, String outputLocation) {
		super(creatorPane, outputLocation);
		this.wsdlUrlLocation = wsdlUrlLocation;
	}
	
	private File downloadUrlToFile(String url) throws Exception {
		URL source = new URL(url);
		File destination = new File(FileUtils.getTempDirectory(), UUID.randomUUID().toString());
		destination.deleteOnExit();
		FileUtils.copyURLToFile(source, destination, 2000, 2000);
		return destination;
	}

	@Override
	protected String getWsdlFileLocation() throws Exception {
		return downloadUrlToFile(wsdlUrlLocation).getAbsolutePath();
	}

}
