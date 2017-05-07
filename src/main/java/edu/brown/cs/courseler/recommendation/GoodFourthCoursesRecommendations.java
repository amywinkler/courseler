package edu.brown.cs.courseler.recommendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.userinfo.User;

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
  private static final int MAX_HOURS = 14;
  private static final int AVG_HOURS = 10;
  private static final double PERCENT_ENJOYED = 0.45;
  private static final double PERCENT_DIFFICULTY = 0.55;

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

    List<Course> toReturn = new ArrayList<>();

    for (Course c : allCourses) {
      if (!concentration.contains(c.getDepartment())) {
        if (c.getCrData() != null
            && c.getCrData().getHoursPerWeek().get("maximum") < MAX_HOURS
            && c.getCrData().getHoursPerWeek().get("average") < AVG_HOURS
            && c.getCrData().getEnjoyed() > PERCENT_ENJOYED
            && c.getCrData().getDifficulty() < PERCENT_DIFFICULTY
            && c.getPrereq() == null) {
          toReturn.add(c);
        }
      }
    }

    toReturn.sort(Course.getCrCompCScore());

    return filter.getFilteredListOfCourses(toReturn);

  }

}
