package br.ufrj.cos.disciplina.bri.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.validator.Length;

@Entity
public class Record {
	
	@Id
	private int id;
	@Column(length=500)
	private String title;
	@Column(length=500)
	private String abztract;
	/*
	 * TODO Manter o nome 'xml' ou modificar para 'dados'? Acho que
	 * 'xml' ficou muito abstrato, dá a idéia do todo, e 'dados' é
	 * só aquele trecho do record, que é o que está sendo persistido...
	 */
	@Column(length=500)
	private String record;
	
	public Record() {

	}
	
	public Record(int id, String titulo, String resumo, String xml) {
		this.id = id;
		this.title = titulo;
		this.abztract = resumo;
		this.record = xml;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitulo() {
		return title;
	}
	
	public void setTitulo(String titulo) {
		this.title = titulo;
	}
	
	public String getResumo() {
		return abztract;
	}
	
	public void setResumo(String resumo) {
		this.abztract = resumo;
	}
	
	public String getXml() {
		return record;
	}
	
	public void setXml(String xml) {
		this.record = xml;
	}

}
