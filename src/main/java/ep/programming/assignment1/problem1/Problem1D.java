package ep.programming.assignment1.problem1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import ep.programming.assignment1.IOHelper;
import ep.programming.assignment1.problem1.ClosestPairs.Pair;
import ep.programming.assignment1.problem1.ClosestPairs.Point;

/**
 * Problem 1 Part D
 * @author Jordan Beth
 *
 */
public class Problem1D {

	private static final String OUT_FILE_NAME = "output_closest_pairs_1D.txt";
	private static final String IN_FILE_NAME = "input_closest_pairs_1D.txt";
	
	private static ClosestPairs closestPairs;
	
	/**
	 * Change this to configure the N closest pairs or N closest distances
	 */
	private static int N = 1;

	private enum OutputType {
		CLOSEST_PAIR(1),
		CLOSEST_PAIR_DISTANCE(1),
		
		N_CLOSEST_PAIRS(N),
		N_CLOSEST_PAIRS_DISTANCES(N);	
		
		private int n;
		private OutputType(int n) {
			this.n = n;
		}
		
		public int getN() {
			return this.n;
		}
		
		public void setN(int n) {
			this.n = n;
		}
	}
	
	/**
	 * Main method. Program driver.
	 * @param args
	 */
	public static void main(String[] args) {
		
		double[][] input = IOHelper.getInputForProblem1(IN_FILE_NAME);	

		// Part D
		closestPairs = new ClosestPairs(input);	
		
		/**
		 * Uncomment for part 1.D.1
		 */
		 // findClosestPairsDivideAndConquer(OutputType.CLOSEST_PAIR_DISTANCE);
		
		/**
		 * Uncomment for part 1.D.2
		 */
		 closestPairs = new ClosestPairs(input);	
		 OutputType.N_CLOSEST_PAIRS.setN(10);
		 // findClosestPairsDivideAndConquer(OutputType.N_CLOSEST_PAIRS);
		
		/**
		 * Uncomment for part 1.D.3 
		 */
		 closestPairs = new ClosestPairs(input);
		 OutputType.N_CLOSEST_PAIRS_DISTANCES.setN(9); 
		 // findClosestPairsDivideAndConquer(OutputType.N_CLOSEST_PAIRS_DISTANCES);

	}
	
	/**
	 * 
	 * @param outputType
	 */
	public static void findClosestPairsDivideAndConquer(OutputType outputType) {
		Point[] points = closestPairs.getPoints();
		Point[] pointsSortedByX = getPointsByXCoord(points);
		Point[] pointsSortedByY = getPointsByYCoord(points);

		StringBuilder sb = new StringBuilder();
		Pair closestPair;
		switch(outputType) {
		case CLOSEST_PAIR:
			
			closestPair = findClosestPairsDivideAndConquer(pointsSortedByX, pointsSortedByY);
			IOHelper.writeOutput(OUT_FILE_NAME, closestPair.toString());
			break;
			
		case CLOSEST_PAIR_DISTANCE:
			
			closestPair = findClosestPairsDivideAndConquer(pointsSortedByX, pointsSortedByY);
			String distance = String.valueOf(closestPair.distance);
			IOHelper.writeOutput(OUT_FILE_NAME, distance);
			break;
			
		case N_CLOSEST_PAIRS_DISTANCES:
			
			int n1 = OutputType.N_CLOSEST_PAIRS_DISTANCES.getN();
			
			if(n1 > closestPairs.getNumberOfPoints() || n1 <= 0) {
				throw new IllegalArgumentException("N cannot be greater than the number of points, or <= 0");
			}
			sb.setLength(0);
			while(n1-- > 0) {
				closestPair = findClosestPairsDivideAndConquer(pointsSortedByX, pointsSortedByY);
				sb.append(closestPair.distance);
				sb.append("\n");
				points = getPointsWithClosestPairRemoved(points, closestPair);	
				pointsSortedByX = getPointsByXCoord(points);
				pointsSortedByY = getPointsByYCoord(points);
			}
			
			// print distances	
			IOHelper.writeOutput(OUT_FILE_NAME, sb.toString());
			
			break;
			
		case N_CLOSEST_PAIRS:
			
			int n2 = OutputType.N_CLOSEST_PAIRS.getN();
			if(n2 > closestPairs.getNumberOfPoints() || n2 <= 0) {
				throw new IllegalArgumentException("N cannot be greater than the number of points, or <= 0");
			}
			
			sb.setLength(0);
			while(n2-- > 0) {
				closestPair = findClosestPairsDivideAndConquer(pointsSortedByX, pointsSortedByY);
				sb.append(closestPair);
				sb.append("\n");
				points = getPointsWithClosestPairRemoved(points, closestPair);
				pointsSortedByX = getPointsByXCoord(points);
				pointsSortedByY = getPointsByYCoord(points);
			}
			
			// print pairs
			IOHelper.writeOutput(OUT_FILE_NAME, sb.toString());
			
			break;
		default:
			throw new IllegalArgumentException("OutputType not supported for outputType: [" + outputType + "]");
		}
	}
	
