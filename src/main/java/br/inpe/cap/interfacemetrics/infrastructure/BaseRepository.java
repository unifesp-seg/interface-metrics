package br.inpe.cap.interfacemetrics.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;

import br.inpe.cap.interfacemetrics.infrastructure.util.ConfigProperties;

public abstract class BaseRepository {

	public final Connection getConnection() throws Exception {
		String url = ConfigProperties.getProperty("jdbc.url.batabase");
		String user = ConfigProperties.getProperty("jdbc.user");
		String pass = ConfigProperties.getProperty("jdbc.pass");

		Connection conn = DriverManager.getConnection(url, user, pass);

		return conn;
	}
}
