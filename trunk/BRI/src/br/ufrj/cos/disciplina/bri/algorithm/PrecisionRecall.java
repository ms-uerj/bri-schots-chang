package br.ufrj.cos.disciplina.bri.algorithm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import br.ufrj.cos.disciplina.bri.model.Point;
import br.ufrj.cos.disciplina.bri.model.Query;

public class PrecisionRecall {
	public static String TYPE_QUERY_BOOLEAN = "BOOLEAN";
	public static String TYPE_QUERY_NATURAL = "NATURAL";
	public static String TYPE_QUERY_NATURAL_EXT = "NATURAL_EXT";

	/*
	 * Retorna conjunto de elementos de uma consulta
	 */
	public static List<Integer> getQueryElementSet(String tipoBusca,Query query, String campoConsulta) {

		ArrayList<Integer> answers = new ArrayList<Integer>();

		System.out.println("Pergunta: " + query.getQuestion());

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bri", "root", "admin");
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bri");
			Statement statement = conn.createStatement();
			ResultSet resultQuestion;

			ResultSet resultAnswers;
			
			String queryString;
			
			queryString = "SELECT *"
				+ " FROM record WHERE MATCH (" + campoConsulta
				+ ") AGAINST ('" + query.getQuestion() +"'";
			if(tipoBusca.equals(PrecisionRecall.TYPE_QUERY_BOOLEAN)){
				queryString += " IN BOOLEAN MODE)"; 
			} else if (tipoBusca.equals(PrecisionRecall.TYPE_QUERY_NATURAL)){
				queryString += ")";
			} else if (tipoBusca.equals(PrecisionRecall.TYPE_QUERY_NATURAL_EXT)) {
				queryString += " WITH QUERY EXPANSION)";
			}
			
			resultAnswers = statement.executeQuery(queryString);
			
			while (resultAnswers.next()) {
				answers.add(resultAnswers.getInt("RECORD_ID"));
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("consulta: "+answers);

		return answers;
	}
	
	/*
	 * Retorna conjunto de elementos relevantes de uma consulta
	 */
	public static List<Integer> getRelevantElementSet(Query query) {
		ArrayList<Integer> relevants = new ArrayList<Integer>();
		for (int i = 0; i < query.getEvaluations().size(); i++) {

			relevants.add(query.getEvaluations().get(i).getRecordId());

		}
		System.out.println("relevantes: "+relevants);

		return relevants;
	}
	
	public static List<Point> getPrecisionRecall(List<Integer> relevants, List<Integer> answers){
		List<Point> lista = new ArrayList<Point>();
		
		double numElementosComum = 0;
		int sizeRelevants = relevants.size();
		// int sizeAnswers = answers.size();
		
		List< Double> precisions = new ArrayList<Double>();
		List< Double> recalls = new ArrayList<Double>();
		
		double recall;
		double precision;
		//recall
		for (int i = 0; i < relevants.size(); i++) {
			if (answers.contains(relevants.get(i))) {
				numElementosComum++;
				recall = numElementosComum/sizeRelevants;
				recalls.add(recall);
			}
		}
		//precision
		numElementosComum = 0;
		for (int j = 0; j < answers.size(); j++) {
			if(relevants.contains(answers.get(j))){
				numElementosComum++;
				precision = numElementosComum/(j+1);
				precisions.add(precision);
			}
		}
		
		for (int i = 0; i < precisions.size(); i++) {
			lista.add(new Point(recalls.get(i), precisions.get(i)));
			System.out.println(recalls.get(i)+"\t"+precisions.get(i));
		}
		
		return lista;
	}
}
