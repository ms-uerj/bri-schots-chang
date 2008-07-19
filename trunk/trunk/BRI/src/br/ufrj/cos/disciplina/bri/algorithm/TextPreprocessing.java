package br.ufrj.cos.disciplina.bri.algorithm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.model.Stemmer;

public class TextPreprocessing {

	public List<String> listOfStopWords;

	/**
	 * Default constructor method 
	 */
	public TextPreprocessing() {
		listOfStopWords = new Vector<String>();
	}

	/**
	 * Removes all special characters from the input string, if any
	 * 
	 * @param source -
	 *            a string
	 * @return the content of the input string without special characters
	 */
	public String removeSpecialCharacters(String source) {
		
		source = source.replaceAll("[^a-zA-Z]"," ");
		source = source.replaceAll("[ ]+"," ");
		return source;
	}

	/**
	 * Removes all special characters from the input string, if any
	 * 
	 * @param source -
	 *            a string
	 * @return a list of all the words from the input string, except the stop
	 *         words
	 */
	public List<String> removeStopWords(String source) {
		String[] words = source.split(" ");

		List<String> listOfWords = new Vector<String>();
		// ((Vector<String>) listOfWords).copyInto(words);

		for (int i = 0; i < words.length; i++) {
			String word = words[i].toUpperCase();
			// this algorithm is not (yet?) considering composite terms
			if (!listOfStopWords.contains(word)) {
				listOfWords.add(word);
			}
		}
		return listOfWords;
	}

	/**
	 * Applies Porter's Stemming Algorithm to the input set of strings
	 * 
	 * @return the content of that set of words, now stemmed
	 */
	public List<String> applyPorterStemmer(List<String> source) {
		List<String> result = new Vector<String>();
		Stemmer stemmer = new Stemmer();

		for (int i = 0; i < source.size(); i++) {
			String wordString = source.get(i);
			// the stemming algorithm works with arrays of chars
			char[] word = wordString.toCharArray();
			for (int pos = 0; pos < word.length; pos++) {
				// the stemming algorithm works with lower-case letters
				stemmer.add(Character.toLowerCase(word[pos]));
			}
			// calling the stemming algorithm
			stemmer.stem();

			String resultString = stemmer.toString();

			// undoing the lower-case conversion (supposing that letters were
			// all upper-case)
			result.add(resultString.toUpperCase());
		}
		return result;
	}

	/**
	 * Loads the list of stop words from the input file path
	 * @param filePath - the file path to the list
	 */
	public void loadListOfStopWords(String filePath) {
		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(filePath);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				// Print the content on the console
				// System.out.println (strLine);
				listOfStopWords.add(strLine);
			}
			// Close the input stream
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes text preprocessing with the input string text
	 * @param text - the text to be preprocessed 
	 * @return the terms obtained by text preprocessing
	 */
	public static List<String> preProcessText(String text) {
		
		TextPreprocessing textProcessor = new TextPreprocessing();
		textProcessor.loadListOfStopWords("resources/stopwords/english.stopwords.txt");
		
		if (text == null) {
			text = "";
		}
		
		String tempText = text.toUpperCase();
		tempText = textProcessor.removeSpecialCharacters(tempText);			
		List<String> tempOriginal = textProcessor.removeStopWords(tempText);
		List<String> terms = textProcessor.applyPorterStemmer(tempOriginal);
		
		return terms;
	}
	
}
