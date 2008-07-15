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

import br.ufrj.cos.disciplina.bri.algorithms.TextPreprocessing;

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
		Transformer t;
		StringWriter sw = new StringWriter();
		try {
			t = TransformerFactory.newInstance().newTransformer();
			t.transform(new DOMSource(node), new StreamResult(sw));

		} catch (TransformerException e) {
			e.printStackTrace();
		}

		this.data = sw.toString();
	}

	public static List<Record> parseRecordFromXML(String path) {
		List<Record> listaRecords = new ArrayList<Record>();

		// objetos necess�rios � leitura do XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// lista de n�s (records) do XML
		NodeList listRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File(path));
			// inicializa��o da lista de registros
			listRecords = myDoc.getElementsByTagName("RECORD");

			for (int i = 0; i < listRecords.getLength(); i++) {

				// instancia o record a ser persistido
				Record record = new Record();

				// captura o record e seu conte�do
				Node nodeRecord = listRecords.item(i);

				NodeList recordContents = nodeRecord.getChildNodes();

				record.setData(nodeRecord);

				// percorre o conte�do capturado do record
				for (int j = 0; j < recordContents.getLength(); j++) {

					// captura o item do record
					Node recordItem = recordContents.item(j);

					// captura o item conforme seu nome
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
				// persiste o conte�do capturado do record
				listaRecords.add(record);
			}
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return listaRecords;
	}
	
	/*
	 * popula a lista de termos: titleTerms, aztractTerms
	 * baseada nas regras de um textProcessor
	 */
	public void processText(TextPreprocessing textProcessor) {
		List<String> tempOriginal = new Vector<String>();
		
		System.out.println("record: "+id);
		
		if (abztract == null) abztract = "";
		
		//System.out.println("title original: "+title);
		//System.out.println("abstract original: "+abztract);
		
		String tempTitle = title.toUpperCase();
		String tempAbztract = abztract.toUpperCase();
		
		tempTitle = textProcessor.removeSpecialCharacters(tempTitle);			
		tempAbztract = textProcessor.removeSpecialCharacters(tempAbztract);
		
		tempOriginal = textProcessor.removeStopWords(tempTitle);
		titleTerms = textProcessor.applyPorterStemmer(tempOriginal);
		//System.out.println("title novo: "+title);
		
		tempOriginal = textProcessor.removeStopWords(tempAbztract);
		abztractTerms = textProcessor.applyPorterStemmer(tempOriginal);
		//System.out.println("title novo: "+abztract);
	}

}
