package net.vandut.agh.magisterka.proxy_creator;

public interface CreationProgress {

	void sourcesGenerated();

	void sourcesCompiled();

	void filesCopied();

	void wsdlAnalyzed();

	void cxfConfCreated();

	void manifestCreated();

}
