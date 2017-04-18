package edu.brown.cs.courseler.courseinfo;

public class MeetingLocation {
  private String mondayMeetingLoc;
  private String tuesdayMeetingLoc;
  private String wednesdayMeetingLoc;
  private String thursdayMeetingLoc;
  private String fridayMeetingLoc;

  public MeetingLocation(){
    mondayMeetingLoc = null;
    tuesdayMeetingLoc = null;
    wednesdayMeetingLoc = null;
    thursdayMeetingLoc = null;
    fridayMeetingLoc = null;
  }

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
