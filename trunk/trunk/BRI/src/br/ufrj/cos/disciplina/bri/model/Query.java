package br.ufrj.cos.disciplina.bri.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@Entity
public class Query {

	@Id
	private int id;
	@Column(name = "QUESTION", length = 500)
	private String question;
	@OneToMany(cascade = CascadeType.ALL)
	List<Evaluation> evaluations;

	public Query() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}
	
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public static List<Query> parseQueryFromXML(String path) {
		List<Query> listaQueries = new ArrayList<Query>();

		// objetos necessários à leitura do XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// lista de nós (records) do XML
		NodeList listQueries;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File(path));
			// inicialização da lista de registros
			listQueries = myDoc.getElementsByTagName("QUERY");

			for (int i = 0; i < listQueries.getLength(); i++) {

				// instancia o record a ser persistido
				Query query = new Query();

				// captura o record e seu conteúdo
				Node nodeQuery = listQueries.item(i);

				NodeList queryContents = nodeQuery.getChildNodes();

				// percorre o conteúdo capturado do record
				for (int j = 0; j < queryContents.getLength(); j++) {

					// captura o item do record
					Node recordItem = queryContents.item(j);

					// captura o item conforme seu nome
					if (recordItem.getNodeName().equals("QueryNumber")) {
						query.setId(Integer.parseInt(recordItem.getFirstChild()
								.getNodeValue().trim()));
					} else if (recordItem.getNodeName().equals("QueryText")) {
						query.setQuestion(recordItem.getFirstChild()
								.getNodeValue());
					} else if (recordItem.getNodeName().equals("Records")) {
						query.evaluations = Query
								.parseEvaluationFromXML(recordItem);
					}
				}
				// persiste o conteúdo capturado do record
				listaQueries.add(query);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return listaQueries;
	}

	public static List<Evaluation> parseEvaluationFromXML(Node node) {
		List<Evaluation> listaEvaluations = new ArrayList<Evaluation>();

		// lista de nós (records) do XML
		NodeList listEvaluations;

		// inicialização da lista de registros
		listEvaluations = node.getChildNodes();

		for (int i = 0; i < listEvaluations.getLength(); i++) {

			// instancia o record a ser persistido
			Evaluation evaluation = new Evaluation();

			// captura o record e seu conteúdo
			Node nodeEvaluation = listEvaluations.item(i);
			//System.out.println("name" + nodeEvaluation.getNodeName());
			//System.out.println("value" + nodeEvaluation.getNodeValue());

			if (nodeEvaluation.getNodeName().equals("Item")) {
				evaluation.setRecordId(Integer.parseInt(nodeEvaluation.getFirstChild()
						.getNodeValue().trim()));
				evaluation.setScore(nodeEvaluation.getAttributes().getNamedItem("score").getNodeValue());

				listaEvaluations.add(evaluation);
			}

			// persiste o conteúdo capturado do record
			
		}

		return listaEvaluations;
	}
}
