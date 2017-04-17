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
   */
  public boolean overlapsWithTimeSlot(SectionTime timeSlot) {
    // TODO: figure out whether or not the coursetime has any overlapping time
    return false;
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
