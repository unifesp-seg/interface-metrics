package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricOccurrencesHelper;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricTestReal {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository();
	private InterfaceMetricParamsRepository paramsRepository = new InterfaceMetricParamsRepository();

	@Test()
	public void processMethod1() throws Exception {
		InterfaceMetric i1 = repository.findById(1409701);
		InterfaceMetric i2 = repository.findById(1409696);

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());

		i1.processMethod();
		i2.processMethod();

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());
	}

	@Test()
	public void compareParamsOrder() throws Exception {
		InterfaceMetric i01 = repository.findById(1418161);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(1418182);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(1409167);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(1409175);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(1411018);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(1411020);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(1411958);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(1412004);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(1411953);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(1412010);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(1409914);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(1410012);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(1409792);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(1409793);//'(java.lang.String,int)'

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
		InterfaceMetric i01 = repository.findById(1418161);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(1418182);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(1409167);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(1409175);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(1411018);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(1411020);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(1411958);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(1412004);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(1411953);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(1412010);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(1409914);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(1410012);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(1409792);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(1409793);//'(java.lang.String,int)'

		@SuppressWarnings("unused")
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(i11);
		helper = new InterfaceMetricOccurrencesHelper(i13);
		helper = new InterfaceMetricOccurrencesHelper(i07);
		helper = new InterfaceMetricOccurrencesHelper(i01);
		helper = new InterfaceMetricOccurrencesHelper(i02);
		helper = new InterfaceMetricOccurrencesHelper(i04);
		
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
		InterfaceMetric i1 = repository.findById(1038628);
		InterfaceMetric i2 = repository.findById(1038869);
		InterfaceMetric i3 = repository.findById(1038619);
		InterfaceMetric i4 = repository.findById(1047659);
		InterfaceMetric i5 = repository.findById(1276894);
		
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
		InterfaceMetric i1 = repository.findById(1038612);
		InterfaceMetric i2 = repository.findById(1038628);
		InterfaceMetric i3 = repository.findById(1038622);
		InterfaceMetric i3a = repository.findById(1038869);
		InterfaceMetric i36 = repository.findById(1423061);

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

		params = paramsRepository.getParams(i36); 
		assertEquals(36, params.size());
		assertEquals("long", params.get(0));
		assertEquals("java.lang.String", params.get(1));
		assertEquals("boolean", params.get(2));
		assertEquals("long", params.get(3));
		assertEquals("java.lang.String", params.get(4));
		assertEquals("int", params.get(10));
		assertEquals("java.lang.Boolean", params.get(33));
		assertEquals("java.lang.String[]", params.get(34));
		assertEquals("java.lang.String[]", params.get(35));
	}

}
