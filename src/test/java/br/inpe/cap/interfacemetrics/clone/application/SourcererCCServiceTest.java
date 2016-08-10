package br.inpe.cap.interfacemetrics.clone.application;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCHeader;
import br.inpe.cap.interfacemetrics.clone.infrastructure.SourcererCCRepository;

public class SourcererCCServiceTest {

	private SourcererCCService service = new SourcererCCService();
	private SourcererCCRepository repository = new SourcererCCRepository();
	
	private List<SourcererCCHeader> headers = new ArrayList<SourcererCCHeader>();

	@Before
	public void listAll() throws Exception {
		
		//Headers
		service.loadHeaders();
		headers = service.getHeaders();
		assertEquals(155884, headers.size());
	}

	@Test
	public void headerFileExists() {
		assertTrue(new File(SourcererCCService.HEADER_FILE_PATH).exists());
	}

	@Test
	public void headerFileTotal() throws Exception {
		int total = 0;
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.HEADER_FILE_PATH));
		while ((reader.readLine()) != null) {
			total++;
		}
		reader.close();
		assertEquals(155884, total);
	}
	
	@Test
	public void headerMatchIndexWithId() throws Exception {
		int i = 0;
		for(SourcererCCHeader ccHeader : this.headers){
			if(i != ccHeader.getHeaderId())
				throw new ArrayIndexOutOfBoundsException(i + " different of " + ccHeader.getHeaderId());
			i++;
		}
	}
	
	@Test
	public void clonePairs10Exists() {
		assertTrue(new File(SourcererCCService.CLONE_PAIRS_10_FILE_PATH).exists());
	}
	
	@Test
	//TODO remover acesso ao arquivo 
	public void oldClonePairs10Total() throws Exception {
		int total = 0;
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.CLONE_PAIRS_10_FILE_PATH));
		while ((reader.readLine()) != null) {
		  total++;
		}
		reader.close();
		assertEquals(660894, total);
	}

	@Test
	public void clonePairs10Total() throws Exception {
		int total = repository.countAllNotProccessed10();
		assertEquals(171046, total);
	}

	@Test
	public void clonePairs8Exists() {
		assertTrue(new File(SourcererCCService.CLONE_PAIRS_8_FILE_PATH).exists());
	}
	
	@Test
	public void clonePairs8Total() throws Exception {
		int total = 0;
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.CLONE_PAIRS_8_FILE_PATH));
		while ((reader.readLine()) != null) {
		  total++;
		}
		reader.close();
		assertEquals(3436520, total);
	}
}
