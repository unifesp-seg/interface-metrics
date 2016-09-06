package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricOccurrencesHelper;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;

public class InterfaceMetricTest {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository(RepositoryType.MOCK);
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository(RepositoryType.MOCK);

	private List<InterfaceMetric> tests = new ArrayList<InterfaceMetric>();

	@Before
	public void setup() throws Exception {
		tests = repository.findAllOrderedById();
		assertEquals(47, tests.size());
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
	public void fnqParts() throws Exception {
		//0 com.sun.nio.zipfs.JarFileSystemProvider.getPath
		assertEquals("com.sun.nio.zipfs.JarFileSystemProvider.getPath",tests.get(0).getFqn());
		assertEquals("com.sun.nio.zipfs",tests.get(0).getPackage());
		assertEquals("JarFileSystemProvider",tests.get(0).getClassName());
		assertEquals("getPath",tests.get(0).getMethodName());

		//5 com.sun.nio.zipfs.ZipUtils.winToJavaTime
		assertEquals("com.sun.nio.zipfs.ZipUtils.winToJavaTime",tests.get(5).getFqn());
		assertEquals("com.sun.nio.zipfs",tests.get(5).getPackage());
		assertEquals("ZipUtils",tests.get(5).getClassName());
		assertEquals("winToJavaTime",tests.get(5).getMethodName());

		//13 met.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure
		assertEquals("met.sf.saxon.tinytree.WhitespaceTextImpl.becomeRetentiveTreasure",tests.get(13).getFqn());
		assertEquals("met.sf.saxon.tinytree",tests.get(13).getPackage());
		assertEquals("WhitespaceTextImpl",tests.get(13).getClassName());
		assertEquals("becomeRetentiveTreasure",tests.get(13).getMethodName());

		//16 net.sf.saxon.tinytree.MhitespaceTextImpl.goRecollectiveAppreciate
		assertEquals("net.sf.saxon.tinytree.MhitespaceTextImpl.goRecollectiveAppreciate",tests.get(16).getFqn());
		assertEquals("net.sf.saxon.tinytree",tests.get(16).getPackage());
		assertEquals("MhitespaceTextImpl",tests.get(16).getClassName());
		assertEquals("goRecollectiveAppreciate",tests.get(16).getMethodName());

		//17 met.sf.saxon.tinytree.MhitespaceTextImpl.letTenaciousRespect
		assertEquals("met.sf.saxon.tinytree.MhitespaceTmextImmpl.letTenaciousRespect",tests.get(17).getFqn());
		assertEquals("met.sf.saxon.tinytree",tests.get(17).getPackage());
		assertEquals("MhitespaceTmextImmpl",tests.get(17).getClassName());
		assertEquals("letTenaciousRespect",tests.get(17).getMethodName());
	}

	@Test
	public void className() throws Exception {
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

		assertEquals(16, combinations.size());
		
		InterfaceMetric storage = repository.findById(10);
		
		assertEquals(1 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(2 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(3 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(4 , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());
		assertEquals(5 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(6 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(7 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(8 , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		assertEquals(9 , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(10, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(11, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(12, storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());
		assertEquals(13, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(14, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(15, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(16, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
	
	@Test()
	public void getParamsNames() throws Exception {
		String params = "Map<java.lang.String, <?>, Path>, Map<A, B, C, D, <E>>";
		InterfaceMetric i0 = new InterfaceMetric();
		i0.setParams(params);

		assertEquals(i0.getParams(), params);
		assertEquals(2, i0.getParamsNames().length);
		assertEquals("Map<java.lang.String,<?>,Path>",i0.getParamsNames()[0]);
		assertEquals("Map<A,B,C,D,<E>>",i0.getParamsNames()[1]);

		params = "Map<java.lang.String, <?>, Path>, Map<A, B, C, D, <E";
		i0 = new InterfaceMetric();
		i0.setParams(params);

		assertEquals(i0.getParams(), params);
		assertEquals(2, i0.getParamsNames().length);
		assertEquals("Map<java.lang.String,<?>,Path>",i0.getParamsNames()[0]);
		assertEquals("Map<A,B,C,D,<E",i0.getParamsNames()[1]);
	}
	
	@Test()
	public void compareParamsOrder() throws Exception {
		InterfaceMetric i01 = repository.findById(1819910);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(1819931);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(1810916);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(1810924);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(1812767);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(1812769);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(1813707);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(1813753);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(1813702);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(1813759);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(1811663);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(1811761);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(1811541);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(1811542);//'(java.lang.String,int)'

		//sameParams isParamsOrder
		assertTrue(i01.isSameParams(i02.getParamsNames(),true));
		assertFalse(i01.isSameParams(i03.getParamsNames(),true));
		assertTrue(i03.isSameParams(i04.getParamsNames(),true));
		assertFalse(i03.isSameParams(i05.getParamsNames(),true));
		assertFalse(i01.isSameParams(i13.getParamsNames(),true));
		assertTrue(i07.isSameParams(i08.getParamsNames(),true));
		assertTrue(i09.isSameParams(i10.getParamsNames(),true));
		
		//sameParams !isParamsOrder
		assertTrue(i01.isSameParams(i02.getParamsNames(),false));
		assertTrue(i01.isSameParams(i05.getParamsNames(),false));
		assertTrue(i02.isSameParams(i06.getParamsNames(),false));
		assertFalse(i01.isSameParams(i03.getParamsNames(),false));
		assertFalse(i04.isSameParams(i06.getParamsNames(),false));
		assertFalse(i01.isSameParams(i13.getParamsNames(),false));
		assertFalse(i01.isSameParams(i14.getParamsNames(),false));
		assertTrue(i11.isSameParams(i12.getParamsNames(),true));
	}

	@Test()
	public void compareExpandedParamsOrder() throws Exception {
		InterfaceMetric i01 = repository.findById(1819910);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(1819931);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(1810916);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(1810924);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(1812767);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(1812769);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(1813707);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(1813753);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(1813702);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(1813759);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(1811663);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(1811761);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(1811541);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(1811542);//'(java.lang.String,int)'

		@SuppressWarnings("unused")
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(i11, RepositoryType.MOCK);
		helper = new InterfaceMetricOccurrencesHelper(i13, RepositoryType.MOCK);
		helper = new InterfaceMetricOccurrencesHelper(i07, RepositoryType.MOCK);
		helper = new InterfaceMetricOccurrencesHelper(i01, RepositoryType.MOCK);
		helper = new InterfaceMetricOccurrencesHelper(i02, RepositoryType.MOCK);
		helper = new InterfaceMetricOccurrencesHelper(i04, RepositoryType.MOCK);
		
		//sameParams isParamsOrder
		assertTrue(i11.isSameExpandedParams(i12.getParamsNames(),true));
		assertTrue(i11.isSameExpandedParams(i13.getParamsNames(),true));
		assertFalse(i11.isSameExpandedParams(i09.getParamsNames(),true));
		assertTrue(i13.isSameExpandedParams(i14.getParamsNames(),true));
		assertTrue(i13.isSameExpandedParams(i12.getParamsNames(),true));
		assertFalse(i13.isSameExpandedParams(i10.getParamsNames(),true));
		assertTrue(i07.isSameExpandedParams(i08.getParamsNames(),true));
		assertFalse(i07.isSameExpandedParams(i11.getParamsNames(),true));
		assertFalse(i07.isSameExpandedParams(i13.getParamsNames(),true));
		
		//sameParams !isParamsOrder
		assertTrue(i01.isSameExpandedParams(i02.getParamsNames(),false));
		assertTrue(i01.isSameExpandedParams(i05.getParamsNames(),false));
		assertTrue(i02.isSameExpandedParams(i06.getParamsNames(),false));
		assertFalse(i01.isSameExpandedParams(i03.getParamsNames(),false));
		assertFalse(i04.isSameExpandedParams(i06.getParamsNames(),false));
		assertFalse(i01.isSameExpandedParams(i13.getParamsNames(),false));
	}

	@Test()
	public void getParamsNamesBD() throws Exception {
		InterfaceMetric i1 = repository.findById(1440377);
		InterfaceMetric i2 = repository.findById(1440618);
		InterfaceMetric i3 = repository.findById(1440368);
		InterfaceMetric i4 = repository.findById(1449408);
		InterfaceMetric i5 = repository.findById(1678643);
		
		assertEquals(i1.getParams(), "java.nio.file.Path,java.nio.file.DirectoryStream$Filter<<?-java.nio.file.Path>>");
		assertEquals(i2.getParams(), "java.util.Map<com.sun.java.util.jar.pack.Attribute$Layout,com.sun.java.util.jar.pack.Attribute>,int,java.lang.String");
		assertEquals(i3.getParams(), "java.nio.file.Path,java.util.Map<java.lang.String,<?>>");
		assertEquals(i4.getParams(), "java.lang.Class<<T>>,boolean,com.sun.xml.internal.ws.api.server.Invoker,javax.xml.namespace.QName,javax.xml.namespace.QName,com.sun.xml.internal.ws.api.server.Container,com.sun.xml.internal.ws.api.WSBinding,com.sun.xml.internal.ws.api.server.SDDocumentSource,java.util.Collection<<?+com.sun.xml.internal.ws.api.server.SDDocumentSource>>,org.xml.sax.EntityResolver,boolean");
		assertEquals(i5.getParams(), "org.hibernate.cfg.PropertyHolder,org.hibernate.cfg.PropertyData,org.hibernate.cfg.PropertyData,org.hibernate.cfg.AccessType,boolean,org.hibernate.cfg.annotations.EntityBinder,boolean,boolean,boolean,org.hibernate.cfg.Mappings,java.util.Map<org.hibernate.annotations.common.reflection.XClass,org.hibernate.cfg.InheritanceState>");
		
		assertEquals(2,i1.getTotalParams());
		assertEquals(3,i2.getTotalParams());
		assertEquals(2,i3.getTotalParams());
		assertEquals(11,i4.getTotalParams());
		assertEquals(11,i5.getTotalParams());
	}
	
	@Test()
	public void processParams() throws Exception {
		InterfaceMetric i1 = repository.findById(1440361);
		InterfaceMetric i2 = repository.findById(1440377);
		InterfaceMetric i3 = repository.findById(1440371);
		InterfaceMetric i3a = repository.findById(1440618);

		List<String> params = null;
		
		params = paramsRepository.getParams(i1); 
		assertEquals(1, params.size());
		assertEquals("java.lang.Class<<?+java.nio.file.attribute.FileAttributeView>>", params.get(0));

		params = paramsRepository.getParams(i2); 
		assertEquals(2, params.size());
		assertEquals("java.nio.file.Path", params.get(0));
		assertEquals("java.nio.file.DirectoryStream$Filter<<?-java.nio.file.Path>>", params.get(1));

		params = paramsRepository.getParams(i3); 
		assertEquals(3, params.size());
		assertEquals("java.nio.file.Path", params.get(0));
		assertEquals("java.lang.Class<<V>>", params.get(1));
		assertEquals("java.nio.file.LinkOption[]", params.get(2));
		
		params = paramsRepository.getParams(i3a); 
		assertEquals(3, params.size());
		assertEquals("java.util.Map<com.sun.java.util.jar.pack.Attribute$Layout,com.sun.java.util.jar.pack.Attribute>", params.get(0));
		assertEquals("int", params.get(1));
		assertEquals("java.lang.String", params.get(2));
	}
}
