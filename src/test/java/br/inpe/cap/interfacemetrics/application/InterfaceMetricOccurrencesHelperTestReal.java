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

public class InterfaceMetricOccurrencesHelperTestReal {

	
	private InterfaceMetricRepository repository = new InterfaceMetricRepository();
	private InterfaceMetric interfaceMetric;
	private InterfaceMetricOccurrencesHelper helper;
	
	@Before
	public void setup() throws Exception {
		interfaceMetric = repository.findById(1816587);
		helper = new InterfaceMetricOccurrencesHelper(interfaceMetric);
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
		assertEquals(1, matches.size());
		assertEquals(1097303, matches.get(0).getId().longValue());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, false, true));
		assertEquals(5, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, false));
		assertEquals(1, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097303)));

		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, true));
		assertEquals(5, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
	}

	@Test
	public void occurrences2of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, false));
		assertEquals(2, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, true));
		assertEquals(10, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097229)));
		assertTrue(matches.contains(new InterfaceMetric(1097230)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097232)));
		assertTrue(matches.contains(new InterfaceMetric(1097233)));
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, false));
		assertEquals(2, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, true));
		assertEquals(10, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097229)));
		assertTrue(matches.contains(new InterfaceMetric(1097230)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097232)));
		assertTrue(matches.contains(new InterfaceMetric(1097233)));
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
	}

	@Test
	public void occurrences3of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, false));
		assertEquals(1, matches.size());
		assertEquals(1097303, matches.get(0).getId().longValue());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, true));
		assertEquals(5, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, false));
		assertEquals(1, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097303)));

		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, true));
		assertEquals(5, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
	}

	@Test
	public void occurrences4of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, false));
		assertEquals(6, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1063094)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1177250)));
		assertTrue(matches.contains(new InterfaceMetric(1314310)));
		assertTrue(matches.contains(new InterfaceMetric(1328491)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, true));
		assertEquals(30, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1063092)));
		assertTrue(matches.contains(new InterfaceMetric(1063093)));
		assertTrue(matches.contains(new InterfaceMetric(1063094)));
		assertTrue(matches.contains(new InterfaceMetric(1063095)));
		assertTrue(matches.contains(new InterfaceMetric(1063096)));
		assertTrue(matches.contains(new InterfaceMetric(1097229)));
		assertTrue(matches.contains(new InterfaceMetric(1097230)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097232)));
		assertTrue(matches.contains(new InterfaceMetric(1097233)));
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
		assertTrue(matches.contains(new InterfaceMetric(1177248)));
		assertTrue(matches.contains(new InterfaceMetric(1177249)));
		assertTrue(matches.contains(new InterfaceMetric(1177250)));
		assertTrue(matches.contains(new InterfaceMetric(1177251)));
		assertTrue(matches.contains(new InterfaceMetric(1177252)));
		assertTrue(matches.contains(new InterfaceMetric(1314308)));
		assertTrue(matches.contains(new InterfaceMetric(1314309)));
		assertTrue(matches.contains(new InterfaceMetric(1314310)));
		assertTrue(matches.contains(new InterfaceMetric(1314311)));
		assertTrue(matches.contains(new InterfaceMetric(1314312)));
		assertTrue(matches.contains(new InterfaceMetric(1328489)));
		assertTrue(matches.contains(new InterfaceMetric(1328490)));
		assertTrue(matches.contains(new InterfaceMetric(1328491)));
		assertTrue(matches.contains(new InterfaceMetric(1328492)));
		assertTrue(matches.contains(new InterfaceMetric(1328493)));
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, false));
		assertEquals(6, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1063094)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1177250)));
		assertTrue(matches.contains(new InterfaceMetric(1314310)));
		assertTrue(matches.contains(new InterfaceMetric(1328491)));

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, true));
		assertEquals(30, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(1063092)));
		assertTrue(matches.contains(new InterfaceMetric(1063093)));
		assertTrue(matches.contains(new InterfaceMetric(1063094)));
		assertTrue(matches.contains(new InterfaceMetric(1063095)));
		assertTrue(matches.contains(new InterfaceMetric(1063096)));
		assertTrue(matches.contains(new InterfaceMetric(1097229)));
		assertTrue(matches.contains(new InterfaceMetric(1097230)));
		assertTrue(matches.contains(new InterfaceMetric(1097231)));
		assertTrue(matches.contains(new InterfaceMetric(1097232)));
		assertTrue(matches.contains(new InterfaceMetric(1097233)));
		assertTrue(matches.contains(new InterfaceMetric(1097301)));
		assertTrue(matches.contains(new InterfaceMetric(1097302)));
		assertTrue(matches.contains(new InterfaceMetric(1097303)));
		assertTrue(matches.contains(new InterfaceMetric(1097304)));
		assertTrue(matches.contains(new InterfaceMetric(1097305)));
		assertTrue(matches.contains(new InterfaceMetric(1177248)));
		assertTrue(matches.contains(new InterfaceMetric(1177249)));
		assertTrue(matches.contains(new InterfaceMetric(1177250)));
		assertTrue(matches.contains(new InterfaceMetric(1177251)));
		assertTrue(matches.contains(new InterfaceMetric(1177252)));
		assertTrue(matches.contains(new InterfaceMetric(1314308)));
		assertTrue(matches.contains(new InterfaceMetric(1314309)));
		assertTrue(matches.contains(new InterfaceMetric(1314310)));
		assertTrue(matches.contains(new InterfaceMetric(1314311)));
		assertTrue(matches.contains(new InterfaceMetric(1314312)));
		assertTrue(matches.contains(new InterfaceMetric(1328489)));
		assertTrue(matches.contains(new InterfaceMetric(1328490)));
		assertTrue(matches.contains(new InterfaceMetric(1328491)));
		assertTrue(matches.contains(new InterfaceMetric(1328492)));
		assertTrue(matches.contains(new InterfaceMetric(1328493)));
	}

	@Test
	public void updateOccurrences() throws Exception {

		helper.updateOccurrences();
		repository.updateProcessedMethod(interfaceMetric);

		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(5 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(5 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());

		assertEquals(2 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(10, storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(2 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(10, storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(5 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(5 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());

		assertEquals(6 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(30, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(6 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(30, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
}
