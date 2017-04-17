package edu.brown.cs.coursler.userinfo;

import java.sql.Connection;

/**
 * A cache of user objects.
 *
 * @author adevor
 *
 */
public class UserCache {

  private Connection conn;

  /**
   * Constructor for User Cache.
   */
  public UserCache() {
    conn = null;
  }

}
