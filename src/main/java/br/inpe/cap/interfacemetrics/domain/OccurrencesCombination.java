package br.inpe.cap.interfacemetrics.domain;

import java.util.ArrayList;
import java.util.List;

public class OccurrencesCombination {

	private boolean sameReturn;
	private boolean sameMethodName;
	private boolean sameParams;
	private boolean disorder;	

	public OccurrencesCombination(boolean sameReturn, boolean sameMethodName, boolean sameParams, boolean disorder) {
		this.sameReturn = sameReturn;
		this.sameMethodName = sameMethodName;
		this.sameParams = sameParams;
		this.disorder = disorder;	
	}

	public static List<OccurrencesCombination> allCombinations(){
		List<OccurrencesCombination> combinations = new ArrayList<OccurrencesCombination>();

		combinations.add(new OccurrencesCombination(true, true, true, false));
		combinations.add(new OccurrencesCombination(true, true, false, false));
		combinations.add(new OccurrencesCombination(true, false, true, false));
		combinations.add(new OccurrencesCombination(true, false, false, false));
		combinations.add(new OccurrencesCombination(false, true, true, false));
		combinations.add(new OccurrencesCombination(false, true, false, false));
		combinations.add(new OccurrencesCombination(false, false, true, false));
		combinations.add(new OccurrencesCombination(false, false, false, false));
		combinations.add(new OccurrencesCombination(true, true, true, true));
		combinations.add(new OccurrencesCombination(true, true, false, true));
		combinations.add(new OccurrencesCombination(true, false, true, true));
		combinations.add(new OccurrencesCombination(true, false, false, true));
		combinations.add(new OccurrencesCombination(false, true, true, true));
		combinations.add(new OccurrencesCombination(false, true, false, true));
		combinations.add(new OccurrencesCombination(false, false, true, true));
		combinations.add(new OccurrencesCombination(false, false, false, true));
		
		return combinations;
	}
	
	public void printCombination(){
		System.out.println("R: " + isSameReturn() + " M: " + isSameMethodName() + " P: " + isSameParams() + " D: " + isDisorder());
	}
	
	public boolean isSameReturn() {
		return sameReturn;
	}

	public boolean isSameMethodName() {
		return sameMethodName;
	}

	public boolean isSameParams() {
		return sameParams;
	}

	public boolean isDisorder() {
		return disorder;
	}
}
