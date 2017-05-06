package edu.brown.cs.courseler.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Multiset;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.data.CourseDataCache;

/**
 * Class for aggregating Search Results.
 *
 * @author amywinkler
 *
 */
public class RankedSearch {
  private CourseDataCache cache;
  private Multiset<String> corpus;
  private Map<String, List<String>> whitespaceSuggestions;
  private Map<String, String> courseMappings;
  private Set<String> shortenings;
  private Set<String> smallWords;

  private DescriptionSearch descriptionSearch;
  private CourseCodeSearch courseCodeSearch;
  private TitleSearch titleSearch;
  /**
   * Constructor for search object.
   *
   * @param cache
   *          the course data cache
   */
  public RankedSearch(CourseDataCache cache) {
    this.cache = cache;
    this.corpus = cache.getCorpus();


    setUpMappingsAndShortenings();
    this.whitespaceSuggestions = new HashMap<>();
    this.descriptionSearch = new DescriptionSearch(cache);
    this.courseCodeSearch = new CourseCodeSearch(cache);
    this.titleSearch = new TitleSearch(cache);
    this.smallWords = new HashSet<>();
    setUpSmallWords();
  }

  private void setUpSmallWords() {
    smallWords.add("a");
    smallWords.add("b");
    smallWords.add("c");
    smallWords.add("d");
    smallWords.add("e");
    smallWords.add("f");
    smallWords.add("g");
    smallWords.add("h");
    smallWords.add("i");
    smallWords.add("j");
    smallWords.add("k");
    smallWords.add("l");
    smallWords.add("m");
    smallWords.add("n");
    smallWords.add("o");
    smallWords.add("p");
    smallWords.add("q");
    smallWords.add("r");
    smallWords.add("s");
    smallWords.add("w");
    smallWords.add("x");
    smallWords.add("y");
    smallWords.add("z");

    smallWords.add("an");
    smallWords.add("of");
    smallWords.add("th");

  }

  private void setUpMappingsAndShortenings() {
    this.shortenings = new HashSet<>(Arrays.asList("cs", "bio", "geo", "taps"));
    this.courseMappings = new HashMap<>();
    courseMappings.put("cs", "csci");
    courseMappings.put("bio", "biol");
    courseMappings.put("geo", "geol");
    courseMappings.put("ta", "taps");

  }

  private List<String> getWhitespaceSuggestions(String word) {
    List<String> toReturn = whitespaceSuggestions.get(word);
    if (toReturn == null) {
      toReturn = whitespaceSuggestions(word);
      whitespaceSuggestions.put(word, toReturn);
    }

    return toReturn;
  }

  private List<String> whitespaceSuggestions(String givenWord) {
    // store possible whitespace suggesitons in a list
    List<String> whitespaceWords = new ArrayList<>();
    for (int i = 1; i < givenWord.length() - 1; i++) {
      if (corpus.contains(givenWord.substring(0, i) + " "
          + givenWord.substring(i))) {
        whitespaceWords.add(givenWord.substring(0, i) + " "
            + givenWord.substring(i));
      } else if (corpus.contains(givenWord.substring(0, i))) {
        whitespaceWords.add(givenWord.substring(0, i));
      } else if (corpus.contains(givenWord.substring(i))) {
        whitespaceWords.add(givenWord.substring(i));
      }

      if (shortenings.contains(givenWord.substring(0, i))) {
        String fullWord = courseMappings.get(givenWord.substring(0, i));
        if (!givenWord.contains(fullWord)) {
          whitespaceWords.add(fullWord + " " + givenWord.substring(i));
        }

      }

    }

    return whitespaceWords;
  }

  private String searchNumberSuggestion(String query) {
    boolean hitNumber = false;
    int count = -1;
    while (!hitNumber && count < query.length()) {
      count++;
      hitNumber = StringUtils.isNumeric(query.substring(count));

    }

    if (hitNumber) {
      int lengthOfCourseCode = query.substring(count).length();

      if (lengthOfCourseCode == 2) {
        String toReturn = query.substring(0, count) + "0"
            + query.substring(count)
            + "0";
        return toReturn;
      } else if (lengthOfCourseCode == 3) {
        return query.substring(0, count) + "0" + query.substring(count);
      }


    }

    return null;
  }

