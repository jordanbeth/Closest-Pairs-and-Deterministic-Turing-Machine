package ep.programming.assignment1.problem2;

import ep.programming.assignment1.IOHelper;
import ep.programming.assignment1.problem2.DTM_2A.Transition;

/**
 * 
 * @author Jordan Beth
 *
 */
public class Problem2A {
	public static final String IN_FILE_NAME = "input_problem_2A.txt";
	public static final String OUT_FILE_NAME = "output_problem_2A.txt";
	
	private static final DTM_2A TURING_MACHINE = new DTM_2A();
	public static void main(String[] args) {
		
		// q0, 0 - (q0, 0, +1)
		Transition t1 = Transition.createTransition(DTM_2A.Q_0, '0', DTM_2A.Q_0, '0', true);
		// q0, 1 - (q0, 1, +1)
		Transition t2 = Transition.createTransition(DTM_2A.Q_0, '1', DTM_2A.Q_0, '1', true);
		// q0, b - (q1, b, -1)
		Transition t3 = Transition.createTransition(DTM_2A.Q_0, 'b', DTM_2A.Q_1, 'b', false);
		
		// q1, 0 - (q2, b, -1)
		Transition t4 = Transition.createTransition(DTM_2A.Q_1, '0', DTM_2A.Q_2, 'b', false);
		// q1, 1 - (q3, b, -1)
		Transition t5 = Transition.createTransition(DTM_2A.Q_1, '1', DTM_2A.Q_3, 'b', false);
		// q1, b - (qN, b, -1)
		Transition t6 = Transition.createTransition(DTM_2A.Q_1, 'b', DTM_2A.Q_N, 'b', false);
		
		// q2, 0 - (qY,b-1)
		Transition t7 = Transition.createTransition(DTM_2A.Q_2, '0', DTM_2A.Q_Y, 'b', false);
		// q2, 1 - (qN, b, -1)
		Transition t8 = Transition.createTransition(DTM_2A.Q_2, '1', DTM_2A.Q_N, 'b', false);	
		// q2, b - (qN, b, -1)
		Transition t9 = Transition.createTransition(DTM_2A.Q_2, 'b', DTM_2A.Q_N, 'b', false);
		
		// q3, 0 - (qN, b, -1)
		Transition t10 = Transition.createTransition(DTM_2A.Q_3, 'b', DTM_2A.Q_N, 'b', false);
		// q3, 1 - (qN, b, -1)
		Transition t11 = Transition.createTransition(DTM_2A.Q_3, 'b', DTM_2A.Q_N, 'b', false);
		// q3, b - (qN, b, -1)
		Transition t12 = Transition.createTransition(DTM_2A.Q_3, 'b', DTM_2A.Q_N, 'b', false);
			
		TURING_MACHINE.addTransitions(t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11, t12);
	
		char[][] input = IOHelper.getInputProblem2(IN_FILE_NAME);
		
		for(char[] tape : input) {
			TURING_MACHINE.states(tape);	
		}
		
	}

}
