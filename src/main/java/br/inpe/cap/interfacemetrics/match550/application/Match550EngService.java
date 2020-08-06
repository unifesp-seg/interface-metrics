package br.inpe.cap.interfacemetrics.match550.application;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import br.inpe.cap.interfacemetrics.match550.infrastructure.Match550Repository;
import br.inpe.cap.interfacemetrics.match550.util.MatchProperties;
import br.unifesp.ict.seg.geniesearchapi.domain.GenieMethod;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.GenieMethodRepository;

public class Match550EngService {

	private Match550Repository match550Repository = new Match550Repository();
	private GenieMethodRepository genieMethodRepository = new GenieMethodRepository();
	
	public void generateMethodSourceCodeFiles() throws Exception {
		List<Long> entityIds = match550Repository.findAllEngEntityIds();
		
		for(Long entityId : entityIds) {
			GenieMethod genieMethod = genieMethodRepository.findByEntityId(entityId);
			this.saveMethodSourceCode(genieMethod);
		}
	}

	void saveMethodSourceCode(GenieMethod genieMethod) throws Exception {
		Path sourceFile = Paths.get(MatchProperties.getMatch550EngSourcePath().toString(), genieMethod.getEntityId() + MatchProperties.SOURCE_FILE_EXTENSION);
		Files.write(sourceFile, genieMethod.getSourceCode().getBytes());
	}
	
}
