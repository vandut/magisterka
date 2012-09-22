package net.vandut.magisterka.ksoap.data;

import java.util.ArrayList;
import java.util.List;

public class MethodModel {
	
	private String name = "";

	private List<String> arguments = new ArrayList<String>();
	
	private final ServiceModel service;
	
	public MethodModel(ServiceModel service) {
		this.service = service;
	}
	
	public String getName() {
		return name;
	}

	public MethodModel setName(String name) {
		this.name = name;
		return this;
	}
	
	public List<String> getArguments() {
		return arguments;
	}
	
	public MethodModel addArgument(String name) {
		arguments.add(name);
		return this;
	}
	
	public MethodModel removeArgument(int location) {
		arguments.remove(location);
		return this;
	}

	public int getArgumentCount() {
		return arguments.size();
	}
	
	public String marshall() {
		if(name == null) throw new IllegalStateException("name is null");
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append('|');
		sb.append(arguments.size());
		for(String a : arguments) {
			sb.append('|');
			sb.append(a);
		}
		return sb.toString();
	}
	
	public static MethodModel unmarshall(ServiceModel service, String[] chunks, int idx) {
		MethodModel method = new MethodModel(service);
		method.name = chunks[idx++];
		int argumentsCount = Integer.valueOf(chunks[idx++]);
		for(int i = 0; i < argumentsCount; i++) {
			method.arguments.add(chunks[idx++]);
		}
		return method;
	}
	
	public static int unmarshallChunksOccupied(MethodModel m) {
		return 2 + m.getArgumentCount();
	}

	public ServiceModel getService() {
		return service;
	}

}
