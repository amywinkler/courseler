package edu.brown.cs.courseler.reccomendation;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.coursler.userinfo.User;

public class Reccomendation implements Reccomend<ReccomendationDatum> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private List<Section> sectionsInUserCart;
  private List<Course> coursesInUserCart;

  /**
   * Constructor for reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
  public Reccomendation(User user, Filter filter, List<Course> allCourses) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.sectionsInUserCart = getSectionsInUserCart();
  }

  private List<Section> getSectionsInUserCart() {
    // TODO: return all the section objects in a user's cart
    return null;
  }

  private List<Course> getCoursesInUserCart() {
    // TODO: this should just return the courses in a user's cart do we need
    // this
    return null;
  }

  @Override
  public List<ReccomendationDatum> getReccomendations() {
    // TODO Auto-generated method stub
    return null;
  }

}
