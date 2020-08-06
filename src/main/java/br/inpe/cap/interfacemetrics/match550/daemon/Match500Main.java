package br.inpe.cap.interfacemetrics.match550.daemon;

import br.inpe.cap.interfacemetrics.match550.application.Match550EngService;
import br.inpe.cap.interfacemetrics.match550.application.Match550NEService;
import br.inpe.cap.interfacemetrics.match550.application.Match550Service;
import br.unifesp.ict.seg.geniesearchapi.infrastructure.util.GenieSearchAPIConfig;

public class Match500Main {

	public static void main(String[] args) throws Exception {
		GenieSearchAPIConfig.loadProperties();
		
		int key = 3;

		switch (key) {
		case 1: //1.427
			new Match550NEService().generateMethodSourceCodeFiles();
			break;
		case 2: //14.699
			new Match550EngService().generateMethodSourceCodeFiles();
			break;
		case 3: //649.299
			new Match550Service().generateMethodMatchSourceCodeFiles();
			break;
		}
	}
}
