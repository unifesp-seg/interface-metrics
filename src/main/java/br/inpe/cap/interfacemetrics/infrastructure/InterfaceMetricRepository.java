package br.inpe.cap.interfacemetrics.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricOccurrencesHelper;
import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.util.ConfigProperties;

public class InterfaceMetricRepository {

	private static final String TABLE = "interface_metrics";
	private String table;

	private static boolean debugMode = false;
	
	public InterfaceMetricRepository() {
		table = TABLE;
	}

	public InterfaceMetricRepository(boolean mock) {
		table = mock ? TABLE + "_test" : TABLE;
	}

	private Connection getConnection() throws Exception {
		String url = ConfigProperties.getProperty("jdbc.url");
		String user = ConfigProperties.getProperty("jdbc.user");
		String pass = ConfigProperties.getProperty("jdbc.pass");

		Connection conn = DriverManager.getConnection(url, user, pass);

		return conn;
	}

	public List<InterfaceMetric> findAllNotProcessed() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String limit = ConfigProperties.getProperty("interface-metrics.processed.max-result");
		String sql = "SELECT * FROM " + table + "";
		sql += " where (processed <> 1 or processed is null)";
		sql += " and project_type = 'CRAWLED'";
		sql += " limit " + limit;

		ResultSet rs = stmt.executeQuery(sql);

		List<InterfaceMetric> list = new ArrayList<InterfaceMetric>();

		while (rs.next()) {
			InterfaceMetric interfaceMetric = new InterfaceMetric(rs);
			list.add(interfaceMetric);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public int countAllNotProccessed() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT count(*) as total FROM " + table + "";
		sql += " where (processed <> 1 or processed is null)";
		sql += " and project_type = 'CRAWLED'";
		ResultSet rs = stmt.executeQuery(sql);

		int total = 0;
		while (rs.next()) {
			total = rs.getInt("total");
		}

		stmt.close();
		conn.close();

		return total;
	}
	
	public List<InterfaceMetric> findAllOrderedById() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT * FROM " + table + " order by id";
		ResultSet rs = stmt.executeQuery(sql);

		List<InterfaceMetric> list = new ArrayList<InterfaceMetric>();

		while (rs.next()) {
			InterfaceMetric interfaceMetric = new InterfaceMetric(rs);
			list.add(interfaceMetric);
		}

		stmt.close();
		conn.close();

		return list;
	}

	public InterfaceMetric findById(long id) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT * FROM " + table + " where id = " + id;
		ResultSet rs = stmt.executeQuery(sql);

		InterfaceMetric interfaceMetric = null;
		while (rs.next()) {
			interfaceMetric = new InterfaceMetric(rs);
		}

		stmt.close();
		conn.close();

		return interfaceMetric;
	}

	public void updateProcessedMethod(InterfaceMetric interfaceMetric) throws Exception {
		updateProcessedMethod(interfaceMetric, false);
	}

	private void updateProcessedMethod(InterfaceMetric interfaceMetric, boolean allLines) throws Exception {
		String sql = "UPDATE " + table + " SET ";
		sql += "processed = ?, ";
		sql += "total_params = ?, ";
		sql += "total_words_method = ?, ";
		sql += "total_words_class = ?, ";
		sql += "only_primitive_types = ?, ";
		sql += "is_static = ?, ";
		sql += "has_type_same_package = ?, ";

		sql += "r0_n0_p0_0 = ?, ";
		sql += "r0_n0_p1_0 = ?, ";
		sql += "r0_n1_p0_0 = ?, ";
		sql += "r0_n1_p1_0 = ?, ";
		sql += "r1_n0_p0_0 = ?, ";
		sql += "r1_n0_p1_0 = ?, ";
		sql += "r1_n1_p0_0 = ?, ";
		sql += "r1_n1_p1_0 = ?, ";
		sql += "r0_n0_p0_1 = ?, ";
		sql += "r0_n0_p1_1 = ?, ";
		sql += "r0_n1_p0_1 = ?, ";
		sql += "r0_n1_p1_1 = ?, ";
		sql += "r1_n0_p0_1 = ?, ";
		sql += "r1_n0_p1_1 = ?, ";
		sql += "r1_n1_p0_1 = ?, ";
		sql += "r1_n1_p1_1 = ?, ";

		sql += "r0_xx_p0_1 = ?, ";
		sql += "r1_xx_p1_1 = ?  ";

		sql += "WHERE project_type = 'CRAWLED' ";
		sql += allLines ? "" : "and id = ? ";
//		sql += allLines ? "" : "WHERE id = ? ";

		Connection conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setInt(1, interfaceMetric.isProcessed() == true ? 1 : 0);
		ps.setInt(2, interfaceMetric.getTotalParams());
		ps.setInt(3, interfaceMetric.getTotalWordsMethod());
		ps.setInt(4, interfaceMetric.getTotalWordsClass());
		ps.setInt(5, interfaceMetric.isOnlyPrimitiveTypes() == true ? 1 : 0);
		ps.setInt(6, interfaceMetric.isStatic() == true ? 1 : 0);
		ps.setInt(7, interfaceMetric.isHasTypeSamePackage() == true ? 1 : 0);

		int i = 8;
		List<OccurrencesCombination> combinations = OccurrencesCombination.allCombinations();
		for(OccurrencesCombination combination : combinations)
			ps.setInt(i++, interfaceMetric.getOccurrencesTotal(combination));

		if (!allLines)
			ps.setLong(26, interfaceMetric.getId());

		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public void clearProcessing() throws Exception {
		InterfaceMetric interfaceMetric = new InterfaceMetric();
		updateProcessedMethod(interfaceMetric, true);
	}

	public List<InterfaceMetric> findOccurrences(InterfaceMetricOccurrencesHelper interfaceMetricSQLHelper) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = interfaceMetricSQLHelper.getOccurrencesSQL(table);
		ResultSet rs = stmt.executeQuery(sql);

		List<InterfaceMetric> list = new ArrayList<InterfaceMetric>();

		while (rs.next()) {
			InterfaceMetric interfaceMetric = new InterfaceMetric(rs);
			list.add(interfaceMetric);
		}

		stmt.close();
		conn.close();

		if(debugMode){
			InterfaceMetric i = interfaceMetricSQLHelper.getInterfaceMetric();
			System.out.println("\n\n-- " + i.getReturnType() + " "+ i.getMethodName() + "(" + i.getParams() + ")");
			System.out.println(sql);
			System.out.println("-- Occurrences = " + list.size());
		}
		
		return list;
	}
}
