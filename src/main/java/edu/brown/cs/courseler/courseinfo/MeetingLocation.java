package edu.brown.cs.courseler.courseinfo;

/**
 * Class representing a meeting Location.
 *
 * @author amywinkler
 *
 */
public class MeetingLocation {
  private String mondayMeetingLoc;
  private String tuesdayMeetingLoc;
  private String wednesdayMeetingLoc;
  private String thursdayMeetingLoc;
  private String fridayMeetingLoc;

  /**
   * Sets the location for a given day.
   * @param day the day
   * @param location the location
   */
  public void setLoc(String day, String location) {
    switch (day) {
      case "M":
        mondayMeetingLoc = location;
        break;
      case "T":
        tuesdayMeetingLoc = location;
        break;
      case "W":
        wednesdayMeetingLoc = location;
        break;
      case "R":
        thursdayMeetingLoc = location;
        break;
      case "F":
        fridayMeetingLoc = location;
        break;
      default:
        System.out.println("Should't be here1");
        break;
    }

  }

}
