package net.vandut.agh.magisterka.sample.remoteservice;

public interface IHello {

	/**
	 * @since 2.0
	 */
	public String hello(String from);
	
	/**
	 * @since 3.0
	 */
	public String helloMessage(HelloMessage message);
}
