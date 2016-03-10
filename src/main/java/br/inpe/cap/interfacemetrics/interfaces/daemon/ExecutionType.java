package br.inpe.cap.interfacemetrics.interfaces.daemon;

public enum ExecutionType {
	INTERFACE_METRICS, PARAMS;

	public boolean isInterfaceMetrics() {
		return this == ExecutionType.INTERFACE_METRICS;
	}

	public boolean isParams() {
		return this == ExecutionType.PARAMS;
	}
}
