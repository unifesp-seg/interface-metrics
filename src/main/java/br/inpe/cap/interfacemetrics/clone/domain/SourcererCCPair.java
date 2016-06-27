package br.inpe.cap.interfacemetrics.clone.domain;

import org.apache.commons.lang.StringUtils;



public class SourcererCCPair {

	private Long headerIdA;
	private Long headerIdB;
	private Long entityIdA = -1L;
	private Long entityIdB = -1L;

	public SourcererCCPair(String line) {
		String[] parts = StringUtils.split(line, ',');
		this.headerIdA = new Long(parts[0]);
		this.headerIdB = new Long(parts[1]);
	}

	public Long getHeaderIdA() {
		return headerIdA;
	}

	public Long getHeaderIdB() {
		return headerIdB;
	}

	public Long getEntityIdA() {
		return entityIdA;
	}

	public Long getEntityIdB() {
		return entityIdB;
	}
}
