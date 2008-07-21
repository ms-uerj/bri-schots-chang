package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufrj.cos.disciplina.bri.algorithm.VectorSearch;
import br.ufrj.cos.disciplina.bri.indexing.Indexing;
import br.ufrj.cos.disciplina.bri.indexing.model.RadixInfo;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;

public class ProcessedSearch {

	/**
	 * Reads data from XML files and populate hash tables,
	 * applying text preprocessing rules to its content.
	 * @param args
	 */
	public static void main(String[] args) {

		// records list
		List<Record> listOfRecords = new ArrayList<Record>();
		// queries list
		List<Query> listOfQueries = new ArrayList<Query>();

		System.out.println("Reading record and queries from XML...");

		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf74.xml"));
		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf75.xml"));
		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf76.xml"));
		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf77.xml"));
		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf78.xml"));
		listOfRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf79.xml"));

		listOfQueries.addAll(Query
				.parseQueryFromXML("resources/inputs/cfquery-corrigido.xml"));

		System.out.println("Finished reading...");

		Indexing indexingRecords = new Indexing();
		String term;

		// populate hash table with records terms (titles)
		for (Iterator<Record> iteratorRecord = listOfRecords.iterator(); iteratorRecord.hasNext();) {
			Record record = iteratorRecord.next();
			for (Iterator<String> iteratorTermsTitle = record.getTitleTerms()
					.iterator(); iteratorTermsTitle.hasNext();) {
				term = iteratorTermsTitle.next();
				indexingRecords.addToHash(term, new RadixInfo(record.getId(),
						record.getTfOnTitle(term), "title"));
			}
		}
		
		// populate hash table with records terms (abstracts)
		for (Iterator<Record> iteratorRecord = listOfRecords.iterator(); iteratorRecord.hasNext();) {
			Record record = iteratorRecord.next();
			for (Iterator<String> iteratorTermsAbztract = record.getAbztractTerms()
					.iterator(); iteratorTermsAbztract.hasNext();) {
				term = iteratorTermsAbztract.next();
				indexingRecords.addToHash(term, new RadixInfo(record.getId(),
						record.getTfOnAbztract(term), "abstract"));
			}
		}
		
		for (Iterator<Query> iteratorQuery = listOfQueries.iterator(); iteratorQuery.hasNext();) {
			Query query = iteratorQuery.next();
			// search each query on records Indexing using
			// logic "OR" between terms and similarity between vectors
			VectorSearch search = new VectorSearch();
			search.vectorSearch(query.getQuestionsTerms(), indexingRecords, listOfRecords.size());	
		}
		System.out.println("End");
	}
}
