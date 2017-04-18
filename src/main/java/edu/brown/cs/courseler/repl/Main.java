package edu.brown.cs.courseler.repl;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import edu.brown.cs.courseler.api.RequestHandler;
/**
 * The Main class of our project. This is where execution begins.
 *
 * @author jj
 */
public final class Main {
  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args
   *          An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
    // CourseDataParser cdp = new CourseDataParser(new CourseDataCache());
  }

  private void run() {
    // Parse command line arguments
    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class)
        .defaultsTo(DEFAULT_PORT);
    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      RequestHandler handler = new RequestHandler();
      handler.runSparkServer((int) options.valueOf("port"));
    }
  }
}
