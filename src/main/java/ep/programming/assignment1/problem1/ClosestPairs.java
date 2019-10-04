package ep.programming.assignment1.problem1;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jordan Beth
 *
 */
public class ClosestPairs {
	private Point[] points;
	private int numberOfPoints;
	
	public ClosestPairs(double[][] points) {
		setPoints(points);		
	}
	
	private void setPoints(double[][] points) {
		List<Point> pointsList = new ArrayList<>();
		
		for(double[] pair : points) {
			double x = pair[0];
			double y = pair[1];
			pointsList.add(new Point(x, y));
		}
		
		this.points = pointsList.toArray(new Point[pointsList.size()]);
		this.numberOfPoints = pointsList.size();
	}
	
	public int getNumberOfPoints() {
		return this.numberOfPoints;
	}
	
	public Point[] getPoints() {
		return this.points;
	}
	
	public static class Pair {
		Point p1;
		Point p2;
		double distance;
		
		public Pair() {
			this.distance = Double.MAX_VALUE;
		}
		
		public Pair(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
			this.distance = distance(this.p1, this.p2);
		}
		
		/**
		 * A method to find the euclidian distance between two points.
		 * @param p1
		 * 			- The first point
		 * @param p2
		 * 			- The second point
		 * @return double representing the euclidian distance between the two points
		 */
		public static double distance(Point p1, Point p2) {
			double diff_x = p2.x - p1.x;
			double diff_y = p2.y - p1.y;
			
			double distanceSquared = Math.pow(diff_x, 2) + Math.pow(diff_y, 2);
			
			return Math.pow(distanceSquared, 0.5);
		}
		
		public void setPair(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
			this.distance = distance(this.p1, this.p2);
		}
		
		public double[][] getUnderlyingPair() {
			return new double[][] { this.p1.getUnderlyingPoint(), this.p2.getUnderlyingPoint() };
		}
		
		@Override
		public String toString() {
			return "[" + this.p1 + ", " + this.p2 + "]";
		}
	}
	
	public static class Point {
		final double x;
		final double y;
		
		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
		
		public double[] getUnderlyingPoint() {
			return new double[]{ x, y };
		}
		
		@Override
		public String toString() {
			return "(" + this.x + ", " + this.y + ")";
		}
	}
}