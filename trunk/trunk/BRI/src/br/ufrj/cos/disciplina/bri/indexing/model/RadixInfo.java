package br.ufrj.cos.disciplina.bri.indexing.model;

public class RadixInfo {

	// shows the document id where a radix occurs
	private int documentId;
	
	// shows how much times the radix occurs in the document
	private double tf;
	
	// specifies the section of the document (TITLE, ABSTRACT)
	private String part;

	/**
	 * Constructor method
	 * @param documentId - indicates what document contains the radix
	 * @param numberOfOccurrences - show the number of occurrences of the radix in the document 
	 */
	public RadixInfo(int documentId, double tf, String part) {
		this.documentId = documentId;
		this.tf = tf;
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

	public void setTf(double tf) {
		this.tf = tf;
	}
	
	public double getTf() {
		return tf;
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
		return "Record: "+documentId+ " - tf: "+tf+" - Where: "+part;
	}
	
}
