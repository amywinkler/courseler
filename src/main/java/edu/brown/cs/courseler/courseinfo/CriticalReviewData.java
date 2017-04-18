package edu.brown.cs.courseler.courseinfo;

import java.util.HashMap;
import java.util.Map;

public class CriticalReviewData {
  private Map<String, Double> hoursPerWeek;
  private Map<String, Double> demographics;

  private Double courseScore;
  private Double profScore;
  private Double recommendedToNonConcentrators;
  private Double learnedALot;
  private Double difficulty;
  private Double enjoyed;

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
