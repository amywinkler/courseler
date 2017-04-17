package edu.brown.cs.courseler.data;

import java.util.List;

import edu.brown.cs.courseler.courseinfo.CourseTime;
import edu.brown.cs.courseler.courseinfo.TimeSlot;

public class CourseDataParser {

  // takes in a courseDataCache, in constructor, call parse banner then crit
  // review then fun and cool

  public static List<TimeSlot> getOverlappingTimeSlots(CourseTime ct) {
    // get the coursetime for each timeslot, check if ct is within that
    return null;
  }

  /**
   * Method to parse the data from the critical review csv.
   */
  public void parseCritReviewData() {
    // File csvData = new File(
    // "/Users/amywinkler/term-project-adevor-awinkler-knakajim-nparrott/data/critreview.csv");
    // try {
    // // CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8,
    // // CSVFormat.EXCEL);
    // //
    // // CsvReader rdr
    // // for (CSVRecord record : parser) {
    // // String[] rec = record.getValues();
    // // System.out.println(rec);
    // // }
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

  public void parseBannerData() {

  }

  public void parseGoogleFormData() {

  }
}
