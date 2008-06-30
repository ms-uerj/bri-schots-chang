package br.ufrj.cos.disciplina.bri.model;

import java.util.ArrayList;
import java.util.List;

public class Point {
	private double xRecall;
	private double yPrecision;
	
	public Point() {
		
	}
	
	public Point(double recall, double precision) {
		super();
		xRecall = recall;
		yPrecision = precision;
	}

	public double getXRecall() {
		return xRecall;
	}
	public void setXRecall(double recall) {
		xRecall = recall;
	}
	public double getYPrecision() {
		return yPrecision;
	}
	public void setYPrecision(double precision) {
		yPrecision = precision;
	}
	
	@Override
	public String toString() {
		return xRecall+"\t"+yPrecision;
	}
	
	//Recebe uma  lista de pontos e normaliza em um conjunto de dez pontos.
	public static List<Point> interpolate(List<Point> lista){
		List<Point> pontos = new ArrayList<Point>();
		for (int k = 0; k < 11; k++) {
			pontos.add(new Point(k, 0.0));
		}
		
		for (int j = 0; j < lista.size(); j++) {
			Double recall = lista.get(j).getXRecall();
			Double precision = lista.get(j).getYPrecision();
			
			for (int k = 0; k < 11; k++) {
				if((k < (recall*10)) && ((recall*10) < k+1)){
					if(precision > pontos.get(k).yPrecision)
						pontos.get(k).yPrecision = precision; 
				}
			}
		}
		for (int k = 0; k < 11; k++) {
			System.out.println(pontos.get(k));
		}
		
		return pontos;
		
	}

}
