package br.ufrj.cos.disciplina.bri.algorithms;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.model.Stemmer;

public class TextPreprocessing {

	public List<String> listOfStopWords;

	public TextPreprocessing() {
		// based on the list found at http://www.ranks.nl/tools/stopwords.html
		listOfStopWords = new Vector<String>();
		listOfStopWords.add("I");
		listOfStopWords.add("a");
		listOfStopWords.add("about");
		listOfStopWords.add("an");
		listOfStopWords.add("are");
		listOfStopWords.add("as");
		listOfStopWords.add("at");
		listOfStopWords.add("be");
		listOfStopWords.add("by");
		listOfStopWords.add("com");
		listOfStopWords.add("de");
		listOfStopWords.add("en");
		listOfStopWords.add("for");
		listOfStopWords.add("from");
		listOfStopWords.add("how");
		listOfStopWords.add("in");
		listOfStopWords.add("is");
		listOfStopWords.add("it");
		listOfStopWords.add("la");
		listOfStopWords.add("of");
		listOfStopWords.add("on");
		listOfStopWords.add("or");
		listOfStopWords.add("that");
		listOfStopWords.add("the");
		listOfStopWords.add("this");
		listOfStopWords.add("to");
		listOfStopWords.add("was");
		listOfStopWords.add("what");
		listOfStopWords.add("when");
		listOfStopWords.add("where");
		listOfStopWords.add("who");
		listOfStopWords.add("will");
		listOfStopWords.add("with");
		listOfStopWords.add("und");
		listOfStopWords.add("the");
		listOfStopWords.add("www");
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
			String word = words[i];
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
				int j = 0;

				// the stemming algorithm works with lower-case letters
				stemmer.add(Character.toLowerCase(word[j]));
				stemmer.stem();

				String resultString = stemmer.toString();

				System.out.print(resultString);
				// undoing the lower-case conversion (supposing that letters were all upper-case)
				result.add(resultString.toUpperCase());
			}
		}
		return result;
	}


}
