package net.vandut.agh.magisterka.gui.thread;

import java.io.File;
import java.util.UUID;

import net.vandut.agh.magisterka.gui.panel.CreatorPane;
import net.vandut.agh.magisterka.proxy_creator.CreationProgress;
import net.vandut.agh.magisterka.proxy_creator.ProxyCreator;

import org.apache.commons.io.FileUtils;

public abstract class ProxyCreatorThread extends Thread {

	private final CreatorPane creatorPane;
	private final String outputLocation;

	public ProxyCreatorThread(CreatorPane creatorPane, String outputLocation) {
		this.creatorPane = creatorPane;
		this.outputLocation = outputLocation;
	}

	public File getUniqueTempDir() {
		UUID uuid = UUID.randomUUID();
		File uniqueDir = new File(FileUtils.getTempDirectory(), uuid.toString());
		uniqueDir.mkdir();
		uniqueDir.deleteOnExit();
		return uniqueDir;
	}
	
	protected abstract String getWsdlFileLocation() throws Exception;

	@Override
	public void run() {
		creatorPane.setOperationEnabled(false);
		creatorPane.setProgress(5);
		File uniqueTempDir = null;
		try {
			uniqueTempDir = getUniqueTempDir();

			ProxyCreator proxyCreator = new ProxyCreator();
			proxyCreator.setOutputLocation(uniqueTempDir.getAbsolutePath());
			proxyCreator.setWsdlLocation(getWsdlFileLocation());
			String bundleJarLocation = proxyCreator.generateProxyBundle(new CreationProgressCallback());

			creatorPane.setProgress(90);
			FileUtils.copyFileToDirectory(new File(bundleJarLocation), new File(outputLocation));
			creatorPane.setProgress(100);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (uniqueTempDir != null && uniqueTempDir.exists()) {
				FileUtils.deleteQuietly(uniqueTempDir);
			}
			creatorPane.setOperationEnabled(true);
		}
	}

	private class CreationProgressCallback implements CreationProgress {

		@Override
		public void wsdlAnalyzed() {
			creatorPane.setProgress(10);
		}

		@Override
		public void sourcesGenerated() {
			creatorPane.setProgress(30);
		}

		@Override
		public void sourcesCompiled() {
			creatorPane.setProgress(45);
		}

		@Override
		public void filesCopied() {
			creatorPane.setProgress(65);
		}

		@Override
		public void cxfConfCreated() {
			creatorPane.setProgress(75);
		}

		@Override
		public void manifestCreated() {
			creatorPane.setProgress(80);
		}

	}

}
