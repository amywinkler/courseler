package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.coursler.userinfo.User;

/**
 * Reccomender for courses based on class year.
 *
 * @author amywinkler
 *
 */
public class ClassYearReccomendations implements Reccomend<Course> {

  private static final double PERCENT_TO_CHECK = 0.4;
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private String classYear;


  /**
   * Constructor for class year reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
  public ClassYearReccomendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.classYear = user.getClassYear();
  }


  @Override
  public List<Course> getReccomendations() {
    String percentName;
    if (classYear.equals("Freshman")) {
      percentName = "percent_freshmen";
    } else if (classYear.equals("Sophomore")) {
      percentName = "percent_sophomores";
    } else if (classYear.equals("Junior")) {
      percentName = "percent_juniors";
    } else if (classYear.equals("Senior")) {
      percentName = "percent_seniors";
    } else {
      percentName = "percent_grad";
    }



    List<Course> goodForClassYear = new ArrayList<>();
    List<Course> toReturn = new ArrayList<Course>();

    for (Course c : allCourses) {
      if (c.getCrData() != null
          && c.getCrData().getDemographics().get(percentName) >= PERCENT_TO_CHECK) {
        goodForClassYear.add(c);
      }
    }

    goodForClassYear.sort(Course.getCrCompCScore());

    for (Course c : goodForClassYear) {
      if (user.getInterests().contains(c.getDepartment())
          || user.getConcentration().equals(c.getDepartment())) {
        toReturn.add(c);
      }
    }



    for (Course c : goodForClassYear) {
      if (!toReturn.contains(c)) {
        toReturn.add(c);
      }
    }

    return filter.getFilteredListOfCourses(toReturn);
  }

}
