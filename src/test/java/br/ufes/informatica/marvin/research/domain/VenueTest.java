package br.ufes.informatica.marvin.research.domain;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO: document this type.
 *
 * @author Luan Thome (https://github.com/luanripax/)
 */
public class VenueTest {
  /** TODO: document this field. */
  private Venue venue;

  /**
   * TODO: document this method.
   */
  @Before
  public void setup() {
    venue = new Venue();
  }

  /**
   * TODO: document this method.
   */
  @Test
  public void testSetCategoryToConference() {
    String category = "Conference";
    venue.setCategory(category);
    assertEquals(VenueCategory.CONFERENCE, venue.getCategory());
  }

  /**
   * TODO: document this method.
   */
  @Test
  public void testSetCategoryToJournal() {
    String category = "Not a conference!";
    venue.setCategory(category);
    assertEquals(VenueCategory.JOURNAL, venue.getCategory());
  }
}
