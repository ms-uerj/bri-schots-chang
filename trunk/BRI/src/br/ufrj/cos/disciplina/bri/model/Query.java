package br.ufrj.cos.disciplina.bri.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufrj.cos.disciplina.bri.algorithm.TextPreprocessing;

@Entity
public class Query {

	@Id
	private int id;
	@Column(name = "QUESTION", length = 500)
	private String question;
	@OneToMany(cascade = CascadeType.ALL)
	List<Evaluation> evaluations;
	
	@Transient
	private Set<String> questionsTerms;

	public Query() {
		questionsTerms = new HashSet<String>();

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
		this.questionsTerms.addAll(TextPreprocessing.preProcessText(question));
	}
	
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	/**
	 * Parses the queries from a XML file
	 * @param filePath - the XML file path
	 * @return the list of queries
	 */
	public static List<Query> parseQueryFromXML(String filePath) {
		List<Query> listOfQueries = new ArrayList<Query>();

		// objects requires for XML reading
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// XML node (record queries) list
		NodeList listOfQueriesFromXML;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File(filePath));
			// query list initialization
			listOfQueriesFromXML = myDoc.getElementsByTagName("QUERY");

			for (int i = 0; i < listOfQueriesFromXML.getLength(); i++) {

				// instantiate the query object
				Query query = new Query();

				// capture the query node and its content
				Node nodeQuery = listOfQueriesFromXML.item(i);

				NodeList queryContents = nodeQuery.getChildNodes();

				// explore content captured from the query
				for (int j = 0; j < queryContents.getLength(); j++) {

					// capture query item
					Node queryItem = queryContents.item(j);

					// capture an item according to its name
					if (queryItem.getNodeName().equals("QueryNumber")) {
						query.setId(Integer.parseInt(queryItem.getFirstChild()
								.getNodeValue().trim()));
					} else if (queryItem.getNodeName().equals("QueryText")) {
						query.setQuestion(queryItem.getFirstChild()
								.getNodeValue());
					} else if (queryItem.getNodeName().equals("Records")) {
						query.evaluations = Query
								.parseEvaluationFromXML(queryItem);
					}
				}
				listOfQueries.add(query);
			}
		} catch (SAXException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		return listOfQueries;
	}

	/**
	 * Parses evaluation values from a XML node
	 * @param node - the node to be explored
	 * @return the list of evaluations
	 */
	public static List<Evaluation> parseEvaluationFromXML(Node node) {
		List<Evaluation> listOfEvaluations = new ArrayList<Evaluation>();

		// XML node (records) list
		NodeList listOfRecords;

		// record list initialization
		listOfRecords = node.getChildNodes();

		for (int i = 0; i < listOfRecords.getLength(); i++) {

			// instantiate the evaluation object
			Evaluation evaluation = new Evaluation();

			// capture the evaluation node and its content
			Node nodeEvaluation = listOfRecords.item(i);
			//System.out.println("name" + nodeEvaluation.getNodeName());
			//System.out.println("value" + nodeEvaluation.getNodeValue());

			if (nodeEvaluation.getNodeName().equals("Item")) {
				evaluation.setRecordId(Integer.parseInt(nodeEvaluation.getFirstChild()
						.getNodeValue().trim()));
				evaluation.setScore(nodeEvaluation.getAttributes().getNamedItem("score").getNodeValue());

				listOfEvaluations.add(evaluation);
			}			
		}
		return listOfEvaluations;
	}
	
	
	public void setQuestionsTerms(HashSet<String> questionsTerms) {
		this.questionsTerms = questionsTerms;
	}
	public Set<String> getQuestionsTerms() {
		return questionsTerms;
	}
}
