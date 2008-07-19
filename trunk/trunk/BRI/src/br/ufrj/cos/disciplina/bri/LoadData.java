package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;
import br.ufrj.cos.disciplina.bri.persistence.JPAResourceBean;
//import br.ufrj.cos.disciplina.bri.persistence.TestConnection;

public class LoadData {

	/**
	 * Reads data from XML files and populate the database.
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

		JPAResourceBean jpaResourceBean = new JPAResourceBean();
		EntityManager em = jpaResourceBean.getEMF("mysql")
				.createEntityManager();
		try {
			em.getTransaction().begin();

			for (int i = 0; i < listOfRecords.size(); i++) {
				em.persist(listOfRecords.get(i));
			}
			
			for (int i = 0; i < listOfQueries.size(); i++) {
				em.persist(listOfQueries.get(i));
			}

			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
}
