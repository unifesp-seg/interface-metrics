package br.inpe.cap.interfacemetrics.domain;

import java.util.ArrayList;
import java.util.List;

public class OccurrencesCombination {

	private boolean expandReturn;
	private boolean expandMethodName;
	private boolean expandParams;
	private boolean expandParamsOrder;
	private boolean ignoreMethodNameOnSearch;

	public OccurrencesCombination(boolean expandReturn, boolean expandMethodName, boolean expandParams, boolean expandParamsOrder) {
		this.expandReturn = expandReturn;
		this.expandMethodName = expandMethodName;
		this.expandParams = expandParams;
		this.expandParamsOrder = expandParamsOrder;	
		this.ignoreMethodNameOnSearch = false;
	}

	public OccurrencesCombination(boolean expandReturnAndParams) {
		this.expandReturn = expandReturnAndParams;
		this.expandParams = expandReturnAndParams;
		this.expandParamsOrder = true;	
		this.ignoreMethodNameOnSearch = true;
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

		return combinations;
	}
	
	public void printCombination(){
		if(!isIgnoreMethodNameOnSearch())
			System.out.println("R: " + isExpandReturn() + " M: " + isExpandMethodName() + " P: " + isExpandParams() + " O: " + isExpandParamsOrder());
		else
			System.out.println("Ignore MethodName: " + isIgnoreMethodNameOnSearch() + " R: " + isExpandReturn() + " P: " + isExpandParams() + " O: " + isExpandParamsOrder());
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
	
}
