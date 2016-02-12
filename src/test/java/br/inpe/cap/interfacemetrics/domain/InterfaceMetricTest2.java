package br.inpe.cap.interfacemetrics.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricOccurrencesHelper;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricTest2 {

	private InterfaceMetricRepository repository = new InterfaceMetricRepository();

	@Test()
	public void processMethod1() throws Exception {
		InterfaceMetric i1 = repository.findById(606204);
		InterfaceMetric i2 = repository.findById(606199);

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());

		i1.processMethod();
		i2.processMethod();

		assertEquals(4, i1.getTotalParams());
		assertEquals(2, i2.getTotalParams());
	}

	@Test()
	public void compareParams() throws Exception {
		InterfaceMetric i01 = repository.findById(614664);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(614685);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(605670);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(605678);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(607521);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(607523);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(608461);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(608507);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(608456);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(608513);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(606417);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(606515);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(606295);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(606296);//'(java.lang.String,int)'

		//sameParams !isDisorder
		assertTrue(i01.isSameParams(i02.getParamsNames(),false));
		assertFalse(i01.isSameParams(i03.getParamsNames(),false));
		assertTrue(i03.isSameParams(i04.getParamsNames(),false));
		assertFalse(i03.isSameParams(i05.getParamsNames(),false));
		assertFalse(i01.isSameParams(i13.getParamsNames(),false));
		assertTrue(i07.isSameParams(i08.getParamsNames(),false));
		assertTrue(i09.isSameParams(i10.getParamsNames(),false));
		
		//sameParams isDisorder
		assertTrue(i01.isSameParams(i02.getParamsNames(),true));
		assertTrue(i01.isSameParams(i05.getParamsNames(),true));
		assertTrue(i02.isSameParams(i06.getParamsNames(),true));
		assertFalse(i01.isSameParams(i03.getParamsNames(),true));
		assertFalse(i04.isSameParams(i06.getParamsNames(),true));
		assertFalse(i01.isSameParams(i13.getParamsNames(),true));
		assertFalse(i01.isSameParams(i14.getParamsNames(),true));
		assertTrue(i11.isSameParams(i12.getParamsNames(),false));
	}

	@Test()
	public void compareExpandedParams() throws Exception {
		InterfaceMetric i01 = repository.findById(614664);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i02 = repository.findById(614685);//'(int,java.lang.String,java.lang.String)'
		InterfaceMetric i03 = repository.findById(605670);//'(int,int,java.lang.String)'
		InterfaceMetric i04 = repository.findById(605678);//'(int,int,java.lang.String)'
		InterfaceMetric i05 = repository.findById(607521);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i06 = repository.findById(607523);//'(java.lang.String,java.lang.String,int)'
		InterfaceMetric i07 = repository.findById(608461);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i08 = repository.findById(608507);//'(java.lang.String,java.lang.Long)'
		InterfaceMetric i09 = repository.findById(608456);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i10 = repository.findById(608513);//'(java.lang.String,java.lang.Integer)'
		InterfaceMetric i11 = repository.findById(606417);//'(java.lang.String,long)'
		InterfaceMetric i12 = repository.findById(606515);//'(java.lang.String,long)'
		InterfaceMetric i13 = repository.findById(606295);//'(java.lang.String,int)'
		InterfaceMetric i14 = repository.findById(606296);//'(java.lang.String,int)'

		@SuppressWarnings("unused")
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(i11);
		helper = new InterfaceMetricOccurrencesHelper(i13);
		helper = new InterfaceMetricOccurrencesHelper(i07);
		helper = new InterfaceMetricOccurrencesHelper(i01);
		helper = new InterfaceMetricOccurrencesHelper(i02);
		helper = new InterfaceMetricOccurrencesHelper(i04);
		
		//sameParams !isDisorder
		assertTrue(i11.isSameExpandedParams(i12.getParamsNames(),false));
		assertTrue(i11.isSameExpandedParams(i13.getParamsNames(),false));
		assertFalse(i11.isSameExpandedParams(i09.getParamsNames(),false));
		assertTrue(i13.isSameExpandedParams(i14.getParamsNames(),false));
		assertTrue(i13.isSameExpandedParams(i12.getParamsNames(),false));
		assertFalse(i13.isSameExpandedParams(i10.getParamsNames(),false));
		assertTrue(i07.isSameExpandedParams(i08.getParamsNames(),false));
		assertFalse(i07.isSameExpandedParams(i11.getParamsNames(),false));
		assertFalse(i07.isSameExpandedParams(i13.getParamsNames(),false));
		
		//sameParams isDisorder
		assertTrue(i01.isSameExpandedParams(i02.getParamsNames(),true));
		assertTrue(i01.isSameExpandedParams(i05.getParamsNames(),true));
		assertTrue(i02.isSameExpandedParams(i06.getParamsNames(),true));
		assertFalse(i01.isSameExpandedParams(i03.getParamsNames(),true));
		assertFalse(i04.isSameExpandedParams(i06.getParamsNames(),true));
		assertFalse(i01.isSameExpandedParams(i13.getParamsNames(),true));
	}
}