	private static Point[] getPointsWithClosestPairRemoved(Point[] points, Pair pairToRemove) {
		List<Point> pointsList = new ArrayList<ClosestPairs.Point>();
	
		for(Point p : points) {
			pointsList.add(p);
		}
		
		
		for(int i = 0; i < points.length; i++) {
			
			Point p = points[i];
			
			if (p.x == pairToRemove.p1.x && p.y == pairToRemove.p1.y) {
				pointsList.remove(i);
				break;
			}
			
			if (p.x == pairToRemove.p2.x && p.y == pairToRemove.p2.y) {
				pointsList.remove(i);
				break;
			}
		}
		
		return pointsList.toArray(new Point[] {});
	}
	
	
	/**
	 * 
	 * @param pointsSortedByX
	 * @param pointsSortedByY
	 * @return
	 */
	private static Pair findClosestPairsDivideAndConquer(Point[] pointsSortedByX, Point[] pointsSortedByY) {
		if(pointsSortedByX.length <= 3) { // base case
			return findClosestPairsBruteForce(pointsSortedByX);
		}
		
		int numPoints = pointsSortedByX.length;
		
		int midpoint = numPoints / 2;
		
		Point[] leftHalf = Arrays.copyOfRange(pointsSortedByX, 0, midpoint);
		Point[] rightHalf = Arrays.copyOfRange(pointsSortedByX, midpoint, numPoints);
		
		// Find the closest pair in the left half
		Point[] leftHalfSortedByY = getPointsByYCoord(leftHalf);
		Pair closestPairLeft = findClosestPairsDivideAndConquer(leftHalf, leftHalfSortedByY);
		
		// Find the closest pair in the right half
		Point[] rightHalfSortedByY = getPointsByYCoord(rightHalf);
		Pair closestPairRight = findClosestPairsDivideAndConquer(rightHalf, rightHalfSortedByY);
		
		Pair closestPair;
		// Find overall closest
		if(closestPairRight.distance < closestPairLeft.distance) {
			closestPair = closestPairRight;
		} else {
			closestPair = closestPairLeft;
		}
		
		// Add points to a separate list if they are close to the vertical line passing through the middle point
		List<Point> stripPoints = new ArrayList<>();
		double shortestDistance = closestPair.distance;
		double midPointX = rightHalf[0].x;
		for (Point point : pointsSortedByY) {
			double xDistAbsValue = Math.abs(midPointX - point.x);
			if (xDistAbsValue < shortestDistance) {
				stripPoints.add(point);		
			}				
		}
		
		// Find out if any point in the strip is closer than the current closestPair
		for (int i = 0; i < stripPoints.size() - 1; i++) {
			Point pointI = stripPoints.get(i);
			for (int j = i + 1; j < stripPoints.size(); j++) {
				Point pointJ = stripPoints.get(j);
				
				double diffY = pointJ.y - pointI.y;
				if (diffY >= shortestDistance) {
					break;		
				}
				
				Pair newPair = new Pair(pointJ, pointI);
				
				if (newPair.distance < closestPair.distance) {
					closestPair = newPair;
					shortestDistance = newPair.distance;
				}
			}
		}
		
		return closestPair;
	}
	
	/**
	 * 
	 * @param points
	 * @return
	 */
	private static Pair findClosestPairsBruteForce(Point[] points) {
		Pair closestPair = new Pair();
		for(int i = 0; i < points.length; i++) {
			Point point_i = points[i];
			
			for(int j = i + 1; j < points.length; j++) {
				Point point_j = points[j];
				
				// First, determine the distance between every pair of points
				double distance = Pair.distance(point_i, point_j);
				
				if(distance < closestPair.distance) {
					// Then, find the points with the closest distance by updating 
					// the overall closest when a new one is found
					closestPair.setPair(point_i, point_j);
				}
			}
		}
		
		return closestPair;
	}
	
	/**
	 * 
	 * @param arrToCopy
	 * @return
	 */
	private static Point[] getPointsByXCoord(Point[] arrToCopy) {
		Point[] pointsByX = Arrays.copyOf(arrToCopy, arrToCopy.length);
		// Merge sort - O(n * lg n)
		Arrays.sort(pointsByX, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				int ret;
				if(p1.x < p2.x) {
					ret = -1;
				} else if (p1.x > p2.x) {
					ret = 1;
				} else {
					ret = 0;						
				}
				
				return ret;
			}});
		
		return pointsByX;
	}
	
	/**
	 * 
	 * @param arrToCopy
	 * @return
	 */
	private static Point[] getPointsByYCoord(Point[] arrToCopy) {
		Point[] pointsByY = Arrays.copyOf(arrToCopy, arrToCopy.length);
		// Merge sort - O(n * lg n)
		Arrays.sort(pointsByY, new Comparator<Point>() {
			@Override
			public int compare(Point p1, Point p2) {
				int ret;
				if(p1.y < p2.y) {
					ret = -1;
				} else if (p1.y > p2.y) {
					ret = 1;
				} else {
					ret = 0;						
				}
				
				return ret;
			}});
		
		return pointsByY;
	}

}
