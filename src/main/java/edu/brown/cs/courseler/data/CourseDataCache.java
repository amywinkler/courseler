package edu.brown.cs.courseler.data;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.SectionTime;
import edu.brown.cs.courseler.courseinfo.TimeSlot;

/**
 * Cache for courses and professors.
 * @author amywinkler
 *
 */
public class CourseDataCache {
  private Map<TimeSlot, SectionTime> timeSlotToTimes;
  private Map<String, Course> courseIdToCourse;
  private Map<String, Section> sectionIdToSection;
  private List<Course> allCourses;
  private Map<String, String> departmentMap;
  private List<String> departmentFullNameList;
  private Multiset<String> corpus;
  private Map<String, String> deptToEmojiMap;

  private static final int FIFTY_MINS = 50;
  private static final int ONE_TWENTY_MINS = 120;
  private static final int TWO_THIRTY_MINS = 230;

  private static final int EIGHT_AM = 800;
  private static final int NINE_AM = 900;
  private static final int TEN_AM = 1000;
  private static final int TEN_THIRTY_AM = 1030;
  private static final int ELEVEN_AM = 1100;
  private static final int TWELVE_PM = 1200;
  private static final int ONE_PM = 1300;
  private static final int TWO_PM = 1400;
  private static final int TWO_THIRTY_PM = 1430;
  private static final int THREE_PM = 1500;
  private static final int FOUR_PM = 1600;
  private static final int SIX_PM = 1800;
  private static final int SIX_FORTY_PM = 1840;
  private static final int EIGHT_PM = 2000;
  private static final int TEN_PM = 2200;

  /**
   * Constructor for CourseDataCache.
   */
  public CourseDataCache() {
    setUpTimeSlots();
    courseIdToCourse = new HashMap<>();
    sectionIdToSection = new HashMap<>();
    allCourses = new ArrayList<>();
    departmentMap = new HashMap<>();
    departmentFullNameList = new ArrayList<>();
    corpus = HashMultiset.create();
    deptToEmojiMap = new HashMap<>();

  }

  /**
   * @return the map of timeslots to times they represent
   */
  public Map<TimeSlot, SectionTime> getTimeSlotToTimes() {
    return timeSlotToTimes;
  }

  /**
   * Add a dept, emoji pair to the dept emoji map.
   *
   * @param dept
   *          the department
   * @param emoji
   *          the emoji
   */
  public void addToDeptToEmojiMap(String dept, String emoji) {
    deptToEmojiMap.put(dept, emoji);
  }

  /**
   * Get the emoji for a given dept.
   *
   * @param dept
   *          the dept name
   * @return the emoji string
   */
  public String getEmojiForDept(String dept) {
    return deptToEmojiMap.get(dept);
  }

  /**
   * Add a word to the corpus of courses.
   *
   * @param word
   *          the word to add (a course code)
   */
  public void addToCorpus(String word) {
    corpus.add(word);
  }

  /**
   * @return the corpus of courses
   */
  public Multiset<String> getCorpus() {
    return corpus;
  }

  /**
   * Add a course to the list of courses.
   * @param c the course to add
   */
  public void addToAllCourses(Course c) {
    allCourses.add(c);
  }

  /**
   * @param sectionId
   *          the section id
   * @return the section object for the id
   */
  public Section getSectionFromCache(String sectionId) {
    return sectionIdToSection.get(sectionId);
  }

  /**
   * @param t
   *          the TimeSlot
   * @return the SectionTime for the TimeSlot
   */
  public SectionTime getTimeForTimeslot(TimeSlot t) {
    return timeSlotToTimes.get(t);
  }

  /**
   * Check if a section is contained in the section cache.
   *
   * @param sectionId
   *          the section id
   * @return a boolean representing whether it is contained or not
   */
  public boolean sectionCacheContains(String sectionId) {
    return sectionIdToSection.containsKey(sectionId);
  }

