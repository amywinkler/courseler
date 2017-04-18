package edu.brown.cs.courseler.courseinfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Class representing the critical review data.
 * @author amywinkler
 *
 */
public class CriticalReviewData {
  private Map<String, Double> hoursPerWeek;
  private Map<String, Double> demographics;

  /**
   * @return the hoursPerWeek
   */
  public Map<String, Double> getHoursPerWeek() {
    return hoursPerWeek;
  }

  /**
   * @param hoursPerWeek the hoursPerWeek to set
   */
  public void setHoursPerWeek(Map<String, Double> hoursPerWeek) {
    this.hoursPerWeek = hoursPerWeek;
  }

  /**
   * @return the demographics
   */
  public Map<String, Double> getDemographics() {
    return demographics;
  }

  /**
   * @param demographics the demographics to set
   */
  public void setDemographics(Map<String, Double> demographics) {
    this.demographics = demographics;
  }

  /**
   * @return the courseScore
   */
  public Double getCourseScore() {
    return courseScore;
  }

  /**
   * @param courseScore the courseScore to set
   */
  public void setCourseScore(Double courseScore) {
    this.courseScore = courseScore;
  }

  /**
   * @return the profScore
   */
  public Double getProfScore() {
    return profScore;
  }

  /**
   * @param profScore the profScore to set
   */
  public void setProfScore(Double profScore) {
    this.profScore = profScore;
  }

  /**
   * @return the recommendedToNonConcentrators
   */
  public Double getRecommendedToNonConcentrators() {
    return recommendedToNonConcentrators;
  }

  /**
   * @param recommendedToNonConcentrators
   *          the recommendedToNonConcentrators to set
   */
  public void setRecommendedToNonConcentrators(
      Double recommendedToNonConcentrators) {
    this.recommendedToNonConcentrators = recommendedToNonConcentrators;
  }

  /**
   * @return the learnedALot
   */
  public Double getLearnedALot() {
    return learnedALot;
  }

  /**
   * @param learnedALot the learnedALot to set
   */
  public void setLearnedALot(Double learnedALot) {
    this.learnedALot = learnedALot;
  }

  /**
   * @return the difficulty
   */
  public Double getDifficulty() {
    return difficulty;
  }

  /**
   * @param difficulty the difficulty to set
   */
  public void setDifficulty(Double difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * @return the enjoyed
   */
  public Double getEnjoyed() {
    return enjoyed;
  }

  /**
   * @param enjoyed the enjoyed to set
   */
  public void setEnjoyed(Double enjoyed) {
    this.enjoyed = enjoyed;
  }

  private Double courseScore;
  private Double profScore;
  private Double recommendedToNonConcentrators;
  private Double learnedALot;
  private Double difficulty;
  private Double enjoyed;

  /**
   * Constructor for CriticalReviewData object.
   */
  public CriticalReviewData() {
    this.hoursPerWeek = new HashMap<>();
    this.demographics = new HashMap<>();
  }

  public void addHoursPerWeek(String key, Double value) {
    hoursPerWeek.put(key, value);
  }

  public void addDemographic(String key, Double value) {
    demographics.put(key, value);
  }

  /**
   * Set all the critical review scores.
   *
   * @param courseSc
   *          the course's score
   * @param profSc
   *          the professor's score
   * @param recommendedNonConc
   *          reccomended to non concentrators
   * @param learned
   *          learned a lot scroee
   * @param difficult
   *          difficulty score
   * @param enjoy
   *          enjoyment score
   */
  public void setAllScores(Double courseSc, Double profSc,
      Double recommendedNonConc, Double learned, Double difficult, Double enjoy) {
    this.courseScore = courseSc;
    this.profScore = profSc;
    this.recommendedToNonConcentrators = recommendedNonConc;
    this.learnedALot = learned;
    this.difficulty = difficult;
    this.enjoyed = enjoy;

  }
}
