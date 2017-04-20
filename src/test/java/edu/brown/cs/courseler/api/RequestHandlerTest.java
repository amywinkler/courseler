package edu.brown.cs.courseler.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import edu.brown.cs.courseler.data.CourseDataCache;

/**
 * JUnit testing for request handler.
 *
 * @author adevor
 *
 */
public class RequestHandlerTest {
  /**
   * JUnit test for ip validation.
   */
  @Test
  public void testIpVerification() {
    RequestHandler handler = new RequestHandler("test_users_1.sqlite3",
        new CourseDataCache());
    Boolean nah = handler.isIpValid("192.91.111.111"); // brown guest pattern
    assertTrue(!nah);
    Boolean yeah = handler.isIpValid("138.16.111.111");
    // secure brown connection
    assertTrue(yeah);
    Boolean alsoYeah = handler.isIpValid("128.148.111.111");
    // secure brown address
    assertTrue(alsoYeah);
  }
}
