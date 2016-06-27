package br.inpe.cap.interfacemetrics.clone.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCHeader;
import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCPair;


public class SourcererCCService {

	public static final String SOURCE_CODE_PROCESSED_PATH = "/home/saini/otavio/sf100-src/0";
	public static final String SOURCE_CODE_PATH = "D:/dev/Sourcerer/repo-sf100/0";

	public static final String SOURCERER_CC_PATH = "D:/dev/Sourcerer/clone-detector";
	public static final String HEADER_FILE_PATH = SOURCERER_CC_PATH + "/headers.file";
	public static final String CLONE_PAIRS_10_FILE_PATH = SOURCERER_CC_PATH + "/tokensclones_index_WITH_FILTER 10.0.txt";
	public static final String CLONE_PAIRS_8_FILE_PATH = SOURCERER_CC_PATH + "/tokensclones_index_WITH_FILTER 8.0.txt";

	//private SourcererCCRepository repository = new SourcererCCRepository();

	private List<SourcererCCHeader> headers = new ArrayList<SourcererCCHeader>();
	private List<SourcererCCPair> pairs10 = new ArrayList<SourcererCCPair>();
	
	private int processIndex = 0;
	private PrintWriter pw = null;
	
	public void execute() throws Exception {

		// Headers
		this.headers = this.getHeaders();

		// Pair
		//this.pairs10 = this.getPairs10();
		
		this.processPairs10();

//		this.pairs10 = repository.findAllNotProcessed10();
//		this.loadPair10EntityId();
//		this.savePairs10();
	}

	public void openPrintWriter() throws Exception {
		pw = new PrintWriter(new FileWriter(SourcererCCService.CLONE_PAIRS_10_FILE_PATH + "2"));
	}
	
	private void processPairs10() throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(SourcererCCService.CLONE_PAIRS_10_FILE_PATH));

		String line;
		int i = 0;
		while ((line = br.readLine()) != null) {

			if(this.processIndex > i){
				i++;
				continue;
			}

			i++;
			
			SourcererCCPair pair = new SourcererCCPair(line);
			pair.loadEntityId(this.headers);
			String line2 = i + "," + pair.getHeaderIdA() + "," + pair.getHeaderIdB() + "," + pair.getEntityIdA() + "," + pair.getEntityIdB();
			pw.println(line2);

			this.processIndex = i;
			
			System.out.println(line2 + " processPairs10");
		}

		br.close();
		pw.close();
	}

	List<SourcererCCHeader> getHeaders() throws Exception {
		this.headers = new ArrayList<SourcererCCHeader>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.HEADER_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			SourcererCCHeader ccHeader = new SourcererCCHeader(line);
			this.headers.add(ccHeader);
		}
		reader.close();

		return headers;
	}
		
	List<SourcererCCPair> getPairs10() throws Exception {
		this.pairs10 = new ArrayList<SourcererCCPair>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.CLONE_PAIRS_10_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			SourcererCCPair pair = new SourcererCCPair(line);
			this.pairs10.add(pair);
		}
		reader.close();

		return pairs10;
	}
/*
	private void loadPair10EntityId() throws Exception {
		int i = 0;
		for(SourcererCCPair pair : this.pairs10){
			System.out.println(i++ + " de " + this.pairs10.size() + " loadPair10EntityId");
			pair.loadEntityId(headers);
		}
	}
	
	private void savePairs10() throws Exception {
		int i = 0;
		for(SourcererCCPair pair : this.pairs10){
			System.out.println(i++ + " de " + this.pairs10.size() + " savePairs10");
			try{
				repository.savePairs10(pair);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	*/
}
