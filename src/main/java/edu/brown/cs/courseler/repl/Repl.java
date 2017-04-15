package edu.brown.cs.courseler.repl;

import java.util.List;
import java.util.Scanner;

/**
 * Class to create a repl.
 *
 * @author awinkler
 *
 */
public class Repl {
  private String currCmdStr;
  private Scanner s;
  private List<MethodRunner<?>> methodRunner;

  /**
   * Constructor for repl.
   *
   * @param methodRunner
   *          the method runner for the repl
   */
  public Repl(List<MethodRunner<?>> methodRunner) {
    currCmdStr = "";
    s = new Scanner(System.in, "UTF-8");
    this.methodRunner = methodRunner;
  }

  /**
   * Method to run the REPL.
   */
  public void run() {
    boolean foundCmd = false;
    while (s.hasNextLine()) {
      currCmdStr = s.nextLine();

      for (int i = 0; i < methodRunner.size(); i++) {
        if (methodRunner.get(i).isCmdForClass(currCmdStr)) {
          foundCmd = true;
          methodRunner.get(i).run(currCmdStr);
        }
      }

      if (!foundCmd) {
        System.out.println("ERROR: Command not found");
      }

      foundCmd = false;
    }
  }
}
