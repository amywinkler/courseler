package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.coursler.userinfo.User;

public class RecommendationExecutor implements Recommend<RecommendationDatum> {
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
  public RecommendationExecutor(User user, Filter filter, List<Course> allCourses,
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
  public List<RecommendationDatum> getReccomendations() {
    List<RecommendationDatum> toReturn = new ArrayList<>();
    WritCourseRecommendations wc = new WritCourseRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> wcReccomendations = wc.getReccomendations();
    RecommendationDatum writRd = new RecommendationDatum(
        "WRIT Courses Based on Your Interests", wcReccomendations);
    toReturn.add(writRd);

    ClassYearRecommendations cyr = new ClassYearRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> cyReccomendations = cyr.getReccomendations();
    RecommendationDatum cyRec = new RecommendationDatum(
        "Good Courses For Your Class Year", cyReccomendations);
    toReturn.add(cyRec);

    return toReturn;
  }

}
