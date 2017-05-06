package edu.brown.cs.courseler.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.com.bytecode.opencsv.CSVReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.CriticalReviewData;
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
  private static final int DEPT_CODE_IDX = 1;
  private static final int COURSE_NUM_IDX = 2;
  private static final int NUM_RESP_IDX = 6;
  private static final int FROSH_IDX = 7;
  private static final int SOPH_IDX = 8;
  private static final int JUN_IDX = 9;
  private static final int SEN_IDX = 10;
  private static final int GRAD_IDX = 11;
  private static final int TOTAL_STUD_IDX = 12;
  private static final int CONCS_IDX = 13;
  private static final int NON_CONCS_IDX = 14;
  private static final int DUNNO_IDX = 15;
  private static final int PROF_SCORE_IDX = 16;
  private static final int COURSE_SCORE_IDX = 17;
  private static final int MEAN_AVG_HOURS_IDX = 18;
  private static final int MEAN_MAX_HOURS_IDX = 20;
  private static final int TALLY_IDX = 22;



  /**
   * Constructor for coursedataparser.
   *
   * @param cache
   *          the cache of courses
   */
  public CourseDataParser(CourseDataCache cache) {
    this.cache = cache;
    parseDeptData();
    parseBannerData();
    parseCritReviewData();
    parseGoogleFormData();
  }

  private void parseDeptData() {
    File file = new File("data/department_codes.txt");

    try (Scanner sc = new Scanner(file)) {
      while (sc.hasNextLine()) {
        String str = sc.nextLine();
        String[] deptNameArr = str.split(",");
        cache.addToDepartmentMap(deptNameArr[1].trim(), deptNameArr[0]);
        cache.addToDepartmentFullNameList(deptNameArr[0]);
        cache.addToCorpus(deptNameArr[1].trim().toLowerCase());
        cache.addToDeptToEmojiMap(deptNameArr[0], deptNameArr[2]);
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
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
        case "WF":
          sectionTime.setSectionTime("fridayStart", startTime);
          sectionTime.setSectionTime("fridayEnd", endTime);
          sectionTime.setSectionTime("wednesdayStart", startTime);
          sectionTime.setSectionTime("wednesdayEnd", endTime);
          locations.setLoc("F", meetingLocation);
          locations.setLoc("W", meetingLocation);
          break;
        default:
          break;
      }
    }
  }


  private void parseCourseSectionFromBanner(JSONObject courseJSON) {

    String sectionId = (String) courseJSON.get("subject");

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
        String[] instructorArr = instructor.split(", ");
        if (instructorArr.length == 2) {
          instructor = instructorArr[1] + " " + instructorArr[0];
        }
        professors.add(instructor);
      }

      Section sect = new Section(sectionId, courseId, title, professors,
          sectionTime, locations, overlaps);

      String sectLetter = nameArr[2].substring(0, 1);
      sect.setIsMainSection(sectLetter.equals("S") || sectLetter.equals("M"));

      if (sectLetter.equals("S")) {
        sect.setSectionType("section");
      } else if (sectLetter.equals("L")) {
        sect.setSectionType("lab");
      } else if (sectLetter.equals("C")) {
        sect.setSectionType("conference");
      } else if (sectLetter.equals("F")) {
        sect.setSectionType("film screening");
      } else if (sectLetter.equals("M")) {
        sect.setSectionType("lecture");
      }
      cache.addToSectionCache(sectionId, sect);

      Course currCourse = cache.getCourseFomCache(courseId);

      if (currCourse == null) {
        //Course hasn't been seen before, need to parse all course info

        currCourse = new Course(courseId);
        cache.addToCorpus(courseId.toLowerCase());
        currCourse.setTitle(title);
        currCourse.setDepartment(cache.lookUpFullName(nameArr[0]));
        String emoji = cache.getEmojiForDept(cache.lookUpFullName(nameArr[0]));
        currCourse.setDeptEmoji(emoji);
        currCourse.addToFunAndCool("emojis", emoji);
        currCourse.setCap(Integer.parseInt(courseJSON.get(
            "maxregallowed").toString()));
        currCourse.setCoursesDotBrownLink((String)
            courseJSON.get("course_preview"));
        String prereq = (String) courseJSON.get("prereq");
        if (prereq != null) {
          currCourse.setPreReq(prereq);
        }

        currCourse.setTerm((String) courseJSON.get("tdesc"));
        currCourse.setDescription((String) courseJSON.get("description"));
        currCourse.addSectionObject(sect);
        cache.addToCourseCache(courseId, currCourse);
        cache.addToAllCourses(currCourse);

      } else {
        //Course exists, just add the section information.
        String prereq = (String) courseJSON.get("prereq");
        if (prereq != null) {
          currCourse.setPreReq(prereq);
        }

        currCourse.addSectionObject(sect);
      }

    }
  }

  private void parseBannerData() {
    JSONParser parser = new JSONParser();
    try {
      Object obj = parser.parse(new FileReader("data/2016_fall_full.txt"));
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
   * @param time
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
      CSVReader reader = new CSVReader(new FileReader("data/critreview.csv"),
          '|');

      String[] nextLine;


      nextLine = reader.readNext();

      while ((nextLine = reader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        Course currCourse = cache.getCourseFomCache(
            nextLine[DEPT_CODE_IDX] + " "
            + nextLine[COURSE_NUM_IDX]);
        if (currCourse != null) {
          CriticalReviewData cr = new CriticalReviewData();
          Double totalPeopleInClass = Double
              .parseDouble(nextLine[TOTAL_STUD_IDX]);
          Double numRespondents = Double
              .parseDouble(nextLine[NUM_RESP_IDX]);

          // frosh, etc. is over total
          cr.addDemographic("percent_freshmen",
              Double.parseDouble(nextLine[FROSH_IDX]) / totalPeopleInClass);
          cr.addDemographic("percent_sophomores",
              Double.parseDouble(nextLine[SOPH_IDX]) / totalPeopleInClass);
          cr.addDemographic("percent_juniors",
              Double.parseDouble(nextLine[JUN_IDX]) / totalPeopleInClass);
          cr.addDemographic("percent_seniors",
              Double.parseDouble(nextLine[SEN_IDX]) / totalPeopleInClass);
          cr.addDemographic("percent_grad",
              Double.parseDouble(nextLine[GRAD_IDX]) / totalPeopleInClass);

          // conc nonconc is over respondents
          cr.addDemographic("percent_concentrators",
              Double.parseDouble(nextLine[CONCS_IDX]) / numRespondents);
          cr.addDemographic("percent_non_concentrators",
              Double.parseDouble(nextLine[NON_CONCS_IDX]) / numRespondents);
          cr.addDemographic("percent_undecided",
              Double.parseDouble(nextLine[DUNNO_IDX]) / numRespondents);

          // add all the hours per week data
          cr.addHoursPerWeek("maximum",
              Double.parseDouble(nextLine[MEAN_MAX_HOURS_IDX]));
          cr.addHoursPerWeek("average",
              Double.parseDouble(nextLine[MEAN_AVG_HOURS_IDX]));

          // get the course and prof average.. set them during jsonarray
          Double profAvg = Double.parseDouble(nextLine[PROF_SCORE_IDX]) / 5.0;
          Double courseAvg =
              Double.parseDouble(nextLine[COURSE_SCORE_IDX]) / 5.0;

          JsonParser parser = new JsonParser();
          JsonElement parsed = parser.parse(nextLine[TALLY_IDX]);
          JsonObject tallys = parsed.getAsJsonObject();
          Double recommendedToNonConcentrators =
              getAverage(tallys.get("non-concs").getAsJsonObject());
          Double learnedALot =
              getAverage(tallys.get("learned").getAsJsonObject());
          Double difficulty =
              getAverage(tallys.get("difficult").getAsJsonObject());
          Double enjoyed =
              getAverage(tallys.get("loved").getAsJsonObject());

          cr.setAllScores(courseAvg, profAvg, recommendedToNonConcentrators,
              learnedALot, difficulty, enjoyed);

          currCourse.setCritReviewData(cr);
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Unable to read csv");
    }
  }

  private Double getAverage(JsonObject counts) {
    JsonElement ones = counts.get("1");
    double numOnes;
    if (ones == null) {
      numOnes = 0.0;
    } else {
      numOnes = ones.getAsDouble();
    }

    JsonElement twos = counts.get("2");
    double numTwos;
    if (twos == null) {
      numTwos = 0.0;
    } else {
      numTwos = twos.getAsDouble();
    }

    JsonElement threes = counts.get("3");
    double numThrees;
    if (threes == null) {
      numThrees = 0.0;
    } else {
      numThrees = threes.getAsDouble();
    }

    JsonElement fours = counts.get("4");
    double numFours;
    if (fours == null) {
      numFours = 0.0;
    } else {
      numFours = fours.getAsDouble();
    }

    JsonElement fives = counts.get("5");
    double numFives;
    if (fives == null) {
      numFives = 0.0;
    } else {
      numFives = fives.getAsDouble();
    }

    // sum up the number of respondents
    return ((numOnes + numTwos * 2.0 + numThrees * 3.0
        + numFours * 4.0 + numFives * 5.0)
        / (numOnes + numTwos + numThrees + numFours + numFives)) / 5.0;
  }

  private void parseGoogleFormData() {
    try {
      CSVReader reader = new CSVReader(new FileReader(
          "data/google_form_data.csv"));
      String[] nextLine;
      reader.readNext();

      while ((nextLine = reader.readNext()) != null) {
        // 2 is course
        // 3 is words to describe
        // 4 is emoji
        // 5 is alternative name
        String courseCode = nextLine[2].toUpperCase();
        Course currCourse = cache.getCourseFomCache(courseCode);
        if (currCourse != null) {
          if (!nextLine[5].trim().isEmpty()) {
            currCourse.addToFunAndCool("alternate_titles", nextLine[5]);
          }

          if (!nextLine[4].trim().isEmpty()) {
            currCourse.addToFunAndCool("emojis", nextLine[4]);
          }
          String[] wordsToDescribe = nextLine[3].split(", ");
          for (int i = 0; i < wordsToDescribe.length; i++) {
            if (!wordsToDescribe[i].trim().isEmpty()) {
              currCourse.addToFunAndCool("descriptions",
                  wordsToDescribe[i].toLowerCase());
            }
          }
        }
      }
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
