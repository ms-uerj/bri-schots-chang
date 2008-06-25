package br.ufrj.cos.disciplina.bri.model;

public class Record {
	private int id;
	private String titulo;
	private String resumo;
	private String xml;
	
	public Record() {
		// TODO Auto-generated constructor stub
	}
	
	public Record(int id, String titulo, String resumo, String xml) {
		this.id = id;
		this.titulo = titulo;
		this.resumo = resumo;
		this.xml = xml;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getResumo() {
		return resumo;
	}
	public void setResumo(String resumo) {
		this.resumo = resumo;
	}
	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	} 
	
	

}
