package br.ufrj.cos.disciplina.bri.tests;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.algorithm.TextPreprocessing;

public class TestTextProcessing {

	/**
	 * Tests text preprocessing.
	 * @param args
	 */
	public static void main(String[] args) {
		String string = new String("The quick brown fox jumps over the lazy dog mating ocurring");
		
		List<String> temporaryListOfTerms = new Vector<String>();
		
		TextPreprocessing textProcessor = new TextPreprocessing();
		textProcessor.loadListOfStopWords("resources/stopwords/english.stopwords.txt");
		
		temporaryListOfTerms = textProcessor.removeStopWords(string);
		
		System.out.println("After stop words removing:");
		for (Iterator<String> iterator = temporaryListOfTerms.iterator(); iterator.hasNext();) {
			String term = (String) iterator.next();
			System.out.println(term);
		}
		
		List<String> resultingListOfTerms = textProcessor.applyPorterStemmer(temporaryListOfTerms);
		
		System.out.println("After Porter stemming:");
		for (Iterator<String> iterator = resultingListOfTerms.iterator(); iterator.hasNext();) {
			String term = (String) iterator.next();
			System.out.println(term);
		}
	}
}
