package br.inpe.cap.interfacemetrics.interfaces.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricsService;
import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricPairRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class InterfaceMetricsMainTest {

	private InterfaceMetricsService service = new InterfaceMetricsService(RepositoryType.MOCK);
	private InterfaceMetricRepository interfaceMetricRepository = new InterfaceMetricRepository(RepositoryType.MOCK);
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository(RepositoryType.MOCK);
	private InterfaceMetricPairRepository pairRepository = new InterfaceMetricPairRepository(RepositoryType.MOCK);

	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void main() throws Exception {
		
		// clearProcessing
		service.clearProcessing(ExecutionType.PARAMS);
		assertEquals(47, interfaceMetricRepository.countAllNotProccessedParams());
		assertEquals(0, paramsRepository.countAll());
		
		service.clearProcessing(ExecutionType.INTERFACE_METRICS);
		assertEquals(47, interfaceMetricRepository.countAllNotProccessed());
		assertEquals(0, pairRepository.countAll());

		List<InterfaceMetric> tests = interfaceMetricRepository.findAllOrderedById();
		assertEquals(47 , tests.size());
		for(InterfaceMetric interfaceMetric : tests){
			if(!"CRAWLED".equals(interfaceMetric.getProjectType()))
				continue;
			assertFalse(interfaceMetric.isProcessed());
			assertFalse(interfaceMetric.isProcessedParams());
			assertEquals(0 ,interfaceMetric.getTotalParams());
			assertEquals(0 ,interfaceMetric.getTotalWordsMethod());
			assertEquals(0 ,interfaceMetric.getTotalWordsClass());
			assertFalse(interfaceMetric.isOnlyPrimitiveTypes());
			assertFalse(interfaceMetric.isStatic());
			assertFalse(interfaceMetric.isHasTypeSamePackage());
		}
		
		// 1. Process only method info
		service.processMethodsInfo();

		// 2. ExecutionType.PARAMS
		service.execute(ExecutionType.PARAMS);
		assertEquals(0, interfaceMetricRepository.countAllNotProccessedParams());
		assertEquals(117, paramsRepository.countAll());

		// 3. ExecutionType.INTERFACE_METRICS
		service.execute(ExecutionType.INTERFACE_METRICS);
		assertEquals(0, interfaceMetricRepository.countAllNotProccessed());
		assertEquals(75, pairRepository.countAll());

		tests = interfaceMetricRepository.findAllOrderedById();
		assertEquals(47 , tests.size());
		for(InterfaceMetric interfaceMetric : tests){
			assertTrue(interfaceMetric.isProcessed());
			assertTrue(interfaceMetric.isProcessedParams());
		}
	}

}