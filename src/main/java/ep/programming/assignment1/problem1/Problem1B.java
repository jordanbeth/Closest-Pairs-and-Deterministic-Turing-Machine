package ep.programming.assignment1.problem1;

import ep.programming.assignment1.IOHelper;
import ep.programming.assignment1.problem1.ClosestPairs.Pair;
import ep.programming.assignment1.problem1.ClosestPairs.Point;

/**
 * 
 * @author Jordan Beth
 *
 */
public class Problem1B {

	private static final String OUT_FILE_NAME = "output_closest_pairs_1B.txt";
	private static final String IN_FILE_NAME = "input_closest_pairs_1B.txt";
	
	private static ClosestPairs closestPairs;

	public static void main(String[] args) {
		
		double[][] input = IOHelper.getInputForProblem1(IN_FILE_NAME);
		
		// Part B
		closestPairs = new ClosestPairs(input);
		Pair closestPairA = findClosestPairsBruteForce(closestPairs.getPoints());			

		IOHelper.writeOutput(OUT_FILE_NAME, closestPairA.toString());
	}
		
	private static Pair findClosestPairsBruteForce(Point[] points) {
		if(points.length < 2) {
			throw new IllegalStateException("Cannot find closest pairs between 1 pair or less.");
		}
		
		Pair closestPair = new Pair();
		for(int i = 0; i < points.length; i++) {
			Point pointI = points[i];
			
			for(int j = i + 1; j < points.length; j++) {
				Point pointJ = points[j];
				
				// First, determine the distance between every pair of points
				double distance = Pair.distance(pointI, pointJ);
				
				if(distance < closestPair.distance) {
					// Then, find the points with the closest distance by updating 
					// the overall closest when a new one is found
					closestPair.setPair(pointI, pointJ);
				}
			}
		}
		
		return closestPair;
	}
}
