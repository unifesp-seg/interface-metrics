package br.inpe.cap.interfacemetrics.application;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.unifesp.ppgcc.sourcereraqe.infrastructure.QueryTerm;

public class InterfaceMetricOccurrencesHelperTest {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository(true);
	private InterfaceMetric interfaceMetric;
	private InterfaceMetricOccurrencesHelper helper;
	
	@Before
	public void setup() throws Exception {
		this.processMethodsInfo();
		interfaceMetric = repository.findById(10);
		helper = new InterfaceMetricOccurrencesHelper(interfaceMetric, true);
	}
	
	private void processMethodsInfo() throws Exception {
		List<InterfaceMetric> tests = repository.findAllOrderedById();
		for(InterfaceMetric interfaceMetric : tests){
			interfaceMetric.processMethod();
			repository.updateProcessedMethod(interfaceMetric);
		}
	}
	
	@Test
	public void returnMethodParamsValues(){
		assertEquals("long", interfaceMetric.getReturnType());
		assertEquals("getLongValue", interfaceMetric.getMethodName());
		assertEquals("net.sf.saxon.tinytree.TinyTree,int", interfaceMetric.getParams());
		assertEquals(2, interfaceMetric.getParamsNames().length);
		assertEquals("net.sf.saxon.tinytree.TinyTree", interfaceMetric.getParamsNames()[0]);
		assertEquals("int", interfaceMetric.getParamsNames()[1]);
	}

	@Test
	public void returnTerms() throws Exception {
		//long, double, float, long, int, Integer, short, byte
		assertEquals(1, helper.getAqeApproach().getReturnTypeTerms().size());
		assertEquals(8, helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().size());
		assertEquals("long", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(0));
		assertEquals("double", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(1));
		assertEquals("float", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(2));
		assertEquals("long", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(3));
		assertEquals("int", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(4));
		assertEquals("Integer", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(5));
		assertEquals("short", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(6));
		assertEquals("byte", helper.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(7));
	}
	
	@Test
	public void methodNameTerms() throws Exception {
		//get: 70 [get, acquire, become, go, let, have, receive, find, ...], not [leave, take away, end]
		//Long: [long, hanker, yearn, retentive, recollective, tenacious], not [short, unretentive]
		//Value: 16 [value, prize, treasure, appreciate, respect, ...], not [disrespect, disesteem]
		
		List<QueryTerm> terms = helper.getAqeApproach().getMethodNameTerms();
		assertEquals(3, terms.size());
		assertEquals("get", terms.get(0).getExpandedTerms().get(0));
		assertEquals("long", terms.get(1).getExpandedTerms().get(0));
		assertEquals("value", terms.get(2).getExpandedTerms().get(0));

		//get
		assertEquals(70, terms.get(0).getExpandedTerms().size());
		assertEquals(3, terms.get(0).getExpandedTermsNot().size());
		assertEquals("leave", terms.get(0).getExpandedTermsNot().get(0));
		assertEquals("take away", terms.get(0).getExpandedTermsNot().get(1));
		assertEquals("end", terms.get(0).getExpandedTermsNot().get(2));
		
		//Long
		assertEquals(6, terms.get(1).getExpandedTerms().size());
		assertEquals("long", terms.get(1).getExpandedTerms().get(0));
		assertEquals("hanker", terms.get(1).getExpandedTerms().get(1));
		assertEquals("yearn", terms.get(1).getExpandedTerms().get(2));
		assertEquals("retentive", terms.get(1).getExpandedTerms().get(3));
		assertEquals("recollective", terms.get(1).getExpandedTerms().get(4));
		assertEquals("tenacious", terms.get(1).getExpandedTerms().get(5));
		assertEquals(2, terms.get(1).getExpandedTermsNot().size());
		assertEquals("short", terms.get(1).getExpandedTermsNot().get(0));
		assertEquals("unretentive", terms.get(1).getExpandedTermsNot().get(1));

		//Value
		assertEquals(16, terms.get(2).getExpandedTerms().size());
		assertEquals(2, terms.get(2).getExpandedTermsNot().size());
		assertEquals("disrespect", terms.get(2).getExpandedTermsNot().get(0));
		assertEquals("disesteem", terms.get(2).getExpandedTermsNot().get(1));
		
	}
	
