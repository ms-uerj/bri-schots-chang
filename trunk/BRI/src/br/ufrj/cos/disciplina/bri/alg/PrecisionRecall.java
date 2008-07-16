package br.ufrj.cos.disciplina.bri.alg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufrj.cos.disciplina.bri.model.Point;
import br.ufrj.cos.disciplina.bri.model.Query;

public class PrecisionRecall {
	public static String TYPE_QUERY_BOOLEAN = "BOOLEAN";
	public static String TYPE_QUERY_NATURAL = "NATURAL";
	public static String TYPE_QUERY_NATURAL_EXT = "NATURAL_EXT";

	/**
	 * @return a set of elements from a query
	 */
	public static List<Integer> getQueryElementSet(String searchType,Query query, String campoConsulta) {

		ArrayList<Integer> answers = new ArrayList<Integer>();

		System.out.println("Pergunta: " + query.getQuestion());

		try {

			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bri", "root", "admin");
			//Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bri");
			Statement statement = conn.createStatement();

			ResultSet resultAnswers;
			
			String queryString;
			
			queryString = "SELECT *"
				+ " FROM record WHERE MATCH (" + campoConsulta
				+ ") AGAINST ('" + query.getQuestion() +"'";
			if(searchType.equals(PrecisionRecall.TYPE_QUERY_BOOLEAN)){
				queryString += " IN BOOLEAN MODE)"; 
			} else if (searchType.equals(PrecisionRecall.TYPE_QUERY_NATURAL)){
				queryString += ")";
			} else if (searchType.equals(PrecisionRecall.TYPE_QUERY_NATURAL_EXT)) {
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

		System.out.println("Consulta: " + answers);

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
		System.out.println("Relevantes: " + relevants);

		return relevants;
	}
	
	public static List<Point> getPrecisionRecall(List<Integer> relevants, List<Integer> answers){
		List<Point> listOfPoints = new ArrayList<Point>();
		
		// number of elements returned by the search that are relevants
		double numElementosComum = 0;
		int sizeRelevants = relevants.size();
		
		List<Double> precisions = new ArrayList<Double>();
		List<Double> recalls = new ArrayList<Double>();
		
		// recall
		double recall;
		for (int i = 0; i < relevants.size(); i++) {
			if (answers.contains(relevants.get(i))) {
				numElementosComum++;
				recall = numElementosComum/sizeRelevants;
				recalls.add(recall);
			}
		}
		
		numElementosComum = 0;
		
		//precision
		double precision;
		for (int j = 0; j < answers.size(); j++) {
			if(relevants.contains(answers.get(j))){
				numElementosComum++;
				precision = numElementosComum/(j+1);
				precisions.add(precision);
			}
		}
		for (int i = 0; i < precisions.size(); i++) {
			listOfPoints.add(new Point(recalls.get(i), precisions.get(i)));
			System.out.println(recalls.get(i)+"\t"+precisions.get(i));
		}
		
		return listOfPoints;
	}
}
