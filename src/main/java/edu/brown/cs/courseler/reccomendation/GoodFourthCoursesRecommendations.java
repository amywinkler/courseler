package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.coursler.userinfo.User;

/**
 * Reccomend based on good fourth couses.
 *
 * @author amywinkler
 *
 */
public class GoodFourthCoursesRecommendations implements Recommend<Course> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private List<String> concentration;

  /**
   * Constructor for fourth course reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   * @param allCourses
   *          all of the courses
   */
  public GoodFourthCoursesRecommendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.concentration = user.getConcentration();
  }

  @Override
  public List<Course> getRecommendations() {

    // TODO: get courses where avg is less than 10, max is less than 15, and
    List<Course> toReturn = new ArrayList<>();

    for (Course c : allCourses) {
      if (!concentration.contains(c.getDepartment())) {
        if (c.getCrData() != null
            && c.getCrData().getHoursPerWeek().get("maximum") < 15
            && c.getCrData().getHoursPerWeek().get("average") < 10
            && c.getCrData().getEnjoyed() > .5) {
          toReturn.add(c);
        }
      }
    }

    toReturn.sort(Course.getCrCompCScore());

    return filter.getFilteredListOfCourses(toReturn);

  }

}
