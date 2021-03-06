package edu.brown.cs.courseler.recommendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.userinfo.User;

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
  private List<String> concentration;

  /**
   * Constructor for class year recommendations.
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
  public List<Course> getRecommendations() {
    List<Course> toReturn = new ArrayList<>();

    if (concentration == null
        || ((concentration.size() == 1) && ((concentration.get(0).equals(
            "Independent Concentration") || concentration.get(0).equals(
            "Undecided"))))) {
      return toReturn;
    } else {
      for (Course c : allCourses) {
        if (concentration.contains(c.getDepartment()) && c.getCrData() != null
            && c.getCrData().getDemographics().get("percent_concentrators") > .25) {
          toReturn.add(c);
        }
      }

      toReturn.sort(Course.getCrCompCScore());

      return filter.getFilteredListOfCourses(toReturn);

    }

  }

}
