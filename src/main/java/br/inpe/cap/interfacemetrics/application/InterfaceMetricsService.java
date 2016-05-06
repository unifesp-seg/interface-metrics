package br.inpe.cap.interfacemetrics.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.inpe.cap.interfacemetrics.interfaces.daemon.ExecutionType;

public class InterfaceMetricsService {

	private InterfaceMetricRepository repository;
	private InterfaceMetricParamsRepository paramsRepository;
	private boolean mock = false;
	
	//Controll
	private int total;
	private int totalPartial;
	private int i;
	private long duraction;
	private long totalduraction;
	private long timestamp; 

	public InterfaceMetricsService() {
		repository = new InterfaceMetricRepository();
		paramsRepository = new InterfaceMetricParamsRepository();
	}

	public InterfaceMetricsService(boolean mock) {
		repository = new InterfaceMetricRepository(mock);
		paramsRepository = new InterfaceMetricParamsRepository(mock);
		this.mock = mock;
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
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(interfaceMetric, this.mock);
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
