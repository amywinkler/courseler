package edu.brown.cs.courseler.courseinfo;

import java.util.List;

/**
 * Class representing a section of a course.
 *
 * @author amywinkler
 *
 */
public class Section {
  // this is the course code and the section name
  private String sectionId;
  private String courseCode;
  private String title;
  private List<String> professors;
  private SectionTime times;
  private MeetingLocation meetingLocations;
  private transient List<TimeSlot> overlappingTimeSlots;

  /**
   * Constructor for a section.
   *
   * @param sectionId
   *          the id
   * @param courseCode
   *          the course code
   * @param title
   *          the title
   * @param professors
   *          the professors
   * @param times
   *          the times
   * @param meetingLocations
   *          the meeting locations
   * @param overlappingTimeSlots
   *          the timelots it overlaps
   */
  public Section(String sectionId, String courseCode,
      String title, List<String> professors, SectionTime times,
      MeetingLocation meetingLocations, List<TimeSlot> overlappingTimeSlots){
    this.sectionId = sectionId;
    this.courseCode = courseCode;
    this.title = title;
    this.professors = professors;
    this.times = times;
    this.meetingLocations = meetingLocations;
    this.overlappingTimeSlots = overlappingTimeSlots;
  }

  /**
   * @return the overlappingTimeSlots
   */
  public List<TimeSlot> getOverlappingTimeSlots() {
    return overlappingTimeSlots;
  }

  /**
   * @param overlappingTimeSlots
   *          the overlappingTimeSlots to set
   */
  public void setOverlappingTimeSlots(List<TimeSlot> overlappingTimeSlots) {
    this.overlappingTimeSlots = overlappingTimeSlots;
  }

}
