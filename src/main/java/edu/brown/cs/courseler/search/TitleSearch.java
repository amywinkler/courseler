package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;

/**
 * Search on the description and title of a query.
 *
 * @author amywinkler
 *
 */
public class TitleSearch implements SearchSuggestions<Course> {
  private CourseDataCache cache;

  /**
   * Constructor for searching on description and titles.
   * @param cache the course cache
   */
  public TitleSearch(CourseDataCache cache) {
    this.cache = cache;
  }

  @Override
  public List<Course> suggest(String searchTerm) {
    List<Course> allCourses = cache.getAllCourses();
    List<Course> toReturn =  new ArrayList<>();

    for (Course c: allCourses) {
      boolean added = false;
      String[] titleArr = c.getTitle().split(" ");
      if (c.getTitle().toLowerCase().equals(searchTerm)) {
        toReturn.add(c);
        added = true;
      }

      for (int i = 0; i < titleArr.length; i++) {

        if (searchTerm.equals(titleArr[i]) && !added) {
          if (c.getCourseCode().equals("PHYS 2630")) {
            System.out.println("here");
          }
          toReturn.add(c);
          added = true;
        }
      }
    }
    return toReturn;
  }

}
