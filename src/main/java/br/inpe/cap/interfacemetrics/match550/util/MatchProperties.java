package br.inpe.cap.interfacemetrics.match550.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class MatchProperties {

	private static Properties properties;

	public static final String SOURCE_FILE_EXTENSION = ".java";
	
	private MatchProperties() {
	}

	static {
		properties = new Properties();
		URL url = ClassLoader.getSystemResource("config.properties");
		try {
	        properties.load(url.openStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getMatch550DatabaseUrl() throws Exception {
		return properties.getProperty("match550-database-url");
	}

	public static Path getMatch550NESourcePath() throws Exception {
		return Paths.get(properties.getProperty("match550-source-path"), "interface_metrics_a_550_ne_instance_src");
	}
	
	public static Path getMatch550EngSourcePath() throws Exception {
		return Paths.get(properties.getProperty("match550-source-path"), "interface_metrics_b_550_instance_src");
	}
	
	public static Path getMatch550SourcePath() throws Exception {
		return Paths.get(properties.getProperty("match550-source-path"), "interface_metrics_550_match_instance_src");
	}
	
}