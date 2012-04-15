package net.vandut.agh.magisterka.gui.thread;

import net.vandut.agh.magisterka.gui.panel.CreatorPane;

public class DiskProxyCreatorThread extends ProxyCreatorThread {

	private final String wsdlFileLocation;

	public DiskProxyCreatorThread(CreatorPane creatorPane, String wsdlFileLocation, String outputLocation) {
		super(creatorPane, outputLocation);
		this.wsdlFileLocation = wsdlFileLocation;
	}

	@Override
	protected String getWsdlFileLocation() {
		return wsdlFileLocation;
	}

}
