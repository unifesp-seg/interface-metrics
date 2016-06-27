package br.inpe.cap.interfacemetrics.clone.domain;

import org.apache.commons.lang.StringUtils;



public class InterfaceMetricsPairsClone {

	private String line;
	private Long interfaceMetricsIdA;
	private Long entityIdA;
	private Long interfaceMetricsIdB;
	private Long entityIdB;
	private boolean clone;

	public InterfaceMetricsPairsClone(String line) {
		this.line = line;
		String[] parts = StringUtils.split(line, ',');
		this.interfaceMetricsIdA = new Long(parts[0]);
		this.entityIdA = new Long(parts[1]);
		this.interfaceMetricsIdB = new Long(parts[2]);
		this.entityIdB = new Long(parts[3]);
	}

	public String getLine() {
		return line;
	}

	public Long getInterfaceMetricsIdA() {
		return interfaceMetricsIdA;
	}

	public Long getEntityIdA() {
		return entityIdA;
	}

	public Long getInterfaceMetricsIdB() {
		return interfaceMetricsIdB;
	}

	public Long getEntityIdB() {
		return entityIdB;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}
}
