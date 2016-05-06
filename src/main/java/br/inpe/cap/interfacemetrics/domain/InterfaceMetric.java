package br.inpe.cap.interfacemetrics.domain;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;

import br.unifesp.ppgcc.sourcereraqe.infrastructure.JavaTermExtractor;
import br.unifesp.ppgcc.sourcereraqe.infrastructure.QueryTerm;

public class InterfaceMetric {

	private Long id;
	private String projectType;
	private Long projectId;
	private String projectName;
	private String entityType;
	private Long entityId;
	private String modifiers;
	private String fqn;
	private String params;
	private String returnType;
	private String relationType;
	private boolean processed;
	private boolean processedParams;
	
	private int totalParams;
	private int totalWordsMethod;
	private int totalWordsClass;
	private boolean onlyPrimitiveTypes;
	private boolean isStatic;
	private boolean hasTypeSamePackage;

	private int p0_c0_w0_t0;
	private int p0_c0_w0_t1;
	private int p0_c0_w1_t0;
	private int p0_c0_w1_t1;
	private int p0_c1_w0_t0;
	private int p0_c1_w0_t1;
	private int p0_c1_w1_t0;
	private int p0_c1_w1_t1;
	private int p1_c0_w0_t0;
	private int p1_c0_w0_t1;
	private int p1_c0_w1_t0;
	private int p1_c0_w1_t1;
	private int p1_c1_w0_t0;
	private int p1_c1_w0_t1;
	private int p1_c1_w1_t0;
	private int p1_c1_w1_t1;
	
	private List<QueryTerm> expandedParams = new ArrayList<QueryTerm>();
	
	public InterfaceMetric() {
	}
	
	public InterfaceMetric(long id) {
		this.id = id;
	}

