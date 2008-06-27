package br.ufrj.cos.disciplina.bri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.management.Query;


public class StartPrecisionRecall {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//SELECT * FROM record
		//WHERE MATCH (TITLE)
		//AGAINST ('clinic' IN BOOLEAN MODE)
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bri", "root", "admin");
			Statement statement = conn.createStatement();
			ResultSet resultQuestion;
			
			resultQuestion = statement.executeQuery("SELECT QUESTION FROM query");
			
			while ( resultQuestion.next() ) {
                String question = resultQuestion.getString("QUESTION");
                System.out.println("Pergunta: "+question);
                
                ResultSet resultAnswers;
                //statement = conn.createStatement();
                resultAnswers = statement.executeQuery("SELECT * FROM record WHERE MATCH (DATA, TITLE, ABSTRACT) AGAINST ('"+question+"' IN BOOLEAN MODE)");
                while ( resultAnswers.next() ) {
                	String answer = resultAnswers.getString("TITLE");
                	System.out.println("Resposta: "+answer);
                }
                
			} 
			
			conn.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		
		
	}

}
