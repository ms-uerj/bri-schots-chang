package br.ufrj.cos.disciplina.bri.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Evaluation {
	@Id
	@GeneratedValue
	private int id;
	@Column(name = "SCORE", length = 500)
	private String score;
	@Column(name = "FK_RECORD_ID")
	private int recordId;

	public Evaluation() {
		// TODO Auto-generated constructor stub
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public int getRecordId() {
		return recordId;
	}

}
