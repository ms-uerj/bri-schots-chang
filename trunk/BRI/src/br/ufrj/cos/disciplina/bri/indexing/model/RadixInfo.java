package br.ufrj.cos.disciplina.bri.indexing.model;

public class RadixInfo {

	// shows the document id where a radix occurs
	private int documentId;
	
	// shows how much times the radix occurs in the document
	private int numberOfOccurrences;
	
	// specifies the section of the document (TITLE, ABSTRACT)
	private String part;

	// TODO a double variable for weight??? is it necessary??? see slides
	
	/**
	 * Constructor method
	 * @param documentId - indicates what document contains the radix
	 * @param numberOfOccurrences - show the number of occurrences of the radix in the document 
	 */
	public RadixInfo(int documentId, int numberOfOccurrences, String part) {
		this.documentId = documentId;
		this.numberOfOccurrences = numberOfOccurrences;
		this.part = part;
	}

	/**
	 * @return the documentId where a radix occurs
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
	 * @return the number of occurrences of the radix in the document
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

	/**
	 * @return the part
	 */
	public String getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(String part) {
		this.part = part;
	}
	
	@Override
	public String toString() {
		return "Record: "+documentId+ "- Ocurr: "+numberOfOccurrences+"- Where: "+part;
	}
	
}
