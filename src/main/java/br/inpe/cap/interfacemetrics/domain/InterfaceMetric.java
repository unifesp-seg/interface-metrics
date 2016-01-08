package br.inpe.cap.interfacemetrics.domain;

import java.sql.ResultSet;

import org.apache.commons.lang.StringUtils;

import br.unifesp.ppgcc.sourcereraqe.infrastructure.JavaTermExtractor;

public class InterfaceMetric {

	private Long id;
	private String projectType;
	private String entityType;
	private String modifiers;
	private String fqn;
	private String params;
	private String returnType;
	private String relationType;
	private boolean processed;
	
	private int totalParams;
	private int totalWordsMethod;
	private int totalWordsClass;
	private boolean onlyPrimitiveTypes;
	private boolean isStatic;
	private boolean hasTypeSamePackage;

	private int returnNameParams;
	private int returnName0params;
	private int return0nameParams;
	private int return0name0params;
	private int s0returnNameParams;
	private int s0returnName0params;
	private int s0return0nameParams;
	private int s0return0name0params;
	private int returnNameParamsDisorder;
	private int returnName0paramsDisorder;
	private int return0nameParamsDisorder;
	private int return0name0paramsDisorder;
	private int s0returnNameParamsDisorder;
	private int s0returnName0paramsDisorder;
	private int s0return0nameParamsDisorder;
	private int s0return0name0paramsDisorder;
	
	public InterfaceMetric() {
	}
	
	public InterfaceMetric(long id) {
		this.id = id;
	}

	public InterfaceMetric(ResultSet rs) throws Exception {
		id = rs.getLong("id");
		projectType = rs.getString("project_type");
		entityType = rs.getString("entity_type");
		modifiers = rs.getString("modifiers");
		fqn = rs.getString("fqn");
		params = rs.getString("params");
		returnType = rs.getString("return_type");
		relationType = rs.getString("relation_type");
		processed = rs.getInt("processed") == 1 ? true : false;

		totalParams = rs.getInt("total_params");
		totalWordsMethod = rs.getInt("total_words_method");
		totalWordsClass = rs.getInt("total_words_class");
		onlyPrimitiveTypes = rs.getInt("only_primitive_types") == 1 ? true : false;
		isStatic = rs.getInt("is_static") == 1 ? true : false;
		hasTypeSamePackage = rs.getInt("has_type_same_package") == 1 ? true : false;

		returnNameParams = rs.getInt("return_name_params");
		returnName0params = rs.getInt("return_name_0params");
		return0nameParams = rs.getInt("return_0name_params");
		return0name0params = rs.getInt("return_0name_0params");
		s0returnNameParams = rs.getInt("s0return_name_params");
		s0returnName0params = rs.getInt("s0return_name_0params");
		s0return0nameParams = rs.getInt("s0return_0name_params");
		s0return0name0params = rs.getInt("s0return_0name_0params");
		returnNameParamsDisorder = rs.getInt("return_name_params_disorder");
		returnName0paramsDisorder = rs.getInt("return_name_0params_disorder");
		return0nameParamsDisorder = rs.getInt("return_0name_params_disorder");
		return0name0paramsDisorder = rs.getInt("return_0name_0params_disorder");
		s0returnNameParamsDisorder = rs.getInt("s0return_name_params_disorder");
		s0returnName0paramsDisorder = rs.getInt("s0return_name_0params_disorder");
		s0return0nameParamsDisorder = rs.getInt("s0return_0name_params_disorder");
		s0return0name0paramsDisorder = rs.getInt("s0return_0name_0params_disorder");
		
		params = StringUtils.replace(params, "(", "");
		params = StringUtils.replace(params, ")", "");
	}
	
	//bussiness
	public void processMethod() {
		totalParams = getParamsNames().length;
		totalWordsMethod = StringUtils.split(JavaTermExtractor.getFQNTermsAsString(getMethodName()), " ").length;
		totalWordsClass = StringUtils.split(JavaTermExtractor.getFQNTermsAsString(getClassName()), " ").length;
		onlyPrimitiveTypes = checkOnlyPrimitiveTypes();
		isStatic = StringUtils.indexOf(modifiers, "STATIC") >= 0;
		hasTypeSamePackage = checkHasTypeSamePackage();
		
		processed = true;
	}
	
	private boolean checkHasTypeSamePackage(){
		String methodPackage = getClassPackage(getFQNClassName());

		for(String param : getParamsNames())
			if(methodPackage.equals(getClassPackage(param)))
				return true;
		
		return methodPackage.equals(getClassPackage(returnType));
	}

	private String getClassPackage(String fqnClass) {
		if(isPrimitive(fqnClass, false))
			return null;
		
		return StringUtils.substringBeforeLast(fqnClass, ".");
	}

	private boolean checkOnlyPrimitiveTypes(){
		for(String param : getParamsNames())
			if(!isPrimitive(param, true))
				return false;
		
		return isPrimitive(returnType, true);
	}
	
	public String[] getParamsNames(){
		String p = params;
		p = StringUtils.replace(p,"(","");
		p = StringUtils.replace(p,")","");
		return StringUtils.split(p,",");
	}
	
	public String[] getWordsMethod(){
		String[] wordsMethod = StringUtils.split(JavaTermExtractor.getFQNTermsAsString(getMethodName()), " ");
		return wordsMethod;
	}
	private boolean isPrimitive(String type, boolean includeString){
		
		type = StringUtils.replace(type,"[]", ""); 
		
		if("boolean".equals(type)) return true;
		if("char".equals(type)) return true;
		if("short".equals(type)) return true;
		if("byte".equals(type)) return true;
		if("int".equals(type)) return true;
		if("long".equals(type)) return true;
		if("float".equals(type)) return true;
		if("double".equals(type)) return true;

		if(includeString && "java.lang.String".equals(type)) return true;

		return false;
	}

