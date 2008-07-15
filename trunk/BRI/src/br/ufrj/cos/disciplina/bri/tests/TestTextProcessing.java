package br.ufrj.cos.disciplina.bri.tests;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.algorithms.TextPreprocessing;

public class TestTextProcessing {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string = new String("The quick brown fox jumps over the lazy dog mating ocurring");
		
		List<String> tempOriginal = new Vector<String>();
		List<String> tempResultante;
		
		TextPreprocessing classe = new TextPreprocessing();
		
		classe.loadListOfStopWords("resources/stopwords/english.stopwords.txt");
		
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
