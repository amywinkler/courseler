package edu.brown.cs.courseler.repl;

import java.util.ArrayList;
import java.util.List;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import edu.brown.cs.courseler.api.CourselerMethodRunner;
import edu.brown.cs.courseler.api.RequestHandler;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.data.CourseDataParser;
/**
 * The Main class of our project. This is where execution begins.
 *
 * @author jj
 */
public final class Main {
  private static final int DEFAULT_PORT = 4567;
  private static CourseDataCache cdc;
  private static CourselerMethodRunner cmr;

  /**
   * The initial method called when execution begins.
   *
   * @param args
   *          An array of command line arguments
   */
  public static void main(String[] args) {
    cdc = new CourseDataCache();
    CourseDataParser cdp = new CourseDataParser(cdc);
    cmr = new CourselerMethodRunner(cdc);

    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {
    //printAllCourses();
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

    List<MethodRunner<?>> methodRunners = new ArrayList<>();
    methodRunners.add(cmr);
    Repl repl = new Repl(methodRunners);
    repl.run();

  }
}
