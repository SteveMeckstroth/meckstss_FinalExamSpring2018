/**
 * Final Exam
 * @author Stephen Meckstroth
 * Description: Final exam, calculate longest word, shortest word, and word with the most vowels
 * Due Date: Apr 24, 2018
 * Course: IT2045 Section 001
 * email: meckstss@mail.uc.edu
 * Citations: https://github.com/meckstss/meckstss_Assignment07.git
 */
package fileReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections; 
import java.util.LinkedHashMap; 
import static java.util.stream.Collectors.*; 
import static java.util.Map.Entry.*;



/**
 * Opens words.txt and calculates statistics
 * @author Steve Meckstroth and Jennifer Palazzolo
 *
 */
public class textFileReader {
	FileReader fileReader;
	String fileName = "";
	ArrayList<String> words = new ArrayList<String>();
	
	private String strMostCommonLetter = "";
	private int intMostCommonLetterCnt = 0;
	private String strLeastCommonLetter = "";
	private int intLeastCommonLetterCnt = 0;
	
	private Double avgWordLength = 0.0;
	private String strLongestWord = "";
	private int intLongestWord = 0;
	
	private String strShortestWord = "";
	private int intShortestWord = 99999;//An extremely large default value to ensure shortest word is calculated
	
	private String strMostVowelsWord = "";
	private int intMostVowelsWord = 0;
	
	public textFileReader (String fileName) throws FileNotFoundException{
		this.fileName = fileName;
		openTextFile();
	}
	
	private void openTextFile() throws FileNotFoundException{
		fileReader = new FileReader(fileName);
	}
	
	public void readTextFile() throws IOException{
		String line;
		
		// Always wrap FileReader in BufferedReader.
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);
        
        
		while((line = bufferedReader.readLine()) != null) {
            words.add(line);
        }   
		
