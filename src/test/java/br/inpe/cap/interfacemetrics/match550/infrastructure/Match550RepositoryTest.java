package br.inpe.cap.interfacemetrics.match550.infrastructure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class Match550RepositoryTest {

	private Match550Repository match550Repository = new Match550Repository();

	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void getMatchConnectionTest() throws Exception {
		assertNotNull(match550Repository.getMatchConnection());
	}

	@Test
	public void totalNETest() throws Exception {
		assertEquals(10980, match550Repository.countAllNE());
	}

	@Test
	public void totalEngTest() throws Exception {
		assertEquals(278519, match550Repository.countAllEng());
	}

	@Test
	public void totalMatchTest() throws Exception {
		assertEquals(649299, match550Repository.countAllMatch());
	}

	@Test
	public void findAllNEEntityIds() throws Exception {
		assertEquals(1427, match550Repository.findAllNEEntityIds().size());
	}

	@Test
	public void findAllEngEntityIds() throws Exception {
		assertEquals(14699, match550Repository.findAllEngEntityIds().size());
	}

	@Test
	public void findAllMatchEntityIds() throws Exception {
		assertEquals(649299, match550Repository.findAllMatchEntityIds().size());
		assertEquals(2, match550Repository.findAllMatchEntityIds().get(0).length);

		long neEntityId = match550Repository.findAllMatchEntityIds().get(0)[0];
		assertEquals(3892574, neEntityId);

		long engEntityId = match550Repository.findAllMatchEntityIds().get(0)[1];
		assertEquals(13053134, engEntityId);
	}
}
