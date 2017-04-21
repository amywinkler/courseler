package edu.brown.cs.coursler.userinfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * This is the class where we control the db. We get info about the user here!
 *
 * @author adevor
 *
 */
public class DbProxy {

  private Connection conn;

  /**
   * DbProxy constructor where we establish connection.
   *
   * @param dbFile
   *          the dbfile we're going to connect to
   */
  public DbProxy(String dbFile) {
    connectToUserDb(dbFile);
  }

  /**
   * Generate a random id for a new user.
   *
   * @return the random user id.
   */
  private String generateRandomId() {
    String tokenId = UUID.randomUUID().toString();
    while (tokenAlreadyInDb(tokenId)) {
      tokenId = UUID.randomUUID().toString();
    }
    return tokenId;
  }

  /**
   * Checks if a token is already in the db. If so we need to generate a new
   * random id.
   *
   * @param token
   *          the id of the user.
   * @return if the ID is already in the db
   */
  private Boolean tokenAlreadyInDb(String token) {
    if (conn == null) {
      return false;
    }
    String query = "SELECT id FROM users WHERE id == ?";
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, token);
      ResultSet rs = prep.executeQuery();
      while (rs.next()) {
        return true;
      }
    } catch (SQLException e) {
      return false;
    }
    return false;
  }

  /**
   * Generates new user object and puts the user data into the db.
   *
   * @param email
   *          the email of the user
   * @param password
   *          the password the user chose.
   * @return the user object
   */
  public User createNewUser(String email, String password) {

    String id = generateRandomId();
    User user = new User(id);

    // Put into DB
    String query = "INSERT INTO users (email, password, id) VALUES(?, ?, ?);";

    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, email);
      prep.setString(2, password);
      prep.setString(3, id);
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  /**
   * Updates user cart.
   *
   * @param user
   *          the user we're updating the cart for.
   * @throws SQLException
   *           if there's an error with the sql query
   */
  public void updateUserCart(User user) throws SQLException {
    // set cart data about user
    StringBuilder sections = new StringBuilder();
    for (String sectionId : user.getSectionsInCart()) {
      sections.append(sectionId);
      sections.append(",");
    }
    String sectionsString = "";
    if (sections.length() > 1) {
      sections.deleteCharAt(sections.length() - 1); // delete the last comma
      sectionsString = sections.toString();
    }
    String id = user.getTokenId();
    System.out.println("The id we're updating is " + id);

    // Put into DB
    String update = "UPDATE users SET sections_in_cart = ? WHERE id = ?";

    PreparedStatement prep = conn.prepareStatement(update);
    prep.setString(1, sectionsString);
    prep.setString(2, id);
    prep.executeUpdate();
    prep.close();
  }

  /**
   * Sets user data for an inputed user.
   *
   * @param id
   *          the id of the user we're updating.
   * @param concentration
   *          the concentration inputted
   * @param interests
   *          the dept interests e.g. "CSCI,VISA"
   * @param classYear
   *          the class year of the person
   * @param favClass
   *          the favorite class code of the person
   * @throws SQLException
   *           if there's an error with the sql query
   */
  public void setUserData(String id, String concentration, String interests,
      String classYear, String favClass) throws SQLException {
    // set new data about the user (concentration, interests, class year)

    // Put into DB
    String update = "UPDATE users SET concentration = ?,"
        + " interests = ?, year = ?, favClass = ?" + " WHERE id == ?;";

    PreparedStatement prep = conn.prepareStatement(update);
    prep.setString(1, concentration);
    prep.setString(2, interests);
    prep.setString(3, classYear);
    prep.setString(4, favClass);
    prep.setString(5, id);
    prep.executeUpdate();
    prep.close();
  }

  /**
   * Gets user including all available data given its email and password.
   * (login)
   *
   * @param email
   *          the email user inputed
   * @param password
   *          the inputed password
   * @return a user object with as much data as possible. If there was no user
   *         with the email we return null. If the inputed password is wrong we
   *         return a user who's id is the text "incorrect_password"
   */
  public User getUserFromEmailAndPassword(String email, String password) {
    if (conn == null) {
      return null;
    }
    String query = "SELECT * FROM users WHERE email  == ? ";
    PreparedStatement prep;
    User user = null;
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, email);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        if (rs.getString("password").equals(password)) {
          user = new User(rs.getString("id"));
          user.setClassYear(rs.getString("year"));
          // user.setEmail(rs.getString("email"));
          // user.setPassword(rs.getString("password"));
          user.setConcentration(rs.getString("concentration"));
          user.setClassYear(rs.getString("year"));

          String interests = rs.getString("interests");
          if (interests != null) {
            List<String> interestList = Arrays.asList(interests.split(","));
            user.setInterests(interestList);
          }
          String sections = rs.getString("sections_in_cart");
          if (sections != null) {
            List<String> sectionList = Arrays.asList(sections.split(","));
            user.setCart(sectionList);
          }

        } else {
          user = new User("incorrect_password");
        }
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return user;
  }

  /**
   * Gets user including all available data given its id.
   *
   * @param id
   *          the id of the user we're getting all of the data from
   * @return a user object with as much data as possible.
   */
  public User getUserFromId(String id) {
    if (conn == null) {
      return null;
    }
    String query = "SELECT * FROM users WHERE id  == ?";
    User user = new User(id);
    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, id);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        user.setClassYear(rs.getString("year"));
        // user.setEmail(rs.getString("email"));
        // user.setPassword(rs.getString("password"));
        user.setConcentration(rs.getString("concentration"));
        user.setClassYear(rs.getString("year"));
        String interests = rs.getString("interests");
        String sections = rs.getString("sections_in_cart");
        if (sections != null && sections.length() > 1) {
          List<String> sectionList = Arrays.asList(sections.split(","));
          user.setCart(sectionList);
        } else {
          user.setCart(new ArrayList<>());
        }
        if (interests != null && interests.length() > 1) {
          List<String> interestList = Arrays.asList(interests.split(","));
          user.setInterests(interestList);
        } else {
          user.setInterests(new ArrayList<>());
        }
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      return null;
    }
    return user;
  }

  private void connectToUserDb(String dbFile) {
    Connection connection = null;

    try {
      Class.forName("org.sqlite.JDBC");
      String urlToDB = "jdbc:sqlite:" + dbFile;
      connection = DriverManager.getConnection(urlToDB);

      Statement stat = connection.createStatement();
      stat.executeUpdate("PRAGMA foreign_keys = ON;");
      stat.close();
    } catch (SQLException e) {
      System.out.println("ERROR: Unable to connect to database");
      return;
    } catch (ClassNotFoundException e) {
      System.out.println(
          "ERROR: Could not open database due to ClassNotFoundException");
      return;
    }

    // Reset the DB Proxy connection and clear all caching
    this.conn = connection;
  }

  /**
   * Closes the db connection.
   *
   * @throws SQLException
   *           if closing fails.
   */
  public void closeConnection() throws SQLException {
    if (conn != null) {
      conn.close();
    }
  }

  /**
   * Clears the table that the db is associated with. For testing only.
   */
  public void clearTable() {

    // delete table data
    String query = "DELETE FROM users;";

    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.executeUpdate();
      prep.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return;
    }
  }
}
