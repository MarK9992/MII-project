package iristk.app.doctor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Class providing the disease sub-system's API.
 * Manipulates a disease map depending on the questions asked and
 * the answers given during the execution. Produces evaluation of it.
 * Stores a set of diseases for which specific questions were asked.
 *
 * Not a singleton because why should we have only doctor?
 * Why not having several doctors evaluating in different manners
 * in the future?
 *
 * @author Marc Karassev
 */
public class Doctor {

    // Attributes

    private DiseaseMap map;
    private HashSet<Disease> checkedDiseases;

    // Constructors

    /**
     * Constructs a new Doctor with a default disease map and an empty checked diseases set.
     */
    public Doctor() {
        map = new DiseaseMap();
        checkedDiseases = new HashSet<Disease>();
    }

    // Methods

    /**
     * Updates the disease map's values for an asked question relative to a symptom.
     *
     * @param symptom
     *            the related symptom
     */
    public void askSymptom(Symptom symptom) {
        map.questionSymptom(symptom);
    }

    /**
     * Updates the disease map's values for a yes to a question relative to a symptom.
     *
     * @param symptom
     *            the related symptom
     */
    public void writeSymptom(Symptom symptom) {
        map.answerSymptom(symptom);
    }

    /**
     * Produces an evaluation of the symptoms that were given.
     * Outputs a sorted list of probable diseases and returns the most probable one.
     *
     * @return the most probable disease, null if no symptoms were asked
     */
    public Disease evaluate() {
        ArrayList<DiseaseMapping> sorted;
        Disease top = null;

        map.calculatePercentages();
        sorted = sortByPercentage();
        resolvePercentageEqualities(sorted);
        if (!sorted.isEmpty()) {
            top = sorted.get(0).getDisease();
        }
        for (DiseaseMapping dm : sorted) {
            System.out.println(dm);
        }
        return top;
    }

    // Returns a list of disease mappings sorted by decreasing percentages
    private ArrayList<DiseaseMapping> sortByPercentage() {
        HashMap<Disease, ArrayList<Integer>> copy = new HashMap<Disease, ArrayList<Integer>>(map);
        ArrayList<DiseaseMapping> sorted = new ArrayList<DiseaseMapping>();
        Iterator<Disease> it;
        Disease key, toAdd;

        while(copy.size() != 0) {
            it = copy.keySet().iterator();
            toAdd = null;
            while(it.hasNext()) {
                key = it.next();
                if(toAdd == null || map.getPercentage(toAdd) < map.getPercentage(key)) {
                    toAdd = key;
                }
            }
            sorted.add(map.entryToDiseaseMapping(toAdd));
            copy.remove(toAdd);
        }
        return sorted;
    }

    // Resort the given disease mapping list by decreasing numbers of questions asked where the percentages are equal.
    // The given disease mapping list is expected to be sorted by percentages.
    private void resolvePercentageEqualities(ArrayList<DiseaseMapping> toSort) {
        if(!toSort.isEmpty()) {
            // TODO not only for the top entry
            int maxIndex = 0;
            DiseaseMapping buffer;

            for (int i = 1; i + 1 <= toSort.size() && toSort.get(i).getPercentage() == toSort.get(maxIndex).getPercentage(); i++) {
                if(toSort.get(i).getQuestions() > toSort.get(maxIndex).getQuestions()) {
                    maxIndex = i;
                }
            }
            buffer = toSort.get(0);
            toSort.set(0, toSort.get(maxIndex));
            toSort.set(maxIndex, buffer);
        }
    }

    // Sorts the given disease mapping list by decreasing numbers of questions asked.
    private void sortByQuestions(ArrayList<DiseaseMapping> toSort) {
        // TODO
    }

    /**
     * Adds a disease into the checked diseases set.
     *
     * @param disease the disease to add
     */
    public void addCheckedDisease(Disease disease) {
        checkedDiseases.add(disease);
    }

    /**
     * Prints the set of checked diseases and return it.
     *
     * @return the value of the checkedDiseases field
     */
    public HashSet<Disease> printCheckedDiseases() {
        System.out.println(checkedDiseases);
        return checkedDiseases;
    }

    // Getters and Setters

    /**
     * Returns the set of checked diseases.
     *
     * @return the value of the checkedDiseases field
     */
    public HashSet<Disease> getCheckedDiseases() {
        return checkedDiseases;
    }
}
