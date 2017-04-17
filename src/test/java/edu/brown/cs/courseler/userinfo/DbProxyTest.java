package edu.brown.cs.courseler.userinfo;

import static org.junit.Assert.assertTrue;

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
    DbProxy proxy = new DbProxy("test_users.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor@brown.edu", "i_love_JJ");
    User alberta2 = proxy.getUserFromEmailAndPassword("alberta_devor@brown.edu",
        "i_love_JJ");
    assertTrue(alberta.getTokenId().equals(alberta2.getTokenId()));
  }
}
