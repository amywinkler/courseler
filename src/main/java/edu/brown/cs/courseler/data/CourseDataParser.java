package edu.brown.cs.courseler.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.com.bytecode.opencsv.CSVReader;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.MeetingLocation;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.courseinfo.SectionTime;
import edu.brown.cs.courseler.courseinfo.TimeSlot;

/**
 * Parse the data about courses.
 *
 * @author amywinkler
 *
 */
public class CourseDataParser {
  private CourseDataCache cache;

  /**
   * Constructor for coursedataparser.
   *
   * @param cache
   *          the cache of courses
   */
  public CourseDataParser(CourseDataCache cache) {
    this.cache = cache;
    parseBannerData();
    parseCritReviewData();
    parseGoogleFormData();
  }

  private void addTimesAndLocations(JSONArray meetingTimes,
      MeetingLocation locations, SectionTime sectionTime) {
    for (int i = 0; i < meetingTimes.size(); i++) {
      JSONObject meetingObject = (JSONObject) meetingTimes.get(i);
      String meetingTime = (String) meetingObject.get("meetingtime");
      String meetingLocation = (String) meetingObject.get("meetinglocation");
      String[] meetingTimeArr = meetingTime.split(" ");
      String meetingDay = meetingTimeArr[3];
      String meetingHourStr = meetingTimeArr[4];
      String[] meetingBeginEnd = meetingHourStr.split("-");
      Integer startTime = null;
      Integer endTime = null;
      if (meetingBeginEnd.length == 2) {
        startTime = Integer.parseInt(meetingBeginEnd[0]);
        endTime = Integer.parseInt(meetingBeginEnd[1]);
      }

      //set the meeting location
      switch (meetingDay) {
        case "M":
          locations.setLoc("M", meetingLocation);
          sectionTime.setSectionTime("mondayStart",
              startTime);
          sectionTime.setSectionTime("mondayEnd",
              endTime);
          break;
        case "T":
          locations.setLoc("T", meetingLocation);
          sectionTime.setSectionTime("tuesdayStart",
              startTime);
          sectionTime.setSectionTime("tuesdayEnd",
              endTime);
          break;
        case "W":
          locations.setLoc("W", meetingLocation);
          sectionTime.setSectionTime("wednesdayStart",
              startTime);
          sectionTime.setSectionTime("wednesdayEnd",
              endTime);
          break;
        case "R":
          locations.setLoc("R", meetingLocation);
          sectionTime.setSectionTime("thursdayStart",
              startTime);
          sectionTime.setSectionTime("thursdayEnd",
              endTime);
          break;
        case "F":
          locations.setLoc("F", meetingLocation);
          sectionTime.setSectionTime("fridayStart",
              startTime);
          sectionTime.setSectionTime("fridayEnd",
              endTime);
          break;
        case "MWF":
          sectionTime.addMonWedFriTime(startTime, endTime);
          locations.setLoc("M", meetingLocation);
          locations.setLoc("W", meetingLocation);
          locations.setLoc("F", meetingLocation);
          break;
        case "TR":
          sectionTime.addTuesThursTime(startTime, endTime);
          locations.setLoc("T", meetingLocation);
          locations.setLoc("R", meetingLocation);
          break;
        case "MTWR":
          sectionTime.setSectionTime("mondayStart",
              startTime);
          sectionTime.setSectionTime("mondayEnd",
              endTime);
          sectionTime.setSectionTime("wednesdayStart",
              startTime);
          sectionTime.setSectionTime("wednesdayEnd",
              endTime);
          sectionTime.addTuesThursTime(startTime, endTime);
          locations.setLoc("M", meetingLocation);
          locations.setLoc("T", meetingLocation);
          locations.setLoc("W", meetingLocation);
          locations.setLoc("R", meetingLocation);
          break;
        case "MW":
          sectionTime.setSectionTime("mondayStart",
              startTime);
          sectionTime.setSectionTime("mondayEnd",
              endTime);
          sectionTime.setSectionTime("wednesdayStart",
              startTime);
          sectionTime.setSectionTime("wednesdayEnd",
              endTime);
          locations.setLoc("M", meetingLocation);
          locations.setLoc("W", meetingLocation);
          break;
        default:
          System.out.println("Should't be here");
          break;
      }
    }
  }


