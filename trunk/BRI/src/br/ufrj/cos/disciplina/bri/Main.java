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

		// objetos necess�rios � leitura do XML
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document myDoc;

		// lista de registros (records) do XML
		NodeList listRecords;

		try {
			db = dbf.newDocumentBuilder();
			myDoc = db.parse(new File("resources/inputs/cf74.xml"));
			
			// inicializa��o da lista de registros
			listRecords = myDoc.getElementsByTagName("RECORD");
			
			// TODO descomentar for (int i = 0; i < listRecords.getLength(); i++) {
				// instancia o record a ser persistido
				Record record = new Record();
				
				// captura o record e seu conte�do
				Node nodeRecord = listRecords.item(1); // TODO mudar de 1 pra i
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
						System.out.println(record.getId()); // TODO remover
					} else if (recordItem.getNodeName().equals("TITLE")){
						record.setTitulo(recordItem.getFirstChild().getNodeValue());
						System.out.println(record.getTitulo()); // TODO remover
					} else if (recordItem.getNodeName().equals("ABSTRACT")){
						record.setResumo(recordItem.getFirstChild().getNodeValue());
						System.out.println(record.getResumo()); // TODO remover
					}
				}

				// persiste o conte�do capturado do record
				record.setXml(null); // TODO aqui ser� colocado o que for capturado acima

			// TODO descomentar }
				
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

}
