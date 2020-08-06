package br.inpe.cap.interfacemetrics.match550.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import br.inpe.cap.interfacemetrics.match550.infrastructure.Match550Repository;
import br.inpe.cap.interfacemetrics.match550.util.MatchProperties;

public class Match550Service {

	private Match550Repository match550Repository = new Match550Repository();

	public void generateMethodMatchSourceCodeFiles() throws Exception {
		List<Long[]> matchEntityIds = match550Repository.findAllMatchEntityIds();
		
		int i = 1;
		for(Long[] matchEntityId : matchEntityIds) {
			System.out.println("Criando " + i++ + "/" + matchEntityIds.size() + " - " + matchEntityId[0] + "-" + matchEntityId[1] + MatchProperties.SOURCE_FILE_EXTENSION);
			this.saveMethodSourceCode(matchEntityId[0], matchEntityId[1]);
		}
	}

	void saveMethodSourceCode(Long entityIdNe, Long entityIdEng) throws Exception {
		
		String sourceCodeNE = new String(Files.readAllBytes(Paths.get(MatchProperties.getMatch550NESourcePath().toString(), entityIdNe + MatchProperties.SOURCE_FILE_EXTENSION)));
		String sourceCodeEng = new String(Files.readAllBytes(Paths.get(MatchProperties.getMatch550EngSourcePath().toString(), entityIdEng + MatchProperties.SOURCE_FILE_EXTENSION)));

		String matchSourceCode = "";
		matchSourceCode += "***************************************** Not Engineering\n";
		matchSourceCode += "SELECT * FROM interface_metrics_550_match_instance where ne_entity_id = " + entityIdNe + ";\n";
		matchSourceCode += "*******************************************************************************\n";
		matchSourceCode += sourceCodeNE;
		matchSourceCode += "\n\n\n";
		matchSourceCode += "***************************************** Engineering\n";
		matchSourceCode += "SELECT * FROM interface_metrics_550_match_instance where eng_entity_id = " + entityIdEng + ";\n";
		matchSourceCode += "*******************************************************************************\n";
		matchSourceCode += sourceCodeEng;
		
		Path sourceFile = Paths.get(MatchProperties.getMatch550SourcePath().toString(), entityIdNe + "-" + entityIdEng + MatchProperties.SOURCE_FILE_EXTENSION);
		Files.write(sourceFile, matchSourceCode.getBytes());
	}
	
}
