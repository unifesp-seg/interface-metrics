package br.inpe.cap.interfacemetrics.match550.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.match550.util.MatchProperties;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class Match550ServiceTest {

	private Match550Service service = new Match550Service();

	@Before
	public void initialize() throws IOException {
		GenieSearchAPIConfig.loadProperties();
	}

	@Test
	public void saveMethodSourceCode() throws Exception {
		long entityIdNe = 3889318;
		long entityIdEng = 13164104;

		service.saveMethodSourceCode(entityIdNe, entityIdEng);

		Path sourceFile = Paths.get(MatchProperties.getMatch550SourcePath().toString(), entityIdNe + "-" + entityIdEng + MatchProperties.SOURCE_FILE_EXTENSION);
		
		assertTrue(sourceFile.toFile().isFile());
		
		String code = "";
		code += "***************************************** Not Engineering\n";
		code += "SELECT * FROM interface_metrics_550_match_instance where ne_entity_id = " + entityIdNe + ";\n";
		code += "*******************************************************************************\n";
		code += "@Override\n";
		code += "    public boolean onCreateOptionsMenu(Menu menu) {\n";
		code += "        // Inflate the menu; this adds items to the action bar if it is present.\n";
		code += "        getMenuInflater().inflate(R.menu.sec_car, menu);\n";
		code += "        return true;\n";
		code += "    }\n";
		code += "\n\n";
		code += "***************************************** Engineering\n";
		code += "SELECT * FROM interface_metrics_550_match_instance where eng_entity_id = " + entityIdEng + ";\n";
		code += "*******************************************************************************\n";
		code += "@Override\n";
		code += "	public boolean onCreateOptionsMenu(Menu menu) {\n";
		code += "\n";
		code += "		// Get a reference to the MenuInflater\n";
		code += "		MenuInflater inflater = getMenuInflater();\n";
		code += "		\n";
		code += "		// Inflate the menu using activity_menu.xml\n";
		code += "		inflater.inflate(R.menu.activity_menu, menu);\n";
		code += "		\n";
		code += "		// Return true to display the menu\n";
		code += "		return true;\n";
		code += "	}";

		String sourceCode = new String(Files.readAllBytes(sourceFile));

		assertNotNull(sourceCode);
		assertEquals(code, sourceCode);
	}
	
}
