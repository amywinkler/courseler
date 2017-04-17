package edu.brown.cs.courseler.userinfo;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import edu.brown.cs.coursler.userinfo.DbProxy;
import edu.brown.cs.coursler.userinfo.User;

/**
 * JUnit testing for the db proxy.
 *
 * @author adevor
 *
 */
public class DbProxyTest {

  /**
   * Testing creating new user and getting back info.
   */
  @Test
  public void testCreateNewUser() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor@brown.edu", "i_love_JJ");
    User alberta2 = proxy.getUserFromEmailAndPassword("alberta_devor@brown.edu",
        "i_love_JJ");

    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertTrue(alberta.getTokenId().equals(alberta2.getTokenId()));
  }

  /**
   * Testing getting the wrong password.
   */
  @Test
  public void testGetIncorrectPassword() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    proxy.createNewUser("alberta_devor1@brown.edu", "i_love_JJ");
    User alberta2 = proxy.getUserFromEmailAndPassword(
        "alberta_devor1@brown.edu", "haha_wrong_pass");

    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    assertTrue(alberta2.getTokenId().equals("incorrect_password"));
    // assertTrue(true);
  }
}
