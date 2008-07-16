package br.ufrj.cos.disciplina.bri.algorithm;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.model.Stemmer;

public class TextPreprocessing {

	public List<String> listOfStopWords;

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
		
		// replaces all the "non-letter" characters by a whitespace
		source = source.replaceAll("[^a-zA-Z]"," ");
		
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(source);
		char character = iterator.current();
		
		// removes excessive whitespace occurrences
		do {
			if (!(Character.isWhitespace(character) && Character.isWhitespace(iterator.next()))) {
				result.append(character);
			}
			character = iterator.next();
		} while (character != CharacterIterator.DONE);
		
		return result.toString().trim();
	}

	/**
	 * Removes stop words (defined in the listOfStopWords list) from the input string, if any
	 * 
	 * @param source -
	 *            a string
	 * @return a list of the words from the input string, but without the stopwords
	 */
	public List<String> removeStopWords(String source) {
		String[] words = source.split(" ");

		List<String> listOfWords = new Vector<String>();

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
			// the stemming algorithm works with arrays of char
			char[] word = wordString.toCharArray();
			for (int pos = 0; pos < word.length; pos++) {
				// the stemming algorithm works with lower-case letters
				stemmer.add(Character.toLowerCase(word[pos]));
			}
			// calling the stemming algorithm
			stemmer.stem();

			String resultString = stemmer.toString();

			// undoing the lower-case conversion
			// (supposing that letters were all upper-case)
			result.add(resultString.toUpperCase());
		}
		return result;
	}

	/**
	 * Loads a list of stop words so that they can be removed
	 * by the function <code>removeStopWords(String source)</code>.
	 * The file must have <i>only one word</i> per line.
	 * @param filePath - string with the file path
	 * @return true if loaded successfully, false otherwise
	 */
	public boolean loadListOfStopWords(String filePath) {
		try {
			// open the file using the path defined in filePath parameter
			FileInputStream fileInputStream = new FileInputStream(filePath);
			// get the object of DataInputStream
			DataInputStream in = new DataInputStream(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
			String stringLine;
			// read the file line by line
			while ((stringLine = bufferedReader.readLine()) != null) {
				// System.out.println (strLine);
				listOfStopWords.add(stringLine.trim());
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
}
