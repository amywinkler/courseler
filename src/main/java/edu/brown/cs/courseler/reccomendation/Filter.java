package edu.brown.cs.courseler.reccomendation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.TimeSlot;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.userinfo.User;

/**
 * Class to do a filter on reccoemndations.
 *
 * @author amywinkler
 *
 */
public class Filter {
  private static final int MAX_NUM_RECCOMENDATIONS = 15;
  private static final int SMALL_COURSE_SIZE = 24;
  private static final int AVG_HOURS_PER_WEEK = 10;

  private CourseDataCache cache;
  private User user;
  private boolean openFilter;
  private boolean lessThanTenHoursFilter;
  private boolean smallCoursesFilter;
  private List<Section> sectionsInCart;
  private List<Course> coursesInCart;
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
    this.sectionsInCart = getSectionsInUserCart();
    this.openTimeSlots = getOpenTimeslots();
    this.coursesInCart = getCoursesInCart();

  }

  private List<Section> getSectionsInUserCart() {
    List<Section> toReturn = new ArrayList<>();

    for (String sectionId : user.getSectionsInCart()) {
      toReturn.add(cache.getSectionFromCache(sectionId));
    }

    return toReturn;
  }

  private List<Course> getCoursesInCart() {
    List<Course> toReturn = new ArrayList<>();

    for (Section s : sectionsInCart) {
      if (!toReturn.contains(cache.getCourseFomCache(s.getCourseCode()))) {
        toReturn.add(cache.getCourseFomCache(s.getCourseCode()));
      }
    }

    return toReturn;
  }

  private EnumSet<TimeSlot> getOpenTimeslots() {
    EnumSet<TimeSlot> toReturn = EnumSet.allOf(TimeSlot.class);
    for (Section s : sectionsInCart) {
      for (TimeSlot t : s.getOverlappingTimeSlots()) {
        toReturn.remove(t);
      }
    }
    return toReturn;
  }

  private void filterOnClassesNotInCart(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {
      if (coursesInCart.contains(c)) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private void filterOnMaxReccomendations(List<Course> currentListOfCourses) {
    while (currentListOfCourses.size() > MAX_NUM_RECCOMENDATIONS) {
      currentListOfCourses.remove(currentListOfCourses.size() - 1);
    }

  }

  private void filterOnSmallCourses(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();

    for (Course c : currentListOfCourses) {
      if (c.getCap() > SMALL_COURSE_SIZE) {
        toRemove.add(c);

      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private void filterOnOpenTimeSlots(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();

    for (Course c : currentListOfCourses) {
      boolean oneMainSectionFitsInCart = false;
      for (Section s : c.getSections()) {
        boolean fitsInCart = true;
        if (s.getIsMainSection() && !oneMainSectionFitsInCart) {
          for (TimeSlot t : s.getOverlappingTimeSlots()) {
            if (!openTimeSlots.contains(t)) {
              fitsInCart = false;
            }
          }
        }

        if (fitsInCart) {
          oneMainSectionFitsInCart = true;
        }
      }

      if (!oneMainSectionFitsInCart) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private void filterOnLessThanTenHours(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {

      if (c.getCrData() == null
          || c.getCrData().getHoursPerWeek().get("average") > AVG_HOURS_PER_WEEK) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
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
