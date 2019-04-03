package br.inpe.cap.interfacemetrics.interfaces.daemon;

import br.inpe.cap.interfacemetrics.application.InterfaceMetricsService;
import br.inpe.cap.interfacemetrics.infrastructure.RepositoryType;
import br.inpe.cap.interfacemetrics.infrastructure.util.LogUtils;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;

public class InterfaceMetricsMain {

	private static long startTime = -1;
	
	public static void main(String[] args) {
		setStartTime();
		
		LogUtils.getLogger().info("");
		LogUtils.getLogger().info("Aplicativo iniciado");
		LogUtils.getLogger().info("");
		
		try {

			LogUtils.getLogger().info("Service");
			InterfaceMetricsService service = new InterfaceMetricsService(RepositoryType.REAL);

			boolean communicationsException = false;
			do{
				try{

					// 1. Process only method info
					service.processMethodsInfo();

					// 2. ExecutionType.PARAMS
					service.execute(true, ExecutionType.PARAMS);

					// 3. ExecutionType.INTERFACE_METRICS
					service.execute(true, ExecutionType.INTERFACE_METRICS);

					communicationsException = false;
				} catch (CommunicationsException ex){
					Thread.sleep(5000);
					communicationsException = true;
				}
			}while(communicationsException);

		} catch (Exception e) {
			LogUtils.getLogger().error(e);
			e.printStackTrace();
		}

		LogUtils.getLogger().info("");
		LogUtils.getLogger().info("Aplicativo finalizado. Tempo de execucao: " + getDuractionTime());
		LogUtils.getLogger().info("");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void setStartTime(){
		startTime = System.currentTimeMillis();
	}

	private static String getDuractionTime(){
		long duraction = System.currentTimeMillis() - startTime;
		
		duraction /= 1000;
		if(duraction < 60)
			return duraction + " segundos.";
		
		duraction /= 60;
		return duraction + " minutos.";
	}
 }