package edu.brown.cs.courseler.search;

import java.util.List;

/**
 * Interface for search suggestions.
 *
 * @author amywinkler
 *
 * @param <T>
 *          the type of object to return
 */
public interface SearchSuggestions<T> {
  /**
   * Method to make search suggestions.
   *
   * @param searchTerm
   *          the term to search on
   * @return a ranked list of suggestions
   */
  List<T> suggest(String searchTerm);
}
