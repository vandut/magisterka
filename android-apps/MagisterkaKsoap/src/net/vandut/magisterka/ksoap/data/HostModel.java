package net.vandut.magisterka.ksoap.data;

import java.util.ArrayList;
import java.util.List;

public class HostModel {

	private String name = "";
	private String ipAddress = "";

	private List<ServiceModel> services = new ArrayList<ServiceModel>();

	public String getName() {
		return name;
	}

	public HostModel setName(String name) {
		this.name = name;
		return this;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public HostModel setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	public List<ServiceModel> getServices() {
		return services;
	}

	public HostModel addService(ServiceModel service) {
		services.add(service);
		return this;
	}

	public HostModel removeService(int location) {
		services.remove(location);
		return this;
	}

	public int getServiceCount() {
		return services.size();
	}

	public String marshall() {
		if (name == null)
			throw new IllegalStateException("name is null");
		if (ipAddress == null)
			throw new IllegalStateException("ipAddress is null");
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append('|');
		sb.append(ipAddress);
		sb.append('|');
		sb.append(services.size());
		for (ServiceModel s : services) {
			sb.append('|');
			sb.append(s.marshall());
		}
		return sb.toString();
	}

	public static HostModel unmarshall(String string) {
		return unmarshall(string.split("\\|"), 0);
	}

	private static HostModel unmarshall(String[] chunks, int idx) {
		HostModel host = new HostModel();
		host.name = chunks[idx++];
		host.ipAddress = chunks[idx++];
		int servicesCount = Integer.valueOf(chunks[idx++]);
		for (int i = 0; i < servicesCount; i++) {
			ServiceModel s = ServiceModel.unmarshall(chunks, idx);
			host.services.add(s);
			idx += ServiceModel.unmarshallChunksOccupied(s);
		}
		return host;
	}

}
