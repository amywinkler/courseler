package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;

/**
 * Search on the description and title of a query.
 * @author amywinkler
 *
 */
public class DescriptionSearch implements SearchSuggestions<Course> {
  private CourseDataCache cache;

  /**
   * Constructor for searching on description and titles.
   * @param cache the course cache
   */
  public DescriptionSearch(CourseDataCache cache) {
    this.cache = cache;
  }

  @Override
  public List<Course> suggest(String searchTerm) {
    List<Course> allCourses = cache.getAllCourses();
    List<Course> toReturn =  new ArrayList<>();

    for (Course c: allCourses) {
      if (c.getDescription() != null
          && (c.getDescription().toLowerCase().contains(" " + searchTerm))) {
        toReturn.add(c);
      }
    }
    return toReturn;
  }

}
