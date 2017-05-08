package edu.brown.cs.courseler.recommendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.userinfo.User;

/**
 * Class to reccomend on writ courses.
 * @author amywinkler
 *
 */
public class WritCourseRecommendations implements Recommend<Course> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;

  /**
   * Constructor for WRIT course recommendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   * @param allCourses
   *          the list of all courses
   *
   */
  public WritCourseRecommendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
  }

  @Override
  public List<Course> getRecommendations() {
    List<Course> writCourses = getOnlyWritCourses();
    List<String> interests = user.getInterests();
    List<Course> orderedResults = new ArrayList<>();

    for (Course wc : writCourses) {
      if (interests != null && interests.contains(wc.getDepartment())) {
        orderedResults.add(wc);
      }
    }

    orderedResults.sort(Course.getCrCompCScore());
    writCourses.sort(Course.getCrCompCScore());

    for (Course wc : writCourses) {
      if (!orderedResults.contains(wc)) {
        orderedResults.add(wc);
      }
    }

    return filter.getFilteredListOfCourses(orderedResults);
  }

  private List<Course> getOnlyWritCourses() {
    List<Course> toReturn = new ArrayList<>();
    for (Course c : allCourses) {
      if (c.getDescription() != null
          && (c.getDescription().contains("WRIT") || c.getDepartment()
              .toLowerCase().equals("literary arts")
              || c.getDepartment().toLowerCase()
                  .equals("comparative literature") || c.getDepartment()
              .toLowerCase().equals("english"))) {
        toReturn.add(c);
      }
    }

    return toReturn;
  }

}