	public InterfaceMetric(ResultSet rs) throws Exception {
		id = rs.getLong("id");
		projectType = rs.getString("project_type");
		projectId = rs.getLong("project_id");
		projectName = rs.getString("project_name");
		entityType = rs.getString("entity_type");
		entityId = rs.getLong("entity_id");
		modifiers = rs.getString("modifiers");
		fqn = rs.getString("fqn");
		params = rs.getString("params");
		returnType = rs.getString("return_type");
		relationType = rs.getString("relation_type");
		processed = rs.getInt("processed") == 1 ? true : false;
		processedParams = rs.getInt("processed_params") == 1 ? true : false;

		totalParams = rs.getInt("total_params");
		totalWordsMethod = rs.getInt("total_words_method");
		totalWordsClass = rs.getInt("total_words_class");
		onlyPrimitiveTypes = rs.getInt("only_primitive_types") == 1 ? true : false;
		isStatic = rs.getInt("is_static") == 1 ? true : false;
		hasTypeSamePackage = rs.getInt("has_type_same_package") == 1 ? true : false;

		p0_c0_w0_t0 = rs.getInt("p0_c0_w0_t0");
		p0_c0_w0_t1 = rs.getInt("p0_c0_w0_t1");
		p0_c0_w1_t0 = rs.getInt("p0_c0_w1_t0");
		p0_c0_w1_t1 = rs.getInt("p0_c0_w1_t1");
		p0_c1_w0_t0 = rs.getInt("p0_c1_w0_t0");
		p0_c1_w0_t1 = rs.getInt("p0_c1_w0_t1");
		p0_c1_w1_t0 = rs.getInt("p0_c1_w1_t0");
		p0_c1_w1_t1 = rs.getInt("p0_c1_w1_t1");
		p1_c0_w0_t0 = rs.getInt("p1_c0_w0_t0");
		p1_c0_w0_t1 = rs.getInt("p1_c0_w0_t1");
		p1_c0_w1_t0 = rs.getInt("p1_c0_w1_t0");
		p1_c0_w1_t1 = rs.getInt("p1_c0_w1_t1");
		p1_c1_w0_t0 = rs.getInt("p1_c1_w0_t0");
		p1_c1_w0_t1 = rs.getInt("p1_c1_w0_t1");
		p1_c1_w1_t0 = rs.getInt("p1_c1_w1_t0");
		p1_c1_w1_t1 = rs.getInt("p1_c1_w1_t1");
		
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

	public String getPackage() {
		String packageName = StringUtils.substringBeforeLast(fqn, ".");
		packageName = StringUtils.substringBeforeLast(packageName, ".");
		
		return packageName;
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
		p = StringUtils.replace(p," ","");
		String[] names = StringUtils.split(p,",");
		
		//Fix Generic situation. i.e.: Map<java.lang.String, <?>, Path>, Map<A, B, C, D, <E>>
		List<String> aux = new ArrayList<String>();
		for (int i = 0; i < names.length; i++) {
			String n = names[i];
			while(StringUtils.countMatches(n, "<") != StringUtils.countMatches(n, ">")){
				if(i+1 == names.length)
					break;
				n += ","+names[++i];
			}
			aux.add(n);
		}
		
		String[] paramsNames = new String[aux.size()];
		paramsNames = aux.toArray(paramsNames);
		return paramsNames;
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

	public String getClassName(){
		String className = StringUtils.substringBeforeLast(fqn, ".");
		return StringUtils.substringAfterLast(className, ".");
	}

	private String getFQNClassName(){
		return StringUtils.substringBeforeLast(fqn, ".");
	}

	public Integer getOccurrencesTotal(OccurrencesCombination combination) {
		boolean differentPackage = combination.isDifferentPackage();
		boolean ignoreClass = combination.isIgnoreClass();
		boolean expandMethodName = combination.isExpandMethodName();
		boolean expandTypes = combination.isExpandTypes();

		if (!differentPackage && !ignoreClass && !expandMethodName && !expandTypes) return p0_c0_w0_t0;
		if (!differentPackage && !ignoreClass && !expandMethodName &&  expandTypes) return p0_c0_w0_t1;
		if (!differentPackage && !ignoreClass &&  expandMethodName && !expandTypes) return p0_c0_w1_t0;
		if (!differentPackage && !ignoreClass &&  expandMethodName &&  expandTypes) return p0_c0_w1_t1;
		if (!differentPackage &&  ignoreClass && !expandMethodName && !expandTypes) return p0_c1_w0_t0;
		if (!differentPackage &&  ignoreClass && !expandMethodName &&  expandTypes) return p0_c1_w0_t1;
		if (!differentPackage &&  ignoreClass &&  expandMethodName && !expandTypes) return p0_c1_w1_t0;
		if (!differentPackage &&  ignoreClass &&  expandMethodName &&  expandTypes) return p0_c1_w1_t1;
		if ( differentPackage && !ignoreClass && !expandMethodName && !expandTypes) return p1_c0_w0_t0;
		if ( differentPackage && !ignoreClass && !expandMethodName &&  expandTypes) return p1_c0_w0_t1;
		if ( differentPackage && !ignoreClass &&  expandMethodName && !expandTypes) return p1_c0_w1_t0;
		if ( differentPackage && !ignoreClass &&  expandMethodName &&  expandTypes) return p1_c0_w1_t1;
		if ( differentPackage &&  ignoreClass && !expandMethodName && !expandTypes) return p1_c1_w0_t0;
		if ( differentPackage &&  ignoreClass && !expandMethodName &&  expandTypes) return p1_c1_w0_t1;
		if ( differentPackage &&  ignoreClass &&  expandMethodName && !expandTypes) return p1_c1_w1_t0;
		if ( differentPackage &&  ignoreClass &&  expandMethodName &&  expandTypes) return p1_c1_w1_t1;

		return null;
	}

	public void setOccurrencesTotal(OccurrencesCombination combination, int total) {
		boolean differentPackage = combination.isDifferentPackage();
		boolean ignoreClass = combination.isIgnoreClass();
		boolean expandMethodName = combination.isExpandMethodName();
		boolean expandTypes = combination.isExpandTypes();
		
		     if (!differentPackage && !ignoreClass && !expandMethodName && !expandTypes) p0_c0_w0_t0 = total;
		else if (!differentPackage && !ignoreClass && !expandMethodName &&  expandTypes) p0_c0_w0_t1 = total;
		else if (!differentPackage && !ignoreClass &&  expandMethodName && !expandTypes) p0_c0_w1_t0 = total;
		else if (!differentPackage && !ignoreClass &&  expandMethodName &&  expandTypes) p0_c0_w1_t1 = total;
		else if (!differentPackage &&  ignoreClass && !expandMethodName && !expandTypes) p0_c1_w0_t0 = total;
		else if (!differentPackage &&  ignoreClass && !expandMethodName &&  expandTypes) p0_c1_w0_t1 = total;
		else if (!differentPackage &&  ignoreClass &&  expandMethodName && !expandTypes) p0_c1_w1_t0 = total;
		else if (!differentPackage &&  ignoreClass &&  expandMethodName &&  expandTypes) p0_c1_w1_t1 = total;
		else if ( differentPackage && !ignoreClass && !expandMethodName && !expandTypes) p1_c0_w0_t0 = total;
		else if ( differentPackage && !ignoreClass && !expandMethodName &&  expandTypes) p1_c0_w0_t1 = total;
		else if ( differentPackage && !ignoreClass &&  expandMethodName && !expandTypes) p1_c0_w1_t0 = total;
		else if ( differentPackage && !ignoreClass &&  expandMethodName &&  expandTypes) p1_c0_w1_t1 = total;
		else if ( differentPackage &&  ignoreClass && !expandMethodName && !expandTypes) p1_c1_w0_t0 = total;
		else if ( differentPackage &&  ignoreClass && !expandMethodName &&  expandTypes) p1_c1_w0_t1 = total;
		else if ( differentPackage &&  ignoreClass &&  expandMethodName && !expandTypes)  p1_c1_w1_t0 = total;
		else if ( differentPackage &&  ignoreClass &&  expandMethodName &&  expandTypes) p1_c1_w1_t1 = total;
	}
	
	public boolean isSameParams(String[] paramsNames) {
		return this.isSameParams(paramsNames, false);
	}

	public boolean isSameParams(String[] paramsNames, boolean isParamsOrder) {
		if (this.getParamsNames().length != paramsNames.length)
			return false;

		if(isParamsOrder){
			for(int i = 0; i < this.getParamsNames().length; i++)
				if(!this.getParamsNames()[i].equals(paramsNames[i]))
					return false;
			return true;
		}else{
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(paramsNames));
			Stack<String> stack = new Stack<String>();
			stack.addAll(Arrays.asList(this.getParamsNames()));

			while(!stack.isEmpty()){
				String param = stack.pop();
				int i = list.indexOf(param);
				if(i >= 0 )
					list.remove(i);
			}
			
			return list.isEmpty();
		}
	}
	
	public boolean isSameExpandedParams(String[] paramsNames) {
		return this.isSameExpandedParams(paramsNames, false);
	}

	public boolean isSameExpandedParams(String[] paramsNames, boolean isParamsOrder) {

		if (this.getExpandedParams().size() != paramsNames.length)
			return false;

		if (isParamsOrder) {
			for (int i = 0; i < this.getExpandedParams().size(); i++) {
				boolean match = false;
				for (String term : this.getExpandedParams().get(i).getExpandedTerms()) {
					if (term.equals(paramsNames[i])){
						match = true;
						break;
					}
				}
				if (!match)
					return false;
			}
			return true;
		}else{
			ArrayList<String> list = new ArrayList<String>(Arrays.asList(paramsNames));
			Stack<QueryTerm> stack = new Stack<QueryTerm>();
			stack.addAll(this.getExpandedParams());

			while(!stack.isEmpty()){
				QueryTerm expandedParam = stack.pop();
				for(String term : expandedParam.getExpandedTerms()){
					int i = list.indexOf(term);
					if(i >= 0 ){
						list.remove(i);
						break;
					}
				}
			}
			
			return list.isEmpty();
		}
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

	public boolean isProcessedParams() {
		return processedParams;
	}

	public void setProcessedParams(boolean processedParams) {
		this.processedParams = processedParams;
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

	public void setParams(String params) {
		this.params = params;
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

	public List<QueryTerm> getExpandedParams() {
		return expandedParams;
	}

	public void setExpandedParams(List<QueryTerm> expandedParams) {
		this.expandedParams = expandedParams;
	}

	public Long getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public Long getEntityId() {
		return entityId;
	}
	
}
