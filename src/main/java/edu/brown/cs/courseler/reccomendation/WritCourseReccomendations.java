package edu.brown.cs.courseler.reccomendation;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.coursler.userinfo.User;

public class WritCourseReccomendations implements Reccomend<Course> {
  private User user;
  private Filter filter;

  /**
   * Constructor for WRIT course reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
  public WritCourseReccomendations(User user, Filter filter) {
    this.user = user;
    this.filter = filter;
  }

  @Override
  public List<Course> getReccomendations() {
    // TODO Auto-generated method stub
    return null;
  }

}