	public String getMethodName(){
		return StringUtils.substringAfterLast(fqn, ".");
	}

	private String getClassName(){
		String className = StringUtils.substringBeforeLast(fqn, ".");
		return StringUtils.substringAfterLast(className, ".");
	}

	private String getFQNClassName(){
		return StringUtils.substringBeforeLast(fqn, ".");
	}

	public Integer getOccurrencesTotal(OccurrencesCombination combination) {
		boolean sameReturn = combination.isSameReturn();
		boolean sameMethodName = combination.isSameMethodName();
		boolean sameParams = combination.isSameParams();
		boolean disorder = combination.isDisorder();
		
		if (sameReturn && sameMethodName && sameParams && !disorder)
			return returnNameParams;
		if (sameReturn && sameMethodName && !sameParams && !disorder)
			return returnName0params;
		if (sameReturn && !sameMethodName && sameParams && !disorder)
			return return0nameParams;
		if (sameReturn && !sameMethodName && !sameParams && !disorder)
			return return0name0params;
		if (!sameReturn && sameMethodName && sameParams && !disorder)
			return s0returnNameParams;
		if (!sameReturn && sameMethodName && !sameParams && !disorder)
			return s0returnName0params;
		if (!sameReturn && !sameMethodName && sameParams && !disorder)
			return s0return0nameParams;
		if (!sameReturn && !sameMethodName && !sameParams && !disorder)
			return s0return0name0params;
		if (sameReturn && sameMethodName && sameParams && disorder)
			return returnNameParamsDisorder;
		if (sameReturn && sameMethodName && !sameParams && disorder)
			return returnName0paramsDisorder;
		if (sameReturn && !sameMethodName && sameParams && disorder)
			return return0nameParamsDisorder;
		if (sameReturn && !sameMethodName && !sameParams && disorder)
			return return0name0paramsDisorder;
		if (!sameReturn && sameMethodName && sameParams && disorder)
			return s0returnNameParamsDisorder;
		if (!sameReturn && sameMethodName && !sameParams && disorder)
			return s0returnName0paramsDisorder;
		if (!sameReturn && !sameMethodName && sameParams && disorder)
			return s0return0nameParamsDisorder;
		if (!sameReturn && !sameMethodName && !sameParams && disorder)
			return s0return0name0paramsDisorder;
		return null;
	}

	public void setOccurrencesTotal(OccurrencesCombination combination, int total) {
		boolean sameReturn = combination.isSameReturn();
		boolean sameMethodName = combination.isSameMethodName();
		boolean sameParams = combination.isSameParams();
		boolean disorder = combination.isDisorder();
		
		if (sameReturn && sameMethodName && sameParams && !disorder)
			returnNameParams = total;
		else if (sameReturn && sameMethodName && !sameParams && !disorder)
			returnName0params = total;
		else if (sameReturn && !sameMethodName && sameParams && !disorder)
			return0nameParams = total;
		else if (sameReturn && !sameMethodName && !sameParams && !disorder)
			return0name0params = total;
		else if (!sameReturn && sameMethodName && sameParams && !disorder)
			s0returnNameParams = total;
		else if (!sameReturn && sameMethodName && !sameParams && !disorder)
			s0returnName0params = total;
		else if (!sameReturn && !sameMethodName && sameParams && !disorder)
			s0return0nameParams = total;
		else if (!sameReturn && !sameMethodName && !sameParams && !disorder)
			s0return0name0params = total;
		else if (sameReturn && sameMethodName && sameParams && disorder)
			returnNameParamsDisorder = total;
		else if (sameReturn && sameMethodName && !sameParams && disorder)
			returnName0paramsDisorder = total;
		else if (sameReturn && !sameMethodName && sameParams && disorder)
			return0nameParamsDisorder = total;
		else if (sameReturn && !sameMethodName && !sameParams && disorder)
			return0name0paramsDisorder = total;
		else if (!sameReturn && sameMethodName && sameParams && disorder)
			s0returnNameParamsDisorder = total;
		else if (!sameReturn && sameMethodName && !sameParams && disorder)
			s0returnName0paramsDisorder = total;
		else if (!sameReturn && !sameMethodName && sameParams && disorder)
			s0return0nameParamsDisorder = total;
		else if (!sameReturn && !sameMethodName && !sameParams && disorder)
			s0return0name0paramsDisorder = total;
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof InterfaceMetric && this.id.longValue() == ((InterfaceMetric)o).getId());
	}

	//accessors
	public boolean isProcessed() {
		return processed;
	}

	public void setProcessed(boolean processed) {
		this.processed = processed;
	}

	public Long getId() {
		return id;
	}

	public String getProjectType() {
		return projectType;
	}

	public String getEntityType() {
		return entityType;
	}

	public String getModifiers() {
		return modifiers;
	}

	public String getFqn() {
		return fqn;
	}

	public String getParams() {
		return params;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getRelationType() {
		return relationType;
	}

	public int getTotalParams() {
		return totalParams;
	}

	public int getTotalWordsMethod() {
		return totalWordsMethod;
	}

	public int getTotalWordsClass() {
		return totalWordsClass;
	}

	public boolean isOnlyPrimitiveTypes() {
		return onlyPrimitiveTypes;
	}

	public boolean isStatic() {
		return isStatic;
	}

	public boolean isHasTypeSamePackage() {
		return hasTypeSamePackage;
	}
}
