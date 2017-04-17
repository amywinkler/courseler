package edu.brown.cs.courseler.courseinfo;

import java.util.List;

/**
 * Class representing a course time object.
 *
 * @author amywinkler
 *
 */
public class SectionTime {
  // A list of all the timeslots that this section is filled by
  private transient List<TimeSlot> slotsFilledBySection;
  private Integer mondayStart;
  private Integer mondayEnd;
  private Integer tuesdayStart;
  private Integer tuesdayEnd;
  private Integer wednesdayStart;
  private Integer wednesdayEnd;
  private Integer thursdayStart;
  private Integer thursdayEnd;
  private Integer fridayStart;
  private Integer fridayEnd;

  /**
   * Constructor for CourseTime object.
   */
  public SectionTime() {
    mondayStart = null;
    mondayEnd = null;
    tuesdayStart = null;
    tuesdayEnd = null;
    wednesdayStart = null;
    wednesdayEnd = null;
    thursdayStart = null;
    thursdayEnd = null;
    fridayStart = null;
    fridayEnd = null;
  }

  /**
   * Adds a monday wednesday friday time to this section time.
   *
   * @param startTime
   *          the starting time
   * @param endTime
   *          the ending time
   */
  public void addMonWedFriTime(int startTime, int endTime) {
    setSectionTime("mondayStart", startTime);
    setSectionTime("wednesdayStart", startTime);
    setSectionTime("fridayStart", startTime);
    setSectionTime("mondayEnd", endTime);
    setSectionTime("wednesdayEnd", endTime);
    setSectionTime("fridayEnd", endTime);
  }

  /**
   * Gets the overlaps between a given section and a time slot.
   *
   * @param timeSlot
   *          the SectionTime representing the timeslot
   * @return
   *    boolean representing whether or not there is overlap for this timeslot
   */
  public boolean overlapsWithTimeSlot(SectionTime timeSlot) {
    boolean hasOverlap = false;
    if (!hasOverlap && mondayStart != null && mondayEnd != null) {
      if (timeSlot.mondayStart != null && timeSlot.mondayEnd != null) {
        if ((mondayStart < timeSlot.mondayStart
            && mondayEnd > timeSlot.mondayStart)
            || (mondayStart >= timeSlot.mondayStart
            && mondayStart < timeSlot.mondayEnd)) {
          hasOverlap = true;
        }
      }

    }

    if (!hasOverlap && tuesdayStart != null && tuesdayEnd != null) {
      if ((tuesdayStart < timeSlot.tuesdayStart
          && tuesdayEnd > timeSlot.tuesdayStart)
          || (tuesdayStart >= timeSlot.tuesdayStart
          && tuesdayStart < timeSlot.tuesdayEnd)) {
        hasOverlap = true;
      }

    }

    if (!hasOverlap && wednesdayStart != null && wednesdayEnd != null) {
      if ((wednesdayStart < timeSlot.wednesdayStart
          && wednesdayEnd > timeSlot.wednesdayStart)
          || (wednesdayStart >= timeSlot.wednesdayStart
          && wednesdayStart < timeSlot.wednesdayEnd)) {
        hasOverlap = true;
      }

    }

    if (!hasOverlap && thursdayStart != null && thursdayEnd != null) {
      if ((thursdayStart < timeSlot.thursdayStart
          && thursdayEnd > timeSlot.thursdayStart)
          || (thursdayStart >= timeSlot.thursdayStart
          && thursdayStart < timeSlot.thursdayEnd)) {
        hasOverlap = true;
      }

    }

    if (!hasOverlap && fridayStart != null && fridayEnd != null) {
      if ((fridayStart < timeSlot.fridayStart
          && fridayEnd > timeSlot.fridayStart)
          || (fridayStart >= timeSlot.fridayStart
          && fridayStart < timeSlot.fridayEnd)) {
        hasOverlap = true;
      }

    }

    return hasOverlap;
  }

  /**
   * Add a tursday and thursday time to this section time.
   *
   * @param startTime
   *          the start time
   * @param endTime
   *          the end time
   */
  public void addTuesThursTime(int startTime, int endTime) {
    setSectionTime("tuesdayStart", startTime);
    setSectionTime("thursdayStart", startTime);
    setSectionTime("tuesdayEnd", endTime);
    setSectionTime("thursdayEnd", endTime);

  }

  /**
   * Set the course time, pass in the name of the time you want to set.
   *
   * @param timeName
   *          the name of the timeslot to set
   * @param time
   *          the time to set
   */
  public void setSectionTime(String timeName, int time) {
    switch (timeName) {
      case "mondayStart":
        mondayStart = time;
        break;
      case "mondayEnd":
        mondayEnd = time;
        break;
      case "tuesdayStart":
        tuesdayStart = time;
        break;
      case "tuesdayEnd":
        tuesdayEnd = time;
        break;
      case "wednesdayStart":
        wednesdayStart = time;
        break;
      case "wednesdayEnd":
        wednesdayEnd = time;
        break;
      case "thursdayStart":
        thursdayStart = time;
        break;
      case "thursdayEnd":
        thursdayEnd = time;
        break;
      case "fridayStart":
        fridayStart = time;
        break;
      case "fridayEnd":
        fridayEnd = time;
        break;
      default:
        break;
    }

  }

}
