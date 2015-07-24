import static org.junit.Assert.*;

import org.junit.Test;

public class TestScrabble {

	@Test
	public void positiveInput(){
		String rack = "HOUR";
		String fileName = "C:\\Users\\test\\Desktop\\sowpods.txt";
		ScrabbleBestWordFinder scrabble = new ScrabbleBestWordFinder(rack, fileName);
		assertEquals("HOUR",scrabble.findBestWord());
	}
	
	@Test
	public void blankSpaceInput(){
		
		String rack = "OR ";
		String fileName = "C:\\Users\\test\\Desktop\\sowpods.txt";
		ScrabbleBestWordFinder scrabble = new ScrabbleBestWordFinder(rack, fileName);
	}
	
	@Test(expected=RuntimeException.class)
	public void invalidInput(){
		String rack = "OR_";
		String fileName = "C:\\Users\\test\\Desktop\\sowpods.txt";
		ScrabbleBestWordFinder scrabble = new ScrabbleBestWordFinder(rack, fileName);
		scrabble.findBestWord();
		
	}
	
	
}
