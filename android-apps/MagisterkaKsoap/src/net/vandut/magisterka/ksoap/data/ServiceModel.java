package net.vandut.magisterka.ksoap.data;

import java.util.ArrayList;
import java.util.List;

public class ServiceModel {

	private String name = "";
	private String port = "";
	private String path = "";
	private String namespace = "";
	
	private List<MethodModel> methods = new ArrayList<MethodModel>();

	public String getName() {
		return name;
	}

	public ServiceModel setName(String name) {
		this.name = name;
		return this;
	}

	public String getPort() {
		return port;
	}

	public ServiceModel setPort(String port) {
		this.port = port;
		return this;
	}

	public String getPath() {
		return path;
	}

	public ServiceModel setPath(String path) {
		this.path = path;
		return this;
	}

	public String getNamespace() {
		return namespace;
	}

	public ServiceModel setNamespace(String namespace) {
		this.namespace = namespace;
		return this;
	}

	public List<MethodModel> getMethods() {
		return methods;
	}

	public ServiceModel addMethod(MethodModel method) {
		methods.add(method);
		return this;
	}

	public ServiceModel removeMethod(int location) {
		methods.remove(location);
		return this;
	}

	public int getMethodCount() {
		return methods.size();
	}
	
	public String marshall() {
		if(name == null)      throw new IllegalStateException("name is null");
		if(port == null)      throw new IllegalStateException("port is null");
		if(path == null)      throw new IllegalStateException("path is null");
		if(namespace == null) throw new IllegalStateException("namespace is null");
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append('|');
		sb.append(port);
		sb.append('|');
		sb.append(path);
		sb.append('|');
		sb.append(namespace);
		sb.append('|');
		sb.append(methods.size());
		for(MethodModel m : methods) {
			sb.append('|');
			sb.append(m.marshall());
		}
		return sb.toString();
	}
	
	public static ServiceModel unmarshall(String[] chunks, int idx) {
		ServiceModel service = new ServiceModel();
		service.name = chunks[idx++];
		service.port = chunks[idx++];
		service.path = chunks[idx++];
		service.namespace = chunks[idx++];
		int methodsCount = Integer.valueOf(chunks[idx++]);
		for(int i = 0; i < methodsCount; i++) {
			MethodModel m = MethodModel.unmarshall(service, chunks, idx);
			service.methods.add(m);
			idx += MethodModel.unmarshallChunksOccupied(m);
		}
		return service;
	}
	
	public static int unmarshallChunksOccupied(ServiceModel s) {
		int count = 5;
		for(int i = 0; i < s.getMethodCount(); i++) {
			count += MethodModel.unmarshallChunksOccupied(s.getMethods().get(i));
		}
		return count;
	}

}
