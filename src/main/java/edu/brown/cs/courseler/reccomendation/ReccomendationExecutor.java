package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.coursler.userinfo.User;

public class ReccomendationExecutor implements Reccomend<ReccomendationDatum> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
  private List<Section> sectionsInUserCart;
  private List<Course> coursesInUserCart;
  private CourseDataCache cache;

  /**
   * Constructor for reccomendations.
   *
   * @param user
   *          the user object
   * @param filter
   *          the filter
   */
  public ReccomendationExecutor(User user, Filter filter, List<Course> allCourses,
      CourseDataCache cache) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.sectionsInUserCart = getSectionsInUserCart();
    this.cache = cache;
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
    List<ReccomendationDatum> toReturn = new ArrayList<>();
    WritCourseReccomendations wc = new WritCourseReccomendations(user, filter,
        cache.getAllCourses());
    List<Course> wcReccomendations = wc.getReccomendations();
    ReccomendationDatum writRd = new ReccomendationDatum(
        "WRIT Courses Based on Your Interests", wcReccomendations);
    toReturn.add(writRd);

    return toReturn;
  }

}
