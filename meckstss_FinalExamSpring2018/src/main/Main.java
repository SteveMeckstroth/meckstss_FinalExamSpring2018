/**
 * Final Exam
 * @author Stephen Meckstroth
 * Description: Final exam, calculate longest word, shortest word, and word with the most vowels
 * Due Date: Apr 24, 2018
 * Course: IT2045 Section 001
 * email: meckstss@mail.uc.edu
 * Citations: https://github.com/meckstss/meckstss_Assignment07.git
 */
package main;

import java.io.IOException;

import fileReader.textFileReader;

public class Main {

	public static void main(String[] args) throws IOException {
		
		//Read the words.txt file
		textFileReader tf = new textFileReader("src/words.txt");
		tf.readTextFile();
		
		//Print the requested values
		System.out.println("Longest word: " + tf.getStrLongestWord() + " = "+ tf.getIntLongestWord());
		System.out.println("Shortest word: " + tf.getStrShortestWord() + " = "+ tf.getIntShortestWord());
		System.out.println("Word with most vowels: " + tf.getStrMostVowelsWord() +" = "+ tf.getIntMostVowelsWord());

	}

}
