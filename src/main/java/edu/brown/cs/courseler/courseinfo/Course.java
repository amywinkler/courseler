package edu.brown.cs.courseler.courseinfo;

import java.util.List;
import java.util.Map;

/**
 * Class that represents a course.
 *
 * @author amywinkler
 *
 */
public class Course {
  private String courseCode;
  private String title;
  private Map<String, Double> hoursPerWeek;
  private Map<String, Double> demographics;
  private Double courseScore;
  private Double profScore;
  private Double recommendedToNonConcentrators;
  private Double learnedALot;
  private Double difficulty;
  private Double enjoyed;
  private String description;
  private String department;
  private int cap;
  private String coursesDotBrownLink;
  private Map<String, String> funAndCool;
  private List<Section> sections;

  /**
   * Constructor for a course object.
   *
   * @param courseCode
   *          the course code
   */
  public Course(String courseCode) {
    this.courseCode = courseCode;
  }

}
