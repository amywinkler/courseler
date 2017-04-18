package edu.brown.cs.courseler.api;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.repl.MethodRunner;
import edu.brown.cs.courseler.search.Search;

/**
 * Run methods for the courseler repl.
 *
 * @author amywinkler
 *
 */
public class CourselerMethodRunner implements MethodRunner<String> {
  private CourseDataCache cache;

  public CourselerMethodRunner(CourseDataCache cache){
    this.cache = cache;
  }


  @Override
  public List<String> run(String currCmdStr) {
    // TODO Auto-generated method stub
    String[] currCmdArr = currCmdStr.split(" ");

    if (currCmdArr.length > 0) {
      switch (currCmdArr[0].trim()) {
        case "search":
          searchCmd(currCmdStr);
        default:
          break;

      }
    }

    return new ArrayList<String>();

  }

  private void searchCmd(String currCmdStr) {
    Search s = new Search(cache);
    List<Course> courses = s.rankedKeywordSearch(currCmdStr.
        substring(currCmdStr.indexOf("search ") + 7));
    for (Course c: courses) {
      System.out.println(c.toString());
    }
  }


  @Override
  public boolean isCmdForClass(String currCmdStr) {
    // TODO:
    return true;
  }

}
