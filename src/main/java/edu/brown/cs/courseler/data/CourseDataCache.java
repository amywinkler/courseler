package edu.brown.cs.courseler.data;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.SectionTime;
import edu.brown.cs.courseler.courseinfo.TimeSlot;

public class CourseDataCache {
  private Map<TimeSlot, SectionTime> timeSlotToTimes;
  private Multimap<String, Course> coursesForProf;
  private Map<String, Course> courseIdToCourse;
  private Map<String, Section> sectionIdToSection;

  /**
   * Constructor for CourseDataCache.
   */
  public CourseDataCache() {
    setUpTimeSlots();
    coursesForProf = ArrayListMultimap.create();
    courseIdToCourse = new HashMap<>();
    sectionIdToSection = new HashMap<>();
  }

  public SectionTime getTimeForTimeslot(TimeSlot t){
    return timeSlotToTimes.get(t);
  }

  public boolean sectionCacheContains(String sectionId){
    return sectionIdToSection.containsKey(sectionId);
  }

  public void addToProfCache(String prof, Course course){
    coursesForProf.put(prof, course);
  }

  public void addToCourseCache(String id, Course c){
    courseIdToCourse.put(id, c);
  }

  /**
   * Get the course from the course cache.
   * @param courseId
   * @return
   */
  public Course getCourseFomCache(String courseId) {
    return courseIdToCourse.get(courseId);
  }

  public void addToSectionCache(String id, Section s){
    sectionIdToSection.put(id, s);
  }


  private void setUpTimeSlots() {
    timeSlotToTimes = new EnumMap<TimeSlot, SectionTime>(TimeSlot.class);
    SectionTime timeSlotA = new SectionTime();
    timeSlotA.addMonWedFriTime(800, 850);
    timeSlotToTimes.put(TimeSlot.A, timeSlotA);

    SectionTime timeSlotB = new SectionTime();
    timeSlotB.addMonWedFriTime(900, 950);
    timeSlotToTimes.put(TimeSlot.B, timeSlotB);

    SectionTime timeSlotC = new SectionTime();
    timeSlotC.addMonWedFriTime(1000, 1050);
    timeSlotToTimes.put(TimeSlot.C, timeSlotC);

    SectionTime timeSlotD = new SectionTime();
    timeSlotD.addMonWedFriTime(1100, 1150);
    timeSlotToTimes.put(TimeSlot.D, timeSlotD);

    SectionTime timeSlotE = new SectionTime();
    timeSlotE.addMonWedFriTime(1200, 1250);
    timeSlotToTimes.put(TimeSlot.E, timeSlotE);

    SectionTime timeSlotF = new SectionTime();
    timeSlotF.addMonWedFriTime(1300, 1350);
    timeSlotToTimes.put(TimeSlot.F, timeSlotF);

    SectionTime timeSlotG = new SectionTime();
    timeSlotG.addMonWedFriTime(1400, 1450);
    timeSlotToTimes.put(TimeSlot.G, timeSlotG);

    SectionTime timeSlotH = new SectionTime();
    timeSlotH.addTuesThursTime(900, 1020);
    timeSlotToTimes.put(TimeSlot.H, timeSlotH);

    SectionTime timeSlotI = new SectionTime();
    timeSlotI.addTuesThursTime(1030, 1150);
    timeSlotToTimes.put(TimeSlot.I, timeSlotI);

    SectionTime timeSlotJ = new SectionTime();
    timeSlotJ.addTuesThursTime(1300, 1420);
    timeSlotToTimes.put(TimeSlot.J, timeSlotJ);

    SectionTime timeSlotK = new SectionTime();
    timeSlotK.addTuesThursTime(1430, 1550);
    timeSlotToTimes.put(TimeSlot.K, timeSlotK);

    SectionTime timeSlotL = new SectionTime();
    timeSlotL.addTuesThursTime(1840, 2000);
    timeSlotToTimes.put(TimeSlot.L, timeSlotL);


    SectionTime timeSlotM = new SectionTime();
    timeSlotM.setSectionTime("mondayStart", 1500);
    timeSlotM.setSectionTime("mondayEnd", 1730);
    timeSlotToTimes.put(TimeSlot.M, timeSlotM);

    SectionTime timeSlotN = new SectionTime();
    timeSlotN.setSectionTime("wednesdayStart", 1500);
    timeSlotN.setSectionTime("wednesdayEnd", 1730);
    timeSlotToTimes.put(TimeSlot.N, timeSlotN);

    SectionTime timeSlotO = new SectionTime();
    timeSlotO.setSectionTime("fridayStart", 1500);
    timeSlotO.setSectionTime("fridayEnd", 1730);
    timeSlotToTimes.put(TimeSlot.O, timeSlotO);

    SectionTime timeSlotP = new SectionTime();
    timeSlotP.setSectionTime("tuesdayStart", 1600);
    timeSlotP.setSectionTime("tuesdayEnd", 1830);
    timeSlotToTimes.put(TimeSlot.P, timeSlotP);

    SectionTime timeSlotQ = new SectionTime();
    timeSlotQ.setSectionTime("thursdayStart", 1600);
    timeSlotQ.setSectionTime("thursdayEnd", 1830);
    timeSlotToTimes.put(TimeSlot.Q, timeSlotQ);

    SectionTime timeSlotR = new SectionTime();
    timeSlotR.setSectionTime("tuesdayStart", 1200);
    timeSlotR.setSectionTime("tuesdayEnd", 1250);
    timeSlotToTimes.put(TimeSlot.R, timeSlotR);

    SectionTime timeSlotS = new SectionTime();
    timeSlotS.setSectionTime("thursdayStart", 1200);
    timeSlotS.setSectionTime("thursdayEnd", 1250);
    timeSlotToTimes.put(TimeSlot.S, timeSlotS);

    SectionTime timeSlotT = new SectionTime();
    timeSlotT.setSectionTime("mondayStart", 1800);
    timeSlotT.setSectionTime("mondayEnd", 2200);
    timeSlotToTimes.put(TimeSlot.T, timeSlotT);

    SectionTime timeSlotU = new SectionTime();
    timeSlotU.setSectionTime("wednesdayStart", 1800);
    timeSlotU.setSectionTime("wednesdayEnd", 2200);
    timeSlotToTimes.put(TimeSlot.U, timeSlotU);

    SectionTime timeSlotV = new SectionTime();
    timeSlotV.setSectionTime("tuesdayStart", 2000);
    timeSlotV.setSectionTime("tuesdayEnd", 2200);
    timeSlotToTimes.put(TimeSlot.V, timeSlotV);

    SectionTime timeSlotW = new SectionTime();
    timeSlotW.setSectionTime("thursdayStart", 2000);
    timeSlotW.setSectionTime("thursdayEnd", 2200);
    timeSlotToTimes.put(TimeSlot.W, timeSlotW);

  }

}
