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
	COUGH(Disease.COMMONCOLD, Disease.FLU, Disease.BRONCHITIS, Disease.PNEUMONIA),
	HEADACHE(Disease.COMMONCOLD, Disease.FLU, Disease.LYME, Disease.CONCUSSION, Disease.MONO),
	SLEEPY(Disease.FLU, Disease.CONCUSSION, Disease.BRONCHITIS, Disease.MYOCARDITIS, Disease.PNEUMONIA, Disease.LYME),
	FEVER(Disease.FLU, Disease.OTITIS, Disease.LYME, Disease.MYOCARDITIS, Disease.PNEUMONIA),
	PAIN(Disease.FLU, Disease.LYME, Disease.OTITIS, Disease.MYOCARDITIS),
	PAIN_MUSCLES(Disease.FLU, Disease.LYME),
	PAIN_EARS(Disease.OTITIS),
	PAIN_EYES(Disease.FLU),
	PAIN_CHEST(Disease.FLU, Disease.MYOCARDITIS),
	RUNNY_NOSE(Disease.COMMONCOLD, Disease.FLU),
	FROZEN(Disease.FLU),
	DIZZY(Disease.CONCUSSION),
	NAUSEA(Disease.CONCUSSION),
	VOMIT(Disease.CONCUSSION),
	AMNESIA(Disease.CONCUSSION),
	FLUCHEAT(Disease.FLU);
	
	// TODO add all the symptoms and replace then in the flow by answerSymptom() or questionSymptom() calls
	
	// Attributes
	
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
