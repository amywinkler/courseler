package edu.brown.cs.courseler.userinfo;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import org.apache.commons.codec.binary.Hex;
import org.mindrot.jbcrypt.BCrypt;

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
   * Create a hash of user id for sharing link.
   *
   * @param id
   *          the id of the user.
   * @return the hashed id.
   * @throws NoSuchAlgorithmException
   */
  private String createShareHash(String id) throws NoSuchAlgorithmException {
    final MessageDigest messageDigest = MessageDigest.getInstance("MD5");
    messageDigest.reset();
    messageDigest.update(id.getBytes(Charset.forName("UTF8")));
    final byte[] resultByte = messageDigest.digest();
    return new String(Hex.encodeHex(resultByte));
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

    String hashedPw = BCrypt.hashpw(password, BCrypt.gensalt());
    String shareId;
    try {
      shareId = createShareHash(id);
    } catch (NoSuchAlgorithmException e1) {
      shareId = null;
    }
    user.setShareId(shareId);

    // Put into DB
    String query = "INSERT INTO users (email, password, id, share_id)"
        + " VALUES(?, ?, ?, ?);";

    PreparedStatement prep;
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, email);
      prep.setString(2, hashedPw);
      prep.setString(3, id);
      prep.setString(4, shareId);
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
   * @param user
   *          the user we want to update the data for.
   * @throws SQLException
   *           if there's an error with the sql query
   */
  public void setUserPreferenceData(User user) throws SQLException {
    // set new data about the user (concentration, interests, class year)

    StringBuilder interests = new StringBuilder();
    for (String interest : user.getInterests()) {
      interests.append(interest);
      interests.append(",");
    }
    String interestString = "";
    if (interests.length() > 1) {
      interests.deleteCharAt(interests.length() - 1); // delete the last comma
      interestString = interests.toString();
    }

    StringBuilder concentrations = new StringBuilder();
    for (String conc : user.getConcentration()) {
      concentrations.append(conc);
      concentrations.append(",");
    }
    String concentrationString = "";
    if (concentrations.length() > 1) {
      concentrations.deleteCharAt(concentrations.length() - 1); // delete the
                                                                // last comma
      concentrationString = concentrations.toString();
    }

    // Put into DB
    String update = "UPDATE users SET concentration = ?,"
        + " interests = ?, year = ?" + " WHERE id == ?;";

    PreparedStatement prep = conn.prepareStatement(update);
    prep.setString(1, concentrationString);
    prep.setString(2, interestString);
    prep.setString(3, user.getClassYear());
    prep.setString(4, user.getTokenId());
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
        if (BCrypt.checkpw(password, rs.getString("password"))) {
          user = new User(rs.getString("id"));
          String concentration = rs.getString("concentration");
          user.setClassYear(rs.getString("year"));

          if (concentration != null && concentration.length() > 1) {
            List<String> concentrationList = new ArrayList<>(
                Arrays.asList(concentration.split(",")));
            user.setConcentration(concentrationList);
          } else {
            user.setConcentration(new ArrayList<>());
          }

          String interests = rs.getString("interests");
          if (interests != null) {
            List<String> interestList =
                new ArrayList<>(Arrays.asList(interests.split(",")));
            user.setInterests(interestList);
          }
          String sections = rs.getString("sections_in_cart");
          if (sections != null) {
            List<String> sectionList =
                new ArrayList<>(Arrays.asList(sections.split(",")));
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
   * Gets sections in cart given a user's share id.
   *
   * @param shareId
   *          the share id of the user we're getting all of the data from
   * @return a list of course sections
   */
  public List<String> getSectionsFromShareId(String shareId) {
    if (conn == null) {
      return null;
    }
    String query = "SELECT sections_in_cart FROM users WHERE share_id  == ?";
    PreparedStatement prep;
    List<String> sectionList = new ArrayList<>();
    try {
      prep = conn.prepareStatement(query);
      prep.setString(1, shareId);
      ResultSet rs = prep.executeQuery();

      while (rs.next()) {
        String sections = rs.getString("sections_in_cart");

        if (sections != null && sections.length() > 1) {
          sectionList = new ArrayList<>(Arrays.asList(sections.split(",")));
        }
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      return null;
    }
    return sectionList;
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
        String concentration = rs.getString("concentration");
        user.setClassYear(rs.getString("year"));
        String interests = rs.getString("interests");
        user.setShareId(rs.getString("share_id"));
        String sections = rs.getString("sections_in_cart");

        if (sections != null && sections.length() > 1) {
          List<String> sectionList =
              new ArrayList<>(Arrays.asList(sections.split(",")));
          user.setCart(sectionList);
        } else {
          user.setCart(new ArrayList<>());
        }

        if (concentration != null && concentration.length() > 1) {
          List<String> concentrationList = new ArrayList<>(
              Arrays.asList(concentration
              .split(",")));
          user.setConcentration(concentrationList);
        } else {
          user.setConcentration(new ArrayList<>());
        }

        if (interests != null && interests.length() > 1) {
          List<String> interestList = new ArrayList<>(Arrays.asList(interests
              .split(",")));
          user.setInterests(interestList);
        } else {
          user.setInterests(new ArrayList<>());
        }

        if (interests != null && interests.length() > 1) {
          List<String> interestList =
              new ArrayList<>(Arrays.asList(interests.split(",")));
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
