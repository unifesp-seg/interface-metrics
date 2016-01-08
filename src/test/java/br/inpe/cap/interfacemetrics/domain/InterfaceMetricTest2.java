package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricTest2 {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository();

	@Test()
	public void processMethod1() throws Exception {
		InterfaceMetric i1 = repository.findById(606204);
		InterfaceMetric i2 = repository.findById(606199);

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());

		i1.processMethod();
		i2.processMethod();

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());
	}
}
