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
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;
import br.unifesp.ict.seg.geniesearchapi.services.searchaqe.infrastructure.QueryTerm;

public class InterfaceMetricOccurrencesHelperTest {

	
	private InterfaceMetricRepository interfaceMetricRepository = new InterfaceMetricRepository(RepositoryType.MOCK);
	private InterfaceMetric interfaceMetricId10;
	private InterfaceMetricOccurrencesHelper helperId10;
	
	@Before
	public void initialize() throws Exception {
		GenieSearchAPIConfig.loadProperties();
		interfaceMetricId10 = interfaceMetricRepository.findById(10);
		helperId10 = new InterfaceMetricOccurrencesHelper(interfaceMetricId10, RepositoryType.MOCK);
	}
	
	@Test
	public void interfaceElements(){
		assertEquals("net.sf.saxon.tinytree", interfaceMetricId10.getPackage());
		assertEquals("WhitespaceTextImpl", interfaceMetricId10.getClassName());
		assertEquals("long", interfaceMetricId10.getReturnType());
		assertEquals("getLongValue", interfaceMetricId10.getMethodName());
		assertEquals("net.sf.saxon.tinytree.TinyTree,int", interfaceMetricId10.getParams());
		assertEquals(2, interfaceMetricId10.getParamsNames().length);
		assertEquals("net.sf.saxon.tinytree.TinyTree", interfaceMetricId10.getParamsNames()[0]);
		assertEquals("int", interfaceMetricId10.getParamsNames()[1]);
	}

	@Test
	public void classNameTerms() throws Exception {
		//http://localhost:8080/related-words-service/GetRelated?word=WhitespaceTextImpl
		
		//fqn: net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue
		//Whitespace: 0 []
		//Text: 6 [text, textualMatter, textbook, textEdition, schoolbook, schoolText]
		//Impl: 0 []
		
		List<QueryTerm> terms = helperId10.getAqeApproach().getClassNameTerms();
		assertEquals(3, terms.size());
		assertEquals("whitespace", terms.get(0).getExpandedTerms().get(0));
		assertEquals("text", terms.get(1).getExpandedTerms().get(0));
		assertEquals("impl", terms.get(2).getExpandedTerms().get(0));

		//Whitespace
		assertEquals(1, terms.get(0).getExpandedTerms().size());
		assertEquals("whitespace", terms.get(0).getExpandedTerms().get(0));
		
		//Text
		assertEquals(6, terms.get(1).getExpandedTerms().size());
		assertEquals("text", terms.get(1).getExpandedTerms().get(0));
		assertEquals("textualMatter", terms.get(1).getExpandedTerms().get(1));
		assertEquals("textbook", terms.get(1).getExpandedTerms().get(2));
		assertEquals("textEdition", terms.get(1).getExpandedTerms().get(3));
		assertEquals("schoolbook", terms.get(1).getExpandedTerms().get(4));
		assertEquals("schoolText", terms.get(1).getExpandedTerms().get(5));

		//Impl
		assertEquals(1, terms.get(2).getExpandedTerms().size());
		assertEquals("impl", terms.get(2).getExpandedTerms().get(0));
	}
	
	@Test
	public void returnTerms() throws Exception {
		//long, double, float, long, int, Integer, short, byte
		assertEquals(1, helperId10.getAqeApproach().getReturnTypeTerms().size());
		assertEquals(8, helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().size());
		assertEquals("long", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(0));
		assertEquals("double", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(1));
		assertEquals("float", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(2));
		assertEquals("long", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(3));
		assertEquals("int", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(4));
		assertEquals("Integer", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(5));
		assertEquals("short", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(6));
		assertEquals("byte", helperId10.getAqeApproach().getReturnTypeTerms().get(0).getExpandedTerms().get(7));
	}
	
	@Test
	public void methodNameTerms() throws Exception {
		//get: 70 [get, acquire, become, go, let, have, receive, find, ...], not [leave, take away, end]
		//Long: [long, hanker, yearn, retentive, recollective, tenacious], not [short, unretentive]
		//Value: 16 [value, prize, treasure, appreciate, respect, ...], not [disrespect, disesteem]
		
		List<QueryTerm> terms = helperId10.getAqeApproach().getMethodNameTerms();
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
		
		List<QueryTerm> terms = helperId10.getAqeApproach().getParamsTerms();
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
		helperId10.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, false, false, false));
		assertEquals(1, matches.size());
		assertEquals(18, matches.get(0).getId().longValue());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, false, false, true));
		assertEquals(7, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, false, true, false));
		assertEquals(3, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(20)));

		matches = helperId10.getOccurrences(new OccurrencesCombination(false, false, true, true));
		assertEquals(12, matches.size());
		
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(16)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(20)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));
		assertTrue(matches.contains(new InterfaceMetric(24)));
		assertTrue(matches.contains(new InterfaceMetric(25)));
	}

	@Test
	public void occurrences2of16() throws Exception {
		helperId10.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, true, false, false));
		assertEquals(1, matches.size());
		assertEquals(18, matches.get(0).getId().longValue());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, true, false, true));
		assertEquals(7, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(false, true, true, false));
		assertEquals(3, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(20)));

		matches = helperId10.getOccurrences(new OccurrencesCombination(false, true, true, true));
		assertEquals(12, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(11)));
		assertTrue(matches.contains(new InterfaceMetric(12)));
		assertTrue(matches.contains(new InterfaceMetric(14)));
		assertTrue(matches.contains(new InterfaceMetric(15)));
		assertTrue(matches.contains(new InterfaceMetric(16)));
		assertTrue(matches.contains(new InterfaceMetric(18)));
		assertTrue(matches.contains(new InterfaceMetric(19)));
		assertTrue(matches.contains(new InterfaceMetric(20)));
		assertTrue(matches.contains(new InterfaceMetric(22)));
		assertTrue(matches.contains(new InterfaceMetric(23)));
		assertTrue(matches.contains(new InterfaceMetric(24)));
		assertTrue(matches.contains(new InterfaceMetric(25)));
	}

	@Test
	public void occurrences3of16() throws Exception {
		helperId10.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, false, false, false));
		assertEquals(0, matches.size());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, false, false, true));
		assertEquals(0, matches.size());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, false, true, false));
		assertEquals(0, matches.size());

		matches = helperId10.getOccurrences(new OccurrencesCombination(true, false, true, true));
		assertEquals(1, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(13)));
	}

	@Test
	public void occurrences4of16() throws Exception {
		helperId10.updateOccurrences();
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, true, false, false));
		assertEquals(0, matches.size());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, true, false, true));
		assertEquals(0, matches.size());
		
		matches = helperId10.getOccurrences(new OccurrencesCombination(true, true, true, false));
		assertEquals(0, matches.size());

		matches = helperId10.getOccurrences(new OccurrencesCombination(true, true, true, true));
		assertEquals(2, matches.size());
		assertTrue(matches.contains(new InterfaceMetric(13)));
		assertTrue(matches.contains(new InterfaceMetric(17)));
	}

	@Test
	public void updateOccurrences() throws Exception {

		helperId10.updateOccurrences();
		interfaceMetricRepository.updateProcessedMethod(interfaceMetricId10);

		InterfaceMetric storage = interfaceMetricRepository.findById(interfaceMetricId10.getId());
		
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());

		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		
		assertEquals(0, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(0, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(0, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(1, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());

		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(0 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
}
