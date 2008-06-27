package br.ufrj.cos.disciplina.bri;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufrj.cos.disciplina.bri.model.Record;
import br.ufrj.cos.disciplina.bri.persistence.JPAResourceBean;
import br.ufrj.cos.disciplina.bri.persistence.TestConnection;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// lista de records
		List<Record> listaRecords = new ArrayList<Record>();

		// objetos necess�rios � leitura do XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// lista de n�s record (dados do XML)
		NodeList listRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File("resources/inputs/cf74.xml"));

			// inicializa��o da lista de n�s record
			listRecords = myDoc.getElementsByTagName("RECORD");

			for (int i = 0; i < listRecords.getLength(); i++) {

				// instancia o record a ser persistido
				Record record = new Record();

				// captura o record e seu conte�do
				Node nodeRecord = listRecords.item(i);
				NodeList recordContents = nodeRecord.getChildNodes();

				// percorre o conte�do capturado do record
				for (int j = 0; j < recordContents.getLength(); j++) {

					// captura o item do record
					Node recordItem = recordContents.item(j);

					// captura o item conforme seu nome
					if (recordItem.getFirstChild() != null) {
						/* TODO todo o conte�do do XML relativo 
						 * �quele record espec�fico vem pra c� (sem as tags),
						 * precisamos ver como fazer (ainda n�o entendi a captura
						 * do XML, se ser� ele inteiro, por registro e tal) */
					}					
					if (recordItem.getNodeName().equals("RECORDNUM")){
						record.setId(Integer.parseInt(recordItem.getFirstChild().getNodeValue().trim()));
					} else if (recordItem.getNodeName().equals("TITLE")){
						record.setTitulo(recordItem.getFirstChild().getNodeValue());
					} else if (recordItem.getNodeName().equals("ABSTRACT")){
						record.setAbztract(recordItem.getFirstChild().getNodeValue());
					}
				}

				// persiste o conte�do capturado do record
				record.setRecordData(null); // TODO aqui ser� colocado o que for capturado acima
				listaRecords.add(record);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

		JPAResourceBean jpaResourceBean = new JPAResourceBean();
		EntityManager em = jpaResourceBean.getEMF("mysql").createEntityManager();
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
