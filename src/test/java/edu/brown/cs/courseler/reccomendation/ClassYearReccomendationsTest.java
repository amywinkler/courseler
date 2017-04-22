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

public class ClassYearReccomendationsTest {
  @Test
  public void testValidClassRecNoFilter() {
    CourseDataCache cdc = new CourseDataCache();
    CourseDataParser cdp = new CourseDataParser(cdc);
    User user = new User("1234");
    List<String> interests = new ArrayList<>();
    interests.add("HIST");
    user.setInterests(interests);
    user.setClassYear("Freshman");
    user.setConcentration("Undecided");
    Filter filter = new Filter(user, false, false, false);
    ClassYearRecommendations cyr = new ClassYearRecommendations(user, filter,
        cdc.getAllCourses());
    List<Course> recs = cyr.getReccomendations();
    assertEquals(recs.size(), 46);
    assertTrue(recs.get(0).getDepartment().equals("HIST"));
  }

}