	@Test
	public void paramsTerms() throws Exception {
		//[net.sf.saxon.tinytree.TinyTree]
		//[int, Integer, double, float, long, int, Integer, short, byte]
		
		List<QueryTerm> terms = helper.getAqeApproach().getParamsTerms();
		assertEquals(2, terms.size());
		assertEquals("net.sf.saxon.tinytree.TinyTree", terms.get(0).getExpandedTerms().get(0));
		assertEquals("int", terms.get(1).getExpandedTerms().get(0));

		//net.sf.saxon.tinytree.TinyTree
		assertEquals(1, terms.get(0).getExpandedTerms().size());
		assertEquals(0, terms.get(0).getExpandedTermsNot().size());
		
		//int
		assertEquals(9, terms.get(1).getExpandedTerms().size());
		assertEquals(0, terms.get(1).getExpandedTermsNot().size());
		assertEquals("int", terms.get(1).getExpandedTerms().get(0));
		assertEquals("Integer", terms.get(1).getExpandedTerms().get(1));
		assertEquals("double", terms.get(1).getExpandedTerms().get(2));
		assertEquals("float", terms.get(1).getExpandedTerms().get(3));
		assertEquals("long", terms.get(1).getExpandedTerms().get(4));
		assertEquals("int", terms.get(1).getExpandedTerms().get(5));
		assertEquals("Integer", terms.get(1).getExpandedTerms().get(6));
		assertEquals("short", terms.get(1).getExpandedTerms().get(7));
		assertEquals("byte", terms.get(1).getExpandedTerms().get(8));
	}

	@Test
	public void occurrences1of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, false, false));
		assertEquals(0, matches.size());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, false));
		assertEquals(1, matches.size());
		assertEquals(11, matches.get(0).getId().longValue());

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, false));
		assertEquals(1, matches.size());
		assertEquals(12, matches.get(0).getId().longValue());

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, false));
		assertEquals(3, matches.size());
		assertEquals(11, matches.get(0).getId().longValue());
		assertEquals(12, matches.get(1).getId().longValue());
		assertEquals(13, matches.get(2).getId().longValue());
	}

	@Test
	public void occurrences2of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, false));
		assertEquals(1, matches.size());
		assertEquals(14, matches.get(0).getId().longValue());
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, false));
		assertEquals(3, matches.size());
		assertEquals(11, matches.get(0).getId().longValue());
		assertEquals(14, matches.get(1).getId().longValue());
		assertEquals(15, matches.get(2).getId().longValue());

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, false));
		assertEquals(3, matches.size());
		assertEquals(12, matches.get(0).getId().longValue());
		assertEquals(14, matches.get(1).getId().longValue());
		assertEquals(16, matches.get(2).getId().longValue());

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, false));
		assertEquals(7, matches.size());
		assertEquals(11, matches.get(0).getId().longValue());
		assertEquals(12, matches.get(1).getId().longValue());
		assertEquals(13, matches.get(2).getId().longValue());
		assertEquals(14, matches.get(3).getId().longValue());
		assertEquals(15, matches.get(4).getId().longValue());
		assertEquals(16, matches.get(5).getId().longValue());
		assertEquals(17, matches.get(6).getId().longValue());
	}

	@Test
	public void occurrences3of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, false, true));
		assertEquals(1, matches.size());
		assertEquals(18, matches.get(0).getId().longValue());
		
		matches = helper.getOccurrences(new OccurrencesCombination(false, false, true, true));
		assertEquals(3, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, false, true));
		assertEquals(3, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(20)));

		matches = helper.getOccurrences(new OccurrencesCombination(false, true, true, true));
		assertEquals(7, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(13)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(20)));
		assertTrue(matches.contains(new InterfaceMetric(21)));
	}

	@Test
	public void occurrences4of16() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, false, true));
		assertEquals(3, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(22)));

		
		matches = helper.getOccurrences(new OccurrencesCombination(true, false, true, true));
		assertEquals(7, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, false, true));
		assertEquals(7, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(16)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(20)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(24)));

		matches = helper.getOccurrences(new OccurrencesCombination(true, true, true, true));
		assertEquals(15, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(13)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(16)));
		assertTrue(matches.contains(new InterfaceMetric(17)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(20)));
		assertTrue(matches.contains(new InterfaceMetric(21)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));
		assertTrue(matches.contains(new InterfaceMetric(24)));
		assertTrue(matches.contains(new InterfaceMetric(25)));
	}

	@Test
	public void occurrences() throws Exception {
		helper.updateOccurrences();
		List<InterfaceMetric> occurrences = helper.getOccurrences();
		assertEquals(15, occurrences.size());
	}
	
	@Test
	public void updateOccurrences() throws Exception {

		helper.updateOccurrences();
		repository.updateProcessedMethod(interfaceMetric);

		InterfaceMetric storage = repository.findById(interfaceMetric.getId());
		
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(15, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());

		assertEquals(3, storage.getOccurrencesTotal(new OccurrencesCombination(false)).intValue());
		assertEquals(15, storage.getOccurrencesTotal(new OccurrencesCombination(true)).intValue());
	
	}
}
