package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;

/**
 * Class to search on the course code (ranked more highly than title or
 * description).
 *
 * @author amywinkler
 *
 */
public class CourseCodeSearch implements SearchSuggestions<Course> {
  private CourseDataCache cache;

  /**
   * Constructor for searching on description and titles.
   *
   * @param cache
   *          the course cache
   */
  public CourseCodeSearch(CourseDataCache cache) {
    this.cache = cache;
  }

  @Override
  public List<Course> suggest(String searchTerm) {

    List<Course> allCourses = cache.getAllCourses();
    List<Course> toReturn = new ArrayList<>();

    for (Course c : allCourses) {
      // if (c.getCourseCode().toLowerCase().contains(searchTerm)
      // || c.getCourseCode().substring(0, c.getCourseCode().indexOf(" "))
      // .toLowerCase().equals(searchTerm)
      // || c.getCourseCode().substring(c.getCourseCode().indexOf(" ") + 1)
      // .toLowerCase().equals(searchTerm)) {
      if (c.getCourseCode().toLowerCase().equals(searchTerm)) {
        toReturn.add(c);
      }
    }

    return toReturn;
  }

}
