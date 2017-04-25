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
  public List<RecommendationDatum> getRecommendations() {
    List<RecommendationDatum> toReturn = new ArrayList<>();
    WritCourseRecommendations wc = new WritCourseRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> wcReccomendations = wc.getRecommendations();
    RecommendationDatum writRd = new RecommendationDatum(
        "WRIT Courses Based on Your Interests", wcReccomendations);
    toReturn.add(writRd);

    BasedOnCartReccomendations bOnCartR = new BasedOnCartReccomendations(user,
        filter, cache.getAllCourses());
    List<Course> bcReccomendations = bOnCartR.getRecommendations();
    RecommendationDatum bcRec = new RecommendationDatum(
        "Courses Based on Your Cart", bcReccomendations);
    toReturn.add(bcRec);

    ClassYearRecommendations cyr = new ClassYearRecommendations(user, filter,
        cache.getAllCourses());
    List<Course> cyReccomendations = cyr.getRecommendations();
    RecommendationDatum cyRec = new RecommendationDatum(
        "Good Courses For Your Class Year", cyReccomendations);
    toReturn.add(cyRec);

    ConcentrationRecommendations cr = new ConcentrationRecommendations(user,
        filter, cache.getAllCourses());
    List<Course> crRecs = cr.getRecommendations();
    RecommendationDatum crRecDatum = new RecommendationDatum(
        "Reccomended Courses in Your Concentration", crRecs);
    toReturn.add(crRecDatum);

    GoodFourthCoursesRecommendations gfc = new GoodFourthCoursesRecommendations(
        user, filter, cache.getAllCourses());
    List<Course> gfcRecs = gfc.getRecommendations();
    RecommendationDatum gfcRecDatum = new RecommendationDatum(
        "Good Fourth Classes", gfcRecs);
    toReturn.add(gfcRecDatum);

    return toReturn;
  }

}
