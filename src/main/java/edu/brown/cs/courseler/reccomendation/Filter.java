package edu.brown.cs.courseler.reccomendation;

/**
 * Class to do a filter on reccoemndations.
 *
 * @author amywinkler
 *
 */
public class Filter {
  private boolean openFilter;
  private boolean lessThanTenHoursFilter;
  private boolean smallCoursesFilter;

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
  public Filter(boolean openFilter, boolean lessThanTenHoursFilter,
      boolean smallCoursesFilter) {
    this.openFilter = openFilter;
    this.lessThanTenHoursFilter = lessThanTenHoursFilter;
    this.smallCoursesFilter = smallCoursesFilter;

  }

}
