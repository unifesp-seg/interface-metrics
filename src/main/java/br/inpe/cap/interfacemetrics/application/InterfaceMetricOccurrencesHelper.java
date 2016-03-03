package br.inpe.cap.interfacemetrics.application;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.inpe.cap.interfacemetrics.domain.InterfaceMetric;
import br.inpe.cap.interfacemetrics.domain.OccurrencesCombination;
import br.inpe.cap.interfacemetrics.infrastructure.InterfaceMetricRepository;
import br.unifesp.ppgcc.sourcereraqe.infrastructure.AQEApproach;
import br.unifesp.ppgcc.sourcereraqe.infrastructure.QueryTerm;
import br.unifesp.ppgcc.sourcereraqe.infrastructure.SourcererQueryBuilder;

public class InterfaceMetricOccurrencesHelper {

	private InterfaceMetric interfaceMetric;
	private AQEApproach aqeApproach;
	private List<InterfaceMetric> occurrences = new ArrayList<InterfaceMetric>();

	private InterfaceMetricRepository repository;
	
	public InterfaceMetricOccurrencesHelper(InterfaceMetric interfaceMetric) throws Exception {
		repository =  new InterfaceMetricRepository();
		this.interfaceMetric = interfaceMetric;
		this.aqeApproach = getAQEApproach();
		interfaceMetric.setExpandedParams(this.aqeApproach.getParamsTerms());
	}

	public InterfaceMetricOccurrencesHelper(InterfaceMetric interfaceMetric, boolean mock) throws Exception {
		repository =  new InterfaceMetricRepository(mock);
		this.interfaceMetric = interfaceMetric;
		this.aqeApproach = getAQEApproach();
		interfaceMetric.setExpandedParams(this.aqeApproach.getParamsTerms());
	}

	private AQEApproach getAQEApproach() throws Exception {
		boolean relaxReturn = false;
		boolean relaxParams = false;
		boolean contextRelevants = true;
		boolean filterMethodNameTermsByParameter = false;
		String relatedWordsServiceUrl = "http://localhost:8080/related-words-service";

		String expanders = "WordNet , Type";
		
		String methodName = interfaceMetric.getMethodName();
		String returnType = interfaceMetric.getReturnType();
		String params = interfaceMetric.getParams();
		
		AQEApproach aqeApproach = new AQEApproach(relatedWordsServiceUrl, expanders, relaxReturn, relaxParams, contextRelevants, filterMethodNameTermsByParameter);
		aqeApproach.loadMethodInterface(methodName, returnType, params);

		return aqeApproach;
	}
	
	public void updateOccurrences() throws Exception {
		this.occurrences = repository.findOccurrences(this);
		
		
		List<OccurrencesCombination> combinations = OccurrencesCombination.allCombinations();
		for(OccurrencesCombination combination : combinations){
			int total = this.getOccurrences(combination).size();
			interfaceMetric.setOccurrencesTotal(combination, total);
		}
	}

	List<InterfaceMetric> getOccurrences(OccurrencesCombination combination) {
		List<InterfaceMetric> matches = new ArrayList<InterfaceMetric>();
		for(InterfaceMetric occurence : occurrences){
			if(this.match(combination, occurence))
				matches.add(occurence);
		}
		return matches;
	}

	private boolean match(OccurrencesCombination combination, InterfaceMetric occurence) {
		boolean matchReturn = this.matchReturn(combination, occurence);
		boolean matchMethodName = this.matchMethodName(combination, occurence);
		boolean matchParams = this.matchParam(combination, occurence);
		
		return matchReturn && matchMethodName && matchParams;
	}

	private boolean matchReturn(OccurrencesCombination combination, InterfaceMetric occurence) {
		if(!combination.isExpandReturn())
			return interfaceMetric.getReturnType().equals(occurence.getReturnType());
		
		return aqeApproach.getReturnTypeTerms().get(0).getExpandedTerms().contains(occurence.getReturnType());
	}

	private boolean matchMethodName(OccurrencesCombination combination, InterfaceMetric occurence) {
		if(combination.isIgnoreMethodNameOnSearch())
			return true;
		
		if(!combination.isExpandMethodName())
			return interfaceMetric.getMethodName().equals(occurence.getMethodName());

		if (aqeApproach.getMethodNameTerms().size() != occurence.getTotalWordsMethod())
			return false;
		
		boolean match = true;
		
		for(int i = 0; i < aqeApproach.getMethodNameTerms().size(); i++){
			QueryTerm term = aqeApproach.getMethodNameTerms().get(i);
			match = term.getExpandedTerms().contains(occurence.getWordsMethod()[i]);
		}
		return match;
	}

	private boolean matchParam(OccurrencesCombination combination, InterfaceMetric occurence) {
		if(!combination.isExpandParams())
			return interfaceMetric.isSameParams(occurence.getParamsNames(), !combination.isExpandParamsOrder());
		else
			return interfaceMetric.isSameExpandedParams(occurence.getParamsNames(), !combination.isExpandParamsOrder());
	}

	public String getOccurrencesSQL(String table) throws Exception {
		String sql = "SELECT * FROM " + table;
		sql += "\n" + "where id <> " + interfaceMetric.getId();
		sql += "\n" + "and total_params = " + interfaceMetric.getTotalParams();
		
		sql += "\n" + this.getSimilarReturnSQLClause();
		sql += "\n" + this.getSimilarParamsSQLClause();
		
		return sql;
	}
	
	private String getSimilarReturnSQLClause() throws Exception {
		SourcererQueryBuilder sourcererQueryBuilder = new SourcererQueryBuilder(aqeApproach);
		String sourcererSyntax = sourcererQueryBuilder.getReturnTypePart(aqeApproach.getReturnTypeTerms());
		
		String clause = sourcererSyntax;
		
		clause = StringUtils.replace(clause, "\nreturn_sname_contents:(( ", " and return_type in ('");
		clause = StringUtils.replace(clause, " ))", "')");
		clause = StringUtils.replace(clause, " OR ", "','");
		
		return clause; 
	}

	private String getSimilarParamsSQLClause() throws Exception {
		SourcererQueryBuilder sourcererQueryBuilder = new SourcererQueryBuilder(aqeApproach);
		String sourcererSyntax = sourcererQueryBuilder.getParamsPart(aqeApproach.getParamsTerms());
		
		String clause = sourcererSyntax;
		clause = StringUtils.substringAfterLast(clause, "\n");
		clause = StringUtils.replace(clause, "params_snames_contents:(( ", " and (\n     params like '%");
		clause = StringUtils.replace(clause, " ) AND ( ", "%'\n  or params like '%");
		clause = StringUtils.replace(clause, " OR ", "%'\n  or params like '%");
		clause = StringUtils.replace(clause, " ))", "%'\n)");
		
		return clause; 
	}

	//Accessors
	public List<InterfaceMetric> getOccurrences() {
		return occurrences;
	}

	public AQEApproach getAqeApproach() {
		return aqeApproach;
	}

	public InterfaceMetric getInterfaceMetric() {
		return interfaceMetric;
	}
}
