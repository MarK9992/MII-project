package iristk.app.doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Hashmap for storing disease evaluation results. Keys are the diseases, the
 * values are list of integers. The first integer in the list is the number of
 * answers matching the disease. The second integer in the list is the number of
 * asked questions matching the disease. The third integer is a resulting
 * percentage from the previous integers.
 * 
 * @author Marc Karassev
 * 
 */
public class DiseaseMap extends HashMap<Disease, ArrayList<Integer>> {

	// Constants

	private static final int ANSWERSMATCHINDEX = 0;
	private static final int QUESTIONSMATCHINDEX = 1;
	private static final int PERCENTAGEINDEX = 2;

	// Constructors
	
	/**
	 * Creates a new map.
	 */
	public DiseaseMap() {
		super();
		init();
	}

	// Methods

	/**
	 * Updates the map's values for a yes to a question relative to a symptom.
	 *
	 * @param symptom
	 *            the related symptom
	 */
	public void answerSymptom(Symptom symptom) {
		Iterator<Disease> it = symptom.getDiseases().iterator();
		Disease disease;

		while(it.hasNext()) {
			disease = it.next();
			incrementAnswersMatch(disease, symptom);
		}
	}

	/**
	 * Updates the map's values for an asked question relative to a symptom.
	 * 
	 * @param symptom
	 *            the related symptom
	 */
	public void questionSymptom(Symptom symptom) {
		Iterator<Disease> it = symptom.getDiseases().iterator();
		Disease disease;
		
		while(it.hasNext()) {
			disease = it.next();
			incrementQuestionsMatch(disease);
		}
	}

    /**
     * Calculates and displays the resulting percentages for each disease.
     */
	public void calculatePercentages() {
		Iterator<Disease> it = keySet().iterator();
		Disease key;

		while (it.hasNext()) {
			key = it.next();
			percentage(key);
			System.out.println(key + " " + get(key).get(PERCENTAGEINDEX));
		}
	}

    /**
	 * Returns a disease's number of matching answers.
	 * 
	 * @param key
	 *            the key matching the wanted disease
	 * @return the number of matching answers
	 */
	public int getMatchingAnswers(Disease key) {
		return get(key).get(ANSWERSMATCHINDEX);
	}

	/**
	 * Returns a disease's number of matching questions.
	 * 
	 * @param key
	 *            the key matching the wanted disease
	 * @return the number of matching questions
	 */
	public int getMatchingQuestions(Disease key) {
		return get(key).get(QUESTIONSMATCHINDEX);
	}

	/**
	 * Returns a disease's resulting percentage.
	 * 
	 * @param key
	 *            the key matching the wanted disease
	 * @return the resulting percentage
	 */
	public int getPercentage(Disease key) {
		return get(key).get(PERCENTAGEINDEX);
	}

	// Initializes the map.
	private void init() {
		ArrayList<Integer> list = new ArrayList<Integer>();

		list.add(0);
		list.add(0);
		list.add(0);
		put(Disease.FLU, new ArrayList<Integer>(list));
		put(Disease.COMMONCOLD, new ArrayList<Integer>(list));
		put(Disease.CONCUSSION, new ArrayList<Integer>(list));
		put(Disease.OTITIS, new ArrayList<Integer>(list));
		put(Disease.BRONCHITIS, new ArrayList<Integer>(list));
		put(Disease.MONO, new ArrayList<Integer>(list));
		put(Disease.MYOCARDITIS, new ArrayList<Integer>(list));
		put(Disease.PNEUMONIA, new ArrayList<Integer>(list));
		put(Disease.LYME, new ArrayList<Integer>(list));
	}

	// Increments the number of answers matching a disease for a given symptom.
	private void incrementAnswersMatch(Disease disease, Symptom symptom) {
		get(disease).set(ANSWERSMATCHINDEX, get(disease).get(ANSWERSMATCHINDEX) + symptom.getDiseasePoints(disease));
	}

	// Increments the number of questions matching a disease.
	private void incrementQuestionsMatch(Disease key) {
		get(key).set(QUESTIONSMATCHINDEX, get(key).get(QUESTIONSMATCHINDEX) + 1);
	}

	// Calculates the resulting percentage of a given disease.
	private void percentage(Disease key) {
		if (get(key).get(QUESTIONSMATCHINDEX) != 0)
			get(key).set(
					PERCENTAGEINDEX,
					get(key).get(ANSWERSMATCHINDEX) * 100
							/ get(key).get(QUESTIONSMATCHINDEX));
	}

    // Converts an entry to a DiseaseMapping instance.
    public DiseaseMapping entryToDiseaseMapping(Disease key) {
        return new DiseaseMapping(key, getMatchingAnswers(key), getMatchingQuestions(key));
    }
}
