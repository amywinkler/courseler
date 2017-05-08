package edu.brown.cs.courseler.recommendation;

import java.util.List;

/**
 * Interface for reccomendations.
 *
 * @author amywinkler
 *
 * @param <T>
 *          the type of reccomendations
 */
public interface Recommend<T> {
  /**
   * Method to get the reccomendations.
   *
   * @return an ordered list of reccomendations
   */
  List<T> getRecommendations();

}
