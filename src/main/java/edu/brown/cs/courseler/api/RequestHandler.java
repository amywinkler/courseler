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
  private static final int THE_NUMBER_NEEDED_FOR_IP = 7;

  /**
   * Constructs request handler.
   *
   * @param fileName
   *          the name of the file for the db.
   */
  public RequestHandler(String fileName) {
    db = new DbProxy(fileName);
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
    Spark.post("/signup", new SignupHandler());
    Spark.get("/ipVerify", new IPVerificationHandler());
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

  boolean isIpValid(String ip) {
    if (ip.length() < THE_NUMBER_NEEDED_FOR_IP) {
      return false;
    }
    String frontOfIp = ip.substring(0, THE_NUMBER_NEEDED_FOR_IP);
    if ((frontOfIp.equals("128.148") || frontOfIp.equals("138.16.")
        || frontOfIp.equals("0:0:0:0"))) {
      // 0:0:0: represents localhost - no one outside of brown's campus better
      // have this on their localhost
      return true;
    }
    return false;
  }

  /**
   * Processes a request to verify ip address.
   *
   * @author adevor
   *
   */
  private class IPVerificationHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      String clientIp = req.ip();
      System.out.println(clientIp);
      Map<String, Object> variables;
      if (isIpValid(clientIp)) {
        variables = ImmutableMap.of("status", "valid");
      } else {
        variables = ImmutableMap.of("status", "invalid");
      }
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to sign up!
   *
   * @author adevor
   *
   */
  private class SignupHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String email = qm.value("email");
      String pass = qm.value("password");
      User alreadyExistingUserWithThatEmail =
          db.getUserFromEmailAndPassword(email, pass);
      if (alreadyExistingUserWithThatEmail != null) {
        variables = ImmutableMap.of("status", "already_registered");
      } else {
        User newUser = db.createNewUser(email, pass);
        variables =
            ImmutableMap.of("status", "success", "id", newUser.getTokenId());
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
