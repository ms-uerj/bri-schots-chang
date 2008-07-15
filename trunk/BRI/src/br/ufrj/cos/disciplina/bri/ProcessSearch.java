package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import br.ufrj.cos.disciplina.bri.algorithms.TextPreprocessing;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;

public class ProcessSearch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 * Lê os dados dos documentos XML.
		 */
		// Lista de Records
		List<Record> listRecords = new ArrayList<Record>();
		// Lista de Queries
		List<Query> listQueries = new ArrayList<Query>();

		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf74.xml"));
		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf75.xml"));
		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf76.xml"));
		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf77.xml"));
		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf78.xml"));
		listRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf79.xml"));
		
		listQueries.addAll(Query.parseQueryFromXML("resources/inputs/cfquery-corrigido.xml"));
		
		/*
		 * Processa Strings TITLE, ABSTRACT, AND QUERIES
		 * 1) conversão para maiúsculas
		 * 2) Eliminação de caracteres diferentes de A-Z
		 * 3) Eliminação de Stop words
		 * 4) Stemming de Porter
		 */
		TextPreprocessing textProcessor = new TextPreprocessing();
		textProcessor.loadListOfStopWords("resources/stopwords/english.stopwords.txt");
		
		for (Iterator<Record> iterator = listRecords.iterator(); iterator.hasNext();) {
			Record record = iterator.next();
			
			List<String> tempOriginal = new Vector<String>();
			List<String> tempResultante;
			
			System.out.println("record: "+record.getId());
			
			String title = record.getTitle();
			String abztract = record.getAbztract();
			if (abztract == null) abztract = "";
			
			System.out.println("title original: "+title);
			System.out.println("abstract original: "+abztract);
			
			title.toUpperCase();
			abztract.toUpperCase();
			
			title = textProcessor.removeSpecialCharacters(title);			
			abztract = textProcessor.removeSpecialCharacters(abztract);
			
			tempOriginal = textProcessor.removeStopWords(title);
			tempResultante = textProcessor.applyPorterStemmer(tempOriginal);
			title = tempResultante.toString();
			System.out.println("title novo: "+title);
			
			tempOriginal = textProcessor.removeStopWords(abztract);
			tempResultante = textProcessor.applyPorterStemmer(tempOriginal);
			abztract = tempResultante.toString();
			System.out.println("title novo: "+abztract);
			
		}
		
	}

}
