package edu.brown.cs.courseler.search;

import java.util.List;

public interface SearchSuggestions<T> {
  List<T> suggest(String searchTerm);
}