  private void searchOnCriteria(SearchSuggestions<Course> criteria,
      List<Course> finalCourseList, List<String> wordsToSearch) {
    for (int i = 0; i < wordsToSearch.size(); i++) {
      // search on the whole word then the whitspace suggestions

      List<String> whitespaceWords = getWhitespaceSuggestions(wordsToSearch
          .get(i));
      whitespaceWords.add(wordsToSearch.get(i));
      String numberSug = searchNumberSuggestion(wordsToSearch.get(i));
      if (numberSug != null) {
        List<String> whitespaceWordsNum = getWhitespaceSuggestions(numberSug);
        for (String w : whitespaceWordsNum) {
          whitespaceWords.add(w);
        }
        whitespaceWords.add(numberSug);
      }

      for (String sugg : whitespaceWords) {

        List<Course> tempLst1 = criteria.suggest(sugg);
        for (Course c : tempLst1) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }
      }
    }

  }

  private void searchIndividualWords(List<Course> finalCourseList,
      List<String> wordsToSearch) {
    searchOnCriteria(titleSearch, finalCourseList, wordsToSearch);
    searchOnCriteria(descriptionSearch, finalCourseList, wordsToSearch);
  }



  /**
   * Method to do a keyword search. Returns the resulting courses in ranked
   * order.
   *
   * @param entireSearch
   *          the entire search term
   * @return list of resulting courses in ranked order
   */
  public List<Course> rankedKeywordSearch(String entireSearch) {
    entireSearch = entireSearch.toLowerCase().trim();

    List<Course> finalCourseList = new ArrayList<>();


    if (StringUtils.isNumeric(entireSearch)) {
      List<Course> numericSearchRes = courseCodeSearch
          .suggestNumeric(entireSearch);
      for (Course c : numericSearchRes) {
        if (!finalCourseList.contains(c)) {
          finalCourseList.add(c);
        }
      }

      List<Course> titleSuggestionsFull = titleSearch.suggest(entireSearch);
      for (Course c : titleSuggestionsFull) {
        if (!finalCourseList.contains(c)) {
          finalCourseList.add(c);
        }
      }

      List<Course> descriptionSuggestionsFull = descriptionSearch
          .suggest(entireSearch);
      for (Course c : descriptionSuggestionsFull) {
        if (!finalCourseList.contains(c)) {
          finalCourseList.add(c);
        }
      }
    } else {
      finalCourseList = courseCodeSearch.suggest(entireSearch);

      String sugg = searchNumberSuggestion(entireSearch);
      if (sugg != null) {
        List<Course> courseCodeResultsOuter = courseCodeSearch.suggest(sugg);
        for (Course c : courseCodeResultsOuter) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }
      }

      if (finalCourseList.size() == 0) {
        List<Course> titleSuggestionsFull = titleSearch.suggest(entireSearch);
        for (Course c : titleSuggestionsFull) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }

        List<Course> descriptionSuggestionsFull = descriptionSearch
            .suggest(entireSearch);
        for (Course c : descriptionSuggestionsFull) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }
      }

      String[] searchWordsSplit = entireSearch.trim().split(" ");
      if (searchWordsSplit.length == 2) {
        String putTogether = searchWordsSplit[0] + searchWordsSplit[1];
        List<Course> courseCodeResults = courseCodeSearch.suggest(putTogether);
        for (Course c : courseCodeResults) {
          if (!finalCourseList.contains(c)) {
            finalCourseList.add(c);
          }
        }

        String suggN = searchNumberSuggestion(putTogether);
        if (suggN != null) {
          List<Course> courseCodeResults2 = courseCodeSearch.suggest(suggN);
          for (Course c : courseCodeResults2) {
            if (!finalCourseList.contains(c)) {
              finalCourseList.add(c);
            }
          }
        }
      }



      if (finalCourseList.size() == 0) {
        List<String> wordsToSearch = new ArrayList<>();
        if (searchWordsSplit.length <= 5) {
          // search on each word
          for (int i = 0; i < searchWordsSplit.length; i++) {
            if (cache.getDeptForCode(searchWordsSplit[i].toUpperCase()) == null
                && !StringUtils.isNumeric(searchWordsSplit[i])
                && !smallWords.contains(searchWordsSplit[i])) {
              wordsToSearch.add(searchWordsSplit[i]);
            }

          }
          searchIndividualWords(finalCourseList, wordsToSearch);

        } else {
          // search on only the last 5 words
          for (int i = searchWordsSplit.length - 1; i > searchWordsSplit.length - 5; i--) {
            if (cache.getDeptForCode(searchWordsSplit[i].toUpperCase()) == null
                && !StringUtils.isNumeric(searchWordsSplit[i])
                && !smallWords.contains(searchWordsSplit[i])) {
              wordsToSearch.add(searchWordsSplit[i]);
            }
          }
          searchIndividualWords(finalCourseList, wordsToSearch);
        }
      }
    }

    return finalCourseList;
  }

}
