/**
 * 
 */
package ep.programming.assignment1.problem2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ep.programming.assignment1.IOHelper;

/**
 * Deterministic Turing Machine Implementation
 * @author Jordan Beth
 *
 */
public class DTM_2A {
	
	public static final String STATE_Q_0 = "q0";
	public static final String STATE_Q_1 = "q1";
	public static final String STATE_Q_2 = "q2";
	public static final String STATE_Q_3 = "q3";
	public static final String HALTING_STATE_Q_Y = "qY";
	public static final String HALTING_STATE_Q_N = "qN";
	
	public static final State Q_0 = State.createState(DTM_2A.STATE_Q_0);
	public static final State Q_1 = State.createState(DTM_2A.STATE_Q_1);
	public static final State Q_2 = State.createState(DTM_2A.STATE_Q_2);
	public static final State Q_3 = State.createState(DTM_2A.STATE_Q_3);
	public static final State Q_Y = State.createState(DTM_2A.HALTING_STATE_Q_Y);
	public static final State Q_N = State.createState(DTM_2A.HALTING_STATE_Q_N);
	
	private final String validTapeCharacters = "b01";
	
	final Map<String, Transition> transitionsByKey;
	
	private final State STARTING_STATE = Q_0;
	
	private State currentState;
	private char currentValue;
	
	private boolean isHalted;
	
	private char[] tape;
	private int tapeSize;
	
	private StringBuilder output;
	
	public DTM_2A() {
		this.transitionsByKey = new HashMap<>();
		this.output = new StringBuilder();
	}
	
	private void resetDTM() {
		this.isHalted = false;
		this.currentState = STARTING_STATE;
		this.output.setLength(0);
	}
	
	/**
	 * i. States method, this method should have all of the operations of your TM.
	 */
	public void states(char[] tapeValues) {
		resetDTM();
		
		this.tape = tapeValues;
		this.tapeSize = tapeValues.length;
		for(int currIndex = 0; currIndex < this.tapeSize; ) {
			char currentTapeValue = tapeValues[currIndex];
			
			if(isValidTapeValue(currentTapeValue) == false) {
				throw new IllegalArgumentException("Invalid value on the tape: " + currentTapeValue);
			}
			
			String key = createKey(this.currentState, currentTapeValue);
			
			Transition transitionFunc = this.transitionsByKey.get(key);
			
			if(transitionFunc == null) {
				throw new IllegalStateException("Could not find transition in map using map key: " + key);
			}
			
			State nextState = transitionFunc.getNextState();
			char newTapeValue = transitionFunc.getNewTapeValue();
			
			programLine(nextState, newTapeValue, currIndex);
		
			if(transitionFunc.getMoveDirection() == true) {
				currIndex++;
			} else {
				currIndex--;
			}
	
			if(this.isHalted) {
				break;
			}	
		}
		
		if (this.tapeSize <= 30) {
			print();
		} else {
			write();
		}
		
	}
	
	/**
	 * ii. Program line that executes the operations for each identified state, this
	 * should follow the n-tuple TM as described above for M.
	 */
	private void programLine(State nextState, char newTapeValue, int currIndex) {
		this.tape[currIndex] = newTapeValue;
		this.currentState = nextState;
		this.currentValue = newTapeValue;
		
		if(HALTING_STATE_Q_N.equals(this.currentState.getName()) || HALTING_STATE_Q_Y.equals(this.currentState.getName())) {
			this.isHalted = true;
		}
		
		appendOutput();
	}
	
	/**
	 * iii. Print method that outputs the change in tape (x ≤ 30) after each
	 * transition function is executed.
	 */
	 public void print() {
		System.out.println(output.toString());
	 }
	 
	/**
	 * iv. Write method, for tape larger than x > 30 write the outputs to a file
	 */
	 public void write() {
		String outFile = IOHelper.OUT_DIR + Problem2A.OUT_FILE_NAME;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)))) {
			writer.write(this.output.toString());
		} catch (IOException ex) {
			System.out.println("Could not read write file because of exception: IOException - " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			System.out.println("Output complete! Check output directory for the file named " + outFile);
		}
	 }
	 
	 public void addTransitions(Transition... transitions) {
		 for(Transition transition : transitions) {	
			 String key = createKey(transition.getCurrentState(), transition.getCurrentTapeValue());
			 this.transitionsByKey.put(key, transition);
		 }
	 }
	 
	private void appendOutput() {
		output.append("tape: ");
		output.append(Arrays.toString(this.tape));
		output.append("- q: ");
		output.append(this.currentValue);
		output.append("\n");
	}
	 
	 private String createKey(State currentState, char currentValue) {
		return currentState + "-" + currentValue;
	 }
	 
	 public static class State {
		 private String name;
		 private State(String name) {
			 this.name = name;
		 }
		 
		 public static State createState(String name) {
			 return new State(name);
		 }
		 
		 public String getName() {
			 return this.name;
		 }
		 
		 @Override
		 public String toString() {
			 return this.name;
		 }
	 }
	 
	public static class Transition {

		private char currentTapeValue;
		private char newTapeValue;
		private State currentState;
		private State nextState;
		/**
		 * True = right False = left
		 */
		boolean moveDirection;

		private Transition(State currentState, char currentTapeValue, State nextState, char newTapeValue, boolean moveDirection) {
			this.currentState = currentState;
			this.nextState = nextState;
			this.moveDirection = moveDirection;
			this.currentTapeValue = currentTapeValue;
			this.newTapeValue = newTapeValue;
		}

		public static Transition createTransition(State currentState, char currentTapeValue, State nextState, char newTapeValue, boolean moveDirection) {
			return new Transition(currentState, currentTapeValue, nextState, newTapeValue, moveDirection);
		}

		public char getCurrentTapeValue() {
			return currentTapeValue;
		}

		public char getNewTapeValue() {
			return newTapeValue;
		}

		public State getCurrentState() {
			return currentState;
		}

		public State getNextState() {
			return nextState;
		}

		public boolean getMoveDirection() {
			return moveDirection;
		}

	 }
	
	private boolean isValidTapeValue(char c) {
		return validTapeCharacters.indexOf(c) != -1;
	}
	
}