  /**
   * Get department name for dept code.
   *
   * @param code
   *          the department code
   * @return the full name of the dept
   */
  public String getDeptForCode(String code) {
    return departmentMap.get(code);
  }

  /**
   * Add something to the course cache.
   *
   * @param id
   *          the id
   * @param c
   *          the course object
   */
  public void addToCourseCache(String id, Course c) {
    courseIdToCourse.put(id, c);
  }

  /**
   * Add to the map of codes to full dept names.
   *
   * @param code
   *          the dept code
   * @param fullName
   *          the full dept name
   */
  public void addToDepartmentMap(String code, String fullName) {
    departmentMap.put(code, fullName);
  }

  /**
   * Look up the full name of a dept from the dept code.
   *
   * @param code
   *          the dept code
   * @return the full name of the dept
   */
  public String lookUpFullName(String code) {
    return departmentMap.get(code);
  }

  /**
   * Add to the department full names list.
   *
   * @param fullName
   *          the full name of the department.
   */
  public void addToDepartmentFullNameList(String fullName) {
    departmentFullNameList.add(fullName);
  }

  /**
   * @return the list of full names of departments
   */
  public List<String> getDepartmentFullNameList() {
    return departmentFullNameList;
  }


  /**
   * Gets all the courses that are currently in the cache.
   *
   * @return a list of courses
   */
  public List<Course> getAllCourses() {
    return allCourses;
  }

  /**
   * Get the course from the course cache.
   *
   * @param courseId
   *          the course id
   * @return the course cache
   */
  public Course getCourseFomCache(String courseId) {
    return courseIdToCourse.get(courseId);
  }

  /**
   * Add a section to the section cache.
   *
   * @param id
   *          the section id
   * @param s
   *          the section object
   */
  public void addToSectionCache(String id, Section s) {
    sectionIdToSection.put(id, s);
  }


