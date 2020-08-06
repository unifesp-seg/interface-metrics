package br.inpe.cap.interfacemetrics.match550.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;
import br.inpe.cap.interfacemetrics.match550.util.MatchProperties;
import br.unifesp.ict.seg.geniesearchapi.domain.GenieMethod;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.GenieMethodRepository;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class Match550NEServiceTest {

	private InterfaceMetricRepository interfaceMetricRepository = new InterfaceMetricRepository(RepositoryType.REAL);
	private GenieMethodRepository genieMethodRepository = new GenieMethodRepository();
	private Match550EngService service = new Match550EngService();
	private long neInterfaceMetricId = 1239485;

	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void getSourceCodeTest() throws Exception {
		InterfaceMetric interfaceMetric = interfaceMetricRepository.findById(neInterfaceMetricId);
		GenieMethod genieMethod = genieMethodRepository.findByEntityId(interfaceMetric.getEntityId());

		String code = "";
		code += "@Override\n";
		code += "    protected Bitmap doInBackground(String... urls) {\n";
		code += "        Log.d(\"doInBackground\", \"starting download of image\");        \n";
		code += "        Bitmap image = downloadImage(urls[0]);\n";
		code += "        return image;\n";
		code += "    }";

		String sourceCode = genieMethod.getSourceCode();
		assertNotNull(sourceCode);
		assertEquals(code, sourceCode);
	}

	@Test
	public void saveMethodSourceCode() throws Exception {
		InterfaceMetric interfaceMetric = interfaceMetricRepository.findById(neInterfaceMetricId);
		GenieMethod genieMethod = genieMethodRepository.findByEntityId(interfaceMetric.getEntityId());
		
		service.saveMethodSourceCode(genieMethod);
		
		Path sourceFile = Paths.get(MatchProperties.getMatch550NESourcePath().toString(),  genieMethod.getEntityId() + MatchProperties.SOURCE_FILE_EXTENSION);
		
		assertTrue(sourceFile.toFile().isFile());
	}
}
