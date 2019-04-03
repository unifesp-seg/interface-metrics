package br.inpe.cap.interfacemetrics.clone.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import br.inpe.cap.interfacemetrics.clone.application.SourcererCCService;


public class SourcererCCHeaderTest {

	@Test
	public void getParams(){
		String line0 = "124555,/home/saini/otavio/sf100-src/0/75/content/platforms/liferay-ext/versions/deprecated/4.3.0_clean/portal-impl/src/com/liferay/portlet/wiki/service/impl/WikiPageLocalServiceImpl.java,173,210";
		SourcererCCHeader cc = new SourcererCCHeader(line0);

		cc.setMethodLine("	public void deletePage(WikiPage page)");
		assertEquals(1, cc.getMethodParams().size());
		assertEquals("WikiPage", cc.getMethodParams().get(0));
		
		cc.setMethodLine("	public void deletePage(com.liferay.portlet.wiki.model.WikiNode a  ,   com.liferay.portlet.wiki.model.WikiPage b   ,java.lang.String[] c  , java.lang.String[] d))");
		assertEquals(4, cc.getMethodParams().size());
		assertEquals("com.liferay.portlet.wiki.model.WikiNode", cc.getMethodParams().get(0));
		assertEquals("com.liferay.portlet.wiki.model.WikiPage", cc.getMethodParams().get(1));
		assertEquals("java.lang.String[]", cc.getMethodParams().get(2));
		assertEquals("java.lang.String[]", cc.getMethodParams().get(3));

		cc.setMethodLine("	public void deletePage(   com.liferay.portlet.wiki.model.WikiNode a,    com.liferay.portlet.wiki.model.WikiPage b,   boolean c, boolean d  )    ");
		assertEquals(4, cc.getMethodParams().size());
		assertEquals("com.liferay.portlet.wiki.model.WikiNode", cc.getMethodParams().get(0));
		assertEquals("com.liferay.portlet.wiki.model.WikiPage", cc.getMethodParams().get(1));
		assertEquals("boolean", cc.getMethodParams().get(2));
		assertEquals("boolean", cc.getMethodParams().get(3));
	}
	
	//@Test
	public void headerId0() throws Exception {
		String line = "0,/home/saini/otavio/sf100-src/0/96/content/healJavaSrc/org/heal/module/oai/heal/OAIProviderServlet.java,95,232";
		SourcererCCHeader ccHeader = new SourcererCCHeader(line);
		
		assertEquals(0, ccHeader.getHeaderId().longValue());
		assertEquals(SourcererCCService.SOURCE_CODE_PATH + "/96/content/healJavaSrc/org/heal/module/oai/heal/OAIProviderServlet.java", ccHeader.getSourceClassPath());
		assertEquals(95, ccHeader.getLineIni());
		assertEquals(232, ccHeader.getLineFin());
		assertTrue(new File(ccHeader.getSourceClassPath()).exists());
		
		ccHeader.loadFromSourceCode();
		assertEquals("public void init() throws ServletException {", ccHeader.getMethodLine());
	
		assertEquals("org.heal.module.oai.heal.OAIProviderServlet.init", ccHeader.getMethodFQN());
		assertEquals(0, ccHeader.getMethodParams().size());
		assertEquals("096", ccHeader.getMethodProjectNamePrefix());

		ccHeader.loadFromDB();
		assertEquals(6042353, ccHeader.getEntityId().longValue());
	}

