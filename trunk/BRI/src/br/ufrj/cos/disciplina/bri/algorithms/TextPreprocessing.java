package br.ufrj.cos.disciplina.bri.algorithms;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.model.Stemmer;

public class TextPreprocessing {

	public List<String> listOfStopWords;

	public TextPreprocessing() {
		// based on the list found at http://www.ranks.nl/tools/stopwords.html
		listOfStopWords = new Vector<String>();
		listOfStopWords.add("I");
		listOfStopWords.add("A");
		listOfStopWords.add("ABOUT");
		listOfStopWords.add("AN");
		listOfStopWords.add("ARE");
		listOfStopWords.add("AS");
		listOfStopWords.add("AT");
		listOfStopWords.add("BE");
		listOfStopWords.add("BY");
		listOfStopWords.add("COM");
		listOfStopWords.add("DE");
		listOfStopWords.add("EN");
		listOfStopWords.add("FOR");
		listOfStopWords.add("FROM");
		listOfStopWords.add("HOW");
		listOfStopWords.add("IN");
		listOfStopWords.add("IS");
		listOfStopWords.add("IT");
		listOfStopWords.add("LA");
		listOfStopWords.add("OF");
		listOfStopWords.add("ON");
		listOfStopWords.add("OR");
		listOfStopWords.add("THAT");
		listOfStopWords.add("THE");
		listOfStopWords.add("THIS");
		listOfStopWords.add("TO");
		listOfStopWords.add("WAS");
		listOfStopWords.add("WHAT");
		listOfStopWords.add("WHEN");
		listOfStopWords.add("WHERE");
		listOfStopWords.add("WHO");
		listOfStopWords.add("WILL");
		listOfStopWords.add("WITH");
		listOfStopWords.add("UND");
		listOfStopWords.add("WWW");
	}

	/**
	 * Removes all special characters from the input string, if any
	 * @param source - a string
	 * @return the content of the input string without special characters 
	 */
	public String removeSpecialCharacters(String source) {
		final StringBuilder result = new StringBuilder();
		final StringCharacterIterator iterator = new StringCharacterIterator(source);
		char character =  iterator.current();
		while (character != CharacterIterator.DONE ){
			// if ((character == '<') || (character == '>') ||	(character == '&') || (character == '\"')
			//		|| (character == '\'') || (character == '(') || (character == ')') || (character == '#')
			//		|| (character == '%') || (character == ';') || (character == '+') || (character == '-')) {
			//	// the current char is a special one, do not add it to the result
			//}	else {
			//	// the current char is not a special one, so add it to the result as it is
			//	result.append(character);
			// }

			// if the character is a letter [A-Z], a digit [0-9] or whitespace, add it to the result
			if (Character.isLetterOrDigit(character) || Character.isWhitespace(character)) {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();
	}

	/**
	 * Removes all special characters from the input string, if any
	 * @param source - a string
	 * @return a list of all the words from the input string, except the stop words 
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
	 * @return the content of that set of words, now stemmed
	 */
	public List<String> applyPorterStemmer(List<String> source) {
		List<String> result = new Vector<String>();
		Stemmer stemmer = new Stemmer();

		for (int i = 0; i < source.size(); i++) {
			String wordString = source.get(i);
			// the stemming algorithm works with arrays of chars
			char[] word = wordString.toCharArray();
			for(int pos = 0; pos < word.length; pos++) {
				// the stemming algorithm works with lower-case letters
				stemmer.add(Character.toLowerCase(word[pos]));
			}
			// calling the stemming algorithm
			stemmer.stem();

			String resultString = stemmer.toString();

			// undoing the lower-case conversion (supposing that letters were all upper-case)
			result.add(resultString.toUpperCase());
		}
		return result;
	}

	// TODO just for test - delete before delivery =) 
	public static void main(String[] args) {
		
		String string = new String("The quick brown fox jumps over the lazy dog mating ocurring");
		
		List<String> tempOriginal = new Vector<String>();
		List<String> tempResultante;
		
		TextPreprocessing classe = new TextPreprocessing();
		
		tempOriginal = classe.removeStopWords(string);
		
		System.out.println("Pós-Stop Words:");
		for (Iterator<String> iterator = tempOriginal.iterator(); iterator.hasNext();) {
			String temp = (String) iterator.next();
			System.out.println(temp);
		}
		
		tempResultante = classe.applyPorterStemmer(tempOriginal);
		
		System.out.println("Pós-Porter:");
		for (Iterator<String> iterator = tempResultante.iterator(); iterator.hasNext();) {
			String temp = (String) iterator.next();
			System.out.println(temp);
		}
	}
}
