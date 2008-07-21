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

	/**
	 * Default constructor method.
	 */
	public Evaluation() {

	}

	/**
	 * @param id - the evaluation id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the evaluation id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the score (of the evaluation)
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score - the score (of the evaluation) to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @param recordId - the evaluation record id to set
	 */
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @return the evaluation record id
	 */
	public int getRecordId() {
		return recordId;
	}

}