	//@Test
	public void headerId124555() throws Exception{
		String line = "124555,/home/saini/otavio/sf100-src/0/75/content/platforms/liferay-ext/versions/deprecated/4.3.0_clean/portal-impl/src/com/liferay/portlet/wiki/service/impl/WikiPageLocalServiceImpl.java,173,210";
		SourcererCCHeader ccHeader = new SourcererCCHeader(line);

		assertEquals(124555, ccHeader.getHeaderId().longValue());
		assertEquals(SourcererCCService.SOURCE_CODE_PATH + "/75/content/platforms/liferay-ext/versions/deprecated/4.3.0_clean/portal-impl/src/com/liferay/portlet/wiki/service/impl/WikiPageLocalServiceImpl.java", ccHeader.getSourceClassPath());
		assertEquals(173, ccHeader.getLineIni());
		assertEquals(210, ccHeader.getLineFin());
		assertTrue(new File(ccHeader.getSourceClassPath()).exists());
		
		ccHeader.loadFromSourceCode();
		assertEquals("public void deletePage(WikiPage page)", ccHeader.getMethodLine());

		assertEquals("com.liferay.portlet.wiki.service.impl.WikiPageLocalServiceImpl.deletePage", ccHeader.getMethodFQN());
		assertEquals(1, ccHeader.getMethodParams().size());
		assertEquals("WikiPage", ccHeader.getMethodParams().get(0));
		assertEquals("075", ccHeader.getMethodProjectNamePrefix());
		
		ccHeader.loadFromDB();
		assertEquals(5935785, ccHeader.getEntityId().longValue());
	}

//	@Test
//	public void headerId8807() throws Exception{
//		String line = "8807,/home/saini/otavio/sf100-src/0/96/content/newdev/WEB-INF/classes/OAI.java,36,40";
//		SourcererCCHeader cc = new SourcererCCHeader(line);
//
//		cc.loadFromSourceCode();
//		cc.loadFromDB();
//
//		assertEquals("public void doPost(HttpServletRequest request,HttpServletResponse response)", cc.getMethodLine());
//		assertEquals("OAI.doPost", cc.getMethodFQN());
//		assertEquals(2, cc.getMethodParams().size());
//		assertEquals("HttpServletRequest", cc.getMethodParams().get(0));
//		assertEquals("HttpServletResponse", cc.getMethodParams().get(1));
//		
//		assertEquals(-1, cc.getEntityId().longValue());
//	}
	
//	@Test
//	public void headerId8777() throws Exception{
//		String line = "8777,/home/saini/otavio/sf100-src/0/96/content/activedev/WEB-INF/classes/UploadUtil.java,97,101";
//		SourcererCCHeader cc = new SourcererCCHeader(line);
//
//		cc.loadFromSourceCode();
//		cc.loadFromDB();
//
//		assertEquals("public void doPost(HttpServletRequest request,HttpServletResponse response)", cc.getMethodLine());
//		assertEquals("UploadUtil.doPost", cc.getMethodFQN());
//		assertEquals(2, cc.getMethodParams().size());
//		assertEquals("HttpServletRequest", cc.getMethodParams().get(0));
//		assertEquals("HttpServletResponse", cc.getMethodParams().get(1));
//		
//		assertEquals(-1, cc.getEntityId().longValue());
//	}
	
	//@Test
	public void headerId3250() throws Exception{
		String line = "3250,/home/saini/otavio/sf100-src/0/96/content/healJavaSrc/org/heal/module/oai/provider/oai_dc/OAI_DCHandler.java,38,40";
		SourcererCCHeader cc = new SourcererCCHeader(line);

		cc.loadFromSourceCode();
		cc.loadFromDB();

		assertEquals("public OAIMetadataFormat getFormat() {", cc.getMethodLine());
		assertEquals("org.heal.module.oai.provider.oai_dc.OAI_DCHandler.getFormat", cc.getMethodFQN());
		assertEquals(0, cc.getMethodParams().size());
		
		assertEquals(6042831, cc.getEntityId().longValue());
	}
	
	//@Test
	public void headerId7316() throws Exception{
		String line = "7316,/home/saini/otavio/sf100-src/0/12/content/src/dsachat/share/hero/Talent.java,42,44";
		SourcererCCHeader cc = new SourcererCCHeader(line);

		cc.loadFromSourceCode();
		cc.loadFromDB();

		assertEquals("public void setName(String name) {", cc.getMethodLine());
		assertEquals("dsachat.share.hero.Talent.setName", cc.getMethodFQN());
		assertEquals(1, cc.getMethodParams().size());
		assertEquals("String", cc.getMethodParams().get(0));
		
		assertEquals(5833508, cc.getEntityId().longValue());
	}

	//@Test
	public void headerId57219() throws Exception{
		String line = "57219,/home/saini/otavio/sf100-src/0/55/content/src/main/java/net/sf/lavalamp/site/AbstractBuildSite.java,18,20";
		SourcererCCHeader cc = new SourcererCCHeader(line);

		cc.loadFromSourceCode();
		cc.loadFromDB();

		assertEquals("public void login( ) throws LoginRequiredException,LoginFailedException, IOException {", cc.getMethodLine());
		assertEquals("net.sf.lavalamp.site.AbstractBuildSite.login", cc.getMethodFQN());
		assertEquals(0, cc.getMethodParams().size());
		
		assertEquals(5893406, cc.getEntityId().longValue());
	}
}
