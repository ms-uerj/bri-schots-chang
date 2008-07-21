package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.ufrj.cos.disciplina.bri.algorithm.PrecisionRecall;
import br.ufrj.cos.disciplina.bri.algorithm.VectorSearch;
import br.ufrj.cos.disciplina.bri.indexing.Indexing;
import br.ufrj.cos.disciplina.bri.indexing.model.RadixInfo;
import br.ufrj.cos.disciplina.bri.model.Point;
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


		for (Iterator<Record> iteratorRecord = listOfRecords.iterator(); iteratorRecord.hasNext();) {

			Record record = iteratorRecord.next();
			
			// populate hash table with records terms (titles)
			for (Iterator<String> iteratorTermsTitle = record.getTitleTerms()
					.iterator(); iteratorTermsTitle.hasNext();) {
				term = iteratorTermsTitle.next();
				indexingRecords.addToHash(term, new RadixInfo(record.getId(),
						record.getTfOnTitle(term), "title"));
			}
			
			// populate hash table with records terms (abstracts)
			for (Iterator<String> iteratorTermsAbstract = record.getAbztractTerms()
					.iterator(); iteratorTermsAbstract.hasNext();) {
				term = iteratorTermsAbstract.next();
				indexingRecords.addToHash(term, new RadixInfo(record.getId(),
						record.getTfOnAbztract(term), "abstract"));
			}
		}
		
		List<Point> listOfPoints = new ArrayList<Point>();
		
		for (int k = 0; k < 11; k++) {
			listOfPoints.add(new Point(k, 0.0));
		}
		
		for (Iterator<Query> iteratorQuery = listOfQueries.iterator(); iteratorQuery.hasNext();) {
			List<Point> listOfPrecisionRecallValues = new ArrayList<Point>();

			Query query = iteratorQuery.next();
			List<Integer> answers;

			// search each query on records Indexing using
			// logic "OR" between terms and similarity between vectors
			VectorSearch search = new VectorSearch();
			answers = search.vectorSearch(query.getQuestionsTerms(), indexingRecords, listOfRecords.size());
			
			List<Integer> relevants;
			relevants = PrecisionRecall.getRelevantElementSet(query);
			
			listOfPrecisionRecallValues = PrecisionRecall.getPrecisionRecall(relevants, answers);
			
			// interpolate list of values
			listOfPrecisionRecallValues = Point.interpolate(listOfPrecisionRecallValues);
			
			for (int k = 0; k < 11; k++) {
				listOfPoints.get(k).setYPrecision(listOfPoints.get(k).getYPrecision() + listOfPrecisionRecallValues.get(k).getYPrecision());
			}
			
		}
		
		
		for (int k = 0; k < 11; k++) {
			System.out.println(listOfPoints.get(k));
		}
		
		for (int k = 0; k < 11; k++) {
			listOfPoints.get(k).setYPrecision(listOfPoints.get(k).getYPrecision()/100);
			System.out.println(listOfPoints.get(k));
		}
		
		System.out.println("End");
	}
}
