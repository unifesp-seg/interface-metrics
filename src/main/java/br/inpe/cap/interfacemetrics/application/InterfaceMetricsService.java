package br.inpe.cap.interfacemetrics.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricPairRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;
import br.inpe.cap.interfacemetrics.interfaces.daemon.ExecutionType;

public class InterfaceMetricsService {

	private InterfaceMetricRepository repository;
	private InterfaceMetricPairRepository pairRepository;
	private InterfaceMetricParamsRepository paramsRepository;
	private RepositoryType repositoryType;
	
	//Controll
	private int total;
	private int totalPartial;
	private int i;
	private long duraction;
	private long totalduraction;
	private long timestamp; 

	public InterfaceMetricsService(RepositoryType repositoryType) {
		repository = new InterfaceMetricRepository(repositoryType);
		pairRepository = new InterfaceMetricPairRepository(repositoryType);
		paramsRepository = new InterfaceMetricParamsRepository(repositoryType);
		this.repositoryType = repositoryType;
	}

	public void execute(boolean dbPrepared, ExecutionType executionType) throws Exception {

		this.setupControllVariables();
		
		// Prepare DB
		if (!dbPrepared) {
			this.printPreparingDB();
			this.clearProcessing(executionType);
			return;
		}
		
		//Print
		if(executionType.isInterfaceMetrics())
			System.out.println("\n\n3. ExecutionType.INTERFACE_METRICS\n");
		else
			System.out.println("\n\n2. ExecutionType.PARAMS\n");

		total = this.getNotProcessedTotal(executionType);
		totalPartial = 0;
		this.printTotalHeader(total);
		
		List<InterfaceMetric> list = null;
		do {
			list = this.getNotProcessedList(executionType);

			//Print
			totalPartial += list.size();
			this.printPartialHeader();

			for (InterfaceMetric interfaceMetric : list) {
				this.process(interfaceMetric, executionType);

				//Print
				this.printRecord();
			}
		} while (!list.isEmpty()); 

		//Print
		this.printFinish();
	}

	public void processMethodsInfo() throws Exception {

		this.setupControllVariables();
		
		//Print
		System.out.println("\n\n1. Process only method info\n");

		total = repository.countAllNotProccessedMethodsInfo();

		totalPartial = 0;
		this.printTotalHeader(total);
		
		List<InterfaceMetric> list = null;
		do {
			list = repository.findAllNotProcessedMethodsInfo();

			//Print
			totalPartial += list.size();
			this.printPartialHeader();

			for (InterfaceMetric interfaceMetric : list) {
				interfaceMetric.processMethod();
				repository.updateProcessedMethodInfo(interfaceMetric);

				//Print
				this.printRecord();
			}
		} while (!list.isEmpty()); 

		//Print
		this.printFinish();
	}

	private void setupControllVariables() {
		this.total = 0;
		this.totalPartial = 0;
		this.i = 1;
		this.duraction = 0;
		this.totalduraction = 0;
		this.timestamp = System.currentTimeMillis(); 
	}

	private int getNotProcessedTotal(ExecutionType executionType) throws Exception {
		int total = 0;
		if(executionType.isInterfaceMetrics()){
			total = repository.countAllNotProccessed();
		} else if(executionType.isParams()){
			total = repository.countAllNotProccessedParams();
		}
		return total;
	}

	private List<InterfaceMetric> getNotProcessedList(ExecutionType executionType) throws Exception {
		List<InterfaceMetric> list = null;
		if(executionType.isInterfaceMetrics()){
			list = repository.findAllNotProcessed();
		} else if(executionType.isParams()){
			list = repository.findAllNotProcessedParam();
		}
		return list;
	}

	private void clearProcessing(ExecutionType executionType) throws Exception {
		if(executionType.isInterfaceMetrics()){
			pairRepository.deleteTable();
			repository.clearProcessing();
		} else if(executionType.isParams()){
			paramsRepository.deleteTable();
			repository.clearProcessedParams();
		}
	}

	private void process(InterfaceMetric interfaceMetric, ExecutionType executionType) throws Exception {
		if(executionType.isInterfaceMetrics()){
			this.processMethod(interfaceMetric);
		} else if(executionType.isParams()){
			paramsRepository.deleteParams(interfaceMetric);
			paramsRepository.insertParams(interfaceMetric);
			interfaceMetric.setProcessedParams(true);
			repository.updateProcessedParams(interfaceMetric);
		}
	}
	
	void processMethod(InterfaceMetric interfaceMetric) throws Exception {
		interfaceMetric.processMethod();
		
		//TODO Comments on special condition - Setup database 
		this.updateOccurrences(interfaceMetric);
		
		repository.updateProcessedMethod(interfaceMetric);
	}
	
	private void updateOccurrences(InterfaceMetric interfaceMetric) throws Exception {
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(interfaceMetric, this.repositoryType);
		helper.updateOccurrences();
	}

	private void printPreparingDB() {
		System.out.println("\nPreparing DB...");
	}
	
	private void printTotalHeader(int total) throws Exception{
		System.out.println("\nTotal não processados: " + total);
	}
	
	private void printPartialHeader(){
		System.out.println("\nParcela até: " + totalPartial + " de " + total + "...");
	}
	
	private void printRecord(){
		duraction = System.currentTimeMillis() - timestamp;
		timestamp = System.currentTimeMillis();
		totalduraction += duraction;
		long average = (totalduraction/i);

		long estimateTime = average * (total-i);
		String estimateTimeMsg = "";
		if(estimateTime / 1000 < 60)
			estimateTimeMsg = estimateTime / 1000 + " seg";
		else if( estimateTime / 1000 / 60 < 60)
			estimateTimeMsg = estimateTime / 1000 / 60 + " min";
		else
			estimateTimeMsg = estimateTime / 1000 / 60 / 60 + " horas";
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		String estimateEndMsg = format.format(new Date(timestamp + estimateTime));
		
		String recordMsg = "Processado: " + i + " de " + total + " (parcela até " + totalPartial + ")";
		String timeMsg = " média = " + average + ", tempo estimado = " + estimateTimeMsg + ", conclusão estimada = " + estimateEndMsg + "\r";
		
		System.out.print(recordMsg + timeMsg);
		i++;
	}
	
	private void printFinish() throws Exception{
		System.out.println("\nProcessamento concluído!");
	}
	

}
