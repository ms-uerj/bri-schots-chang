package br.ufjf.cos.disciplina.bri.indexing.model;

public class RadixInfo {

	// shows the document id where a radix occurs
	private int documentId;
	
	// shows how much times the radix occurs in the document
	private int numberOfOccurrences;
	
	// specifies the section of the document (TITLE, ABSTRACT)
	private int part;

	// TODO a double variable for weight??? is it necessary??? see slides
	
	/**
	 * Constructor method
	 * @param documentId - indicates what document contains the radix
	 * @param numberOfOccurrences - show the number of occurrences of the radix in the document 
	 */
	public RadixInfo(int documentId, int numberOfOccurrences) {
		this.documentId = documentId;
		this.numberOfOccurrences = numberOfOccurrences;
	}

	/**
	 * @return the documentId
	 */
	public int getDocumentId() {
		return documentId;
	}

	/**
	 * @param documentId the documentId to set
	 */
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	/**
	 * @return the number of occurrences
	 */
	public int getNumberOfOccurrences() {
		return numberOfOccurrences;
	}

	/**
	 * @param numberOfOccurrences the number of occurrences to set
	 */
	public void setNumberOfOccurrences(int numberOfOccurrences) {
		this.numberOfOccurrences = numberOfOccurrences;
	}	
}
