package edu.brown.cs.courseler.reccomendation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.data.CourseDataParser;
import edu.brown.cs.coursler.userinfo.User;

public class WritCourseReccomendationsTest {
  @Test
  public void testValidWritReccomendNoFilter() {
    CourseDataCache cdc = new CourseDataCache();
    CourseDataParser cdp = new CourseDataParser(cdc);
    User user = new User("1234");
    List<String> interests = new ArrayList<>();
    interests.add("APMA");
    interests.add("HIST");
    user.setConcentration("Undecided");
    user.setInterests(interests);
    Filter filter = new Filter(cdc, user, false, false, false);
    WritCourseRecommendations wc = new WritCourseRecommendations(user, filter,
        cdc.getAllCourses());
    List<Course> recs = wc.getReccomendations();
    assertEquals(recs.size(), 15);
    assertTrue(recs.get(0).getDepartment().equals("HIST"));
  }

}