package edu.brown.cs.courseler.reccomendation;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.TimeSlot;
import edu.brown.cs.coursler.userinfo.User;

/**
 * Class to do a filter on reccoemndations.
 *
 * @author amywinkler
 *
 */
public class Filter {
  private User user;
  private boolean openFilter;
  private boolean lessThanTenHoursFilter;
  private boolean smallCoursesFilter;
  private List<TimeSlot> openTimeSlots;

  /**
   * Constructor for filter object.
   *
   * @param openFilter
   *          filter for only open courses
   * @param lessThanTenHoursFilter
   *          filter for only courses that take less than ten hours a week
   * @param smallCoursesFilter
   *          filter for only small courses (leq 24 students)
   */
  public Filter(User user, boolean openFilter,
      boolean lessThanTenHoursFilter,
      boolean smallCoursesFilter) {
    this.user = user;
    this.openFilter = openFilter;
    this.lessThanTenHoursFilter = lessThanTenHoursFilter;
    this.smallCoursesFilter = smallCoursesFilter;
    this.openTimeSlots = getOpenTimeslots();

  }

  private List<TimeSlot> getOpenTimeslots() {
    // TODO: go through each section id and remove from list of all timeslots
    return null;
  }

  private void filterOnLessThanTenHours(List<Course> currentListOfCourses) {
    for (Course c : currentListOfCourses) {
      if (c.getCrData() == null
          || c.getCrData().getHoursPerWeek().get("average_hours") > 10) {
        currentListOfCourses.remove(c);
      }
    }
  }

  public List<Course> getFilteredListOfCourses(List<Course> currentListOfCourses) {
    // TODO: filter must apply all the filters and then return max 15 courses
    // automatically filter by classes that are not in your cart
    // automatically filter by max 15 courses

    return currentListOfCourses;
  }

}
