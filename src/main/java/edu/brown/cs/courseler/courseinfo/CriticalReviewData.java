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


}
