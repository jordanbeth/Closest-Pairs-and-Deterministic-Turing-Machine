package ep.programming.assignment1.problem2;

import ep.programming.assignment1.IOHelper;

/**
 * 
 * @author Jordan Beth
 *
 */
public class Problem2B {
	public static final String IN_FILE_NAME = "input_problem_2B.txt";
	public static final String OUT_FILE_NAME = "output_problem_2B.txt";
	
	private static final DTM_2B TURING_MACHINE = new DTM_2B();
	
	/**
	 * Driver for problem 2 part B. 
	 * The program will output directly to the console.
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("\n## Additon: \n");
		runAddition();
	
		System.out.println("\n## Subtraction: \n");
		runSubtraction();
		
		System.out.println("\n## Multiplication (Bitwise AND): \n");
		runMultiplication();
	}
	
	private static void runAddition() {
		char[][] input = IOHelper.getInputProblem2(IN_FILE_NAME);
		for(char[] tape: input) {
			String additionOutput = TURING_MACHINE.runAddition(tape);	
			System.out.println(additionOutput);	
		}
	}
	
	private static void runSubtraction() {
		char[][] input = IOHelper.getInputProblem2(IN_FILE_NAME);
		for(char[] tape : input) {
			String subtractionOutput = TURING_MACHINE.runSubtraction(tape);	
			System.out.println(subtractionOutput);
		}
	}
	
	/**
	 * Multiplication uses bitwise &
	 */
	private static void runMultiplication() {
		char[][] input = IOHelper.getInputProblem2(IN_FILE_NAME);
		for(char[] tape: input) {
			String muiltiplicationOutput = TURING_MACHINE.runMultiplication(tape);	
			System.out.println(muiltiplicationOutput);	
		}
	}

}
