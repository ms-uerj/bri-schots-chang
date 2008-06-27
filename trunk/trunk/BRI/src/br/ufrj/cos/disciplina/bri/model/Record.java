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
	@Column(length=500)
	private String recordData;
	
	public Record() {

	}
	
	public Record(int id, String titulo, String abztract, String recordData) {
		this.id = id;
		this.title = titulo;
		this.abztract = abztract;
		this.recordData = recordData;
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
	
	public String getAbztract() {
		return abztract;
	}
	
	public void setAbztract(String abztract) {
		this.abztract = abztract;
	}
	
	public String getRecordData() {
		return recordData;
	}
	
	public void setRecordData(String recordData) {
		this.recordData = recordData;
	}

}
