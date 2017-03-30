package br.inpe.cap.interfacemetrics.clone.infrastructure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCHeader;
import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCPair;
import br.inpe.cap.interfacemetrics.infrastructure.BaseRepository;

public class SourcererCCRepository extends BaseRepository {

	private static boolean debugMode = false;

	public Long getEntityId(SourcererCCHeader ccHeader) throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = "";
		sql += "\n select e.entity_id as entity_id";
		sql += "\n from   entities e,";
		sql += "\n        projects p";
		sql += "\n where  e.project_id = p.project_id";
		sql += "\n and    p.project_type = 'CRAWLED'";
		sql += "\n and    SUBSTRING(p.`name`,1,3) = '" + ccHeader.getMethodProjectNamePrefix() + "'";
		sql += "\n and    e.fqn = '" + ccHeader.getMethodFQN() + "'";
		if (ccHeader.getMethodParams().isEmpty())
			sql += "\n and e.params = '()'";
		else {
			for (String arg : ccHeader.getMethodParams())
				sql += "\n and e.params like '(%" + arg + "%)'";
		}
		
		ResultSet rs = stmt.executeQuery(sql);

		Long entityId = -1L;
		int i = 0;
		while (rs.next()) {
			entityId = rs.getLong("entity_id");
			i++;
		}

		stmt.close();
		conn.close();

		entityId = i != 1 ? -1L : entityId;
		
		if(debugMode){
			System.out.println("\n\n-- HeaderId: " + ccHeader.getHeaderId());
			System.out.println("-- Path: " + ccHeader.getSourceClassPath());
			System.out.println("-- Linha: " + ccHeader.getLineIni());
			System.out.println("-- EntityId: " + entityId);
			System.out.println(sql);
			System.out.println("-- Occurrences = " + i);
		}

		return entityId;
	}

	public void savePairs10(SourcererCCPair pair) throws Exception {

		Connection conn = getConnection();
		PreparedStatement ps = null;

		String sql = "INSERT into interface_metrics_pairs_clone_10 (header_id_a, header_id_b, entity_id_a, entity_id_b) values (?, ?, ?, ?)";
		ps = conn.prepareStatement(sql);
		ps.setLong(1, pair.getHeaderIdA());
		ps.setLong(2, pair.getHeaderIdB());
		ps.setLong(3, pair.getEntityIdA());
		ps.setLong(4, pair.getEntityIdB());
		ps.executeUpdate();

		ps.close();
		conn.close();
	}

	public int countAllNotProccessed10() throws Exception {
		Connection conn = getConnection();
		Statement stmt = conn.createStatement();

		String sql = "SELECT count(*) as total FROM interface_metrics_pairs_clone_10";
		sql += " where (processed <> 1 or processed is null)";
		ResultSet rs = stmt.executeQuery(sql);

		int total = 0;
		while (rs.next()) {
			total = rs.getInt("total");
		}

		stmt.close();
		conn.close();

		return total;
	}
}
