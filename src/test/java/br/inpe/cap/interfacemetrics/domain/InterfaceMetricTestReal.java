package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricOccurrencesHelper;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;

public class InterfaceMetricTestReal {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository(RepositoryType.REAL);
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository(RepositoryType.REAL);

	@Test()
	public void processMethod1() throws Exception {
		InterfaceMetric i1 = repository.findById(1811450);
		InterfaceMetric i2 = repository.findById(1811445);

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());

		i1.processMethod();
		i2.processMethod();

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());
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
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(i11, RepositoryType.REAL);
		helper = new InterfaceMetricOccurrencesHelper(i13, RepositoryType.REAL);
		helper = new InterfaceMetricOccurrencesHelper(i07, RepositoryType.REAL);
		helper = new InterfaceMetricOccurrencesHelper(i01, RepositoryType.REAL);
		helper = new InterfaceMetricOccurrencesHelper(i02, RepositoryType.REAL);
		helper = new InterfaceMetricOccurrencesHelper(i04, RepositoryType.REAL);
		
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

	@Test
	public void interface_1824386() throws Exception {
		InterfaceMetric storage = repository.findById(1824386);
		
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, false, true)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, false, true,  true)).intValue());

		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  false, true)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(false, true,  true,  true)).intValue());
		
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, false, true)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  false)).intValue());
		assertEquals(0    , storage.getOccurrencesTotal(new OccurrencesCombination(true,  false, true,  true)).intValue());

		assertEquals(12130, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, false)).intValue());
		assertEquals(12130, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  false, true)).intValue());
		assertEquals(12137, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  false)).intValue());
		assertEquals(12137, storage.getOccurrencesTotal(new OccurrencesCombination(true,  true,  true,  true)).intValue());
	}
}
