package br.inpe.cap.interfacemetrics.match550.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.inpe.cap.interfacemetrics.match550.util.MatchProperties;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class Match550Repository {

	private String neTable = "interface_metrics_a_550_ne_instance";
	private String engTable = "interface_metrics_b_550_instance";

	public final Connection getMatchConnection() throws Exception {
		String url = MatchProperties.getMatch550DatabaseUrl();
		String user = GenieSearchAPIConfig.getDatabaseUser();
		String pass = GenieSearchAPIConfig.getDatabasePassword();

		Connection conn = DriverManager.getConnection(url, user, pass);

		return conn;
	}

	public int countAllNE() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT count(*) as total FROM " + neTable;
		ResultSet rs = stmt.executeQuery(sql);

		int total = 0;
		while (rs.next()) {
			total = rs.getInt("total");
		}

		stmt.close();
		conn.close();

		return total;
	}
		
	public int countAllEng() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT count(*) as total FROM " + engTable;
		ResultSet rs = stmt.executeQuery(sql);

		int total = 0;
		while (rs.next()) {
			total = rs.getInt("total");
		}

		stmt.close();
		conn.close();

		return total;
	}

	public int countAllMatch() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT count(*) as total FROM interface_metrics_550_match_instance";
		ResultSet rs = stmt.executeQuery(sql);

		int total = 0;
		while (rs.next()) {
			total = rs.getInt("total");
		}

		stmt.close();
		conn.close();

		return total;
	}
	
	public List<Long> findAllNEEntityIds() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT distinct(ne_entity_id) entity_id FROM interface_metrics_550_match_instance";

		ResultSet rs = stmt.executeQuery(sql);

		List<Long> list = new ArrayList<Long>();

		while (rs.next()) {
			list.add(rs.getLong("entity_id"));
		}

		stmt.close();
		conn.close();

		return list;
	}

	public List<Long> findAllEngEntityIds() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT  distinct(eng_entity_id) entity_id FROM interface_metrics_550_match_instance";

		ResultSet rs = stmt.executeQuery(sql);

		List<Long> list = new ArrayList<Long>();

		while (rs.next()) {
			list.add(rs.getLong("entity_id"));
		}

		stmt.close();
		conn.close();

		return list;
	}

	public List<Long[]> findAllMatchEntityIds() throws Exception {
		Connection conn = getMatchConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT ne_entity_id, eng_entity_id FROM interface_metrics_550_match_instance";

		ResultSet rs = stmt.executeQuery(sql);

		List<Long[]> list = new ArrayList<Long[]>();

		while (rs.next()) {
			Long[] matchIds = {rs.getLong("ne_entity_id"), rs.getLong("eng_entity_id")};
			list.add(matchIds);
		}

		stmt.close();
		conn.close();

		return list;
	}
}
