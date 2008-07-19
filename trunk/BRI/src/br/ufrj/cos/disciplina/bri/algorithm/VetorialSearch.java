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

public class VetorialSearch {
	Hashtable<Integer, List<Double>> docMatrix;
	
	public VetorialSearch() {
		docMatrix = new Hashtable<Integer, List<Double>>();
	}

	public List<Integer> vetorialSearch(Set<String> questionTerms, Indexing documents, int numberDocs) {
		List<Integer> result = new ArrayList<Integer>();
		
		int posicao = 0;
		
		List<Double> temporaryList = new ArrayList<Double>();
		for (int i = 0; i < questionTerms.size(); i++) {
			temporaryList.add(0.0);		
		}
		
		for (Iterator<String> iteratorQuery = questionTerms.iterator(); iteratorQuery.hasNext();) {
			String term = iteratorQuery.next();
			
			double idf;
			
			if (documents.getHash().containsKey(term)) {
				double numberRelevantDocs = documents.getHash().get(term).size();
				idf = numberDocs / numberRelevantDocs;
				idf = Math.log(idf) / Math.log(2);
				
				List<RadixInfo> listDocInfo = documents.getHash().get(term);
				
				for (Iterator<RadixInfo> iteratorDoc = listDocInfo.iterator(); iteratorDoc.hasNext();) {
					RadixInfo doc = iteratorDoc.next();
					
					double tfIdf = 0.0;
					tfIdf = doc.getTf()*idf;
					if(!docMatrix.containsKey(doc.getDocumentId())){
						docMatrix.put(doc.getDocumentId(), new ArrayList<Double>(temporaryList));
					}
					docMatrix.get(doc.getDocumentId()).add(posicao, tfIdf);
				}
			} 
			posicao++;
		}
		
		Hashtable<Integer, Double> ranking = new Hashtable<Integer, Double>();
		
		for(Enumeration<Integer> n = docMatrix.keys(); n.hasMoreElements(); ){  
			int key = n.nextElement();
			ranking.put(key, calculateSimilarity(docMatrix.get(key)));
		}
		
		/*
		 * Ordenando pelo valor de similaridade
		 */

		ArrayList tempRanking = new ArrayList(ranking.entrySet());
		
		Collections.sort(tempRanking, new SimilarityComparator());
		
		Iterator itr = tempRanking.iterator();
		while(itr.hasNext()){
			
			Map.Entry e = (Map.Entry)itr.next();
			result.add((Integer)e.getKey());
		}
		
		return result;
	}
	

	public double calculateSimilarity(List<Double> docRelevantTerms) {
		double similarity;
		
		/*
		 * X * Y / |X|*|Y|
		 * Y = {1,1,1,1...,1}
		 * logo:
		 * Somatório(xi)[1..n] / sqrt(som(xi^2)[1..n])) * sqrt(n)
		 */
		
		double somatorio = 0.0;
		double normaDoc = 0.0;
		double normaQuestion = 0.0;
		
		for (int i = 0; i < docRelevantTerms.size(); i++) {
			somatorio += docRelevantTerms.get(i);
			normaDoc += Math.pow(docRelevantTerms.get(i), 2);
		}
		normaQuestion = Math.sqrt(docRelevantTerms.size());
		normaDoc = Math.sqrt(normaDoc);
		
		similarity = somatorio/ (normaDoc * normaQuestion);
		
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

				// Sort string in an ascending order
				result = word1.compareTo(word2);

			} else {
				// Sort values in a descending order
				result = value2.compareTo(value1);
			}
			return result;
		}
	}
}
