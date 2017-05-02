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
  private String sectionType;
  private List<String> professors;
  private SectionTime times;
  private MeetingLocation meetingLocations;
  private List<TimeSlot> overlappingTimeSlots;
  private boolean isMainSection;

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
      MeetingLocation meetingLocations, List<TimeSlot> overlappingTimeSlots) {
    this.sectionId = sectionId;
    this.courseCode = courseCode;
    this.title = title;
    this.professors = professors;
    this.times = times;
    this.meetingLocations = meetingLocations;
    this.overlappingTimeSlots = overlappingTimeSlots;
  }

  /**
   * @param mainSection
   *          boolean representing whether or not the section is a main section
   */
  public void setIsMainSection(boolean mainSection) {
    this.isMainSection = mainSection;

  }

  /**
   * Set the section type of the section.
   *
   * @param sectType
   *          the section type
   */
  public void setSectionType(String sectType) {
    this.sectionType = sectType;
  }

  /**
   * @return the overlappingTimeSlots
   */
  public List<TimeSlot> getOverlappingTimeSlots() {
    return overlappingTimeSlots;
  }

  /**
   * @return the isMainSection boolean
   */
  public boolean getIsMainSection() {
    return isMainSection;
  }

  /**
   * @return the course code
   */
  public String getCourseCode() {
    return courseCode;
  }

  /**
   * @param overlappingTimeSlots
   *          the overlappingTimeSlots to set
   */
  public void setOverlappingTimeSlots(List<TimeSlot> overlappingTimeSlots) {
    this.overlappingTimeSlots = overlappingTimeSlots;
  }

}
