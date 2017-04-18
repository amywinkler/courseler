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
public class DescriptionTitleSearch implements SearchSuggestions<Course> {
  private CourseDataCache cache;

  /**
   * Constructor for searching on description and titles.
   * @param cache the course cache
   */
  public DescriptionTitleSearch(CourseDataCache cache) {
    this.cache = cache;
  }

  @Override
  public List<Course> suggest(String searchTerm) {
    List<Course> allCourses = cache.getAllCourses();
    List<Course> toReturn =  new ArrayList<>();

    //TODO: split this into two for loops
    for (Course c: allCourses) {

      if (c.getCourseCode().toLowerCase().contains(searchTerm)){
        toReturn.add(c);
      } else if (c.getTitle().toLowerCase().contains(searchTerm)) {
        toReturn.add(c);
      } else if (c.getDescription() != null
          && c.getDescription().toLowerCase().contains(searchTerm)) {
        toReturn.add(c);
      }
    }
    return toReturn;
  }

}
