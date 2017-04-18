package edu.brown.cs.courseler.api;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import edu.brown.cs.coursler.userinfo.DbProxy;
import edu.brown.cs.coursler.userinfo.User;
import freemarker.template.Configuration;
import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * The main request handler class where all API calls can be called. All calls
 * from GUI come in from here.
 *
 * @author adevor
 *
 */
public final class RequestHandler {

  private static final Gson GSON = new Gson();
  private DbProxy db;

  /**
   * Constructs request handler.
   */
  public RequestHandler() {
    // Someone remind me to switch this to the live db when the time is right.
    db = new DbProxy("test_users_1.sqlite3");
  }

  /**
   * Run the spark server!
   *
   * @param port
   *          the port it runs at.
   */
  public void runSparkServer(int port) {

    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");
    Spark.exception(Exception.class, new ExceptionPrinter());

    FreeMarkerEngine freeMarker = createEngine();

    // Setup Spark Routes:
    Spark.get("/", new MainHandler(), freeMarker);
    Spark.post("/login", new LoginHandler());

  }

  /**
   * Main homepage.
   *
   * @author nateparrott
   *
   */
  private static final class MainHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      HashMap<String, Object> variables = new HashMap<String, Object>();
      return new ModelAndView(variables, "main.ftl");
    }
  }

  /**
   * Processes a request to log in!
   *
   * @author adevor
   *
   */
  private class LoginHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String email = qm.value("email");
      String pass = qm.value("password");
      User user = db.getUserFromEmailAndPassword(email, pass);
      if (user == null) {
        variables = ImmutableMap.of("status", "unregistered");
      } else if (user.getTokenId().equals("incorrect_password")) {
        variables = ImmutableMap.of("status", "wrong_password");
      } else {
        String year = user.getClassYear();
        if (year == null) {
          year = "";
        }
        String concentration = user.getConcentration();
        if (concentration == null) {
          concentration = "";
        }
        String favClass = user.getFavClassCode();
        if (favClass == null) {
          favClass = "";
        }
        List<String> interests = user.getInterests();

        variables = ImmutableMap.of("status", "success", "id",
            user.getTokenId(), "sections_in_cart",
            ImmutableMap.of("class_year", year, "concentration", concentration,
                "favorite_class", favClass, "dept_interests", interests));

      }

      return GSON.toJson(variables);
    }
  }

  /**
   * Display an error page when an exception occurs in the server.
   *
   * @author jj
   */
  private static class ExceptionPrinter implements ExceptionHandler {
    @Override
    public void handle(Exception e, Request req, Response res) {
      res.status(500);

      StringWriter stacktrace = new StringWriter();
      try (PrintWriter pw = new PrintWriter(stacktrace)) {
        pw.println("<pre>");

        e.printStackTrace(pw);
        pw.println("</pre>");
      }
      res.body(stacktrace.toString());
    }
  }

  private static FreeMarkerEngine createEngine() {
    Configuration config = new Configuration();
    File templates = new File("src/main/resources/spark/template/freemarker");
    try {
      config.setDirectoryForTemplateLoading(templates);
    } catch (IOException ioe) {
      System.out.printf("ERROR: Unable use %s for template loading.%n",
          templates);
      System.exit(1);
    }
    return new FreeMarkerEngine(config);
  }

}
