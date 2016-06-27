package br.inpe.cap.interfacemetrics.domain;

import java.sql.ResultSet;

public class InterfaceMetricPair {

	private Long interfaceMetricsA;
	private Long interfaceMetricsB;
	private String searchType;
	
	public InterfaceMetricPair() {
	}
	
	public InterfaceMetricPair(Long interfaceMetricsA, Long interfaceMetricsB, String searchType) {
		this.interfaceMetricsA = interfaceMetricsA;
		this.interfaceMetricsB = interfaceMetricsB;
		this.searchType = searchType;
	}

	public InterfaceMetricPair(ResultSet rs) throws Exception {
		interfaceMetricsA = rs.getLong("interface_metrics_a");
		interfaceMetricsB = rs.getLong("interface_metrics_b");
		searchType = rs.getString("search_type");
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof InterfaceMetricPair))
			return false;
		
		boolean a = this.interfaceMetricsA.longValue() == ((InterfaceMetricPair)o).getInterfaceMetricsA();
		boolean b = this.interfaceMetricsB.longValue() == ((InterfaceMetricPair)o).getInterfaceMetricsB();
		boolean s = this.searchType.equals(((InterfaceMetricPair)o).getSearchType());
		
		return (a && b && s);
	}

	//accessors
	public Long getInterfaceMetricsA() {
		return interfaceMetricsA;
	}

	public Long getInterfaceMetricsB() {
		return interfaceMetricsB;
	}

	public String getSearchType() {
		return searchType;
	}
}
