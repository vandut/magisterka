package net.vandut.agh.magisterka.logic.service;

import javax.jws.WebService;

@WebService(serviceName = "LogicService", targetNamespace = "http://service.logic.magisterka.agh.vandut.net/", endpointInterface = "net.vandut.agh.magisterka.logic.service.Logic")
public class LogicImpl implements Logic {

	public String logicStatus() {
		return "Status";
	}

	public String logicStart() {
		return "Started";
	}

	public String logicStop() {
		return "Stopped";
	}

}