  private void setUpTimeSlots() {
    timeSlotToTimes = new EnumMap<TimeSlot, SectionTime>(TimeSlot.class);
    SectionTime timeSlotA = new SectionTime();
    timeSlotA.addMonWedFriTime(EIGHT_AM, EIGHT_AM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.A, timeSlotA);

    SectionTime timeSlotB = new SectionTime();
    timeSlotB.addMonWedFriTime(NINE_AM, NINE_AM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.B, timeSlotB);

    SectionTime timeSlotC = new SectionTime();
    timeSlotC.addMonWedFriTime(TEN_AM, TEN_AM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.C, timeSlotC);

    SectionTime timeSlotD = new SectionTime();
    timeSlotD.addMonWedFriTime(ELEVEN_AM, ELEVEN_AM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.D, timeSlotD);

    SectionTime timeSlotE = new SectionTime();
    timeSlotE.addMonWedFriTime(TWELVE_PM, TWELVE_PM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.E, timeSlotE);

    SectionTime timeSlotF = new SectionTime();
    timeSlotF.addMonWedFriTime(ONE_PM, ONE_PM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.F, timeSlotF);

    SectionTime timeSlotG = new SectionTime();
    timeSlotG.addMonWedFriTime(TWO_PM, TWO_PM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.G, timeSlotG);

    SectionTime timeSlotH = new SectionTime();
    timeSlotH.addTuesThursTime(NINE_AM, NINE_AM + ONE_TWENTY_MINS);
    timeSlotToTimes.put(TimeSlot.H, timeSlotH);

    SectionTime timeSlotI = new SectionTime();
    timeSlotI.addTuesThursTime(TEN_THIRTY_AM, TEN_THIRTY_AM + ONE_TWENTY_MINS);
    timeSlotToTimes.put(TimeSlot.I, timeSlotI);

    SectionTime timeSlotJ = new SectionTime();
    timeSlotJ.addTuesThursTime(ONE_PM, ONE_PM + ONE_TWENTY_MINS);
    timeSlotToTimes.put(TimeSlot.J, timeSlotJ);

    SectionTime timeSlotK = new SectionTime();
    timeSlotK.addTuesThursTime(TWO_THIRTY_PM, TWO_THIRTY_PM + ONE_TWENTY_MINS);
    timeSlotToTimes.put(TimeSlot.K, timeSlotK);

    SectionTime timeSlotL = new SectionTime();
    timeSlotL.addTuesThursTime(SIX_FORTY_PM, EIGHT_PM);
    timeSlotToTimes.put(TimeSlot.L, timeSlotL);


    SectionTime timeSlotM = new SectionTime();
    timeSlotM.setSectionTime("mondayStart", THREE_PM);
    timeSlotM.setSectionTime("mondayEnd", THREE_PM + TWO_THIRTY_MINS);
    timeSlotToTimes.put(TimeSlot.M, timeSlotM);

    SectionTime timeSlotN = new SectionTime();
    timeSlotN.setSectionTime("wednesdayStart", THREE_PM);
    timeSlotN.setSectionTime("wednesdayEnd", THREE_PM + TWO_THIRTY_MINS);
    timeSlotToTimes.put(TimeSlot.N, timeSlotN);

    SectionTime timeSlotO = new SectionTime();
    timeSlotO.setSectionTime("fridayStart", THREE_PM);
    timeSlotO.setSectionTime("fridayEnd", THREE_PM + TWO_THIRTY_MINS);
    timeSlotToTimes.put(TimeSlot.O, timeSlotO);

    SectionTime timeSlotP = new SectionTime();
    timeSlotP.setSectionTime("tuesdayStart", FOUR_PM);
    timeSlotP.setSectionTime("tuesdayEnd", FOUR_PM + TWO_THIRTY_MINS);
    timeSlotToTimes.put(TimeSlot.P, timeSlotP);

    SectionTime timeSlotQ = new SectionTime();
    timeSlotQ.setSectionTime("thursdayStart", FOUR_PM);
    timeSlotQ.setSectionTime("thursdayEnd", FOUR_PM + TWO_THIRTY_MINS);
    timeSlotToTimes.put(TimeSlot.Q, timeSlotQ);

    SectionTime timeSlotR = new SectionTime();
    timeSlotR.setSectionTime("tuesdayStart", TWELVE_PM);
    timeSlotR.setSectionTime("tuesdayEnd", TWELVE_PM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.R, timeSlotR);

    SectionTime timeSlotS = new SectionTime();
    timeSlotS.setSectionTime("thursdayStart", TWELVE_PM);
    timeSlotS.setSectionTime("thursdayEnd", TWELVE_PM + FIFTY_MINS);
    timeSlotToTimes.put(TimeSlot.S, timeSlotS);

    SectionTime timeSlotT = new SectionTime();
    timeSlotT.setSectionTime("mondayStart", SIX_PM);
    timeSlotT.setSectionTime("mondayEnd", TEN_PM);
    timeSlotToTimes.put(TimeSlot.T, timeSlotT);

    SectionTime timeSlotU = new SectionTime();
    timeSlotU.setSectionTime("wednesdayStart", SIX_PM);
    timeSlotU.setSectionTime("wednesdayEnd", TEN_PM);
    timeSlotToTimes.put(TimeSlot.U, timeSlotU);

    SectionTime timeSlotV = new SectionTime();
    timeSlotV.setSectionTime("tuesdayStart", EIGHT_PM);
    timeSlotV.setSectionTime("tuesdayEnd", TEN_PM);
    timeSlotToTimes.put(TimeSlot.V, timeSlotV);

    SectionTime timeSlotW = new SectionTime();
    timeSlotW.setSectionTime("thursdayStart", EIGHT_PM);
    timeSlotW.setSectionTime("thursdayEnd", TEN_PM);
    timeSlotToTimes.put(TimeSlot.W, timeSlotW);


  }

}
