package edu.brown.cs.courseler.reccomendation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.data.CourseDataParser;
import edu.brown.cs.courseler.userinfo.User;

public class FilterTest {
  @Test
  public void testFilterOpenCourses() {
    CourseDataCache cdc = new CourseDataCache();
    CourseDataParser cdp = new CourseDataParser(cdc);

    User u = new User("1234");
    u.addToCart("GREK 0100 S01");
    u.setClassYear("Freshman");
    Filter f = new Filter(cdc, u, true, 999, "any", "capped");
    List<Course> lstToFilter = new ArrayList<>();
    lstToFilter.add(cdc.getCourseFomCache("CSCI 0330"));

    List<Course> filtered = f.getFilteredListOfCourses(lstToFilter);
    // Should have nothing bc at the same time
    assertEquals(filtered.size(), 0);

  }

}
