package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.coursler.userinfo.User;

/**
 * Class for reccomendations based on concentration.
 *
 * @author amywinkler
 *
 */
public class ConcentrationRecommendations implements Recommend<Course> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private String concentration;

  /**
   * Constructor for class year reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   * @param allCourses
   *          all of the courses
   */
  public ConcentrationRecommendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.concentration = user.getConcentration();
  }

  @Override
  public List<Course> getReccomendations() {
    List<Course> toReturn = new ArrayList<>();

    if (concentration == null
        || concentration.equals("Independent Concentration")
        || concentration.equals("Undecided")) {
      return toReturn;
    } else {
      for (Course c : allCourses) {
        if (concentration.equals(c.getDepartment())) {
          toReturn.add(c);
        }
      }

      toReturn.sort(Course.getCrCompCScore());

      return filter.getFilteredListOfCourses(toReturn);

    }

  }

}
