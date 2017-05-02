package edu.brown.cs.courseler.userinfo;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

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
    proxy.clearTable();
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
    proxy.clearTable();
    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertTrue(alberta2.getTokenId().equals("incorrect_password"));
  }

  /**
   * Testing setting new user data.
   */
  @Test
  public void testSetNewUserData() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor1@brown.edu", "i_love_JJ");
    alberta.setInterests(ImmutableList.of("CSCI", "VISA", "HIAA"));
    alberta.setConcentration(ImmutableList.of("CSCI"));
    alberta.setClassYear("2019");
    try {
      proxy.setUserPreferenceData(alberta);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    User alberta2 = proxy
        .getUserFromEmailAndPassword("alberta_devor1@brown.edu", "i_love_JJ");
    proxy.clearTable();
    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertTrue(alberta2.getConcentration().get(0).equals("CSCI"));
    assertTrue(alberta2.getInterests().contains("VISA"));
    assertTrue(alberta2.getClassYear().equals("2019"));
  }

  /**
   * Testing setting new user data.
   */
  @Test
  public void testSetNewUserDataGettingFromId() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor1@brown.edu", "i_love_JJ");
    alberta.setInterests(ImmutableList.of("CSCI", "VISA", "HIAA"));
    alberta.setConcentration(ImmutableList.of("CSCI"));
    alberta.setClassYear("2019");
    try {
      proxy.setUserPreferenceData(alberta);
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    User alberta2 = proxy.getUserFromId(alberta.getTokenId());
    proxy.clearTable();
    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertTrue(alberta2.getConcentration().get(0).equals("CSCI"));
    assertTrue(alberta2.getInterests().contains("VISA"));
    assertTrue(alberta2.getClassYear().equals("2019"));
  }

  /**
   * Testing updating user cart.
   */
  @Test
  public void testUserCartChanges() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor1@brown.edu", "i_love_JJ");
    String myId = alberta.getTokenId();
    try {
      proxy.updateUserCart(alberta);
    } catch (SQLException e1) {
      e1.printStackTrace();
      proxy.clearTable();
      try {
        proxy.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    User alberta2 = proxy.getUserFromId(myId);

    assertTrue(alberta2.getSectionsInCart().size() == 0);
    alberta.addToCart("CSCI 0320 S01");
    try {
      proxy.updateUserCart(alberta);
    } catch (SQLException e1) {
      e1.printStackTrace();
      proxy.clearTable();
      try {
        proxy.closeConnection();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    User alberta3 = proxy.getUserFromId(myId);

    proxy.clearTable();
    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertTrue(alberta3.getSectionsInCart().size() == 1);
    assertTrue(alberta3.getSectionsInCart().get(0).equals("CSCI 0320 S01"));

  }

  /**
   * Testing getting user email from a share id.
   */
  @Test
  public void testGetEmailFromShareId() {
    DbProxy proxy = new DbProxy("test_users_1.sqlite3");
    User alberta = proxy.createNewUser("alberta_devor@brown.edu", "i_love_JJ");
    String email = proxy.getEmailForShareId(alberta.getShareId());

    proxy.clearTable();
    try {
      proxy.closeConnection();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    assertTrue(email.equals("alberta_devor@brown.edu"));
  }
}
