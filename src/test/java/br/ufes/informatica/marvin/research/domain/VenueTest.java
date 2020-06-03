package br.ufes.informatica.marvin.research.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class VenueTest {
	
	private Venue venue;
	
	@Before
	public void setup() {
		venue = new Venue();
	}
	
	@Test
	public void setCategoryTest1() {
		
		String category;
		category = "Conference";
		
		venue.setCategory(category);
		
		assertEquals(VenueCategory.CONFERENCE, venue.getCategory());
	}
	
	@Test
	public void setCategoryTest2() {
		
		String category;
		category = "Not a conference!";
		
		venue.setCategory(category);
		
		assertEquals(VenueCategory.JOURNAL, venue.getCategory());
	}
	
}