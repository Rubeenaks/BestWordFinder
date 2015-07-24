

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Scrabble {
	
	final int rackSize = 7;
	String rack;
	ArrayList<String> rackList = new ArrayList<String>();
	String dictionaryFileName;
	TreeMap<Integer,ArrayList<String>> dictionary = new TreeMap<Integer, ArrayList<String>>(Collections.reverseOrder());
	final int[] letterScores = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
			25, 26};
	int[] rackLetterCount = new int[26];
	
	Scrabble (String rack, String fName) {
		this.rack = sortWord(rack.toUpperCase().trim());
		createRackSpace(rack);
		populateRackLetterCounts ();
		this.dictionaryFileName = fName;
	}
	
	private void populateRackLetterCounts () {
		char[] letters = rack.toCharArray();
		for (char letter : letters) {
			rackLetterCount[letter - 'A']++;
		}
	}
	
	private int createRackSpace (String rack) {
		if (! rack.contains(" ")) {
			rackList.add(sortWord(rack.toUpperCase()));
			return 0;
		}
		char alpha = 'A';
		int index = rack.indexOf(" ");
		char[] word = rack.toCharArray();
		
		while (Arrays.asList(word).contains(" ") || alpha != 'Z') {
			word[index] = alpha;
			alpha ++;
			createRackSpace(new String(word));
		}
		
		word[index] = 'Z';
		rackList.add(new String(word).toUpperCase());
		return 0;
	}
	
	private boolean isLetterInRack (char letter, int[] letterCount) {
		
		if (letterCount[letter - 'A'] > 0) {
			letterCount[letter - 'A']--;
			return true;
		}
		return false;
	}
	
	private int computeWordScore(String word) {
		int[] letterScores = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
				25, 26};
		int[] rackLetterCountCopy = Arrays.copyOf(this.rackLetterCount, this.rackLetterCount.length);
		int wordScore = 0;
		for (char letter : word.toCharArray()) {
			if ( isLetterInRack(letter, rackLetterCountCopy)) {
				wordScore += letterScores[letter - 'A'];
			}
		}
		
		return wordScore;
	}
	
	private void addWordsToDictionary(int score,String word){
		
		if (dictionary.containsKey(score))
		{
			dictionary.get(score).add(word);	
		} else {
			
			//To be checked
			ArrayList<String> wordList= new ArrayList<String>();
			wordList.add(word);
			dictionary.put(score, wordList);
		}
	}
	
	private String sortWord(String word) {
		char[] letters = word.toCharArray();
		Arrays.sort(letters);
		return new String(letters);
	}
	
	private void buildDictionary()
	{
		File file = new File(this.dictionaryFileName);

		try {

			Scanner sc = new Scanner(file);

			while (sc.hasNextLine()) {
				String word = sc.next();
				if (word.length() <= rackSize) {
					//System.out.println(word);
					addWordsToDictionary(computeWordScore(word),word);
				}
			}
			sc.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private String extractValidWords() {
		
		String validWords = "";
		for (Map.Entry<Integer, ArrayList<String>> entry : dictionary.entrySet()) {
	        Integer score = entry.getKey();
	        ArrayList<String> wordList = entry.getValue();
	        for (String word : wordList) {
	        	
	        	if ( isWordValid(word) ) {
	        		
	        		validWords += word + " "; 
	        	}
	        }
	        if ( validWords.length() > 0 ) {
	        	break;
	        }
	    }
		return validWords.trim();
	}
	
	private boolean isWordValid(String word) {
	
		
		for (String rack : rackList) {
			if (rack.contains(sortWord(word))) {
				return true;
			}
		}
		return false;
	}
	
	public String findBestWord() {
		buildDictionary();
		return extractValidWords();
	}
	
	public static void main(String args[]) {
		
		String rack = "OR ";
		String fileName = "C:\\Users\\test\\Desktop\\sowpods.txt";
		Scrabble scrabble = new Scrabble(rack, fileName);
		System.out.println(scrabble.findBestWord());
	}
}