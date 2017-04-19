package edu.brown.cs.courseler.courseinfo;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SectionTimeTest {
  @Test
  public void overlapsWithTimeSlotTestContainedWithin() {
    SectionTime st = new SectionTime();
    st.addMonWedFriTime(800, 1000);
    SectionTime st2 = new SectionTime();
    st2.addMonWedFriTime(900, 1000);
    assertTrue(st.overlapsWithTimeSlot(st2));
    assertTrue(st2.overlapsWithTimeSlot(st));

  }

}
