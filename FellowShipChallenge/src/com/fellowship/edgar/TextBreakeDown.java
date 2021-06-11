package com.fellowship.edgar;

import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import java.io.IOException;

import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.*;

/**
 * This class processes text in order to generate statics in terms of its words, phrases, paragraphs, among others.
 * @author Edgar Roman
 * @version v1.0
 */

public class TextBreakeDown {

	/**
	 * Class constructor
	 */
	public TextBreakeDown() {}
	
	/**
	 * Method that calculates the number of words that a text has.
	 * @param theText Text to be Analyzed.
	 * @return As a String the number of words in the text.
	 */
	public String numberOfWords(String theText) {
		theText = removePunctuation(theText);
		String[] words = theText.split("\\s+");
		String numOfWrd = String.valueOf(words.length);
		return numOfWrd;
	}
	
	/**
	 * Method that calculates the number of sentences that a text has. Each sentence is separated by a period.
	 * @param theText Text to be Analyzed.
	 * @return As a String the number of sentences in the text.
	 */	
	public String numberOfSentences(String theText) {
		String[] phrases = theText.split("\\.");
		
		int blanks = 0;
		for (int i=0; i<phrases.length; i++) {
			phrases[i] = phrases[i].trim();
			if(phrases[i].isEmpty())
				blanks++;
		}
		
		//if the number of paragraphs is bigger than phrases, the priority the number of paragraphs (Paragraphs with not period at the end).
		int numberOfParagraphs = Integer.parseInt(numberOfParaphs(theText));
		int numberOfSentences = phrases.length - blanks;
		
		if(numberOfParagraphs > numberOfSentences) {
			numberOfSentences = numberOfParagraphs;
		}

		String numOfSents = String.valueOf(numberOfSentences);
		return numOfSents;
	}
	
	/**
	 * Method that calculates the number of paragraphs that a text has. Each paragraphs is separated by a period and enter.
	 * @param theText Text to be Analyzed.
	 * @return As a String the number of paragraphs in the text.
	 */	
	public String numberOfParaphs(String theText) {
		String[] paragraphs = theText.split("\n");
		
		//controls multiple enters as a false paragraph
		int blanks = 0;
		for (int i=0; i<paragraphs.length; i++) {
			if(paragraphs[i].isEmpty())
				blanks++;
		}
		
		String numOfParag = String.valueOf(paragraphs.length - blanks);
		return numOfParag;
	}
	
	/**
	 * Method that calculates the number of bigrams (counts of unique pairs of words) that a text has. 
	 * @param theText Text to be Analyzed.
	 * @return As a String the number of bigrams in the text.
	 */	
	public String numbersOfBigrams(String theText) {
		
		theText = removePunctuation(theText);
		String[] words = theText.split("\\s+");
		String[] bigrams = new String[words.length - 1];
		
		
		//creation of the possibles bigrams
		for (int i=0; i<words.length-1; i++ ) {
			bigrams[i] = words[i] + " " + words[i+1];
		}
		
		Map <String,Integer> bgms = new TreeMap <>();
		
        for(int i=0;i<bigrams.length;i++)
        {
            //Identifying number of Bigrams (counts of unique pairs of words)
        	// Condition to check if the element is present in the hash map.
            if(bgms.containsKey(bigrams[i]))
            {
            	bgms.put(bigrams[i], bgms.get(bigrams[i])+1);
            }
            else
            {
            	bgms.put(bigrams[i],1);
            }
        }
        
        // Loop to iterate through the elements of the map
        int numberOfBigrams = 0;
        for(Map.Entry<String,Integer> entry:  bgms.entrySet())
        {
          // System.out.println(entry.getKey()+ " - "+entry.getValue());
        	numberOfBigrams = numberOfBigrams + 1;
        }
        
        String numOfBigr = String.valueOf(numberOfBigrams);
        return numOfBigr;

	}
	
	/**
	 * Method that shows how many words are in the text classified by their length.
	 * @param theText Text to be Analyzed.
	 * @return An array of Strings where the position i represents the number of letters of a word and the number inside the position i represents the ocurrency of this word in the text.
	 */	
	public String[] wordsByLength(String theText) {
		
		theText = removePunctuation(theText);
		String[] words = theText.split("\\s+");
		//Largest word in the world is of 45 letters
		int[] sizeOcurrency = new int[45];
		int largestWord = 0;
	
		int wordSize = 0;
		for(int i=0; i<words.length; i++) {
			wordSize = words[i].length();
			if (wordSize > largestWord) 
				largestWord = wordSize;
			sizeOcurrency[wordSize] = sizeOcurrency[wordSize] + 1;
		}
		
		//prepare a final array with the right size in order to have an accurate return
		String[] finalArry = new String[largestWord+1];
		for (int i=0; i<=largestWord; i++) {
			if(sizeOcurrency[i] != 0) {
				finalArry[i] = String.valueOf(sizeOcurrency[i]);				
			}
		}
		return finalArry;
			
	}
	
	/**
	 * Method that detects the text language.
	 * @param theText Text to be Analyzed.
	 * @return The code language (two letters) of the detected language
	 */	
	public String getLanguageFrom(String theText) throws IOException {
	    
		LanguageDetector detector = new OptimaizeLangDetector().loadModels();
	    LanguageResult lr = detector.detect(theText);
	    String theLanguageIs = expandedLanguage(lr.getLanguage());
	    return theLanguageIs;
	}
	
	/**
	 * Method that converts the code language (two letters) into a full word of the languages detected
	 * @param language The code language
	 * @return The language detected in one full word (not two letters).
	 */	
	public String expandedLanguage(String language) {
		
		Locale loc = new Locale(language);
		String theLanguage = loc.getDisplayLanguage(loc);
		
		//Return with the first letter as UpperCase
		return theLanguage.substring(0, 1).toUpperCase() + theLanguage.substring(1);
	}
	
	/**
	 * Method that removes all punctuation signs of the Text
	 * @param theText Text to be Analyzed.
	 * @return String with the text to be analyzed without punctuation signs.
	 */	
	public String removePunctuation(String theText) {
		
		return theText.replaceAll("\\p{Punct}", "");
	}
}
