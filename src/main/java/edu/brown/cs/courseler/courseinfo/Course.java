package edu.brown.cs.courseler.courseinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that represents a course.
 *
 * @author amywinkler
 *
 */
public class Course {
  private String courseCode;
  private String title;
  private String department;
  private int cap;
  private String coursesDotBrownLink;
  private String prereq;
  private String description;

  //crit review
  private CriticalReviewData crData;


  //google form data
  private Map<String, String> funAndCool;
  private List<Section> sections;

  /**
   * Constructor for a course object.
   *
   * @param courseCode
   *          the course code
   */
  public Course(String courseCode) {
    this.courseCode = courseCode;
    this.sections = new ArrayList<>();
  }

  public void setCritReviewData(CriticalReviewData cr) {
    this.crData = cr;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPreReq(String prereq){
    this.prereq = prereq;
  }

  public void setTitle(String title){
    this.title = title;
  }

  public void setDepartment(String department){
    this.department = department;
  }

  public void setCap(int cap){
    this.cap = cap;
  }

  public void setCoursesDotBrownLink(String link){
    this.coursesDotBrownLink = link;
  }

  public void addSectionObject(Section s) {
    sections.add(s);
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  @Override
  public boolean equals(Object o){
    if (o instanceof Course){
      Course c = (Course) o;
      return c.courseCode.equals(courseCode);
    }

    return false;
  }

  @Override
  public String toString() {
    return "Course code: " + courseCode + "\n"
        + "Title: " + title + "\n"
        + "Cap: " + cap + "\n" + "Courses dot brown link: "
        + coursesDotBrownLink + "\n" + "Prereqs: " + prereq + "\n"
        + "Description:  " + description + "\n";
  }

  @Override
  public int hashCode(){
    return courseCode.hashCode();
  }

  public String getCourseCode() {
    return courseCode;
  }

}
