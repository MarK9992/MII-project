package iristk.app.doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Hashmap for storing disease evaluation results.
 * Keys are the diseases, the values are list of integers.
 * The first integer in the list is the number of answers matching the disease.
 * The second integer in the list is the number of asked questions matching the disease.
 * The third integer is a resulting percentage from the previous integers.
 * 
 * @author Marc Karassev
 *
 */
public class DiseaseMap extends HashMap<String, ArrayList<Integer>> {

	// Constants
	
	private static final int ANSWERSMATCHINDEX = 0;
	private static final int QUESTIONSMATCHINDEX = 1;
	private static final int PERCENTAGEINDEX = 2;
	
	/**
	 * Creates a new map.
	 */
	public DiseaseMap() {
		super();
		init();
	}
	
	/**
	 * Calculates and displays the resulting percentages for each disease.
	 */
	public void evaluate() {
		Set<String> set = keySet();
		Iterator<String> it = set.iterator();
		String key;
		
		while(it.hasNext()) {
			key = it.next();
			percentage(key);
			System.out.println(get(key).get(PERCENTAGEINDEX));
		}
	}
	
	// Initializes the map.
	private void init() {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		list.add(0);
		list.add(0);
		list.add(0);
		put("flu", new ArrayList<Integer>(list));
		put("comcold", new ArrayList<Integer>(list));
		put("concussion", new ArrayList<Integer>(list));
		put("otitis", new ArrayList<Integer>(list));
		put("bronchitis", new ArrayList<Integer>(list));
		put("mono", new ArrayList<Integer>(list));
		put("myocarditis", new ArrayList<Integer>(list));
		put("pneumonia", new ArrayList<Integer>(list));
		put("lyme", new ArrayList<Integer>(list));
	}
	
	// Increments the number of answers matching a disease.
	private void incrementAnswersMatch(String key) {
		get(key).set(ANSWERSMATCHINDEX, get(key).get(ANSWERSMATCHINDEX) + 1);
	}
	
	// Increments the number of questions matching a disease.
	private void incrementQuestionsMatch(String key) {
		get(key).set(QUESTIONSMATCHINDEX, get(key).get(QUESTIONSMATCHINDEX) + 1);
	}
	
	// Calculates the resulting percentage of a given disease.
	private void percentage(String key) {
		if(get(key).get(QUESTIONSMATCHINDEX) != 0)
			get(key).set(PERCENTAGEINDEX, get(key).get(ANSWERSMATCHINDEX) * 100 / get(key).get(QUESTIONSMATCHINDEX));
	}
}
