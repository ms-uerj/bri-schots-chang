package br.ufrj.cos.disciplina.bri;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.ufrj.cos.disciplina.bri.model.Record;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;
		NodeList listRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File("resources/inputs/cf74.xml"));
			System.out.println(myDoc.getNodeName());

			listRecords = myDoc.getElementsByTagName("RECORD");

			//for (int i = 0; i < listRecords.getLength(); i++) {
				Node nodeRecord = listRecords.item(1);

				NodeList nl = nodeRecord.getChildNodes();
				Record record = new Record();
				for (int j = 0; j < nl.getLength(); j++) {
					
					Node n = nl.item(j);
					if(n.getNodeName().equals("RECORDNUM")){
						System.out.println(n.getFirstChild().getNodeValue());
						record.setId(Integer.parseInt(n.getFirstChild().getNodeValue().trim()));
					}
					if(n.getNodeName().equals("TITLE")){
						System.out.println(n.getFirstChild().getNodeValue());
						record.setTitulo(n.getFirstChild().getNodeValue());
					}
					if(n.getNodeName().equals("ABSTRACT")){
						System.out.println(n.getFirstChild().getNodeValue());
						record.setResumo(n.getFirstChild().getNodeValue());
					}
					
				//}

			}
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
