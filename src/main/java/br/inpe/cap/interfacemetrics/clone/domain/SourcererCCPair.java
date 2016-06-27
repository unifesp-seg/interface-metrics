package br.inpe.cap.interfacemetrics.clone.domain;

import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;



public class SourcererCCPair {

	private String line;
	private Long index;
	private Long headerIdA;
	private Long headerIdB;
	private Long entityIdA = -1L;
	private Long entityIdB = -1L;

	public SourcererCCPair(String line) {
		this.line = line;
		String[] parts = StringUtils.split(line, ',');
		this.headerIdA = new Long(parts[0]);
		this.headerIdB = new Long(parts[1]);
	}

//	public SourcererCCPair(String line, boolean processed) {
//		this.line = line;
//
//		String line2 = i + "," + pair.getHeaderIdA() + "," + pair.getHeaderIdB() + "," + pair.getEntityIdA() + "," + pair.getEntityIdB();
//
//		
//		String[] parts = StringUtils.split(line, ',');
//		this.index = new Long(parts[0]);
//		this.headerIdA = new Long(parts[0]);
//		this.headerIdB = new Long(parts[1]);
//		private Long entityIdA = -1L;
//		private Long entityIdB = -1L;
//		
//	}

//	public SourcererCCPair(ResultSet rs) throws Exception {
//		headerIdA = rs.getLong("header_id_a");
//		headerIdB = rs.getLong("header_id_b");
//		entityIdA = rs.getLong("entity_id_a");
//		entityIdB = rs.getLong("entity_id_a");
//	}
//
//	public void loadEntityId(List<SourcererCCHeader> headers) throws Exception {
//		SourcererCCHeader entityIdA = headers.get(this.headerIdA.intValue());
//		entityIdA.loadFromSourceCode();
//		entityIdA.loadFromDB();
//		this.entityIdA = entityIdA.getEntityId();
//
//		SourcererCCHeader entityIdB = headers.get(this.headerIdB.intValue());
//		entityIdB.loadFromSourceCode();
//		entityIdB.loadFromDB();
//		this.entityIdB = entityIdB.getEntityId();
//	}
//	
//	public String getLine() {
//		return line;
//	}

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
