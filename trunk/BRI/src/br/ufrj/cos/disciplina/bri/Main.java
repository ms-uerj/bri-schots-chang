package br.ufrj.cos.disciplina.bri;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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
		List<Record> listaRecords = new ArrayList<Record>();

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

		JPAResourceBean jpaResourceBean = new JPAResourceBean();
		EntityManager em = jpaResourceBean.getEMF("mysql")
				.createEntityManager();
		try {
			em.getTransaction().begin();

			for (int i = 0; i < listaRecords.size(); i++) {
				em.persist(listaRecords.get(i));
			}

			em.getTransaction().commit();
		} finally {
			em.close();
		}

		System.out.println("Fim");

	}

}
