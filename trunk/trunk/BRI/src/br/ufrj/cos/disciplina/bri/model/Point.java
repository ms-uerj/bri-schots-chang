package br.ufrj.cos.disciplina.bri.model;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	private double xRecall;
	private double yPrecision;
	
	
	/**
	 * Default constructor method. 
	 * @see br.ufrj.cos.disciplina.bri.model.Point#Point(double xRecall, double yPrecision)
	 */
	public Point() {

	}
	
	/**
	 * Constructor method.
	 * @param xRecall - the recall (x coordinate point)
	 * @param yPrecision - the precision (y coordinate point)
	 */
	public Point(double xRecall, double yPrecision) {
		super();
		this.xRecall = xRecall;
		this.yPrecision = yPrecision;
	}

	/**
	 * @return recall (x coordinate point)
	 */
	public double getXRecall() {
		return xRecall;
	}
	
	/**
	 * @param recall - the recall (x coordinate point) to set
	 */
	public void setXRecall(double recall) {
		xRecall = recall;
	}
	
	/**
	 * @return precision (y coordinate point)
	 */
	public double getYPrecision() {
		return yPrecision;
	}
	
	/**
	 * @param precision - the precision (y coordinate point) to set
	 */
	public void setYPrecision(double precision) {
		yPrecision = precision;
	}
	
	@Override
	public String toString() {
		return xRecall + "\t" + yPrecision;
	}

	/**
	 * Receives a list of Point objects and normalizes them in a set of ten Points.
	 * @param listOfPoints - the list that contains Point objects to be interpolated
	 * @return a new list of interpolated Point objects
	 */
	public static List<Point> interpolate(List<Point> listOfPoints) {
		List<Point> newListOfPoints = new ArrayList<Point>();
		for (int k = 0; k < 11; k++) {
			newListOfPoints.add(new Point(k, 0.0));
		}
		
		for (int j = 0; j < listOfPoints.size(); j++) {
			double recall = listOfPoints.get(j).getXRecall();
			double precision = listOfPoints.get(j).getYPrecision();
			
			for (int k = 0; k < 11; k++) {
				if ((k < (recall * 10)) && ((recall * 10) < k + 1)) {
					if (precision > newListOfPoints.get(k).yPrecision)
						newListOfPoints.get(k).yPrecision = precision; 
				}
			}
		}
		for (int k = 0; k < 11; k++) {
			System.out.println(newListOfPoints.get(k));
		}
		return newListOfPoints;		
	}

}
