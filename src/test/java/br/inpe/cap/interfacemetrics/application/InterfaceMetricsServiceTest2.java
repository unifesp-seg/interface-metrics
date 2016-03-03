package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricsServiceTest2 {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository();
	private InterfaceMetricsService service = new InterfaceMetricsService();

	@Test
	public void processMethod1() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(606204);
		service.processMethod(interfaceMetric);
		
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, false, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, false, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, true, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, false, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, false, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, false, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, true, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, false, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true, true)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		
	}

	@Test
	public void processMethod2() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(607111);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int rnpo = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false));
		int rn1po = storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, false));
		assertTrue(rn1po - rnpo >= 0);
	}

	@Test
	public void processMethod3() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(610507);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int r0_n0_p0_0 = storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false));
		int r0_n1_p0_0 = storage.getOccurrencesTotal(new OccurrencesCombination(false, true, false, false));
		assertTrue(r0_n1_p0_0 - r0_n0_p0_0 >= 0);
	}

	@Test
	public void processMethod4() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(615049);
		service.processMethod(interfaceMetric);
		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		int rnpo = storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false));
		int rn1po = storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, false));
		assertTrue(rn1po - rnpo >= 0);
	}
	
}
