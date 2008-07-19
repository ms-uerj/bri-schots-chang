package br.ufrj.cos.disciplina.bri.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufrj.cos.disciplina.bri.indexing.Indexing;
import br.ufrj.cos.disciplina.bri.indexing.model.RadixInfo;

public class VectorSearch {
	Hashtable<Integer, List<Double>> docMatrix;
	
	/**
	 * Default constructor method.
	 */
	public VectorSearch() {
		docMatrix = new Hashtable<Integer, List<Double>>();
	}

	/**
	 * Executes vector search.
	 * @param questionTerms
	 * @param documents
	 * @param numberOfDocs
	 * @return
	 */
	public List<Integer> vectorSearch(Set<String> questionTerms, Indexing documents, int numberOfDocs) {
		List<Integer> result = new ArrayList<Integer>();
		
		int position = 0;
		
		// creating a temporary list so that
		// it won't be necessary creating a loop
		// inside another loop
		List<Double> temporaryList = new ArrayList<Double>();
		for (int i = 0; i < questionTerms.size(); i++) {
			temporaryList.add(0.0);		
		}
		
		for (Iterator<String> iteratorQuery = questionTerms.iterator(); iteratorQuery.hasNext();) {
			String term = iteratorQuery.next();
			
			double idf;
			
			if (documents.getHash().containsKey(term)) {
				double numberOfRelevantDocs = documents.getHash().get(term).size();
				idf = numberOfDocs / numberOfRelevantDocs;
				idf = Math.log(idf) / Math.log(2);
				
				List<RadixInfo> listDocInfo = documents.getHash().get(term);
				
				for (Iterator<RadixInfo> iteratorDoc = listDocInfo.iterator(); iteratorDoc.hasNext();) {
					RadixInfo doc = iteratorDoc.next();
					
					double tfIdf = 0.0;
					tfIdf = doc.getTf()*idf;
					if(!docMatrix.containsKey(doc.getDocumentId())){
						docMatrix.put(doc.getDocumentId(), new ArrayList<Double>(temporaryList));
					}
					docMatrix.get(doc.getDocumentId()).add(position, tfIdf);
				}
			} 
			position++;
		}
		
		Hashtable<Integer, Double> ranking = new Hashtable<Integer, Double>();
		
		for(Enumeration<Integer> n = docMatrix.keys(); n.hasMoreElements(); ){  
			int key = n.nextElement();
			ranking.put(key, calculateSimilarity(docMatrix.get(key)));
		}
		
		// ordering by similarity rating
		ArrayList tempRanking = new ArrayList(ranking.entrySet());
		
		Collections.sort(tempRanking, new SimilarityComparator());
		
		Iterator rankingIterator = tempRanking.iterator();
		while(rankingIterator.hasNext()){
			
			Map.Entry e = (Map.Entry)rankingIterator.next();
			result.add((Integer)e.getKey());
		}
		return result;
	}
	

	public double calculateSimilarity(List<Double> documentsWithRelevantTerms) {
		
		double sum = 0.0;
		double normDoc = 0.0;
		double normQuestion = 0.0;
		
		for (int i = 0; i < documentsWithRelevantTerms.size(); i++) {
			sum += documentsWithRelevantTerms.get(i);
			normDoc += Math.pow(documentsWithRelevantTerms.get(i), 2);
		}
		normQuestion = Math.sqrt(documentsWithRelevantTerms.size());
		normDoc = Math.sqrt(normDoc);
		
		/*
		 * X * Y / |X|*|Y|
		 * Y = {1,1,1,1...,1}
		 * logo:
		 * Somatório(xi)[1..n] / sqrt(som(xi^2)[1..n])) * sqrt(n)
		 */
		
		double similarity = (sum / (normDoc * normQuestion));
		return similarity;
	}
	
	static class SimilarityComparator implements Comparator {

		public int compare(Object obj1, Object obj2) {

			int result = 0;
			Map.Entry e1 = (Map.Entry) obj1;

			Map.Entry e2 = (Map.Entry) obj2;// Sort based on values.

			Double value1 = (Double) e1.getValue();
			Double value2 = (Double) e2.getValue();

			if (value1.compareTo(value2) == 0) {

				Integer word1 = (Integer) e1.getKey();
				Integer word2 = (Integer) e2.getKey();

				// sort string in an ascending order
				result = word1.compareTo(word2);

			} else {
				// sort values in a descending order
				result = value2.compareTo(value1);
			}
			return result;
		}
	}
}
