package edu.brown.cs.courseler.api;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import spark.ExceptionHandler;
import spark.ModelAndView;
import spark.QueryParamsMap;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import spark.TemplateViewRoute;
import spark.template.freemarker.FreeMarkerEngine;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.brown.cs.courseler.courseinfo.Course;
import edu.brown.cs.courseler.courseinfo.Section;
import edu.brown.cs.courseler.data.CourseDataCache;
import edu.brown.cs.courseler.recommendation.Filter;
import edu.brown.cs.courseler.recommendation.RecommendationExecutor;
import edu.brown.cs.courseler.search.RankedSearch;
import edu.brown.cs.courseler.userinfo.DbProxy;
import edu.brown.cs.courseler.userinfo.User;
import edu.brown.cs.courseler.userinfo.UserCache;
import freemarker.template.Configuration;

/**
 * The main request handler class where all API calls can be called. All calls
 * from GUI come in from here.
 *
 * @author adevor
 *
 */
public final class RequestHandler {
  private static final Gson GSON = new GsonBuilder().serializeNulls().create();
  private DbProxy db;
  private static final int THE_NUMBER_NEEDED_FOR_IP = 7;
  private static final int MAX_SEARCH_LIST_SIZE = 35;
  private CourseDataCache courseCache;
  private UserCache userCache;

  /**
   * Constructs request handler.
   *
   * @param fileName
   *          the name of the file for the db.
   * @param courseCache
   *          the course data cache
   */
  public RequestHandler(String fileName, CourseDataCache courseCache) {
    db = new DbProxy(fileName);
    this.courseCache = courseCache;
    this.userCache = new UserCache(db);
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
    Spark.get("/timeslots", new TimeSlotHandler());
    Spark.post("/course", new CourseHandler());
    Spark.post("/addSection", new AddCartSectionHandler());
    Spark.post("/removeSection", new RemoveCartSectionHandler());
    Spark.post("/getCart", new GetCartHandler());
    Spark.post("/getSharedCart", new GetSharableCartHandler());
    Spark.get("/departments", new DepartmentHandler());
    Spark.post("/recommend", new ReccomendationHandler());
    Spark.post("/search", new SearchHandler());
    Spark.post("/getUserPrefs", new UserPrefHandler());
    Spark.post("/setUserPrefs", new SetUserPrefHandler());
    Spark.post("/getShareId", new GetShareIdHandler());
    Spark.get("/user/:userId", new GuestCartHandler(), freeMarker);
    Spark.post("/addEmoji", new AddEmojiHandler());
    Spark.post("/addAltName", new AddAltNameHandler());
    Spark.post("/addWord", new AddWordHandler());
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
   * Guest cart page.
   *
   * @author adevor
   *
   */
  private static final class GuestCartHandler implements TemplateViewRoute {
    @Override
    public ModelAndView handle(Request req, Response res) {
      HashMap<String, Object> variables = new HashMap<String, Object>();
      return new ModelAndView(variables, "guest_cart.ftl");
    }
  }

  /**
   * Get Share Id API for a user.
   *
   * @author adevor
   *
   */
  private final class GetShareIdHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      User user = userCache.getUserForId(id);
      if (user == null) {
        variables = ImmutableMap.of("status", "no_such_user");
      } else {
        String shareId = user.getShareId();
        variables = ImmutableMap.of("status", "success", "share_id", shareId);
      }
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to set user preferences!
   *
   * @author adevor
   *
   */
  private class SetUserPrefHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      User user = userCache.getUserForId(id);
      if (user == null) {
        variables = ImmutableMap.of("status", "no_such_user");
      } else {
        String concentration = qm.value("concentration");
        if (concentration != null) {
          List<String> concList =
              new ArrayList<>(Arrays.asList(concentration.split(",")));
          user.setConcentration(concList);
        }
        String interests = qm.value("interests");
        if (interests != null) {
          List<String> interestList =
              new ArrayList<>(Arrays.asList(interests.split(",")));
          user.setInterests(interestList);
        }
        String year = qm.value("year");
        if (year != null) {
          user.setClassYear(year);
        }
        try {
          db.setUserPreferenceData(user);
        } catch (SQLException e) {
          variables = ImmutableMap.of("status", "failure");
        }

        variables = ImmutableMap.of("status", "success");
      }
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to get user preferences!
   *
   * @author adevor
   *
   */
  private class UserPrefHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");

      User user = userCache.getUserForId(id);

      if (user == null) {
        variables = ImmutableMap.of("status", "does_not_exist");
      } else {
        String year = user.getClassYear();
        if (year == null) {
          year = "";
        }
        List<String> concentration = user.getConcentration();

        List<String> interests = user.getInterests();

        variables = ImmutableMap.of("status", "success", "id",
            user.getTokenId(), "preferences",
            ImmutableMap.of("class_year", year, "concentration", concentration,
                "dept_interests", interests));
      }
      return GSON.toJson(variables);
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
        variables =
            ImmutableMap.of("status", "success", "id", user.getTokenId());
      }
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to get cart for share id!
   *
   * @author adevor
   *
   */
  private class GetSharableCartHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String shareId = qm.value("id");
      List<String> sections = db.getSectionsFromShareId(shareId);
      List<Section> sectionList = new ArrayList<>();
      for (String section : sections) {
        Section sect = courseCache.getSectionFromCache(section);
        if (sect != null) {
          sectionList.add(sect);
        }

      }
      String email = db.getEmailForShareId(shareId);
      if (email == null) {
        email = "";
      }

