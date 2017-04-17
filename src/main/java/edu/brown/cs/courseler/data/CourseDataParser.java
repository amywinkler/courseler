package edu.brown.cs.courseler.data;

import java.util.List;

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

  private SectionTime parseSectionTime(String timeSlot) {
    // TODO: return a section time
    return null;
  }

  public void parseBannerData() {
    // if course not in db, then create new course object

    // otherwise, just need to create new section object and add it to the
    // course
    // 1. check if section cache contains the current section id
    // 2a. if yes - done
    // 2b. if no -
  }

  /**
   * Gets the overlapping timeslots for a given section time.
   *
   * @param st
   *          The section time
   * @return a list of overlapping timselots
   */
  public static List<TimeSlot> getOverlappingTimeSlots(SectionTime st) {
    // get the coursetime for each timeslot, check if ct is within that
    // call overlapswithtimeslot
    return null;
  }

  /**
   * Method to parse the data from the critical review csv.
   */
  public void parseCritReviewData() {

    // try {
    //
    // CSVReader reader = new CSVReader(
    // new FileReader(
    // "/Users/amywinkler/term-project-adevor-awinkler-knakajim-nparrott/data/critreview.csv"),
    // '|');
    //
    // String[] nextLine;
    // int i = 0;
    // while ((nextLine = reader.readNext()) != null) {
    // // nextLine[] is an array of values from the line
    // if (i > 0) {
    // JSONObject jsonArrayOfObj = new JSONObject(nextLine[22]);
    // JSONObject obj = (JSONObject) jsonArrayOfObj.get("conc");
    // Set<String> ob2 = obj.keySet();
    // System.out.println("Hi");
    // // {'loved': how much you liked the course?
    // // 'requirement': did you take this course for a requirement?
    // // 'attendance': how much did you attend?
    // // 'non-concs': is this course good for non-concentrators
    // // 'effective': presented material effectively
    // // 'availableFeedback':is the prof available for feedback (why is this
    // // on 1-6)
    // // 'efficient': used class time efficiently
    // // 'receptive': is the prof available for feedback
    // // 'grading-speed': grading was timely,
    // // 'readings': **how much reading is there?
    // // 'grade': expected grade in the class
    // // 'conc': expected conc or non-conc?
    // // 'difficult': how hard the class is
    // // 'grading-fairness': how fair the grading is
    // // 'class-materials': class materials were useful
    // // 'passionate': was the instructor passionate about the material,
    // // 'learned': did you learn a lot?,
    // // 'encouraged': did the professor encourage questions and
    // // discussions}}
    // } else {
    //
    // }
    // i++;
    //
    // System.out.println("etc...");
    // }

    // } catch (IOException e) {
    // throw new RuntimeException("Unable to read csv");
    // }
  }

  public void parseGoogleFormData() {

  }
}
