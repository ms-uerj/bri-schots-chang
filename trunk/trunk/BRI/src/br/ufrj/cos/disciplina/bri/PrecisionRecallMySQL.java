package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import br.ufrj.cos.disciplina.bri.algorithm.PrecisionRecall;
import br.ufrj.cos.disciplina.bri.model.Point;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;
// import br.ufrj.cos.disciplina.bri.persistence.TestConnection;

public class PrecisionRecallMySQL {

	/**
	 * Reads data from XML files and calculates
	 * precision-recall for the searches.
	 * @param args
	 */
	public static void main(String[] args) {

		//TestConnection connection = new TestConnection();
		//connection.test();

		// records list
		List<Record> listOfRecords = new ArrayList<Record>();
		// queries list
		List<Query> listOfQueries = new ArrayList<Query>();

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
		
		listOfQueries.addAll(Query.parseQueryFromXML("resources/inputs/cfquery-corrigido.xml"));
		
		List<Point> listOfPoints = new ArrayList<Point>();
		
		for (int k = 0; k < 11; k++) {
			listOfPoints.add(new Point(k, 0.0));
		}
		
		for (int i = 0; i < listOfQueries.size(); i++) {
			List<Point> listOfPrecisionRecallValues = new ArrayList<Point>();
			
			List<Integer> answers;
			List<Integer> relevants;
			answers = PrecisionRecall.getQueryElementSet(PrecisionRecall.TYPE_QUERY_BOOLEAN, listOfQueries.get(i), "DATA");
			relevants = PrecisionRecall.getRelevantElementSet(listOfQueries.get(i));
			
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
		
		System.out.println("Fim");

	}

}
