package br.inpe.cap.interfacemetrics.domain;

import java.util.ArrayList;
import java.util.List;

public class OccurrencesCombination {

	private boolean ignorePackage;
	private boolean ignoreClass;
	private boolean expandMethodName;
	private boolean expandTypes;

	public OccurrencesCombination(boolean ignorePackage, boolean ignoreClass, boolean expandMethodName, boolean expandTypes) {
		this.ignorePackage = ignorePackage;
		this.ignoreClass = ignoreClass;
		this.expandMethodName = expandMethodName;
		this.expandTypes = expandTypes;	
	}

	public static List<OccurrencesCombination> allCombinations(){
		List<OccurrencesCombination> combinations = new ArrayList<OccurrencesCombination>();

		combinations.add(new OccurrencesCombination(false, false, false, false));
		combinations.add(new OccurrencesCombination(false, false, false, true));
		combinations.add(new OccurrencesCombination(false, false, true,  false));
		combinations.add(new OccurrencesCombination(false, false, true,  true));
		combinations.add(new OccurrencesCombination(false, true,  false, false));
		combinations.add(new OccurrencesCombination(false, true,  false, true));
		combinations.add(new OccurrencesCombination(false, true,  true,  false));
		combinations.add(new OccurrencesCombination(false, true,  true,  true));
		combinations.add(new OccurrencesCombination(true,  false, false, false));
		combinations.add(new OccurrencesCombination(true,  false, false, true));
		combinations.add(new OccurrencesCombination(true,  false, true,  false));
		combinations.add(new OccurrencesCombination(true,  false, true,  true));
		combinations.add(new OccurrencesCombination(true,  true,  false, false));
		combinations.add(new OccurrencesCombination(true,  true,  false, true));
		combinations.add(new OccurrencesCombination(true,  true,  true,  false));
		combinations.add(new OccurrencesCombination(true,  true,  true,  true));
		
		return combinations;
	}
	
	public void printCombination(){
		String p = isIgnorePackage() ? "IgnorePackage | " : "";
		String c = isIgnoreClass() ? "IgnoreClass | " : "";
		String w = " W: " + isExpandMethodName();
		String t = " T: " + isExpandTypes();

		System.out.println(p + c + w + t);
	}

	public boolean isIgnorePackage() {
		return ignorePackage;
	}

	public boolean isIgnoreClass() {
		return ignoreClass;
	}

	public boolean isExpandMethodName() {
		return expandMethodName;
	}

	public boolean isExpandTypes() {
		return expandTypes;
	}
	
}
