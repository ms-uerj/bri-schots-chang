package br.ufrj.cos.disciplina.bri.model;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

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

import br.ufrj.cos.disciplina.bri.alg.TextPreprocessing;

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
	private List<String> abztractTerms;
	
	public Record() {
		titleTerms = new ArrayList<String>();
		abztractTerms = new ArrayList<String>();
	}

	public Record(int id, String title, String abztract, String document) {
		super();
		this.id = id;
		this.title = title;
		this.abztract = abztract;
		this.data = document;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAbztract() {
		return abztract;
	}

	public void setAbztract(String abztract) {
		this.abztract = abztract;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

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
	 * Parses the records from a XML node
	 * @param filePath - the XML file path
	 * @return the list of records
	 */
	public static List<Record> parseRecordFromXML(String filePath) {
		List<Record> listaRecords = new ArrayList<Record>();

		// objects requires for XML reading
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// XML node (records) list
		NodeList listRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File(filePath));
			// query list initialization
			listRecords = myDoc.getElementsByTagName("RECORD");

			for (int i = 0; i < listRecords.getLength(); i++) {

				// instantiate the record object
				Record record = new Record();

				// capture the record node and its content
				Node nodeRecord = listRecords.item(i);
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
						record.setTitle(recordItem.getFirstChild()
								.getNodeValue());
					} else if (recordItem.getNodeName().equals("ABSTRACT")) {
						record.setAbztract(recordItem.getFirstChild()
								.getNodeValue());
					}
				}
				listaRecords.add(record);
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
		return listaRecords;
	}
	
	/**
	 * Populates the list of terms (titleTerms, aztractTerms)
	 * based on textProcessor rules
	 * @param textProcessor - a text processor
	 */
	public void processText(TextPreprocessing textProcessor) {
		List<String> tempOriginal = new Vector<String>();
		
		System.out.println("Record: " + id);
		
		if (abztract == null) {
			abztract = "";
		}
		
		//System.out.println("Title original: "+title);
		//System.out.println("Abstract original: "+abztract);
		
		String tempTitle = title.toUpperCase();
		String tempAbztract = abztract.toUpperCase();
		
		tempTitle = textProcessor.removeSpecialCharacters(tempTitle);			
		tempAbztract = textProcessor.removeSpecialCharacters(tempAbztract);
		
		tempOriginal = textProcessor.removeStopWords(tempTitle);
		titleTerms = textProcessor.applyPorterStemmer(tempOriginal);
		//System.out.println("Title novo: "+title);
		
		tempOriginal = textProcessor.removeStopWords(tempAbztract);
		abztractTerms = textProcessor.applyPorterStemmer(tempOriginal);
		//System.out.println("Abstract novo: "+abztract);
	}

}
