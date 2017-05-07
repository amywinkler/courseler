package edu.brown.cs.courseler.recommendation;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;

/**
 * Class representing reccomendation data.
 *
 * @author amywinkler
 *
 */
public class RecommendationDatum {
  private String name;
  private List<Course> courses;

  /**
   * Constructor for reccomendationDatum.
   *
   * @param name
   *          the reccomendation name
   * @param courses
   *          the list of courses to be reccomended
   */
  public RecommendationDatum(String name, List<Course> courses) {
    this.name = name;
    this.courses = courses;
  }
}
