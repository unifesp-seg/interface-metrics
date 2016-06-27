package br.inpe.cap.interfacemetrics.clone.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;


public class SourcererCCPairTest {

	@Test
	public void pair10Id0() throws Exception {
		String line = "2,8807";
		SourcererCCPair pair = new SourcererCCPair(line);
		
		assertEquals(2, pair.getHeaderIdA().longValue());
		assertEquals(8807, pair.getHeaderIdB().longValue());
		assertEquals(-1, pair.getEntityIdA().longValue());
		assertEquals(-1, pair.getEntityIdB().longValue());
	}

	@Test
	public void pair10Id1() throws Exception {
		String line = "2,8777";
		SourcererCCPair pair = new SourcererCCPair(line);
		
		assertEquals(2, pair.getHeaderIdA().longValue());
		assertEquals(8777, pair.getHeaderIdB().longValue());
		assertEquals(-1, pair.getEntityIdA().longValue());
		assertEquals(-1, pair.getEntityIdB().longValue());
	}

	@Test
	public void pair10Id336() throws Exception {
		String line = "157,7316";
		SourcererCCPair pair = new SourcererCCPair(line);
		
		assertEquals(157, pair.getHeaderIdA().longValue());
		assertEquals(7316, pair.getHeaderIdB().longValue());
		assertEquals(-1, pair.getEntityIdA().longValue());
		assertEquals(-1, pair.getEntityIdB().longValue());
	}
}
