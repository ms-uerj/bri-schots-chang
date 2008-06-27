package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.ufrj.cos.disciplina.bri.model.Query;
import br.ufrj.cos.disciplina.bri.model.Record;
import br.ufrj.cos.disciplina.bri.persistence.JPAResourceBean;
import br.ufrj.cos.disciplina.bri.persistence.TestConnection;

public class LoadData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		TestConnection connection = new TestConnection();
		connection.test();

		// Lista de Records
		List<Record> listaRecords = new ArrayList<Record>();
		// Lista de Queries
		List<Query> listaQueries = new ArrayList<Query>();

		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf74.xml"));
		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf75.xml"));
		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf76.xml"));
		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf77.xml"));
		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf78.xml"));
		listaRecords.addAll(Record
				.parseRecordFromXML("resources/inputs/cf79.xml"));
		
		listaQueries.addAll(Query.parseQueryFromXML("resources/inputs/cfquery-corrigido.xml"));

		JPAResourceBean jpaResourceBean = new JPAResourceBean();
		EntityManager em = jpaResourceBean.getEMF("mysql")
				.createEntityManager();
		try {
			em.getTransaction().begin();

			for (int i = 0; i < listaRecords.size(); i++) {
				em.persist(listaRecords.get(i));
			}
			
			for (int i = 0; i < listaQueries.size(); i++) {
				em.persist(listaQueries.get(i));
			}

			em.getTransaction().commit();
		} finally {
			em.close();
		}

		System.out.println("Fim");

	}

}
