package br.inpe.cap.interfacemetrics.domain;

import java.util.ArrayList;
import java.util.List;

public class OccurrencesCombination {

	private boolean expandReturn;
	private boolean expandMethodName;
	private boolean expandParams;
	private boolean expandParamsOrder;
	private boolean ignoreMethodNameOnSearch;
	private boolean classNameOnSearch;

	public OccurrencesCombination(boolean expandReturn, boolean expandMethodName, boolean expandParams, boolean expandParamsOrder) {
		this.expandReturn = expandReturn;
		this.expandMethodName = expandMethodName;
		this.expandParams = expandParams;
		this.expandParamsOrder = expandParamsOrder;	
		this.ignoreMethodNameOnSearch = false;
		this.classNameOnSearch = false;
	}

	public OccurrencesCombination(boolean expandReturnAndParams) {
		this.expandReturn = expandReturnAndParams;
		this.expandParams = expandReturnAndParams;
		this.expandParamsOrder = true;	
		this.ignoreMethodNameOnSearch = true;
		this.classNameOnSearch = false;
	}

	public OccurrencesCombination(boolean expand, boolean expandParamsOrder, boolean ignoreMethodNameOnSearch) {
		this.expandReturn = expand;
		this.expandMethodName = expand;
		this.expandParams = expand;
		this.expandParamsOrder = expandParamsOrder;
		this.ignoreMethodNameOnSearch = ignoreMethodNameOnSearch;
		this.classNameOnSearch = true;
	}
	
	public static List<OccurrencesCombination> allCombinations(){
		List<OccurrencesCombination> combinations = new ArrayList<OccurrencesCombination>();

		combinations.add(new OccurrencesCombination(false, false, false, false));
		combinations.add(new OccurrencesCombination(false, false, true,  false));
		combinations.add(new OccurrencesCombination(false, true,  false, false));
		combinations.add(new OccurrencesCombination(false, true,  true,  false));
		combinations.add(new OccurrencesCombination(true,  false, false, false));
		combinations.add(new OccurrencesCombination(true,  false, true,  false));
		combinations.add(new OccurrencesCombination(true,  true,  false, false));
		combinations.add(new OccurrencesCombination(true,  true,  true,  false));
		combinations.add(new OccurrencesCombination(false, false, false, true));
		combinations.add(new OccurrencesCombination(false, false, true,  true));
		combinations.add(new OccurrencesCombination(false, true,  false, true));
		combinations.add(new OccurrencesCombination(false, true,  true,  true));
		combinations.add(new OccurrencesCombination(true,  false, false, true));
		combinations.add(new OccurrencesCombination(true,  false, true,  true));
		combinations.add(new OccurrencesCombination(true,  true,  false, true));
		combinations.add(new OccurrencesCombination(true,  true,  true,  true));

		combinations.add(new OccurrencesCombination(false));
		combinations.add(new OccurrencesCombination(true));

		combinations.add(new OccurrencesCombination(false, false, false));
		combinations.add(new OccurrencesCombination(false, true,  false));
		combinations.add(new OccurrencesCombination(true,  true,  false));
		combinations.add(new OccurrencesCombination(false, true,  true));
		combinations.add(new OccurrencesCombination(true,  true,  true));
		
		return combinations;
	}
	
	public void printCombination(){
		String c = isClassNameOnSearch() ? "ClassNameOnSearch | " : "";
		c += isIgnoreMethodNameOnSearch() ? "IgnoreMethodNameOnSearch | " : "";
		String r = " R: " + isExpandReturn();
		String m = isIgnoreMethodNameOnSearch() ? "" : " M: " + isExpandMethodName();
		String p = " P: " + isExpandParams();
		String o = " O: " + isExpandParamsOrder();

		System.out.println(c + r + m + p + o);
	}
	
	public boolean isExpandReturn() {
		return expandReturn;
	}

	public boolean isExpandMethodName() {
		return expandMethodName;
	}

	public boolean isExpandParams() {
		return expandParams;
	}

	public boolean isExpandParamsOrder() {
		return expandParamsOrder;
	}

	public boolean isIgnoreMethodNameOnSearch() {
		return ignoreMethodNameOnSearch;
	}

	public boolean isClassNameOnSearch() {
		return classNameOnSearch;
	}
}
