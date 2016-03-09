package br.inpe.cap.interfacemetrics.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricParamsRepository;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricsService {

	private InterfaceMetricRepository repository;
	private InterfaceMetricParamsRepository paramsRepository;
	
	//Controll
	private int total = 0;
	private int totalPartial = 0;
	private int i = 1;
	private long duraction = 0;
	private long totalduraction = 0;
	private long timestamp = System.currentTimeMillis(); 

	public InterfaceMetricsService() {
		repository = new InterfaceMetricRepository();
		paramsRepository = new InterfaceMetricParamsRepository();
	}

	public InterfaceMetricsService(boolean mock) {
		repository = new InterfaceMetricRepository(mock);
		paramsRepository = new InterfaceMetricParamsRepository(mock);
	}

	public void execute(boolean dbPrepared, int execution) throws Exception {

		// Prepare DB
		if (!dbPrepared) {
			this.printPreparingDB();
			this.clearProcessing(execution);
			return;
		}
		
		//Print
		total = this.getNotProcessedTotal(execution);
		totalPartial = 0;
		this.printTotalHeader(total);
		
		List<InterfaceMetric> list = null;
		do {
			list = this.getNotProcessedList(execution);

			//Print
			totalPartial += list.size();
			this.printPartialHeader();

			for (InterfaceMetric interfaceMetric : list) {
				this.process(interfaceMetric, execution);

				//Print
				this.printRecord();
			}
		} while (!list.isEmpty()); 

		//Print
		this.printFinish();
	}

	private int getNotProcessedTotal(int execution) throws Exception {
		int total = 0;
		if(execution == 1){
			total = repository.countAllNotProccessed();
		} else if(execution == 2){
			total = repository.countAllNotProccessedParams();
		}
		return total;
	}

	private List<InterfaceMetric> getNotProcessedList(int execution) throws Exception {
		List<InterfaceMetric> list = null;
		if(execution == 1){
			list = repository.findAllNotProcessed();
		} else if(execution == 2){
			list = repository.findAllNotProcessedParam();
		}
		return list;
	}

	private void clearProcessing(int execution) throws Exception {
		if(execution == 1){
			repository.clearProcessing();
		} else if(execution == 2){
			paramsRepository.deleteTable();
			repository.clearProcessedParams();
		}
	}

	private void process(InterfaceMetric interfaceMetric, int execution) throws Exception {
		if(execution == 1){
			this.processMethod(interfaceMetric);
		} else if(execution == 2){
			paramsRepository.deleteParams(interfaceMetric);
			paramsRepository.insertParams(interfaceMetric);
			interfaceMetric.setProcessedParams(true);
			repository.updateProcessedParams(interfaceMetric);
		}
	}
	
	void processMethod(InterfaceMetric interfaceMetric) throws Exception {
		interfaceMetric.processMethod();
		
		this.updateOccurrences(interfaceMetric);
		
		repository.updateProcessedMethod(interfaceMetric);
	}
	
	private void updateOccurrences(InterfaceMetric interfaceMetric) throws Exception {
		InterfaceMetricOccurrencesHelper helper = new InterfaceMetricOccurrencesHelper(interfaceMetric);
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
