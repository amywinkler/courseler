package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.coursler.userinfo.User;

public class RecommendationExecutor implements Recommend<RecommendationDatum> {
  private User user;
  private Filter filter;
  private List<Course> allCourses;
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
    this.cache = cache;
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
