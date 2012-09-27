package net.vandut.agh.magisterka.logic.service;

import javax.jws.WebService;

@WebService(serviceName = "LogicService", targetNamespace = "http://service.logic.magisterka.agh.vandut.net/", endpointInterface = "net.vandut.agh.magisterka.logic.service.Logic")
public class LogicImpl implements Logic {
	
	String status = "UNKNOWN";

	public String logicStatus() {
		return status;
	}

	public String logicStart() {
		status = "RUNNING";
		return "Started";
	}

	public String logicStop() {
		status = "IDLE";
		return "Stopped";
	}

}
