package iristk.app.doctor;

import java.util.HashSet;
import java.util.Iterator;

import iristk.app.doctor.Disease;

/**
 * Enumeration of possible symptoms. Symptoms are related to one or several
 * symptom matches. Symptom matches consist of a disease and a number of points
 * for this disease. This means that a symptom is related to one or several
 * diseases but among these diseases some of them are more related to the
 * symptom than the others.
 * 
 * @author Marc Karassev
 * 
 */
public enum Symptom {

	// Constants

	SOARTHROAT(new SymptomMatch(Disease.COMMONCOLD, 1), new SymptomMatch(
			Disease.FLU, 1), new SymptomMatch(Disease.BRONCHITIS, 1),
			new SymptomMatch(Disease.PNEUMONIA, 1), new SymptomMatch(
					Disease.MONO, 1)), COUGH(new SymptomMatch(
			Disease.COMMONCOLD, 1), new SymptomMatch(Disease.FLU, 1),
			new SymptomMatch(Disease.BRONCHITIS, 1), new SymptomMatch(
					Disease.PNEUMONIA, 1)), HEADACHE(new SymptomMatch(
			Disease.COMMONCOLD, 1), new SymptomMatch(Disease.FLU, 1),
			new SymptomMatch(Disease.LYME, 1), new SymptomMatch(
					Disease.CONCUSSION, 1), new SymptomMatch(Disease.MONO, 1)), SLEEPY(
			new SymptomMatch(Disease.FLU, 1), new SymptomMatch(
					Disease.CONCUSSION, 1), new SymptomMatch(
					Disease.BRONCHITIS, 1), new SymptomMatch(
					Disease.MYOCARDITIS, 1), new SymptomMatch(
					Disease.PNEUMONIA, 1), new SymptomMatch(Disease.LYME, 1)), FEVER(
			new SymptomMatch(Disease.FLU, 1), new SymptomMatch(Disease.OTITIS,
					1), new SymptomMatch(Disease.LYME, 1), new SymptomMatch(
					Disease.MYOCARDITIS, 1), new SymptomMatch(
					Disease.PNEUMONIA, 1)), PAIN(new SymptomMatch(Disease.FLU,
			3), new SymptomMatch(Disease.LYME, 1), new SymptomMatch(
			Disease.OTITIS, 1), new SymptomMatch(Disease.MYOCARDITIS, 1)), PAIN_MUSCLES(
			new SymptomMatch(Disease.FLU, 1), new SymptomMatch(Disease.LYME, 1)), PAIN_EARS(
			new SymptomMatch(Disease.OTITIS, 1)), PAIN_EYES(new SymptomMatch(
			Disease.FLU, 1)), PAIN_CHEST(new SymptomMatch(Disease.FLU, 1),
			new SymptomMatch(Disease.MYOCARDITIS, 1)), RUNNY_NOSE(
			new SymptomMatch(Disease.COMMONCOLD, 1), new SymptomMatch(
					Disease.FLU, 1)), FROZEN(new SymptomMatch(Disease.FLU, 1)), DIZZY(
			new SymptomMatch(Disease.CONCUSSION, 1)), NAUSEA(new SymptomMatch(
			Disease.CONCUSSION, 1)), VOMIT(new SymptomMatch(Disease.CONCUSSION,
			1)), AMNESIA(new SymptomMatch(Disease.CONCUSSION, 1));

	// TODO add all the symptoms and replace then in the flow by answerSymptom()
	// or questionSymptom() calls

	// Attributes

	private HashSet<SymptomMatch> matches;

	// Constructors

	// Constructs the set of diseases of a symptom
	private Symptom(SymptomMatch... args) {
		matches = new HashSet<SymptomMatch>();
		for (SymptomMatch sm : args) {
			matches.add(sm);
		}
	}

	// Methods

	/**
	 * Returns the diseases field.
	 * 
	 * @return the value of the diseases field
	 */
	public HashSet<Disease> getDiseases() {
		HashSet<Disease> diseases = new HashSet<Disease>();
		Iterator<SymptomMatch> it = matches.iterator();
		SymptomMatch smMatch;

		while (it.hasNext()) {
			smMatch = it.next();
			diseases.add(smMatch.getDisease());
		}

		return diseases;
	}

	/**
	 * Returns the number of points of a disease related to the symptom.
	 * 
	 * @param disease
	 *            the disease to look for
	 * @return the disease's points for that symptom or 0 if the given disease
	 *         is not related to the symptom
	 */
	public int getDiseasePoints(Disease disease) {
		Iterator<SymptomMatch> it = matches.iterator();
		SymptomMatch smMatch;

		do {
			smMatch = it.next();
		} while (it.hasNext() && disease != smMatch.getDisease());
		if (smMatch.getDisease() != disease) {
			return 0;
		}
		return smMatch.getPoints();
	}
}
