package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricsServiceTestReal {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository();
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository();
	private InterfaceMetricsService service = new InterfaceMetricsService();

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
	public void verifyTotalParams() throws Exception {
		int sumAllTotalParams = repository.getSumAllTotalParams();
		int totalParamsTable = paramsRepository.countAll();
		
		assertEquals(sumAllTotalParams, totalParamsTable);
	}
}
