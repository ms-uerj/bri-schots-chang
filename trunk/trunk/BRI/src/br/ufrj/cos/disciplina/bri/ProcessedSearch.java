package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufrj.cos.disciplina.bri.algorithm.VetorialSearch;
import br.ufrj.cos.disciplina.bri.indexing.Indexing;
import br.ufrj.cos.disciplina.bri.indexing.model.RadixInfo;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;

public class ProcessedSearch {

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

		System.out.println("Reading record and queries from xml...");

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

		listQueries.addAll(Query
				.parseQueryFromXML("resources/inputs/cfquery-corrigido.xml"));

		System.out.println("Finished reading...");

		Indexing indexingRecords = new Indexing();
		String term;

		/*
		 * popula tabela hash com os termos dos records (Titles)
		 */
		//TODO: repetir processo para abstract 
		for (Iterator<Record> iteratorRecord = listRecords.iterator(); iteratorRecord.hasNext();) {

			Record record = iteratorRecord.next();

			// System.out.println(record.getId());
			// System.out.println("title: "+record.getTitle());
			// System.out.println("terms title: "+record.getTitleTerms());
			// System.out.println("abztract: "+record.getAbztract());
			// System.out.println("terms title: "+record.getAbztractTerms());

			for (Iterator<String> itTermsTitle = record.getTitleTerms()
					.iterator(); itTermsTitle.hasNext();) {
				term = itTermsTitle.next();
				indexingRecords.addToHash(term, new RadixInfo(record.getId(),
						record.getTfOnTitle(term), "title"));
			}
		}
		
		
		
		for (Iterator<Query> iteratorQuery = listQueries.iterator(); iteratorQuery.hasNext();) {

			Query query = iteratorQuery.next();

			// System.out.println(query.getId());
			// System.out.println("question: "+query.getQuestion());
			// System.out.println("terms question: "+query.getQuestionsTerms());

			/*
			 * Buscar cada query no Indexing de records
			 * a partir de ou de termos e similaridade entre os vetores.
			 */	
			VetorialSearch search = new VetorialSearch();
			search.vetorialSearch(query.getQuestionsTerms(), indexingRecords, listRecords.size());
			
		}

		/*
		 * 
		 */

		System.out.println("End");

	}

}