      variables = ImmutableMap.of("status", "success", "sections", sectionList,
          "email", email);
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to get cart section!
   *
   * @author adevor
   *
   */
  private class GetCartHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      User user = userCache.getUserForId(id);
      if (user == null) {
        variables = ImmutableMap.of("status", "no_such_user");
      } else {
        List<String> sections = user.getSectionsInCart();
        List<Section> sectionList = new ArrayList<>();
        for (String section : sections) {
          Section sect = courseCache.getSectionFromCache(section);
          if (sect != null) {
            sectionList.add(sect);
          }
        }
        variables =
            ImmutableMap.of("status", "success", "sections", sectionList);
      }
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to add cart section!
   *
   * @author adevor
   *
   */
  private class AddCartSectionHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      String section = qm.value("section");
      User user = userCache.getUserForId(id);
      if (section == null) {
        variables = ImmutableMap.of("status", "null_section_error");
        return GSON.toJson(variables);
      }
      user.addToCart(section);
      try {
        db.updateUserCart(user);
      } catch (SQLException e) {
        variables = ImmutableMap.of("status", "failure");
        return GSON.toJson(variables);
      }
      variables = ImmutableMap.of("status", "success");
      return GSON.toJson(variables);
    }
  }

  /**
   * Processes a request to remove cart section!
   *
   * @author adevor
   *
   */
  private class RemoveCartSectionHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      Map<String, Object> variables;
      QueryParamsMap qm = req.queryMap();
      String id = qm.value("id");
      String section = qm.value("section");
      User user = userCache.getUserForId(id);
      try {
        user.removeFromCart(section);
      } catch (IllegalArgumentException e) {
        variables = ImmutableMap.of("status", "no_such_cart_object_failure");
        return GSON.toJson(variables);
      }

      try {
        db.updateUserCart(user);
      } catch (SQLException e) {
        variables = ImmutableMap.of("status", "failure");
        return GSON.toJson(variables);
      }
      variables = ImmutableMap.of("status", "success");
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
      if (email == null || pass == null) {
        variables = ImmutableMap.of("status", "null_input");
      }
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
   * Processes a get course request.
   *
   * @author amywinkler
   *
   */
  private class CourseHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String courseId = qm.value("courseId");
      Course currCourse = courseCache.getCourseFomCache(courseId);
      return GSON.toJson(currCourse);
    }
  }

  /**
   * Return an alphabetically sorted list of departments.
   *
   * @author amywinkler
   *
   */
  private class DepartmentHandler implements Route {
    @Override
    public String handle(Request req, Response res) {
      List<String> deptNames = courseCache.getDepartmentFullNameList();
      return GSON.toJson(deptNames);
    }
  }

  /**
   * Handler for recommendationss.
   *
   * @author amywinkler
   *
   */
  private class ReccomendationHandler implements Route {
    @Override
    public String handle(Request req, Response res) {

      QueryParamsMap qm = req.queryMap();

      String userId = qm.value("id");
      User currUser = userCache.getUserForId(userId);
      // open=true|false&hours=2&course_size=small|medium|large|any

      String open = qm.value("open");
      boolean openFilter = false;
      if (open != null) {
        openFilter = Boolean.parseBoolean(open);
      }

      String maxAverageHoursPerWeekStr = qm.value("hours");
      int maxAverageHoursPerWeek = Integer.parseInt(maxAverageHoursPerWeekStr);


      String courseSize = qm.value("course_size");


      String cappedCourses = qm.value("cap");


      List<Course> allCourses = courseCache.getAllCourses();
      Filter filter = new Filter(courseCache, currUser, openFilter,
          maxAverageHoursPerWeek, courseSize, cappedCourses);
      RecommendationExecutor allRecs =
          new RecommendationExecutor(currUser, filter, allCourses, courseCache);

      return GSON.toJson(allRecs.getRecommendations());
    }
  }

  /**
   * Handler for search.
   *
   * @author amywinkler
   *
   */
  private class SearchHandler implements Route {

    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String queryValue = qm.value("query");
      RankedSearch s = new RankedSearch(courseCache);
      List<Course> courses = s.rankedKeywordSearch(queryValue);
      while (courses.size() > MAX_SEARCH_LIST_SIZE) {
        courses.remove(courses.size() - 1);
      }

      return GSON.toJson(courses);

    }
  }

  /**
   * Handler for search.
   *
   * @author amywinkler
   *
   */
  private class TimeSlotHandler implements Route {

    @Override
    public String handle(Request req, Response res) {
      return GSON.toJson(courseCache.getTimeSlotToTimes());

    }
  }

  /**
   * Handler for emojis.
   *
   * @author amywinkler
   *
   */
  private class AddEmojiHandler implements Route {

    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String courseId = qm.value("courseId");
      String emoji = qm.value("emoji");
      Course currCourse = courseCache.getCourseFomCache(courseId);
      currCourse.addToFunAndCool("emojis", emoji);
      String strToWrite = "time" + ","
          + "courseler_user," + courseId + ",," + emoji + ",";
      writeLineToFile(strToWrite);
      return GSON.toJson("");

    }
  }

  /**
   * Handler for emojis.
   *
   * @author amywinkler
   *
   */
  private class AddAltNameHandler implements Route {

    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String courseId = qm.value("courseId");
      String altName = qm.value("altName");
      Course currCourse = courseCache.getCourseFomCache(courseId);
      currCourse.addToFunAndCool("alternate_titles", altName);
      String strToWrite = "time,courseler_user," + courseId + ",,,"
          + altName;
      writeLineToFile(strToWrite);
      return GSON.toJson("");

    }
  }

  /**
   * Handler for words.
   *
   * @author amywinkler
   *
   */
  private class AddWordHandler implements Route {

    @Override
    public String handle(Request req, Response res) {
      QueryParamsMap qm = req.queryMap();
      String courseId = qm.value("courseId");
      String word = qm.value("word");
      Course currCourse = courseCache.getCourseFomCache(courseId);
      currCourse.addToFunAndCool("descriptions", word);
      String strToWrite = "time,courseler_user," + courseId + "," + word + ",,";
      writeLineToFile(strToWrite);
      return GSON.toJson("");

    }
  }

  private void writeLineToFile(String strToWrite) {
    File file = new File("data/google_form_data.csv");
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
      bw.write("\n" + strToWrite);
    } catch (IOException except) {
      except.printStackTrace();
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
