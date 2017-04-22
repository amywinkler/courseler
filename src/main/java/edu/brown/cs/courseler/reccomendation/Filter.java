package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.TimeSlot;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.coursler.userinfo.User;

/**
 * Class to do a filter on reccoemndations.
 *
 * @author amywinkler
 *
 */
public class Filter {
  private static final int MAX_NUM_RECCOMENDATIONS = 15;
  private static final int SMALL_COURSE_SIZE = 15;

  private CourseDataCache cache;
  private User user;
  private boolean openFilter;
  private boolean lessThanTenHoursFilter;
  private boolean smallCoursesFilter;
  private List<Section> sectionsInUserCart;
  private EnumSet<TimeSlot> openTimeSlots;


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
  public Filter(CourseDataCache cache, User user, boolean openFilter,
      boolean lessThanTenHoursFilter,
      boolean smallCoursesFilter) {
    this.cache = cache;
    this.user = user;
    this.openFilter = openFilter;
    this.lessThanTenHoursFilter = lessThanTenHoursFilter;
    this.smallCoursesFilter = smallCoursesFilter;
    this.sectionsInUserCart = getSectionsInUserCart();
    this.openTimeSlots = getOpenTimeslots();

  }

  private List<Section> getSectionsInUserCart() {
    List<Section> toReturn = new ArrayList<>();

    for (String sectionId : user.getSectionsInCart()) {
      toReturn.add(cache.getSectionFromCache(sectionId));
    }

    return toReturn;
  }

  private EnumSet<TimeSlot> getOpenTimeslots() {
    EnumSet<TimeSlot> toReturn = EnumSet.allOf(TimeSlot.class);
    for (Section s : sectionsInUserCart) {
      for (TimeSlot t : s.getOverlappingTimeSlots()) {
        toReturn.remove(t);
      }
    }
    return toReturn;
  }

  private void filterOnClassesNotInCart(List<Course> currentListOfCourses) {
    // TODO: make this actually do something lmao
  }

  private void filterOnMaxReccomendations(List<Course> currentListOfCourses) {
    while (currentListOfCourses.size() > MAX_NUM_RECCOMENDATIONS) {
      currentListOfCourses.remove(currentListOfCourses.size() - 1);
    }

  }

  private void filterOnSmallCourses(List<Course> currentListOfCourses) {
    for (Course c : currentListOfCourses) {
      if (c.getCap() > SMALL_COURSE_SIZE) {
        currentListOfCourses.remove(c);
      }
    }
  }

  private void filterOnOpenTimeSlots(List<Course> currentListOfCourses) {
    // for (Course c : currentListOfCourses) {
    // for (Section s : c.getSections()) {
    // boolean doesNotFitInCart = false;
    // if (s.getIsMain()) {
    // for (TimeSlot t : s.getOverlappingTimeSlots()) {
    // if(openTimeSlots.con)
    // }
    // }
    // }
    // }
  }

  private void filterOnLessThanTenHours(List<Course> currentListOfCourses) {
    for (Course c : currentListOfCourses) {
      if (c.getCrData() == null
          || c.getCrData().getHoursPerWeek().get("average_hours") > 10) {
        currentListOfCourses.remove(c);
      }
    }
  }

  /**
   * Get the filtered list of courses based on the current filters.
   *
   * @param currentListOfCourses
   *          the current list of courses
   * @return the filtered list of courses
   */
  public List<Course> getFilteredListOfCourses(List<Course> currentListOfCourses) {
    if (openFilter) {
      filterOnOpenTimeSlots(currentListOfCourses);
    }

    if (lessThanTenHoursFilter) {
      filterOnLessThanTenHours(currentListOfCourses);
    }

    if (smallCoursesFilter) {
      filterOnSmallCourses(currentListOfCourses);
    }

    filterOnClassesNotInCart(currentListOfCourses);
    filterOnMaxReccomendations(currentListOfCourses);

    return currentListOfCourses;
  }

}
