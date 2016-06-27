package br.inpe.cap.interfacemetrics.infrastructure;

public enum RepositoryType {
	REAL, MOCK, INNER;

	public String getSufix() {
		String sufix = "";
		if(this == RepositoryType.MOCK)
			sufix = "_test";
		else if(this == RepositoryType.INNER)
			sufix = "_inner";
		return sufix;
	}
}
