package edu.brown.cs.coursler.userinfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
   * DbPrxy constructor where we establish connection.
   *
   * @param connection
   *          the connection to the Db
   */
  public DbProxy(Connection connection) {
    if (connection == null) {
      throw new NullPointerException("Connection is null");
    }
    conn = connection;
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
    // TODO: create new user object and generate id and put user into db
    return null;
  }

  /**
   * Set user data.
   */
  public void setUserData() {
    // set new data about the user (concentration, interests, class year)
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
        user.setFavClassCode(rs.getString("favClass"));
        String interests = rs.getString("interests");
        List<String> interestList = Arrays.asList(interests.split(","));
        user.setInterests(interestList);
      }
      rs.close();
      prep.close();
    } catch (SQLException e) {
      return null;
    }
    return user;
  }
}
