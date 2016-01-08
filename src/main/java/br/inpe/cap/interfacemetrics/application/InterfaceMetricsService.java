package br.inpe.cap.interfacemetrics.application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;

public class InterfaceMetricsService {

	private InterfaceMetricRepository repository;
	
	//Controll
	private int total = 0;
	private int totalPartial = 0;
	private int i = 1;
	private long duraction = 0;
	private long totalduraction = 0;
	private long timestamp = System.currentTimeMillis(); 

	public InterfaceMetricsService() {
		repository =  new InterfaceMetricRepository();
	}

	public InterfaceMetricsService(boolean mock) {
		repository =  new InterfaceMetricRepository(mock);
	}

	public void execute() throws Exception {

		//Prepare DB
		//repository.clearProcessing();
		
		//Print
		this.printTotalHeader();
		
		List<InterfaceMetric> list = null;
		do {
			list = repository.findAllNotProcessed();

			//Print
			totalPartial += list.size();
			this.printPartialHeader();

			for (InterfaceMetric interfaceMetric : list) {
				this.processMethod(interfaceMetric);

				//Print
				this.printRecord();
			}
		} while (!list.isEmpty()); 

		//Print
		this.printFinish();
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

	private void printTotalHeader() throws Exception{
		total = repository.countAllNotProccessed();
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
