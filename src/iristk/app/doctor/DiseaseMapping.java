package iristk.app.doctor;

/**
 * Class representing a disease mapping.
 * A disease mapping consists of a disease mapped to three integers.
 * The first integer represents the number of answers matching the disease.
 * The second one is the total number of questions asked matching the disease.
 * And the third one is a resulting percentage from the two previous integers.
 * Disease mappings are used to represent key-values associations from the disease
 * map in order to manipulate them into sortable lists for instance.
 *
 * @author Marc Karassev
 */
public class DiseaseMapping {

    // Attributes

    private Disease disease;
    private int answers, questions, percentage;

    // Constructors

    /**
     * Default constructor, constructs a new disease mapping for the flu with 0 matching questions and answers.
     */
    public DiseaseMapping() {
        this(Disease.FLU, 0, 0);
    }

    /**
     * Constructs a new disease mapping with the given disease and numbers of matching questions and answers.
     *
     * @param disease   the disease mapped
     * @param answers   the number of matching answers
     * @param questions the number of matching questions
     */
    public DiseaseMapping(Disease disease, int answers, int questions) {
        this.disease = disease;
        this.answers = answers;
        this.questions = questions;
        if (questions != 0) {
            percentage = 100 * answers / questions;
        } else percentage = 0;
    }

    // Methods

    public String toString() {
        return disease + " " + answers + " " + questions + " " + percentage;
    }

    /**
     * Returns the mapped disease.
     *
     * @return the disease field's value
     */
    public Disease getDisease() {
        return disease;
    }

    /**
     * Returns the number of matching answers.
     *
     * @return the value of the answers field
     */
    public int getAnswers() {
        return answers;
    }

    /**
     * Returns the number of matching questions.
     *
     * @return the value of the questions field
     */
    public int getQuestions() {
        return questions;
    }

    /**
     * Returns the resulting percentage.
     *
     * @return the value of the percentage field
     */
    public int getPercentage() {
        return percentage;
    }

    /**
     * Sets the value of the questions field.
     *
     * @param questions the value to set
     */
    public void setQuestions(int questions) {
        this.questions = questions;
    }

    /**
     * Sets the value of the answers field.
     *
     * @param answers the value to set
     */
    public void setAnswers(int answers) {
        this.answers = answers;
    }
}
