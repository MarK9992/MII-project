package iristk.app.doctor;

import java.util.HashSet;
import iristk.app.doctor.Disease;

/**
 * Enumeration of possible symptoms. Symptoms are related to one or several
 * diseases.
 * 
 * @author Marc Karassev
 * 
 */
public enum Symptom {
	
	// Constants
	
	SOARTHROAT(Disease.COMMONCOLD, Disease.FLU, Disease.BRONCHITIS, Disease.PNEUMONIA, Disease.MONO),
	COUGH(Disease.COMMONCOLD, Disease.FLU, Disease.BRONCHITIS, Disease.PNEUMONIA);
	// TODO add all the symptoms and replace then in the flow by answerSymptom() or questionSymptom() calls
	
	// Attributes
	
	private Disease d;
	private HashSet<Disease> diseases;
	
	// Constructors
	
	// Constructs the set of diseases of a symptom
	private Symptom(Disease... args) {
		diseases = new HashSet<Disease>();
		for(Disease d: args) {
			diseases.add(d);
		}
	}
	
	// Methods
	
	/**
	 * Returns the diseases field.
	 * 
	 * @return the value of the diseases field
	 */
	public HashSet<Disease> getDiseases() {
		return diseases;
	}
}
