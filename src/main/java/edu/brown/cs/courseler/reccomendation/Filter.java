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
  private static final int LARGE_COURSE_SIZE = 50;
  private static final int CAP_SIZE = 999;
  private static final double PERCENT_OF_CLASS_YEAR = 0.2;

  private CourseDataCache cache;
  private User user;
  private boolean openFilter;
  private int maxAvgHoursPerWeek;
  private String courseSize;
  private String cappedCourses;
  private List<Section> sectionsInCart;
  private List<Course> coursesInCart;
  private EnumSet<TimeSlot> openTimeSlots;


  /**
   * Constructor for filter object.
   *
   * @param cache
   *          the course data cache
   * @param user
   *          the user
   * @param openFilter
   *          filter for only open courses
   * @param lessThanTenHoursFilter
   *          filter for only courses that take less than ten hours a week
   * @param smallCoursesFilter
   *          filter for only small courses (leq 24 students)
   * @param cappedCoursesFilter
   *           filter on capped courses
   */
  public Filter(CourseDataCache cache, User user, boolean openFilter,
      int maxAvgHoursPerWeek, String courseSize,
 String cappedCourses) {
    this.cache = cache;
    this.user = user;
    this.openFilter = openFilter;
    this.maxAvgHoursPerWeek = maxAvgHoursPerWeek;
    this.courseSize = courseSize;
    this.cappedCourses = cappedCourses;
    this.sectionsInCart = getSectionsInUserCart();
    this.openTimeSlots = getOpenTimeslots();
    this.coursesInCart = getCoursesInCart();

  }

  /**
   * Get all the section objects in a user's cart.
   *
   * @return all the section objects in a user's cart
   */
  public List<Section> getSectionsInUserCart() {
    List<Section> toReturn = new ArrayList<>();

    for (String sectionId : user.getSectionsInCart()) {
      toReturn.add(cache.getSectionFromCache(sectionId));
    }

    return toReturn;
  }

  /**
   * Get all the courses in a user's cart.
   *
   * @return all the course objects in a user's cart
   */
  public List<Course> getCoursesInCart() {
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

  private void filterOnNoFys(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {
      if (c.getDescription() != null && c.getDescription().contains(" FYS")) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }

  }

  private void filterOnNoSoph(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {
      if (c.getDescription() != null && c.getDescription().contains(" SOPH")) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }

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

  private void filterOnCourseSize(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    if (courseSize.equals("small")) {
      for (Course c : currentListOfCourses) {
        if (c.getCap() > SMALL_COURSE_SIZE) {
          toRemove.add(c);

        }
      }
    } else if (courseSize.equals("medium")) {
      for (Course c : currentListOfCourses) {
        if (c.getCap() <= SMALL_COURSE_SIZE || c.getCap() > LARGE_COURSE_SIZE) {
          toRemove.add(c);

        }
      }

    } else if (courseSize.equals("large")) {
      for (Course c : currentListOfCourses) {
        if (c.getCap() <= LARGE_COURSE_SIZE) {
          toRemove.add(c);

        }
      }

    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private boolean openContainsAllElements(Section s) {
    for (TimeSlot t : s.getOverlappingTimeSlots()) {
      if (!openTimeSlots.contains(t)) {
        return false;
      }
    }

    return true;
  }

  private void filterOnOpenTimeSlots(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();

    for (Course c : currentListOfCourses) {
      boolean oneMainSectionFitsInCart = false;
      for (Section s : c.getSections()) {

        if (s.getIsMainSection() && !oneMainSectionFitsInCart) {
          if (openContainsAllElements(s)) {
            oneMainSectionFitsInCart = true;
          }

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

  private void filterHoursPerWeek(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {

      if (c.getCrData() == null
          || c.getCrData().getHoursPerWeek().get("average") > maxAvgHoursPerWeek + 1) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private void filterOnCapped(List<Course> currentListOfCourses) {
    List<Course> toRemove = new ArrayList<>();
    if (cappedCourses.equals("capped")) {
      for (Course c : currentListOfCourses) {
        if (c.getCap() == CAP_SIZE) {
          toRemove.add(c);
        }
      }
    } else if (cappedCourses.equals("uncapped")) {
      for (Course c : currentListOfCourses) {
        if (c.getCap() != CAP_SIZE) {
          toRemove.add(c);
        }
      }
    }


    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }
  }

  private void filterOnFewFromClassYear(List<Course> currentListOfCourses,
      String classYear) {
    List<Course> toRemove = new ArrayList<>();
    for (Course c : currentListOfCourses) {
      String percentName;
      if (classYear.equals("Freshman")) {
        percentName = "percent_freshmen";
      } else if (classYear.equals("Sophomore")) {
        percentName = "percent_sophomores";
      } else if (classYear.equals("Junior")) {
        percentName = "percent_juniors";
      } else if (classYear.equals("Senior")) {
        percentName = "percent_seniors";
      } else {
        percentName = "percent_grad";
      }

      if (c.getCrData() != null
          && c.getCrData().getDemographics().get(percentName)
          < PERCENT_OF_CLASS_YEAR) {
        toRemove.add(c);
      }
    }

    for (Course c : toRemove) {
      currentListOfCourses.remove(c);
    }

    for (Course c : toRemove) {
      currentListOfCourses.add(c);
    }
  }

  /**
   * Get the filtered list of courses based on the current filters.
   *
   * @param currentListOfCourses
   *          the current list of courses
   * @return the filtered list of courses
   */
  public List<Course> getFilteredListOfCourses(
      List<Course> currentListOfCourses) {
    if (openFilter) {
      filterOnOpenTimeSlots(currentListOfCourses);
    }

    filterHoursPerWeek(currentListOfCourses);

    filterOnCourseSize(currentListOfCourses);

    filterOnCapped(currentListOfCourses);


    filterOnClassesNotInCart(currentListOfCourses);


    if (user.getClassYear() != null
        && !user.getClassYear().equals("Freshman")) {
      filterOnNoFys(currentListOfCourses);
    }

    if (user.getClassYear() != null
        && !user.getClassYear().equals("Sophomore")) {
      filterOnNoSoph(currentListOfCourses);
    }

    if (user.getClassYear() != null) {
      filterOnFewFromClassYear(currentListOfCourses, user.getClassYear());
    }

    filterOnMaxReccomendations(currentListOfCourses);


    return currentListOfCourses;
  }

}
