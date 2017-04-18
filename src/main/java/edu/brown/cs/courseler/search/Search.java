package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.List;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;

public class Search {
  private CourseDataCache cache;

  public Search(CourseDataCache cache){
    this.cache = cache;
  }

  public List<Course> rankedKeywordSearch(String entireSearch){
    entireSearch = entireSearch.toLowerCase();
    DescriptionTitleSearch dt = new DescriptionTitleSearch(cache);

    List<Course> finalCourseList = new ArrayList<>();
    finalCourseList = dt.suggest(entireSearch);
    //TODO break up how to do keyword search
    String[] searchWordsSplit = entireSearch.split(" ");
    if (searchWordsSplit.length <= 5){
      //search on each word
      for (int i = searchWordsSplit.length - 1; i > 0; i--) {
        List<Course> tempLst = dt.suggest(searchWordsSplit[i]);
        for (Course c: tempLst){
          if (!finalCourseList.contains(c)){
            finalCourseList.add(c);
          }
        }
      }
    } else {
      for (int i = searchWordsSplit.length - 1; i > searchWordsSplit.length - 5;
          i--) {
        List<Course> tempLst = dt.suggest(searchWordsSplit[i]);
        for (Course c: tempLst) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }
      }
      //search on only the last 5 words
    }

    return finalCourseList;
  }

}
