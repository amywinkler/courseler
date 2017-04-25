package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.userinfo.User;

/**
 * Class to recommend based on courses that are already in your cart.
 *
 * @author amywinkler
 *
 */
public class BasedOnCartReccomendations implements Recommend<Course> {

  private User user;
  private Filter filter;
  private List<Course> allCourses;

  /**
   * Constructor for cart reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   * @param allCourses
   *          the list of courses
   */
  public BasedOnCartReccomendations(User user, Filter filter,
      List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
  }

  @Override
  public List<Course> getRecommendations() {
    // list of departments that are in your cart, only reccomend
    // based on those departments (sort by crScore)
    List<Course> toReturn = new ArrayList<Course>();
    List<Course> coursesInCart = filter.getCoursesInCart();
    Set<String> departmentsInCart = new HashSet<>();
    for (Course c : coursesInCart) {
      departmentsInCart.add(c.getDepartment());
    }

    for (Course c : allCourses) {
      if (departmentsInCart.contains(c.getDepartment())) {
        toReturn.add(c);
      }

    }

    toReturn.sort(Course.getCrCompCScore());

    return filter.getFilteredListOfCourses(toReturn);

  }

}