  private void parseCourseSectionFromBanner(JSONObject courseJSON) {
    String sectionId = (String) courseJSON.get("subjectc");
    if (!cache.sectionCacheContains(sectionId)) {
      String[] nameArr = sectionId.split(" ");
      String courseId = nameArr[0] + " " + nameArr[1];

      JSONArray meetingTimes = (JSONArray) courseJSON.get("meet_time");
      MeetingLocation locations = new MeetingLocation();
      SectionTime sectionTime = new SectionTime();

      addTimesAndLocations(meetingTimes, locations, sectionTime);

      List<TimeSlot> overlaps = getTimeSlots(sectionTime);

      String title = (String) courseJSON.get("title");
      JSONArray instructors = (JSONArray) courseJSON.get("instructors");
      List<String> professors = new ArrayList<>();

      for (int i = 0; i < instructors.size(); i++) {
        JSONObject instructorObject = (JSONObject) instructors.get(i);
        String instructor = (String) instructorObject.get("instructor");
        professors.add(instructor);
      }

      Section sect = new Section(sectionId, courseId, title, professors,
          sectionTime, locations, overlaps);
      cache.addToSectionCache(sectionId, sect);

      Course currCourse = cache.getCourseFomCache(courseId);

      if (currCourse == null) {
        //Course hasn't been seen before, need to parse all course info
        currCourse = new Course(courseId);
        currCourse.setTitle(title);
        currCourse.setDepartment(nameArr[0]);
        currCourse.setCap(Integer.parseInt(courseJSON.get(
            "maxregallowed").toString()));
        currCourse.setCoursesDotBrownLink((String)
            courseJSON.get("course_preview"));
        currCourse.setPreReq((String) courseJSON.get("prereq"));
        currCourse.setDescription((String) courseJSON.get("description"));
        currCourse.addSectionObject(sect);
        cache.addToCourseCache(courseId, currCourse);

      } else {
        //Course exists, just add the section information.
        currCourse.addSectionObject(sect);

      }

      for (String prof: professors) {
        cache.addToProfCache(prof, currCourse);
      }
    }
  }

  private void parseBannerData() {
    JSONParser parser = new JSONParser();
    try {
      Object obj =  parser.parse(new FileReader(""
          + "/Users/amywinkler/term-project-adevor-awinkler-knakajim-nparrott/"
          + "data/banner2016.txt"));
      JSONObject jsonObj = (JSONObject) obj;
      JSONArray items = (JSONArray) jsonObj.get("items");
      for (int i = 0; i < items.size(); i++) {
        parseCourseSectionFromBanner((JSONObject) items.get(i));
      }
    } catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Gets the overlapping timeslots for a given section time.
   *
   * @param st
   *          The section time
   * @return a list of overlapping timselots
   */
  public List<TimeSlot> getTimeSlots(SectionTime time) {

    // get the coursetime for each timeslot, check if ct is within that
    // call overlapswithtimeslot
    Set<TimeSlot> uniqueSlotsWithOverlap = new HashSet<>();

    for (TimeSlot slot : TimeSlot.values()) {
      if (time.overlapsWithTimeSlot(
          cache.getTimeForTimeslot(slot))) {
        uniqueSlotsWithOverlap.add(slot);
      }
    }

    return new ArrayList<TimeSlot>(uniqueSlotsWithOverlap);
  }

  /**
   * Method to parse the data from the critical review csv.
   */
  public void parseCritReviewData() {

    try {
      CSVReader reader = new CSVReader(
          new FileReader(
          "/Users/amywinkler/term-project-adevor-awinkler-"
              + "knakajim-nparrott/data/critreview.csv"), '|');

      String[] nextLine;
      int i = 0;
      while ((nextLine = reader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        if (i > 0) {
          // JSONObject jsonArrayOfObj = JSONObject. nextLine[22].;
          // JSONObject obj = (JSONObject) jsonArrayOfObj.get("conc");
          // Set<String> ob2 = obj.keySet();

        } else {

        }
        i++;

        System.out.println("etc...");
      }

    } catch (IOException e) {
      throw new RuntimeException("Unable to read csv");
    }
  }

  public void parseGoogleFormData() {

  }
}
