package br.ufrj.cos.disciplina.bri.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
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

	/**
	 * Default constructor method.
	 */
	public Query() {
		questionsTerms = new HashSet<String>();
	}

	/**
	 * @return the query id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id - the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the question of the query
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question - the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}
	
	/**
	 * @param evaluations - the query evaluations to set
	 */
	public void setEvaluations(List<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}
	
	/**
	 * @return the query evaluations
	 */
	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	/**
	 * Parses the queries from a XML file.
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
						String tempQuestion = queryItem.getFirstChild().getNodeValue(); 
						query.setQuestion(tempQuestion);
						query.getQuestionsTerms().addAll(TextPreprocessing.preProcessText(tempQuestion));						
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
	 * Parses evaluation values from a XML node.
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
			//System.out.println("Name" + nodeEvaluation.getNodeName());
			//System.out.println("Value" + nodeEvaluation.getNodeValue());

			if (nodeEvaluation.getNodeName().equals("Item")) {
				evaluation.setRecordId(Integer.parseInt(nodeEvaluation.getFirstChild()
						.getNodeValue().trim()));
				evaluation.setScore(nodeEvaluation.getAttributes().getNamedItem("score").getNodeValue());

				listOfEvaluations.add(evaluation);
			}			
		}
		return listOfEvaluations;
	}
	
	/**
	 * @param questionsTerms - a set with the question terms to set
	 */
	public void setQuestionsTerms(Set<String> questionsTerms) {
		this.questionsTerms = questionsTerms;
	}
	
	/**
	 * @return a set with the questions terms
	 */
	public Set<String> getQuestionsTerms() {
		return questionsTerms;
	}
}
