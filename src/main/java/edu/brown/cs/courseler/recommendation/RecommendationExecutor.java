package edu.brown.cs.courseler.recommendation;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.userinfo.User;

/**
 * Executor for reccomendations.
 * @author amywinkler
 *
 */
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
   * @param allCourses
   *          the list of all courses
   * @param cache
   *          the cache of courses
   */
  public RecommendationExecutor(User user, Filter filter,
      List<Course> allCourses,
      CourseDataCache cache) {
    this.user = user;
    this.filter = filter;
    this.allCourses = allCourses;
    this.cache = cache;
  }

  @Override
  public List<RecommendationDatum> getRecommendations() {
    List<RecommendationDatum> toReturn = new ArrayList<>();

    BasedOnCartReccomendations bOnCartR = new BasedOnCartReccomendations(user,
        filter, cache.getAllCourses());
    List<Course> bcReccomendations = bOnCartR.getRecommendations();
    RecommendationDatum bcRec = new RecommendationDatum(
        "Courses Based on Your Cart", bcReccomendations);
    if (bcReccomendations.size() != 0) {
      toReturn.add(bcRec);
    }


    ConcentrationRecommendations cr = new ConcentrationRecommendations(user,
        filter, cache.getAllCourses());
    List<Course> crRecs = cr.getRecommendations();
    RecommendationDatum crRecDatum = new RecommendationDatum(
        "Recommended Courses in Your Concentration", crRecs);
    if (crRecs.size() != 0) {
      toReturn.add(crRecDatum);
    }

    GoodFourthCoursesRecommendations gfc = new GoodFourthCoursesRecommendations(
        user, filter, cache.getAllCourses());
    List<Course> gfcRecs = gfc.getRecommendations();
    RecommendationDatum gfcRecDatum = new RecommendationDatum(
        "Good Fourth Classes", gfcRecs);
    if (gfcRecs.size() != 0) {
      toReturn.add(gfcRecDatum);
    }

    ClassYearRecommendations cyr = new ClassYearRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> cyReccomendations = cyr.getRecommendations();
    RecommendationDatum cyRec = new RecommendationDatum(
        "Good Courses For Your Class Year", cyReccomendations);
    if (cyReccomendations.size() != 0) {
      toReturn.add(cyRec);
    }

    WritCourseRecommendations wc = new WritCourseRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> wcReccomendations = wc.getRecommendations();
    RecommendationDatum writRd = new RecommendationDatum(
        "Interesting WRIT Courses", wcReccomendations);
    if (wcReccomendations.size() != 0) {
      toReturn.add(writRd);
    }

    return toReturn;
  }

}
