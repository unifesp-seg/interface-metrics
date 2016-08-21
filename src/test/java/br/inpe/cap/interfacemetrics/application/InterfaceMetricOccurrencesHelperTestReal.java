package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;

public class InterfaceMetricOccurrencesHelperTestReal {

	
	private InterfaceMetricRepository repository = new InterfaceMetricRepository(RepositoryType.REAL);
	private InterfaceMetric interfaceMetric;
	private InterfaceMetricOccurrencesHelper helper;
	
	@Before
	public void setup() throws Exception {
		interfaceMetric = repository.findById(1816587);
		helper = new InterfaceMetricOccurrencesHelper(interfaceMetric, RepositoryType.REAL);
	}
	
	@Test
	public void returnMethodParamsValues(){
		assertEquals("org.apache.struts.action.ActionForward", interfaceMetric.getReturnType());
		assertEquals("execute", interfaceMetric.getMethodName());
		assertEquals("org.apache.struts.action.ActionMapping,org.apache.struts.action.ActionForm,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse", interfaceMetric.getParams());
		assertEquals(4, interfaceMetric.getParamsNames().length);
		assertEquals("org.apache.struts.action.ActionMapping", interfaceMetric.getParamsNames()[0]);
		assertEquals("org.apache.struts.action.ActionForm", interfaceMetric.getParamsNames()[1]);
		assertEquals("javax.servlet.http.HttpServletRequest", interfaceMetric.getParamsNames()[2]);
		assertEquals("javax.servlet.http.HttpServletResponse", interfaceMetric.getParamsNames()[3]);
	}

	@Test
	public void occurrences1of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, false, false));
		assertEquals(21, matches.size());
		assertEquals(1546494, matches.get(0).getId().longValue());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, false, true));
		assertEquals(21, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1546499)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, false));
		assertEquals(21, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1546499)));

		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, true));
		assertEquals(21, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1546499)));
	}

	@Test
	public void occurrences2of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, false));
		assertEquals(21, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, true));
		assertEquals(21, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, false));
		assertEquals(21, matches.size());

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, true));
		assertEquals(21, matches.size());
	}

	@Test
	public void occurrences3of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, false));
		assertEquals(179, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, true));
		assertEquals(179, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, false));
		assertEquals(181, matches.size());

		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, true));
		assertEquals(181, matches.size());
	}

	@Test
	public void occurrences4of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, false));
		assertEquals(179, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, true));
		assertEquals(179, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, false));
		assertEquals(181, matches.size());

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, true));
		assertEquals(181, matches.size());
	}

	@Test
	public void updateOccurrences() throws Exception {

		helper.updateOccurrences();
		repository.updateProcessedMethod(interfaceMetric);

		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());

		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(21 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		
		assertEquals(179, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(179, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(181, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(181, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());

		assertEquals(179, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(179, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(181, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(181, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
}
