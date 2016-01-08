package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricTest {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository(true);

	private List<InterfaceMetric> tests = new ArrayList<InterfaceMetric>();

	@Before
	public void setup() throws Exception {
		tests = repository.findAllOrderedById();
		assertEquals(26, tests.size());
		this.processMethodsInfo();
	}

	private void processMethodsInfo(){
		for(InterfaceMetric interfaceMetric : tests)
			interfaceMetric.processMethod();
	}

	/*
0	JAVA_LIBRARY	METHOD	PUBLIC	com.sun.nio.zipfs.JarFileSystemProvider.getPath	(java.net.URI)	java.nio.file.Path	RETURNS
1	JAVA_LIBRARY	METHOD	PUBLIC	com.sun.nio.zipfs.ZipFileSystemProvider.getFileAttributeView	(java.nio.file.Path,java.lang.Class<<V>>,java.nio.file.LinkOption[])	<V>	RETURNS
2	JAVA_LIBRARY	METHOD	PUBLIC	sun.io.CharToByteEUC_TW.canConvert	(char)	boolean	RETURNS
3	JAVA_LIBRARY	METHOD	PUBLIC	sun.io.CharToByteDBCS_ASCII.convert	(char[],int,int,byte[],int,int)	int	RETURNS
4	JAVA_LIBRARY	METHOD	PUBLIC,STATIC	com.sun.nio.zipfs.ZipUtils.javaToDosTime	(long)	long	RETURNS
5	JAVA_LIBRARY	METHOD	PUBLIC,STATIC,FINAL	com.sun.nio.zipfs.ZipUtils.winToJavaTime	(long)	long	RETURNS
6	JAR	METHOD	PUBLIC,STATIC	net.sf.saxon.tinytree.WhitespaceTextImpl.getLongValue	(net.sf.saxon.tinytree.TinyTree,int)	long	RETURNS
7	JAR	METHOD	PUBLIC,STATIC	net.sf.saxon.om.FastStringBuffer.diagnosticPrint	(java.lang.CharSequence)	java.lang.String	RETURNS
8	JAR	METHOD	PUBLIC,STATIC	net.sf.saxon.om.StandardNames.getURI	(int)	java.lang.String	RETURNS
9	JAVA_LIBRARY	METHOD	PUBLIC,STATIC	java.lang.String.format	(java.lang.String,java.lang.Object[])	java.lang.String	RETURNS
10	JAVA_LIBRARY	METHOD	PUBLIC	java.lang.String.split	(java.lang.String)	java.lang.String[]	RETURNS
	 */
	
	@Test()
	public void methodInfo() throws Exception {
		InterfaceMetric test = tests.get(0);

		assertEquals("CRAWLED", test.getProjectType());
		assertEquals("METHOD", test.getEntityType());
		assertEquals("PUBLIC", test.getModifiers());
		assertEquals("com.sun.nio.zipfs.JarFileSystemProvider.getPath", test.getFqn());
		assertEquals("java.net.URI", test.getParams());
		assertEquals("java.nio.file.Path", test.getReturnType());
		assertEquals("RETURNS", test.getRelationType());
	}
	
	@Test
	public void totalParams() throws Exception {
		assertEquals(1, tests.get(0).getTotalParams());
		assertEquals(3, tests.get(1).getTotalParams());
	}

	@Test
	public void totalWordsMethod() throws Exception {
		assertEquals(2, tests.get(0).getTotalWordsMethod());
		assertEquals(4, tests.get(1).getTotalWordsMethod());
	}

	@Test
	public void totalWordsClass() throws Exception {
		assertEquals(4, tests.get(0).getTotalWordsClass());
		assertEquals(5, tests.get(2).getTotalWordsClass());
		assertEquals(5, tests.get(3).getTotalWordsClass());
	}

	@Test
	public void onlyPrimitiveTypes() throws Exception {
		assertFalse(tests.get(0).isOnlyPrimitiveTypes());
		assertFalse(tests.get(1).isOnlyPrimitiveTypes());
		assertTrue(tests.get(2).isOnlyPrimitiveTypes());
		assertTrue(tests.get(3).isOnlyPrimitiveTypes());
		assertTrue(tests.get(8).isOnlyPrimitiveTypes());
		assertFalse(tests.get(10).isOnlyPrimitiveTypes());
	}
	
	@Test
	public void isStatic() throws Exception {
		assertFalse(tests.get(3).isStatic());
		assertTrue(tests.get(4).isStatic());
		assertTrue(tests.get(5).isStatic());
	}

	@Test
	public void hasTypeSamePackage() throws Exception {
		assertFalse(tests.get(4).isHasTypeSamePackage());
		assertFalse(tests.get(5).isHasTypeSamePackage());
		assertFalse(tests.get(8).isHasTypeSamePackage());
		assertFalse(tests.get(7).isHasTypeSamePackage());
		assertTrue(tests.get(6).isHasTypeSamePackage());
		assertTrue(tests.get(9).isHasTypeSamePackage());
	}

	@Test
	public void storageOccurrenceField() throws Exception {
		InterfaceMetric interfaceMetric = repository.findById(10);

		int i = 1;
		List<OccurrencesCombination> combinations = OccurrencesCombination.allCombinations();
		for(OccurrencesCombination combination : combinations)
			interfaceMetric.setOccurrencesTotal(combination, i++);
		
		repository.updateProcessedMethod(interfaceMetric);

		InterfaceMetric storage = repository.findById(10);
		
		assertEquals(1, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, false)).intValue());
		assertEquals(2, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, false, false)).intValue());
		assertEquals(3, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, false)).intValue());
		assertEquals(4, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, false, false)).intValue());
		assertEquals(5, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, true, false)).intValue());
		assertEquals(6, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, false, false)).intValue());
		assertEquals(7, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true, false)).intValue());
		assertEquals(8, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(9, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, true, true)).intValue());
		assertEquals(10, storage.getOccurrencesTotal(new OccurrencesCombination(true, true, false, true)).intValue());
		assertEquals(11, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, true, true)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(true, false, false, true)).intValue());
		assertEquals(13, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, true, true)).intValue());
		assertEquals(14, storage.getOccurrencesTotal(new OccurrencesCombination(false, true, false, true)).intValue());
		assertEquals(15, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true, true)).intValue());
		assertEquals(16, storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
	}
}
