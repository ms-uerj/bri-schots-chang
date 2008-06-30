package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.ufrj.cos.disciplina.bri.algorithm.PrecisionRecall;
import br.ufrj.cos.disciplina.bri.model.Point;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;
import br.ufrj.cos.disciplina.bri.persistence.JPAResourceBean;
import br.ufrj.cos.disciplina.bri.persistence.TestConnection;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		///TestConnection connection = new TestConnection();
		//connection.test();

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
		
		List<Point> pontos = new ArrayList<Point>();
		for (int k = 0; k < 11; k++) {
			pontos.add(new Point(k, 0.0));
		}
		
		for (int i = 0; i < listQueries.size(); i++) {
			List<Point> lista = new ArrayList<Point>();
			
			List<Integer> answers;
			List<Integer> relevants;
			
			answers = PrecisionRecall.getQueryElementSet(PrecisionRecall.TYPE_QUERY_BOOLEAN, listQueries.get(i), "DATA");
			relevants = PrecisionRecall.getRelevantElementSet(listQueries.get(i));
			
			lista = PrecisionRecall.getPrecisionRecall(relevants, answers);
			
			//interpola lista de valores
			lista = Point.interpolate(lista);
			
			for (int k = 0; k < 11; k++) {
				pontos.get(k).setYPrecision(pontos.get(k).getYPrecision()+lista.get(k).getYPrecision());
			}
		}
		
		for (int k = 0; k < 11; k++) {
			System.out.println(pontos.get(k));
		}
		
		for (int k = 0; k < 11; k++) {
			pontos.get(k).setYPrecision(pontos.get(k).getYPrecision()/100);
			System.out.println(pontos.get(k));
		}
		
		System.out.println("Fim");

	}

}
