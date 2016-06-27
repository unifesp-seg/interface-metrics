package br.inpe.cap.interfacemetrics.clone.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.inpe.cap.interfacemetrics.clone.application.SourcererCCService;
import br.inpe.cap.interfacemetrics.clone.infrastructure.SourcererCCRepository;


public class SourcererCCHeader {

	private String line;
	private Long headerId;
	private String sourceClassPath;
	private int lineIni;
	private int lineFin;

	private String methodLine;
	private String methodFQN;
	private String methodProjectNamePrefix;

	private Long entityId;
	
	private SourcererCCRepository repository = new SourcererCCRepository();

	public SourcererCCHeader(String line) {
		this.line = line;
		String[] parts = StringUtils.split(line, ',');
		this.headerId = new Long(parts[0]);
		this.sourceClassPath = parts[1];
		this.sourceClassPath = StringUtils.replace(sourceClassPath, SourcererCCService.SOURCE_CODE_PROCESSED_PATH, SourcererCCService.SOURCE_CODE_PATH);
		this.lineIni = new Integer(parts[2]);
		this.lineFin = new Integer(parts[3]);
		
		String pre = StringUtils.replace(parts[1],SourcererCCService.SOURCE_CODE_PROCESSED_PATH+"/","");
		pre = StringUtils.substring(pre, 0, StringUtils.indexOf(pre, "/"));
		if(pre.length() == 1)
			pre = "00" + pre;
		else if(pre.length() == 2)
			pre = "0" + pre;
		this.methodProjectNamePrefix = pre;
	}

	public void loadFromSourceCode() throws Exception {
		this.loadMethodLine();
		String strPackage = this.loadPackage();
		strPackage = "".equals(strPackage) ? "" : strPackage + ".";
		methodFQN = strPackage + StringUtils.replace(new File(this.sourceClassPath).getName(),".java","") + "." + this.getMethodName();
	}
	
	public void loadFromDB() throws Exception {
		this.entityId = repository.getEntityId(this);
	}

	private void loadMethodLine() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(this.sourceClassPath));
		int total = 0;
		String line;
		while ((line = reader.readLine()) != null) {
			total++;
			if (total == this.lineIni) {
				this.methodLine = StringUtils.trim(line);
				while(StringUtils.indexOf(line, ")") < 0){
					line = StringUtils.trim(reader.readLine());
					this.methodLine += line;
				}
				break;
			}
		}
		reader.close();
	}

	private String loadPackage() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(this.sourceClassPath));
		String line;
		while ((line = reader.readLine()) != null) {
			line = StringUtils.trim(line);
			if (line.startsWith("package")){
				reader.close();
				line = StringUtils.replace(line, "package", "");  
				line = StringUtils.substring(line, 0, StringUtils.indexOf(line, ';'));
				return StringUtils.trim(line);
			}
		}
		reader.close();
		
		return "";
	}

	public String getMethodName() {
		String name = methodLine;
		name = StringUtils.substring(name, 0, StringUtils.indexOf(name, "("));
		name = StringUtils.reverse(name);
		name = StringUtils.trim(name);
		name = StringUtils.substring(name, 0, StringUtils.indexOf(name, " "));
		name = StringUtils.reverse(name);
		return name;
	}

	public List<String> getMethodParams() {
		int i = StringUtils.indexOf(methodLine, "(");
		int f = StringUtils.indexOf(methodLine, ")");
		String params = StringUtils.substring(methodLine, i+1, f);
		String[] parts = StringUtils.split(params,',');
		
		List<String> args = new ArrayList<String>(); 
		for(String arg : parts){
			arg = StringUtils.trim(arg);
			arg = StringUtils.split(arg," ")[0];
			arg = StringUtils.trim(arg);
			args.add(arg);
		}
		
		return args;
	}
	
	public String getLine() {
		return line;
	}

	public Long getHeaderId() {
		return headerId;
	}

	public String getSourceClassPath() {
		return sourceClassPath;
	}

	public int getLineIni() {
		return lineIni;
	}

	public int getLineFin() {
		return lineFin;
	}

	public String getMethodLine() {
		return methodLine;
	}

	void setMethodLine(String methodLine) {
		this.methodLine = methodLine;
	}
	
	public String getMethodFQN() {
		return methodFQN;
	}

	public String getMethodProjectNamePrefix() {
		return methodProjectNamePrefix;
	}

	public Long getEntityId() {
		return entityId;
	}
}
