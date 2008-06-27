package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.ufrj.cos.disciplina.bri.algorithm.PrecisionRecall;
import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;
import br.ufrj.cos.disciplina.bri.persistence.JPAResourceBean;
import br.ufrj.cos.disciplina.bri.persistence.TestConnection;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestConnection connection = new TestConnection();
		connection.test();

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
		
		//for (int i = 0; i < listQueries.size(); i++) {
			List<Integer> answers;
			List<Integer> relevants;
			
			answers = PrecisionRecall.getQueryElementSet("BOOLEAN", listQueries.get(56), "ABSTRACT");
			relevants = PrecisionRecall.getRelevantElementSet(listQueries.get(56));
			
			PrecisionRecall.getPrecisionRecall(relevants, answers);
			
		//}
		
		System.out.println("Fim");

	}

}
