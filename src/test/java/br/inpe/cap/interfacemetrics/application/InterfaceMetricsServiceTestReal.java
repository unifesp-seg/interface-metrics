package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.InterfaceMetricPair;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricPairRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;

public class InterfaceMetricsServiceTestReal {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository(RepositoryType.REAL);
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository(RepositoryType.REAL);
	private InterfaceMetricPairRepository pairRepository = new InterfaceMetricPairRepository(RepositoryType.REAL);
	private InterfaceMetricsService service = new InterfaceMetricsService(RepositoryType.REAL);

	@Test
	public void processMethod1() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(1812357);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int p1_c1_w1_t0 = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false));
		int p1_c1_w1_t1 = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, true));
		assertTrue(p1_c1_w1_t1 - p1_c1_w1_t0 >= 0);
	}

	@Test
	public void processMethod2() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(1815753);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int p0_c0_w0_t0 = storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false));
		int p0_c0_w1_t0 = storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true , false));
		assertTrue(p0_c0_w1_t0 - p0_c0_w0_t0 >= 0);
	}

	@Test
	public void processMethod3() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(1816587);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int p1_c1_w1_t0 = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false));
		int p1_c1_w1_t1 = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, true));
		assertTrue(p1_c1_w1_t1 - p1_c1_w1_t0 >= 0);
	}
	
	@Test
	public void wordnetExpansionRules_1812413() throws Exception {
		long idA = 1812413;
		InterfaceMetric interfaceMetric = repository.findById(idA);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		assertEquals(1, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, true)).intValue());
		assertEquals(1, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false)).intValue());

		List<InterfaceMetricPair> pairs = pairRepository.getPairs(storage);
		assertEquals(2, pairs.size());
		
		assertEquals("p1_c1_w1_t0", pairs.get(0).getSearchType());
		assertEquals(idA, pairs.get(0).getInterfaceMetricsA().longValue());
		assertEquals(1659303, pairs.get(0).getInterfaceMetricsB().longValue());

		assertEquals("p1_c1_w1_t1", pairs.get(1).getSearchType());
		assertEquals(idA, pairs.get(1).getInterfaceMetricsA().longValue());
		assertEquals(1659303, pairs.get(1).getInterfaceMetricsB().longValue());
	}
	
	@Test
	public void classNameMatch_1716777() throws Exception {
		long idA = 1810953;
		InterfaceMetric interfaceMetric = repository.findById(idA);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		//Ignore Class
		OccurrencesCombination combination = new OccurrencesCombination(true, true, true, true);
		List<InterfaceMetricPair> pairs = pairRepository.getPairs(storage, combination);
		assertEquals(5, pairs.size());
		assertEquals(idA, pairs.get(0).getInterfaceMetricsA().longValue());
		assertEquals(1546107, pairs.get(0).getInterfaceMetricsB().longValue());
		assertEquals(1574538, pairs.get(1).getInterfaceMetricsB().longValue());
		assertEquals(1574539, pairs.get(2).getInterfaceMetricsB().longValue());
		assertEquals(1597165, pairs.get(3).getInterfaceMetricsB().longValue());
		assertEquals(1664164, pairs.get(4).getInterfaceMetricsB().longValue());

		//Consider Class
		combination = new OccurrencesCombination(true, false, true, true);
		pairs = pairRepository.getPairs(storage, combination);
		assertEquals(5, pairs.size());
		assertEquals(idA, pairs.get(0).getInterfaceMetricsA().longValue());
		assertEquals(1546107, pairs.get(0).getInterfaceMetricsB().longValue());
		assertEquals(1574538, pairs.get(1).getInterfaceMetricsB().longValue());
		assertEquals(1574539, pairs.get(2).getInterfaceMetricsB().longValue());
		assertEquals(1597165, pairs.get(3).getInterfaceMetricsB().longValue());
		assertEquals(1664164, pairs.get(4).getInterfaceMetricsB().longValue());
	}
	
	@Test
	public void classNameMatch_1816587() throws Exception {
		long idA = 1816587;
		InterfaceMetric interfaceMetric = repository.findById(idA);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		//Ignore Class
		OccurrencesCombination combination = new OccurrencesCombination(true, true, true, true);
		List<InterfaceMetricPair> pairs = pairRepository.getPairs(storage, combination);
		assertEquals(181, pairs.size());
		assertEquals(idA, pairs.get(0).getInterfaceMetricsA().longValue());

		//Consider Class
		combination = new OccurrencesCombination(true, false, true, true);
		pairs = pairRepository.getPairs(storage, combination);
		assertEquals(181, pairs.size());
		assertEquals(idA, pairs.get(0).getInterfaceMetricsA().longValue());
	}
	
	@Test
	public void verifyTotalParams() throws Exception {
		int sumAllTotalParams = repository.getSumAllTotalParams();
		int totalParamsTable = paramsRepository.countAll();
		
		assertEquals(sumAllTotalParams, totalParamsTable);
	}

	//@Test
	public void verifyTotalPairs() throws Exception {
		
		for(OccurrencesCombination combination : OccurrencesCombination.allCombinations()){
			int sumAllTotalOccurrences = repository.countAllByCombination(combination);
			int totalPairsTable = pairRepository.countAllByCombination(combination);
			assertEquals(sumAllTotalOccurrences, totalPairsTable);
		}
	}
}
