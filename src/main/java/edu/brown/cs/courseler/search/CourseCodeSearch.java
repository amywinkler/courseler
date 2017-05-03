package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  private Map<String, String> courseMappings;
  private Set<String> shortenings;

  /**
   * Constructor for searching on description and titles.
   *
   * @param cache
   *          the course cache
   */
  public CourseCodeSearch(CourseDataCache cache) {
    this.cache = cache;
    setUpMappingsAndShortenings();
  }

  private void setUpMappingsAndShortenings() {
    this.shortenings = new HashSet<>(Arrays.asList("csci", "biol", "geol",
        "taps"));
    this.courseMappings = new HashMap<>();
    courseMappings.put("csci", "cs");
    courseMappings.put("biol", "bio");
    courseMappings.put("geol", "geo");
    courseMappings.put("taps", "ta");

  }

  @Override
  public List<Course> suggest(String searchTerm) {

    List<Course> allCourses = cache.getAllCourses();
    List<Course> toReturn = new ArrayList<>();


    for (Course c : allCourses) {
      String[] courseCodeArr = c.getCourseCode().toLowerCase().split(" ");
      String courseCodeNoSpace = "";
      for (int i = 0; i < courseCodeArr.length; i++) {
        courseCodeNoSpace += courseCodeArr[i];
      }


      if (c.getCourseCode().toLowerCase().equals(searchTerm)
          || courseCodeNoSpace.equals(searchTerm)
          || courseCodeNoSpace.substring(0,
              Math.min(courseCodeNoSpace.length(), searchTerm.length()))
              .equals(searchTerm)
          || c.getCourseCode()
              .toLowerCase()
              .substring(0,
                  Math.min(c.getCourseCode().length(), searchTerm.length()))
              .equals(searchTerm)) {
        toReturn.add(c);
      } else if (shortenings.contains(courseCodeArr[0].toLowerCase())) {
        String newCourseCode = courseMappings.get(courseCodeArr[0]
            .toLowerCase())
            + courseCodeArr[1].toLowerCase();
        String newCourseCodeSpace = courseMappings.get(courseCodeArr[0]
            .toLowerCase()) + " " + courseCodeArr[1].toLowerCase();

        if (newCourseCode.equals(searchTerm)
            || newCourseCodeSpace.equals(searchTerm)
            || newCourseCodeSpace.substring(0,
                Math.min(newCourseCodeSpace.length(), searchTerm.length()))
                .equals(searchTerm)
            || newCourseCode.substring(0,
                Math.min(newCourseCode.length(), searchTerm.length()))
                .equals(searchTerm)) {
          toReturn.add(c);
        }
      }
    }

    for (Course c : allCourses) {
      if (c.getCourseCode().toLowerCase().split(" ")[0].equals(searchTerm)) {
        if (!toReturn.contains(c)) {
          toReturn.add(c);
        }

      }

      // TODO: is this needed?
      if (shortenings.contains(c.getCourseCode().toLowerCase().split(" ")[0]
          .toLowerCase())) {
        if (courseMappings.get(
            c.getCourseCode().toLowerCase().split(" ")[0].toLowerCase())
            .equals(searchTerm)) {
          if (!toReturn.contains(c)) {
            toReturn.add(c);
          }
        }
      }
    }

    Collections.sort(toReturn, Course.getAlphabetComp());

    return toReturn;
  }


}
