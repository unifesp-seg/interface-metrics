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

	private int r0_n0_p0_0;
	private int r0_n0_p1_0;
	private int r0_n1_p0_0;
	private int r0_n1_p1_0;
	private int r1_n0_p0_0;
	private int r1_n0_p1_0;
	private int r1_n1_p0_0;
	private int r1_n1_p1_0;
	private int r0_n0_p0_1;
	private int r0_n0_p1_1;
	private int r0_n1_p0_1;
	private int r0_n1_p1_1;
	private int r1_n0_p0_1;
	private int r1_n0_p1_1;
	private int r1_n1_p0_1;
	private int r1_n1_p1_1;
	private int r0_xx_p0_1;
	private int r1_xx_p1_1;
	
	private List<QueryTerm> expandedParams = new ArrayList<QueryTerm>();
	
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

		r0_n0_p0_0 = rs.getInt("r0_n0_p0_0");
		r0_n0_p1_0 = rs.getInt("r0_n0_p1_0");
		r0_n1_p0_0 = rs.getInt("r0_n1_p0_0");
		r0_n1_p1_0 = rs.getInt("r0_n1_p1_0");
		r1_n0_p0_0 = rs.getInt("r1_n0_p0_0");
		r1_n0_p1_0 = rs.getInt("r1_n0_p1_0");
		r1_n1_p0_0 = rs.getInt("r1_n1_p0_0");
		r1_n1_p1_0 = rs.getInt("r1_n1_p1_0");
		r0_n0_p0_1 = rs.getInt("r0_n0_p0_1");
		r0_n0_p1_1 = rs.getInt("r0_n0_p1_1");
		r0_n1_p0_1 = rs.getInt("r0_n1_p0_1");
		r0_n1_p1_1 = rs.getInt("r0_n1_p1_1");
		r1_n0_p0_1 = rs.getInt("r1_n0_p0_1");
		r1_n0_p1_1 = rs.getInt("r1_n0_p1_1");
		r1_n1_p0_1 = rs.getInt("r1_n1_p0_1");
		r1_n1_p1_1 = rs.getInt("r1_n1_p1_1");
		r0_xx_p0_1 = rs.getInt("r0_xx_p0_1");
		r1_xx_p1_1 = rs.getInt("r1_xx_p1_1");

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
		boolean expandReturn = combination.isExpandReturn();
		boolean expandMethodName = combination.isExpandMethodName();
		boolean expandParams = combination.isExpandParams();
		boolean expandParamsOrder = combination.isExpandParamsOrder();
		boolean ignoreMethodNameOnSearch = combination.isIgnoreMethodNameOnSearch();
		
		if (ignoreMethodNameOnSearch)
			return (!expandReturn && !expandParams) ? r0_xx_p0_1 : r1_xx_p1_1;
		
		if (!expandReturn && !expandMethodName && !expandParams && !expandParamsOrder) return r0_n0_p0_0;
		if (!expandReturn && !expandMethodName &&  expandParams && !expandParamsOrder) return r0_n0_p1_0;
		if (!expandReturn &&  expandMethodName && !expandParams && !expandParamsOrder) return r0_n1_p0_0;
		if (!expandReturn &&  expandMethodName &&  expandParams && !expandParamsOrder) return r0_n1_p1_0;
		if ( expandReturn && !expandMethodName && !expandParams && !expandParamsOrder) return r1_n0_p0_0;
		if ( expandReturn && !expandMethodName &&  expandParams && !expandParamsOrder) return r1_n0_p1_0;
		if ( expandReturn &&  expandMethodName && !expandParams && !expandParamsOrder) return r1_n1_p0_0;
		if ( expandReturn &&  expandMethodName &&  expandParams && !expandParamsOrder) return r1_n1_p1_0;
		if (!expandReturn && !expandMethodName && !expandParams &&  expandParamsOrder) return r0_n0_p0_1;
		if (!expandReturn && !expandMethodName &&  expandParams &&  expandParamsOrder) return r0_n0_p1_1;
		if (!expandReturn &&  expandMethodName && !expandParams &&  expandParamsOrder) return r0_n1_p0_1;
		if (!expandReturn &&  expandMethodName &&  expandParams &&  expandParamsOrder) return r0_n1_p1_1;
		if ( expandReturn && !expandMethodName && !expandParams &&  expandParamsOrder) return r1_n0_p0_1;
		if ( expandReturn && !expandMethodName &&  expandParams &&  expandParamsOrder) return r1_n0_p1_1;
		if ( expandReturn &&  expandMethodName && !expandParams &&  expandParamsOrder) return r1_n1_p0_1;
		if ( expandReturn &&  expandMethodName &&  expandParams &&  expandParamsOrder) return r1_n1_p1_1;

		return null;
	}

	public void setOccurrencesTotal(OccurrencesCombination combination, int total) {
		boolean expandReturn = combination.isExpandReturn();
		boolean expandMethodName = combination.isExpandMethodName();
		boolean expandParams = combination.isExpandParams();
		boolean expandParamsOrder = combination.isExpandParamsOrder();
		boolean ignoreMethodNameOnSearch = combination.isIgnoreMethodNameOnSearch();
		
		     if ( ignoreMethodNameOnSearch && !expandReturn && !expandParams &&  expandParamsOrder) r0_xx_p0_1 = total;
		else if ( ignoreMethodNameOnSearch &&  expandReturn &&  expandParams &&  expandParamsOrder) r1_xx_p1_1 = total;
		
		else if (!expandReturn && !expandMethodName && !expandParams && !expandParamsOrder) r0_n0_p0_0 = total;
		else if (!expandReturn && !expandMethodName &&  expandParams && !expandParamsOrder) r0_n0_p1_0 = total;
		else if (!expandReturn &&  expandMethodName && !expandParams && !expandParamsOrder) r0_n1_p0_0 = total;
		else if (!expandReturn &&  expandMethodName &&  expandParams && !expandParamsOrder) r0_n1_p1_0 = total;
		else if ( expandReturn && !expandMethodName && !expandParams && !expandParamsOrder) r1_n0_p0_0 = total;
		else if ( expandReturn && !expandMethodName &&  expandParams && !expandParamsOrder) r1_n0_p1_0 = total;
		else if ( expandReturn &&  expandMethodName && !expandParams && !expandParamsOrder) r1_n1_p0_0 = total;
		else if ( expandReturn &&  expandMethodName &&  expandParams && !expandParamsOrder) r1_n1_p1_0 = total;
		else if (!expandReturn && !expandMethodName && !expandParams &&  expandParamsOrder) r0_n0_p0_1 = total;
		else if (!expandReturn && !expandMethodName &&  expandParams &&  expandParamsOrder) r0_n0_p1_1 = total;
		else if (!expandReturn &&  expandMethodName && !expandParams &&  expandParamsOrder) r0_n1_p0_1 = total;
		else if (!expandReturn &&  expandMethodName &&  expandParams &&  expandParamsOrder) r0_n1_p1_1 = total;
		else if ( expandReturn && !expandMethodName && !expandParams &&  expandParamsOrder) r1_n0_p0_1 = total;
		else if ( expandReturn && !expandMethodName &&  expandParams &&  expandParamsOrder) r1_n0_p1_1 = total;
		else if ( expandReturn &&  expandMethodName && !expandParams &&  expandParamsOrder) r1_n1_p0_1 = total;
		else if ( expandReturn &&  expandMethodName &&  expandParams &&  expandParamsOrder) r1_n1_p1_1 = total;
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

	public List<QueryTerm> getExpandedParams() {
		return expandedParams;
	}

	public void setExpandedParams(List<QueryTerm> expandedParams) {
		this.expandedParams = expandedParams;
	}
}
