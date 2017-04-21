package edu.brown.cs.courseler.courseinfo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

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
  private Multimap<String, String> funAndCool;
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
    this.funAndCool = ArrayListMultimap.create();
  }

  /**
   * Comparator for courses based on the critical review score.
   */
  private static Comparator<Course> crCompCScore = new Comparator<Course>() {
    @Override
    public int compare(Course c1, Course c2) {
      return c1.crData.getCourseScore().compareTo(c2.crData.getCourseScore());
    }
  };

  /**
   * Get the comparator for course score.
   *
   * @return the comparator for course score
   */
  public Comparator<Course> getCrCompCScore() {
    return crCompCScore;
  }


  /**
   * Add to the map of "fun and cool" data.
   *
   * @param key
   *          the key for the map
   * @param value
   *          the value for the map
   */
  public void addToFunAndCool(String key, String value) {
    funAndCool.put(key, value);
  }

  /**
   * @param cr
   *          the critical review data
   */
  public void setCritReviewData(CriticalReviewData cr) {
    this.crData = cr;
  }

  /**
   * @param description
   *          the course description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @param prerequisites
   *          the course's prereq
   */
  public void setPreReq(String prerequisites) {
    this.prereq = prerequisites;
  }

  /**
   * @param title
   *          the course title
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @param dept
   *          the course's department
   */
  public void setDepartment(String dept) {
    this.department = dept;
  }

  /**
   * @param cap
   *          the course cap
   */
  public void setCap(int cap) {
    this.cap = cap;
  }

  /**
   * @param link
   *          the courses dot brown link
   */
  public void setCoursesDotBrownLink(String link) {
    this.coursesDotBrownLink = link;
  }

  /**
   * Add the section object to the list of sections.
   *
   * @param s
   *          the section
   */
  public void addSectionObject(Section s) {
    sections.add(s);
  }

  /**
   * @return the course title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @return the course description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @return the course code
   */
  public String getCourseCode() {
    return courseCode;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Course) {
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
  public int hashCode() {
    return courseCode.hashCode();
  }
}
