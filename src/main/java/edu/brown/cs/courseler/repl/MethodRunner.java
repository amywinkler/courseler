package edu.brown.cs.courseler.repl;

import java.util.List;

/**
 * Interface for creating a class that gives methods for repl to run.
 *
 * @author awinkler
 *
 * @param <T>
 *          the type of object to be worked on
 */
public interface MethodRunner<T> {
  /**
   * Method for a given class to be passe into the general repl.
   *
   * @param currCmdStr
   *          - the current command string
   * @return a list of the objects being printed to the command line
   */
  List<T> run(String currCmdStr);

  /**
   * Method that checks if a command is for a given class of methods.
   *
   * @param currCmdStr
   *          The string of the current command
   * @return boolean representing whether or not the command is for class
   */
  boolean isCmdForClass(String currCmdStr);
}
