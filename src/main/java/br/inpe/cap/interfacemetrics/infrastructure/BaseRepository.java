package br.inpe.cap.interfacemetrics.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;

import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public abstract class BaseRepository {

	public final Connection getConnection() throws Exception {
		String url = GenieSearchAPIConfig.DATABASE_URL();
		String user = GenieSearchAPIConfig.DATABASE_USER();
		String pass = GenieSearchAPIConfig.DATABASE_PASSWORD();

		Connection conn = DriverManager.getConnection(url, user, pass);

		return conn;
	}
}
