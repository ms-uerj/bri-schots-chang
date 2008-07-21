package br.ufrj.cos.disciplina.bri.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufrj.cos.disciplina.bri.algorithm.TextPreprocessing;

@Entity
public class Record {

	@Id@Column(name = "RECORD_ID")
	private int id;
	@Column(name = "TITLE", length = 500)
	private String title;
	@Column(name = "ABSTRACT", length = 500)
	private String abztract;
	@Column(name = "DATA",length = 500)
	private String data;
	
	@Transient
	private List<String> titleTerms;
	@Transient
	private List<String> abztractTerms;
	
	/**
	 * Default constructor method.
	 */
	public Record() {
		titleTerms = new ArrayList<String>();
		abztractTerms = new ArrayList<String>();
	}

	/**
	 * Optional constructor method.
	 * @param id - the record id to set
	 * @param title - the record title to set
	 * @param abztract - the record abstract to set
	 * @param data - the record data to set
	 */
	public Record(int id, String title, String abztract, String data) {
		// never used
		this.id = id;
		this.title = title;
		this.abztract = abztract;
		this.data = data;
		
		titleTerms = new ArrayList<String>();
		abztractTerms = new ArrayList<String>();
	}

	/**
	 * @return the record id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id - the record id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the record title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title - the record title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the record abstract
	 */
	public String getAbztract() {
		return abztract;
	}

	/**
	 * @param abztract - the record abstract to set
	 */
	public void setAbztract(String abztract) {
		this.abztract = abztract;
	}

	/**
	 * @return the record data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data - the record data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	 * @return the record title terms
	 */
	public List<String> getTitleTerms() {
		return titleTerms;
	}

	/**
	 * @param titleTerms - the record title terms to set 
	 */
	public void setTitleTerms(List<String> titleTerms) {
		this.titleTerms = titleTerms;
	}

	/**
	 * @return the record abstract terms
	 */
	public List<String> getAbztractTerms() {
		return abztractTerms;
	}

	/**
	 * @param abztractTerms - the record abstract terms to set
	 */
	public void setAbztractTerms(List<String> abztractTerms) {
		this.abztractTerms = abztractTerms;
	}

	/**
	 * @param node - the record node to be used to set data
	 */
	public void setData(Node node) {
		Transformer transformer;
		StringWriter stringWriter = new StringWriter();
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(new DOMSource(node), new StreamResult(stringWriter));

		} catch (TransformerException e) {
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
		this.data = stringWriter.toString();
	}

	/**
	  * Parses the records from a XML node.
	  * @param filePath - the XML file path
	  * @return the list of records
	 */
	public static List<Record> parseRecordFromXML(String filePath) {
		List<Record> listOfRecords = new ArrayList<Record>();

		// objects requires for XML reading
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// XML node (records) list
		NodeList nodeListOfRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File(filePath));
			// query list initialization
			nodeListOfRecords = myDoc.getElementsByTagName("RECORD");

			for (int i = 0; i < nodeListOfRecords.getLength(); i++) {

				// instantiate the record object
				Record record = new Record();

				// capture the record node and its content
				Node nodeRecord = nodeListOfRecords.item(i);

				NodeList recordContents = nodeRecord.getChildNodes();

				record.setData(nodeRecord);

				// explore content captured from the record
				for (int j = 0; j < recordContents.getLength(); j++) {

					// capture record item
					Node recordItem = recordContents.item(j);

					// capture an item according to its name
					if (recordItem.getNodeName().equals("RECORDNUM")) {
						record.setId(Integer.parseInt(recordItem
								.getFirstChild().getNodeValue().trim()));
					} else if (recordItem.getNodeName().equals("TITLE")) {
						String tempTitle = recordItem.getFirstChild().getNodeValue();
						record.setTitle(tempTitle);
						record.setTitleTerms(TextPreprocessing.preProcessText(tempTitle));
						
					} else if (recordItem.getNodeName().equals("ABSTRACT")) {
						String tempAbztract = recordItem.getFirstChild().getNodeValue(); 
						record.setAbztract(tempAbztract);
						record.setAbztractTerms(TextPreprocessing.preProcessText(tempAbztract));
					}
				}
				listOfRecords.add(record);
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
		return listOfRecords;
	}
	
	/**
	 * Calculates term frequency on TITLE field.
	 * @param term - the term whose frequency will be verified
	 * @return the term frequency on TITLE field
	 */
	public double getTfOnTitle(String term) {
		double counter = 0;
		if (titleTerms.contains(term)) {
			for (Iterator<String> iterator = titleTerms.iterator(); iterator.hasNext();) {
				if (iterator.next().equals(term))
					counter++;
			}
		}
		return counter/titleTerms.size();
	}
	
	/**
	 * Calculates term frequency on ABSTRACT field.
	 * @param term - the term whose frequency will be verified
	 * @return the term frequency on ABSTRACT field
	 */
	public double getTfOnAbztract(String term) {
		double counter = 0;
		if (abztractTerms.contains(term)) {
			for (Iterator<String> iterator = abztractTerms.iterator(); iterator.hasNext();) {
				if (iterator.next().equals(term))
					counter++;
			}
		}
		return (counter / abztractTerms.size());
	}
}
