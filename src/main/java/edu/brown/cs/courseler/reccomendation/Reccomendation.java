package edu.brown.cs.courseler.reccomendation;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.TimeSlot;
import edu.brown.cs.coursler.userinfo.User;

public class Reccomendation {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private List<TimeSlot> openTimeSlots;
  private List<Section> sectionsInUserCart;
  private List<Course> coursesInUserCart;

  // private List<ReccomendationDatum>

  /**
   * Constructor for reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
//  public Reccomendation(User user, Filter filter, List<Course> allCourses) {
//    this.user = user;
//    this.filter = filter;
//    this.allCourses = allCourses;
//  }

}
