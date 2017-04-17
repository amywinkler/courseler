package edu.brown.cs.courseler.data;

import java.util.EnumMap;
import java.util.Map;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.CourseTime;
import edu.brown.cs.courseler.courseinfo.TimeSlot;

public class CourseDataCache {
  private Map<TimeSlot, CourseTime> timeSlotToTimes;
  private Map<String, Course> coursesForProf;
  private Map<String, Course> courseIdToCourse;
  private Map<String, Course> sectionIdToSection;

  public CourseDataCache() {
    setUpTimeSlots();
  }


  private void setUpTimeSlots() {
    timeSlotToTimes = new EnumMap<TimeSlot, CourseTime>(TimeSlot.class);
    CourseTime timeSlotA = new CourseTime();
    timeSlotA.setCourseTime("mondayStart", 800);
    timeSlotA.setCourseTime("mondayEnd", 850);
    // timeSlotToTimes.put(TimeSlot.A, )
    // TODO: add all the timeslots to the course data cache
  }
}
