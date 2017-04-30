package edu.brown.cs.courseler.reccomendation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.data.CourseDataParser;
import edu.brown.cs.courseler.userinfo.User;

public class ClassYearReccomendationsTest {
  @Test
  public void testValidClassRecNoFilter() {
    CourseDataCache cdc = new CourseDataCache();
    CourseDataParser cdp = new CourseDataParser(cdc);
    User user = new User("1234");
    List<String> interests = new ArrayList<>();
    interests.add("History");
    user.setInterests(interests);
    user.setClassYear("Freshman");
    user.setConcentration(ImmutableList.of("Undecided"));
    Filter filter = new Filter(cdc, user, false, false, false, false);
    ClassYearRecommendations cyr = new ClassYearRecommendations(user, filter,
        cdc.getAllCourses());
    List<Course> recs = cyr.getRecommendations();
    assertEquals(recs.size(), 15);
    assertTrue(recs.get(0).getDepartment().equals("History"));
  }

}
