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
	 * Executes vector search, obtaining ranking values.
	 * @param questionTerms
	 * @param documents
	 * @param numberOfDocs
	 * @return a list with ranking values 
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> vectorSearch(Set<String> questionTerms, Indexing documents, int numberOfDocs) {
		int position = 0;
		
		// creating a temporary list of double values
		// so that it won't be necessary 
		// creating a loop inside another loop
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
					if(!docMatrix.containsKey(doc.getDocumentId())) {
						docMatrix.put(doc.getDocumentId(), new ArrayList<Double>(temporaryList));
					}
					docMatrix.get(doc.getDocumentId()).add(position, tfIdf);
				}
			} 
			position++;
		}
		
		Hashtable<Integer, Double> ranking = new Hashtable<Integer, Double>();
		for(Enumeration<Integer> n = docMatrix.keys(); n.hasMoreElements();) {  
			int key = n.nextElement();
			ranking.put(key, calculateSimilarity(docMatrix.get(key)));
		}
		
		// ordering by similarity rating
		ArrayList tempRanking = new ArrayList(ranking.entrySet());
		
		Collections.sort(tempRanking, new SimilarityComparator());
		
		List<Integer> result = new ArrayList<Integer>();
		
		Iterator rankingIterator = tempRanking.iterator();
		while(rankingIterator.hasNext()) {
			Map.Entry e = (Map.Entry)rankingIterator.next();
			result.add((Integer)e.getKey());
		}
		
		System.out.println("Consulta: " + result);
		
		return result;
	}
	

	/**
	 * Calculates similarity between document and question vectors. 
	 * @param documentsWithRelevantTerms
	 * @return the similarity value
	 */
	public double calculateSimilarity(List<Double> documentsWithRelevantTerms) {
		
		// similarity calculation is a result of dividing
		// the dot product between document and question vectors
		// by the product of multiplying its isolated norm values
		// let D be the document vector and Q the question vector:
		// sim = ( Q · D ) / ( ||Q|| * ||D|| )
		
		// here, the question vector Q is (1, 1, 1, ... , 1)
		// (all its components equals 1)
		// so, the dot product can be just a sum of the
		// values of the document vector components
		
		// furthermore, the norm calculus is as follows:
		// norm(V) = || V || = sqrt(sum(xi)^2), i=1..n
		// where xi are the vector V components and
		// n is the number of components
		
		// for vector Q (whose all components values equals 1),
		// || Q || = sqrt(n)
		// for vector D, default calculation is used
		
		
		// sum = dot product
		double sum = 0.0;
		// norm of document vector
		double normDoc = 0.0;
		// norm of question vector
		double normQuestion = 0.0;
		
		for (int i = 0; i < documentsWithRelevantTerms.size(); i++) {
			sum += documentsWithRelevantTerms.get(i);
			normDoc += Math.pow(documentsWithRelevantTerms.get(i), 2);
		}
		// as explained, only the number of components is needed
		normQuestion = Math.sqrt(documentsWithRelevantTerms.size());
		// finishing document vector calculation
		normDoc = Math.sqrt(normDoc);
		
		// calculating similarity
		double similarity = (sum / (normDoc * normQuestion));
		return similarity;
	}
	
	/**
	 * Inner class for helping vector search.
	 */
	@SuppressWarnings("unchecked")
	static class SimilarityComparator implements Comparator {

		@SuppressWarnings("unchecked")
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
