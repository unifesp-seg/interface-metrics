package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricPairRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class InterfaceMetricsServiceTest {

	private InterfaceMetricRepository interfaceMetricRepository = new InterfaceMetricRepository(RepositoryType.MOCK);
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository(RepositoryType.MOCK);
	private InterfaceMetricPairRepository pairRepository = new InterfaceMetricPairRepository(RepositoryType.MOCK);
	private InterfaceMetricsService service = new InterfaceMetricsService(RepositoryType.MOCK);
	
	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void proccessedMethodsExecute() throws Exception {
		List<InterfaceMetric> tests = interfaceMetricRepository.findAllOrderedById();
		assertEquals(47 , tests.size());

		for(InterfaceMetric interfaceMetric : tests){
			assertTrue(interfaceMetric.isProcessed());
			assertTrue(interfaceMetric.isProcessedParams());
		}

		// Fqn
		assertEquals(tests.get(0).getFqn(), "com.sun.nio.zipfs.JarFileSystemProvider.getPath");
		assertEquals(tests.get(1).getFqn(), "com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView");
		assertEquals(tests.get(2).getFqn(), "sun.io.CharToByteEUC_TW.canConvert");
		assertEquals(tests.get(3).getFqn(), "sun.io.CharToByteDBCS_ASCII.convert");
		assertEquals(tests.get(4).getFqn(), "com.sun.nio.zipfs.ZipUtils.javaToDosTime");
		assertEquals(tests.get(5).getFqn(), "com.sun.nio.zipfs.ZipUtils.winToJavaTime");
		assertEquals(tests.get(6).getFqn(), "java.lang.String.split");
		assertEquals(tests.get(7).getFqn(), "net.sf.saxon.om.FastStringBuffer.diagnosticPrint");
		assertEquals(tests.get(8).getFqn(), "net.sf.saxon.om.StandardNames.getURI");
		assertEquals(tests.get(9).getFqn(), "java.lang.String.format");
		assertEquals(tests.get(10).getFqn(), "net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue");

		// First parameter
		assertEquals(tests.get(0).getParamsNames()[0], "java.net.URI");
		assertEquals(tests.get(1).getParamsNames()[0], "java.nio.file.Path");
		assertEquals(tests.get(2).getParamsNames()[0], "char");
		assertEquals(tests.get(3).getParamsNames()[0], "char[]");
		assertEquals(tests.get(4).getParamsNames()[0], "long");
		assertEquals(tests.get(5).getParamsNames()[0], "long");
		assertEquals(tests.get(6).getParamsNames()[0], "java.lang.String");
		assertEquals(tests.get(7).getParamsNames()[0], "java.lang.CharSequence");
		assertEquals(tests.get(8).getParamsNames()[0], "int");
		assertEquals(tests.get(9).getParamsNames()[0], "java.lang.String");
		assertEquals(tests.get(10).getParamsNames()[0], "net.sf.saxon.tinytree.TinyTree");

		// Total parameters
		assertEquals(tests.get(0).getTotalParams(), 1);
		assertEquals(tests.get(1).getTotalParams(), 3);
		assertEquals(tests.get(2).getTotalParams(), 1);
		assertEquals(tests.get(3).getTotalParams(), 6);
		assertEquals(tests.get(4).getTotalParams(), 1);
		assertEquals(tests.get(5).getTotalParams(), 1);
		assertEquals(tests.get(6).getTotalParams(), 1);
		assertEquals(tests.get(7).getTotalParams(), 1);
		assertEquals(tests.get(8).getTotalParams(), 1);
		assertEquals(tests.get(9).getTotalParams(), 2);
		assertEquals(tests.get(10).getTotalParams(), 2);
	}
	
	@Test
	public void verifyTotalParams() throws Exception {
		int sumAllTotalParams = interfaceMetricRepository.getSumAllTotalParams();
		int totalParamsTable = paramsRepository.countAll();
		
		assertEquals(sumAllTotalParams, totalParamsTable);
	}

	@Test
	public void verifyTotalPairs() throws Exception {
		
		int incrementPairs = 0;
		for(OccurrencesCombination combination : OccurrencesCombination.allCombinations()){
			int sumAllTotalOccurrences = interfaceMetricRepository.countAllByCombination(combination);
			int totalPairsTable = pairRepository.countAllByCombination(combination);
			assertEquals(sumAllTotalOccurrences, totalPairsTable);
			incrementPairs += totalPairsTable;
		}
		assertEquals(pairRepository.countAll(), incrementPairs);
	}

	@Test
	public void processInterfaceMetricsMethodId10() throws Exception {
		InterfaceMetric interfaceMetric = interfaceMetricRepository.findById(10);
		InterfaceMetric storage = interfaceMetricRepository.findById(interfaceMetric.getId());

		service.processMethod(storage);

		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(2 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
	
}
