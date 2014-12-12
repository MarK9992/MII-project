package iristk.app.doctor;

/**
 * Class defining a match relating to a symptom. A match consists of a disease
 * and a point value for this disease.
 * 
 * @author Marc Karassev
 * 
 */
public class SymptomMatch {

	// Attributes

	private Disease disease;
	private int points;

	// Constructors

	/**
	 * Default constructor, constructs a symptom match for the common cold and
	 * gives it one point.
	 */
	public SymptomMatch() {
		this(Disease.COMMONCOLD, 1);
	}

	/**
	 * Constructs a symptom match with the given parameters.
	 * 
	 * @param disease
	 *            the disease to associate the match with
	 * @param points
	 *            the number of points
	 */
	public SymptomMatch(Disease disease, int points) {
		this.disease = disease;
		this.points = points;
	}

	// Getters and setters

	/**
	 * Returns the disease related to the match.
	 * 
	 * @return the value of the disease field
	 */
	public Disease getDisease() {
		return disease;
	}

	/**
	 * Returns the number of points related to the match.
	 * 
	 * @return the value of the points field
	 */
	public int getPoints() {
		return points;
	}
}
