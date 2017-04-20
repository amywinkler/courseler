package edu.brown.cs.courseler.api;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.TimeSlot;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.repl.MethodRunner;
import edu.brown.cs.courseler.search.RankedSearch;

/**
 * Run methods for the courseler repl.
 *
 * @author amywinkler
 *
 */
public class CourselerMethodRunner implements MethodRunner<String> {
  private CourseDataCache cache;

  /**
   * Constructor for courselermethodrunner.
   *
   * @param cache
   *          the course cache
   */
  public CourselerMethodRunner(CourseDataCache cache) {
    this.cache = cache;
  }


  @Override
  public List<String> run(String currCmdStr) {
    String[] currCmdArr = currCmdStr.split(" ");

    if (currCmdArr.length > 0) {
      switch (currCmdArr[0].trim()) {
        case "search":
          searchCmd(currCmdStr);
          break;
        case "time":
          getTimeSlots(currCmdStr);
          break;
        default:
          break;
      }
    }

    return new ArrayList<String>();

  }

  private void searchCmd(String currCmdStr) {
    RankedSearch s = new RankedSearch(cache);
    List<Course> courses = s.rankedKeywordSearch(currCmdStr
        .substring(currCmdStr.indexOf("search ") + "search ".length()));
    for (Course c: courses) {
      System.out.println(c.toString());
    }
  }

  private void getTimeSlots(String currCmdStr) {
    String sectionString = currCmdStr
        .substring(currCmdStr.indexOf("time ") + 5);
    Section currSect = cache.getSectionFromCache(sectionString);
    for (TimeSlot t : currSect.getOverlappingTimeSlots()) {
      System.out.println(t);
    }
  }


  @Override
  public boolean isCmdForClass(String currCmdStr) {
    // TODO: error handling
    return true;
  }

}
