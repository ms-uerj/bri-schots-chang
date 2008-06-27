package br.ufrj.cos.disciplina.bri.algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufrj.cos.disciplina.bri.model.Query;

public class PrecisionRecall {

	public static void getPrecisionRecall(String tipoBusca, Query query, String campoConsulta) {
		ArrayList<Integer> answers = new ArrayList<Integer>();

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost/bri", "root", "admin");
			Statement statement = conn.createStatement();
			ResultSet resultQuestion;

			ResultSet resultAnswers;
			resultAnswers = statement
					.executeQuery("SELECT *"
							+ " FROM record WHERE MATCH (DATA, TITLE, ABSTRACT) AGAINST ('"
							+ query.getQuestion() + "' IN BOOLEAN MODE)");
			while (resultAnswers.next()) {
				answers.add(resultAnswers.getInt("RECORD_ID"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