		 // Always close files.
        bufferedReader.close();   
	}
	/**
	 * Calculate the least/most common letters among the words in the text file
	 * @return String representing the most common letter
	 */
	private void commonLetter(){
		
		Map<String, Integer> letters = new HashMap<String, Integer>();
		
		//iterate over the words array, and then each letter for each word, collect counts for each letter into the letters HashMap
		for(int i = 0; i < words.size(); i++){
			for (int j = 0; j < words.get(i).length(); j++){
				String c =  String.valueOf(words.get(i).charAt(j));
				if (letters.get(c) != null){
					letters.put(c, letters.get(c) + 1);
				} else {
					letters.put(c,  1);
				}  
			}	
		}
		
		//Sort the letters HasMap by values and store it into a new HasMap
		//Modified code from http://javarevisited.blogspot.com/2017/09/java-8-sorting-hashmap-by-values-in.html
		 Map<String, Integer> sortedLetters = letters .entrySet() .stream() .sorted(Collections.reverseOrder(Map.Entry.comparingByValue())) .collect( toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		 
		 //After the descending sort the first key/value pair should be the most common letter
		 setStrMostCommonLetter(sortedLetters.entrySet().iterator().next().getKey());
		 setIntMostCommonLetterCnt(sortedLetters.entrySet().iterator().next().getValue());
		 
		 //Rebuild sortedLetters but sort ascending by count
		 sortedLetters = letters .entrySet() .stream() .sorted(comparingByValue()) .collect( toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		 
		 //After the descending sort the first key/value pair should be the most common letter
		 setStrLeastCommonLetter(sortedLetters.entrySet().iterator().next().getKey());
		 setIntLeastCommonLetterCnt(sortedLetters.entrySet().iterator().next().getValue());
	}
	/**
	 * Executes private method mostCommonLetter()
	 * @return String representing most common letter
	 */
	public String getStrMostCommonLetter() {
		if (strMostCommonLetter == ""){
			commonLetter();
		}
		return strMostCommonLetter;
	}
	/**
	 * The setter is private to the class to avoid bad data
	 * @param strMostCommonLetter
	 */
	private void setStrMostCommonLetter(String strMostCommonLetter) {
		this.strMostCommonLetter = strMostCommonLetter;
	}
	/**
	 * Executes private method mostCommonLetter()
	 * @return Int representing the most common letter count
	 */
	public int getIntMostCommonLetterCnt() {
		//If the most common letter hasn't been calculated then calculate it
		if (strMostCommonLetter == ""){
			commonLetter();
		}
		return intMostCommonLetterCnt;
	}
	/**
	 * The setter is private to avoid bad data
	 * @param intMostCommonLetterCnt
	 */
	private void setIntMostCommonLetterCnt(int intMostCommonLetterCnt) {
		this.intMostCommonLetterCnt = intMostCommonLetterCnt;
	}
	/**
	 * Executes private method mostCommonLetter() 
	 * @return String for least common letter
	 */
	public String getStrLeastCommonLetter() {
		//If the most common letter hasn't been calculated then calculate it
				if (strMostCommonLetter == ""){
					commonLetter();
				}
		return strLeastCommonLetter;
	}
	/**
	 * Setter is private to avoid bad data
	 * @param strLeastCommonLetter
	 */
	private void setStrLeastCommonLetter(String strLeastCommonLetter) {
		this.strLeastCommonLetter = strLeastCommonLetter;
	}
	/**
	 * Executes private method mostCommonLetter()
	 * @return Int representing least common letter's count
	 */
	public int getIntLeastCommonLetterCnt() {
		//If the most common letter hasn't been calculated then calculate it
				if (strMostCommonLetter == ""){
					commonLetter();
				}
		return intLeastCommonLetterCnt;
	}
	/**
	 * Setter is private to avoid bad data
	 * @param intLeastCommonLetterCnt
	 */
	private void setIntLeastCommonLetterCnt(int intLeastCommonLetterCnt) {
		this.intLeastCommonLetterCnt = intLeastCommonLetterCnt;
	}
	/**
	 * Calculate Average word length, and longest word lenght, and shortest word length
	 */
	private void calculateWordCounts(){
		
		int wordLengthTotal = 0;
		int vowelCount = 0;
		String currentWord = "";
		//iterate over the words array, and sum the length of all of the words
		for(int i = 0; i < words.size(); i++){
			currentWord = words.get(i);
			 wordLengthTotal += currentWord.length();
			 if (currentWord.length() > intLongestWord){
				 setStrLongestWord(currentWord);
				 setIntLongestWord(currentWord.length());
			 }
			 // Ongoing test for shortest word
			 if (currentWord.length() < intShortestWord) {
				setStrShortestWord(currentWord);
				setIntShortestWord(currentWord.length());
			 }
			 // Ongoing test for word with the most vowels
			 vowelCount = countVowels(currentWord);
			 if (vowelCount > intMostVowelsWord) {
				 setStrMostVowelsWord(currentWord);
				 setIntMostVowelsWord(vowelCount);
			 }
			 
			 
		}
		//Set the class variable for average word length, and round to 2 decimals
		avgWordLength = ((double) words.size()) / wordLengthTotal;
		avgWordLength = Math.round(avgWordLength*100.0)/100.0;
		
	}
	/**
	 * Counts the number of vowels in a word
	 * @param word String of the word to evaluate
	 * @return int of the number of vowels
	 */
	private int countVowels(String word){
		int vowelCount = 0;
		char letter;
		
		// Iterate over each letter and count the vowels
		for(int i=0; i< word.length(); i++){
			letter = word.toUpperCase().charAt(i);
			switch (letter){
			case 'A':
			case 'E':
			case 'I':
			case 'O':
			case 'U':
				vowelCount++;
				break;
			default:
			}
		 }
		
		return vowelCount;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return Double representing average word length
	 */
	public Double getAvgWordLength() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return avgWordLength;
	}
	/**
	 * Private setter for average word length
	 * @param avgWordLength Double
	 */
	public void setAvgWordLength(Double avgWordLength) {
		this.avgWordLength = avgWordLength;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return String of longest word
	 */
	public String getStrLongestWord() {
		if (getAvgWordLength() == 0.0){
			calculateWordCounts();
		}
		return strLongestWord;
	}
	/**
	 * Private setter for longest word
	 * @param strLongestWord
	 */
	private void setStrLongestWord(String strLongestWord) {
		this.strLongestWord = strLongestWord;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return Int representing the longest word's size
	 */
	public int getIntLongestWord() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return intLongestWord;
	}
	/**
	 * Private setter for the Longest word size
	 * @param intLongestWord
	 */
	private void setIntLongestWord(int intLongestWord) {
		this.intLongestWord = intLongestWord;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return String value of shortest word
	 */
	public String getStrShortestWord() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return strShortestWord;
	}
	/**
	 * Private setter for the shortest word
	 * @param strShortestWord String value of the shortest word
	 */
	private void setStrShortestWord(String strShortestWord) {
		
		this.strShortestWord = strShortestWord;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return Int length of shortest word
	 */
	public int getIntShortestWord() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return intShortestWord;
	}
	/** private setter for shortest word length
	 * 
	 * @param intShortestWord int value of the shortest word
	 */
	private void setIntShortestWord(int intShortestWord) {
		this.intShortestWord = intShortestWord;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return String of word with most vowels
	 */
	public String getStrMostVowelsWord() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return strMostVowelsWord;
	}
	/**
	 * Private setter to protect the value
	 * @param strMostVowelsWord String representing the word with the most vowels
	 */
	private void setStrMostVowelsWord(String strMostVowelsWord) {
		this.strMostVowelsWord = strMostVowelsWord;
	}
	/**
	 * Executes calculateWordCounts() if not already executed, and then returns the appropriate value
	 * @return int representing a count of the vowels from the word with the most vowels
	 */
	public int getIntMostVowelsWord() {
		if (avgWordLength == 0.0){
			calculateWordCounts();
		}
		return intMostVowelsWord;
	}
	/**
	 * Private setter to protect the value
	 * @param intMostVowelsWord int representing the count of vowels from the word with the most vowels
	 */
	private void setIntMostVowelsWord(int intMostVowelsWord) {
		this.intMostVowelsWord = intMostVowelsWord;
	}
}
