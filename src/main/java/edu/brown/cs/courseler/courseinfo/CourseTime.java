package edu.brown.cs.courseler.courseinfo;

import java.util.List;

/**
 * Class representing a course time object.
 *
 * @author amywinkler
 *
 */
public class CourseTime {
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
  public CourseTime() {
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
   * Set the course time, pass in the name of the time you want to set.
   *
   * @param timeName
   *          the name of the timeslot to set
   * @param time
   *          the time to set
   */
  public void setCourseTime(String timeName, int time) {
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
