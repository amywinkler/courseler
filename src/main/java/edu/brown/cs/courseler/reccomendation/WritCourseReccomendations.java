package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.coursler.userinfo.User;

public class WritCourseReccomendations implements Reccomend<Course> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;

  /**
   * Constructor for WRIT course reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
  public WritCourseReccomendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
  }

  @Override
  public List<Course> getReccomendations() {
    // TODO: get all writ courses, then find if any are in your interests, then
    // just add highest remaining writ courses
    List<Course> writCourses = getOnlyWritCourses();
    List<String> interests = user.getInterests();

    // add all the writ courses in interests and then sort on cr data
    return null;
  }

  private List<Course> getOnlyWritCourses() {
    List<Course> toReturn = new ArrayList<>();
    for (Course c : allCourses) {
      if (c.getDescription().contains("WRIT")) {
        toReturn.add(c);
      }
    }

    return toReturn;
  }

}
