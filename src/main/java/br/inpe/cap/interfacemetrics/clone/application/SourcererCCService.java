package br.inpe.cap.interfacemetrics.clone.application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import br.inpe.cap.interfacemetrics.clone.domain.InterfaceMetricsPairsClone;
import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCHeader;
import br.inpe.cap.interfacemetrics.clone.domain.SourcererCCPair;


public class SourcererCCService {

	public static final String SOURCE_CODE_PROCESSED_PATH = "/home/saini/otavio/sf100-src/0";
	public static final String SOURCE_CODE_PATH = "D:/dev/Sourcerer/repo-sf100/0";

	public static final String SOURCERER_CC_PATH = "D:/dev/Sourcerer/clone-detector";
	public static final String HEADER_FILE_PATH = SOURCERER_CC_PATH + "/headers.file";
	public static final String CLONE_PAIRS_10_FILE_PATH = SOURCERER_CC_PATH + "/tokensclones_index_WITH_FILTER 10.0.txt";
	public static final String CLONE_PAIRS_8_FILE_PATH = SOURCERER_CC_PATH + "/tokensclones_index_WITH_FILTER 8.0.txt";
	public static final String INTERFACE_METRICS_PAIRS_FILE_PATH = SOURCERER_CC_PATH + "/interface_metrics_pairs.txt";

	//private SourcererCCRepository repository = new SourcererCCRepository();

	private List<SourcererCCHeader> headers = new ArrayList<SourcererCCHeader>();
	private List<SourcererCCPair> pairs10 = new ArrayList<SourcererCCPair>();
	private List<InterfaceMetricsPairsClone> interfaceMetricsPairsClone = new ArrayList<InterfaceMetricsPairsClone>();
	
	private int processIndex = 0;
	private PrintWriter pw = null;
	
	public void execute() throws Exception {

		// Headers
		this.loadHeaders();

		this.processHeaders();

//		this.pairs10 = repository.findAllNotProcessed10();
//		this.loadPair10EntityId();
//		this.savePairs10();
	}

	public void openPrintWriter() throws Exception {
		pw = new PrintWriter(new FileWriter(SourcererCCService.HEADER_FILE_PATH + "2"));
	}
	
	private void processHeaders() throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(SourcererCCService.HEADER_FILE_PATH));

		String line;
		int i = 0;
		while ((line = br.readLine()) != null) {

			if(this.processIndex > i){
				i++;
				continue;
			}

			i++;
			
			SourcererCCHeader header = new SourcererCCHeader(line);
			header.loadFromSourceCode();
			header.loadFromDB();
			String line2 = header.getLine() + "," + header.getEntityId();
			pw.println(line2);

			this.processIndex = i;
			
			System.out.println(line2);
		}

		br.close();
		pw.close();
	}
	
	private void processInterfaceMetricsPairsClone() throws Exception {
		
		int clones = 0;
		int index = 1;
		for(InterfaceMetricsPairsClone imPair :  this.getInterfaceMetricsPairsClone()){
			Long headerIdA = this.getHeaderIdFromEntityId(imPair.getEntityIdA()); 
			Long headerIdB = this.getHeaderIdFromEntityId(imPair.getEntityIdB());
			
			boolean isClone = this.match10(headerIdA, headerIdB);
			imPair.setClone(isClone);
			
			if(isClone)
				clones++;

			String headers = " [headerIdA: " + headerIdA + ", headerIdB: " + headerIdB + "]";
			System.out.println("index: " + index++ + headers +  " (clones " + clones + ")");
		}
		System.out.println("Clones 100%: " + clones);
	}

	private boolean match10(Long headerIdA, Long headerIdB) {
		if(headerIdA == null || headerIdB == null)
			return false;

		for(SourcererCCPair pair : this.pairs10){

			boolean a = headerIdA.longValue() == pair.getHeaderIdA().longValue();
			boolean b = headerIdB.longValue() == pair.getHeaderIdB().longValue();
			if(a && b)
				return true;

			a = headerIdA.longValue() == pair.getHeaderIdB().longValue();
			b = headerIdB.longValue() == pair.getHeaderIdA().longValue();
			if(a && b)
				return true;
		}
		return false;
	}

	private Long getHeaderIdFromEntityId(Long entityId) {
		for(SourcererCCHeader header : this.headers){
			if(entityId.longValue() == header.getEntityId().longValue())
				return header.getHeaderId();
		}
		return null;
	}

	private void loadHeaders() throws Exception {
		this.headers = new ArrayList<SourcererCCHeader>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.HEADER_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			SourcererCCHeader ccHeader = new SourcererCCHeader(line);
			this.headers.add(ccHeader);
		}
		reader.close();
	}
		
	private void loadPairs10() throws Exception {
		this.pairs10 = new ArrayList<SourcererCCPair>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.CLONE_PAIRS_10_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			SourcererCCPair pair = new SourcererCCPair(line);
			this.pairs10.add(pair);
		}
		reader.close();
	}

	private void loadInterfaceMetricsPairsClone() throws Exception {
		this.interfaceMetricsPairsClone = new ArrayList<InterfaceMetricsPairsClone>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.INTERFACE_METRICS_PAIRS_FILE_PATH));
		String line;
		while ((line = reader.readLine()) != null) {
			InterfaceMetricsPairsClone interfaceMetricsPairsClone = new InterfaceMetricsPairsClone(line);
			this.interfaceMetricsPairsClone.add(interfaceMetricsPairsClone);
		}
		reader.close();
	}
		
	private void loadProcessedHeaders() throws Exception {
		this.headers = new ArrayList<SourcererCCHeader>();
		
		BufferedReader reader = new BufferedReader(new FileReader(SourcererCCService.HEADER_FILE_PATH+"2"));
		String line;
		while ((line = reader.readLine()) != null) {
			SourcererCCHeader ccHeader = new SourcererCCHeader(line);
			this.headers.add(ccHeader);
		}
		reader.close();
	}
		
	public void executeIdsMatch() throws Exception {
		
		this.loadProcessedHeaders();
		this.loadPairs10();
		this.loadInterfaceMetricsPairsClone();

		this.processInterfaceMetricsPairsClone();
	}

	public List<SourcererCCHeader> getHeaders() {
		return headers;
	}

	public List<SourcererCCPair> getPairs10() {
		return pairs10;
	}

	public List<InterfaceMetricsPairsClone> getInterfaceMetricsPairsClone() {
		return interfaceMetricsPairsClone;
	}

	
}
