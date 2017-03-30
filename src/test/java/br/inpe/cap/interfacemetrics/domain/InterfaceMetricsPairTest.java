package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricPairRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;

public class InterfaceMetricsPairTest {

	private InterfaceMetricPairRepository repository = new InterfaceMetricPairRepository(RepositoryType.MOCK);

	@Test
	public void combinationsNames() throws Exception {
		List<OccurrencesCombination> combinations = OccurrencesCombination.allCombinations();
		assertEquals(16, combinations.size());
		
		assertEquals("p0_c0_w0_t0", combinations.get(0).getName());
		assertEquals("p0_c0_w0_t1", combinations.get(1).getName());
		assertEquals("p0_c0_w1_t0", combinations.get(2).getName());
		assertEquals("p0_c0_w1_t1", combinations.get(3).getName());
		assertEquals("p0_c1_w0_t0", combinations.get(4).getName());
		assertEquals("p0_c1_w0_t1", combinations.get(5).getName());
		assertEquals("p0_c1_w1_t0", combinations.get(6).getName());
		assertEquals("p0_c1_w1_t1", combinations.get(7).getName());
		assertEquals("p1_c0_w0_t0", combinations.get(8).getName());
		assertEquals("p1_c0_w0_t1", combinations.get(9).getName());
		assertEquals("p1_c0_w1_t0", combinations.get(10).getName());
		assertEquals("p1_c0_w1_t1", combinations.get(11).getName());
		assertEquals("p1_c1_w0_t0", combinations.get(12).getName());
		assertEquals("p1_c1_w0_t1", combinations.get(13).getName());
		assertEquals("p1_c1_w1_t0", combinations.get(14).getName());
		assertEquals("p1_c1_w1_t1", combinations.get(15).getName());
	}
		
	@Test
	public void pairsId10() throws Exception {
		
		List<InterfaceMetricPair> pairs = repository.getPairs(new InterfaceMetric(10));
		assertEquals(49, pairs.size());

		String name = "p0_c0_w0_t0";
		List<InterfaceMetricPair> pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(1, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		
		name = "p0_c0_w0_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(7, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 11L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 15L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 22L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 14L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 23L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 19L, name)));

		name = "p0_c0_w1_t0";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(3, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 12L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 20L, name)));

		name = "p0_c0_w1_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(12, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 11L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 25L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 19L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 20L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 15L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 24L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 23L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 14L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 12L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 22L, name)));

		name = "p0_c1_w0_t0";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(1, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));

		name = "p0_c1_w0_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(7, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 22L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 23L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 14L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 11L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 19L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 15L, name)));

		name = "p0_c1_w1_t0";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(3, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 12L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 20L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		
		name = "p0_c1_w1_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(12, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 23L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 11L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 20L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 24L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 12L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 22L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 25L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 14L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 15L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 19L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 16L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 18L, name)));
		
		name = "p1_c0_w1_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(1, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 13L, name)));

		name = "p1_c1_w1_t1";
		pairsByName = this.getPairsByCombination(pairs, name);
		assertEquals(2, pairsByName.size());
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 17L, name)));
		assertTrue(pairsByName.contains(new InterfaceMetricPair(10L, 13L, name)));
	}
	
	private List<InterfaceMetricPair> getPairsByCombination(List<InterfaceMetricPair> pairs, String combinationName){
		List<InterfaceMetricPair>  list = new ArrayList<InterfaceMetricPair>();

		for (InterfaceMetricPair pair : pairs) {
			if(pair.getSearchType().equals(combinationName))
				list.add(pair);
		}
		return list;
	}
	
}
