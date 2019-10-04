/**
 * 
 */
package ep.programming.assignment1.problem2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Deterministic Turing Machine Implementation
 * @author Jordan Beth
 *
 */
public class DTM_2B {
	private static final char DELIMITER = 'b';
	private static final char NULL_CHARACTER = 'n';
//	private static final char ONE_PRIME_SYMBOL = 'i';
//	private static final char MULTIPLY_DELIMITER = 'e';
//	private static final char MULTIPLY_DELIMITER_2 = 'f';

	private final List<Character> validTapeCharacters = new ArrayList<>();
	{
		validTapeCharacters.add('1');
		validTapeCharacters.add('0');
		validTapeCharacters.add(DELIMITER);
//		validTapeCharacters.add(MULTIPLY_DELIMITER);
//		validTapeCharacters.add(MULTIPLY_DELIMITER_2);
	}
	
	private boolean isHalted;
	
	private int head;
	
	private StringBuilder output;
	
	private final List<Character> tape = new LinkedList<Character>();
	
	public DTM_2B() {
		this.output = new StringBuilder();
	}
	
	private void setupDTM(char[] tape) {
		this.isHalted = false;
		this.output.setLength(0);
		this.tape.clear();
		for(char c : tape) {
			this.tape.add(c);
		}
		
		this.head = tape.length - 1;
	}
	
	/**
	 * Move right until reaching the end of the second operand.
	 * Decrement second operand.
	 * Move left to the right end of first operand.
	 * Increment first operand.
	 * Continue until second operand is all 0's
	 * @param tape
	 * @return String representing the output of the addition
	 */
	public String runAddition(char[] tape) {
		if (!isValidTape(tape)) {
			throw new IllegalArgumentException("Invalid tape: " + Arrays.toString(tape));
		}
		
		setupDTM(tape);
		
		while (!this.isHalted) {
			moveHeadToEndOfTape(); // start at the end of the tape

			decrementSecondOperand(); // decrement the 2nd operand by 1
			incrementFirstOperand(); // increment the 1st operand by 1
			
			if(isSecondOperandAllZeroes()) {
				this.isHalted = true;
			}
		}
		
		moveHeadToEndOfFirstOperand();
		
		char[] outputArr = new char[this.head + 1];
		while(this.head >= 0) {
			outputArr[this.head] = this.tape.get(this.head);
			this.head--;
		}
		
		String output = new String(outputArr);
		
		return output;
	}
	
	/**
	 * Move right until reaching the end of the second operand.
	 * Decrement second operand.
	 * Move left to the right end of first operand.
	 * decrement the first operand.
	 * Continue until second operand is all 0's
	 * @param tape
	 * @return String representing the output of the subtraction
	 */
	public String runSubtraction(char[] tape) {
		if (!isValidTape(tape)) {
			throw new IllegalArgumentException("Invalid tape: " + Arrays.toString(tape));
		}
		
		setupDTM(tape);
		
		while (!this.isHalted) {
			moveHeadToEndOfTape();

			decrementSecondOperand(); // decrement the 2nd operand by 1
			decrementFirstOperand(); // decrement the 1st operand by 1
			
			if(isSecondOperandAllZeroes()) {
				this.isHalted = true;
			}
		}
		
		moveHeadToEndOfFirstOperand();
		
		char[] outputArr = new char[this.head + 1];
		while(this.head >= 0) {
			outputArr[this.head] = this.tape.get(this.head);
			this.head--;
		}
		
		String output = new String(outputArr);
		
		return output;
	}
	
	/**
	 * Move right until reaching the end of the second operand.
	 * Swap rightmost bit of second operand with null character
	 * Move left to bit in same position in the first operand
	 * Perform bitwise & based on value read.
	 * Continue until second operand is all null characters
	 * Perform & with 0 on remaining characters in first operand
	 * @param tape
	 * @return String representing the output of the subtraction
	 */
	public String runMultiplication(char[] tape) {
		if (!isValidTape(tape)) {
			throw new IllegalArgumentException("Invalid tape: " + Arrays.toString(tape));
		}
		
		setupDTM(tape);
		
		int offset = 0;
		while (!this.isHalted) {
			moveHeadToEndOfTape();

			while (offset-- > 0) {
				this.head--;
			}

			char currTapeChar = this.tape.get(this.head);
			if (isDelimiter(currTapeChar)) {
				break;
			}

			if (currTapeChar == '0') { // if 0, we
				this.tape.set(this.head, NULL_CHARACTER);
				performBitwiseAndOnFirstOperandWithDeAssertedBit();
			} else if (currTapeChar == '1') { // if 1, set the subtract
				this.tape.set(this.head, NULL_CHARACTER);
				performBitwiseAndOnFirstOperandWithAssertedBit();
			}

			offset = this.tape.indexOf(DELIMITER) - this.head;

			if (isSecondOperandAllNull()) {
				this.isHalted = true;
			}

		}
		
		
		while (offset-- > 0) {
			this.head--;
		}
		
		
		if(this.head != 0) {
			while(this.head > 0) {
				this.head--;
				this.tape.set(this.head, '0');
			}
		}
	
		moveHeadToEndOfFirstOperand();
		char[] outputArr = new char[this.head + 1];
		while(this.head >= 0) {
			outputArr[this.head] = this.tape.get(this.head);
			this.head--;
		}
		
		String output = new String(outputArr);
		
		return output;
		
	}
	
	/*
	public String runMultiplication(char[] tape) {
		if (!isValidTape(tape)) {
			throw new IllegalArgumentException("Invalid tape: " + Arrays.toString(tape));
		}
		
		setupDTM(tape);
		
		this.tape.add(0, MULTIPLY_DELIMITER);
		this.tape.add(0, MULTIPLY_DELIMITER_2);

		copyMultiplicand(); // increment the 1st operand by itself
		while (!this.isHalted) {
			moveHeadToEndOfTape();

			decrementSecondOperand(); // decrement second operand
			// swapSymbolToPreserveValue(); // swap 1's with special value in order to restore after decrementing
			while(!isMultiplicandAllZeroes()) {
				// decrementFirstOperandModifiedToPreserveOriginalValue();
				incrementFirstOperand();	
			}
			
			if(isSecondOperandAllZeroes()) {
				this.isHalted = true;
			}
		}
		
		char[] outputArr = new char[this.head + 1];
		while(this.head >= 0) {
			outputArr[this.head] = this.tape.get(this.head);
			this.head--;
		}
		
		String output = new String(outputArr);
		
		return output;
		
	}
	*/

	private void performBitwiseAndOnFirstOperandWithAssertedBit() {
		moveHeadToEndOfFirstOperandPlusPreviousHeadValueOffset();
		
		
		char currentTapeChar = this.tape.get(this.head); // perform bitwise &
		if(currentTapeChar == '1') { // we have 1 * 1, so we & to get 1
			this.tape.set(this.head, '1');	
		} else if (currentTapeChar == '0' ) { // we have x * 0, so we & to get 0
			this.tape.set(this.head, '0');
		}	
	}
	
	
	private void performBitwiseAndOnFirstOperandWithDeAssertedBit() {
		moveHeadToEndOfFirstOperandPlusPreviousHeadValueOffset();
		
		char currentTapeChar = this.tape.get(this.head); // perform bitwise &
		if(currentTapeChar == '1') { // we have 1 * 1, so we & to get 1
			this.tape.set(this.head, '0');	
		} else if (currentTapeChar == '0' ) { // we have x * 0, so we & to get 0
			this.tape.set(this.head, '0');
		}	
	}


	/*
	private void swapSymbolToPreserveValue() {
		moveHeadToEndOfFirstOperand();
		
		while(true) {
			char currTapeChar = this.tape.get(this.head);
			if(isMultiplicationDelimiter(currTapeChar)) {
				break;
			}
			
			if(currTapeChar == '1') {
				this.tape.set(this.head, ONE_PRIME_SYMBOL);
			}
			
			this.head--;
		}	
	}
	*/

	/**
	 * Copy multiplicand to front of tape.
	 */
	/*
	private void copyMultiplicand() {
		
		int i = 0;
		while(true) {
			moveHeadToEndOfFirstOperand();
			this.head = this.head - i;
			if(isMultiplicationDelimiter(this.tape.get(this.head))) {
				break;
			}
			char value = this.tape.get(this.head);
			moveHeadToStartOfTape();
			this.tape.add(this.head, value);
			i++;
		}
	}
	*/
	
	/**
	 * Increment product in tape.
	 */
	/*
	private void incrementProduct() {
		moveHeadToEndOfFirstOperandMultiplication();
		
		boolean requiresCarry = false;
		while(true) {
			char currTapeChar = this.tape.get(this.head); // get character
			if(this.head <= 0 || isMultiplicationDelimiter(currTapeChar)) {
				break;
			}
					
			if(currTapeChar == '0') { // if 0, we must set to 1
				if(requiresCarry) break;
				
				this.tape.set(this.head, '1');
				break;
			} else if (currTapeChar == '1') { // if 1, we must perform carry
				this.tape.set(this.head, '0');
				this.head--;
				requiresCarry = true;
				continue;
			}

			this.head--;
		}
		
		// Perform carry
		if(requiresCarry) { 
			while (this.head > 0) {
				if(this.tape.get(this.head) == '1') {
					this.tape.set(this.head, '0');					
				} else {
					this.tape.set(this.head, '1');
					break;
				}
				
				this.head--;
			}
		}	
	}
	*/

	/**
	 * Perform addition by 1 with carry on the first operand
	 */
	private void incrementFirstOperand() {
		moveHeadToEndOfFirstOperand();
		
		boolean requiresCarry = false;
		while(true) {
			char currTapeChar = this.tape.get(this.head); // get character
			if(this.head <= 0 || isDelimiter(currTapeChar)) {
				break;
			}
					
			if(currTapeChar == '0') { // if 0, we must set to 1
				if(requiresCarry) break;
				
				this.tape.set(this.head, '1');
				break;
			} else if (currTapeChar == '1') { // if 1, we must perform carry
				this.tape.set(this.head, '0');
				this.head--;
				requiresCarry = true;
				continue;
			}

			this.head--;
		}
		
		// Perform carry
		if(requiresCarry) { 
			while (this.head > 0) {
				if(this.tape.get(this.head) == '1') {
					this.tape.set(this.head, '0');					
				} else {
					this.tape.set(this.head, '1');
					break;
				}
				
				this.head--;
			}
		}
		
	}
	
	/**
	 * Perform subtraction by 1 (with carry) on the first operand
	 */
	private void decrementFirstOperand() {
		moveHeadToEndOfFirstOperand();
		
		boolean requiresCarry = false;
		while(true) {
			char currTapeChar = this.tape.get(this.head);
			if(isDelimiter(currTapeChar)) {
				break;
			}
					
			if(currTapeChar == '0') { // if 0, we must carry perform a carry
				requiresCarry = true;
				this.head--;
				continue;
			} else if (currTapeChar == '1') { // if 1, set the subtract
				this.tape.set(this.head, '0');
				break;
			}

			this.head--;
		}
		
		// Perform the carry
		if(requiresCarry) {
			while(!isDelimiter(this.tape.get(++this.head))) {
				this.tape.set(this.head, '1');
			}
		}
		
	}


	/**
	 * Perform subtraction by 1 with carry on the second operand
	 */
	private void decrementSecondOperand() {
		moveHeadToEndOfTape();
		boolean requiresCarry = false;
			
		while(true) {
			char currTapeChar = this.tape.get(this.head);
			if(isDelimiter(currTapeChar)) {
				break;
			}
					
			if(currTapeChar == '0') { // if 0, we must perform a carry
				requiresCarry = true;
				this.head--;
				continue;
			} else if (currTapeChar == '1') { // if 1, set the subtract
				this.tape.set(this.head, '0');
				break;
			}

			this.head--;
		}
		
		// Perform the carry
		if(requiresCarry) {
			while(this.head < this.tape.size() - 1) {
				this.head++;
				this.tape.set(this.head, '1');
			}
		}	
	}
	
	
	/**
	 * Move head to the end of the first operand for mult.
	 */
	
	/*
	private void moveHeadToEndOfFirstOperandMultiplication() {
		// move head to beginning of tape
		while(this.head-- > this.tape.indexOf(MULTIPLY_DELIMITER)) {
			
			// if we pass the delimiter, stop there
			if(isDelimiter(this.tape.get(this.head))) {
				this.head--;
				break;
			}
		}
		
		// move to delimiter
		while(!isDelimiter(this.tape.get(this.head))) {
			this.head++;
		}
		
		// move to tape position right before delimiter 
		if(isDelimiter(this.tape.get(this.head))) {
			this.head--;
		}
	}
	*/
	
	/**
	 * Move head to the end of the first operand for mult.
	 */
	/*
	private void moveHeadToStartOfTape() {
		while(this.head > 0) {
			this.head--;
		}
	}
	*/
	
	/**
	 * Move head to the end of the first operand
	 */
	/*
	private void moveHeadToStartOfMultiplicationResult() {
		// move head to beginning of tape
		while(this.head-- > this.tape.indexOf(MULTIPLY_DELIMITER)) {
			
			// if we pass the delimiter, stop there
			if(isMultiplicationDelimiter(this.tape.get(this.head))) {
				this.head--;
				break;
			}
		}
		
		// move to delimiter
		while(!isMultiplicationDelimiter(this.tape.get(this.head))) {
			this.head++;
		}
		
		// move to tape position right before delimiter 
		if(isMultiplicationDelimiter(this.tape.get(this.head))) {
			this.head--;
		}
	}
	*/
	
	/**
	 * Move head to the end of the first operand plus current head offset
	 */
	private void moveHeadToEndOfFirstOperandPlusPreviousHeadValueOffset() {
		
		int offset = (this.tape.size() - 1) - this.head;
		// move head to beginning of tape
		while(this.head > 1) {
			this.head--;
			// if we pass the delimiter, stop there
			if(isDelimiter(this.tape.get(this.head))) {
				this.head--;
				break;
			}
		}
		
		// move to delimiter
		while(!isDelimiter(this.tape.get(this.head))) {
			this.head++;
		}
		
		// move to tape position right before delimiter 
		if(isDelimiter(this.tape.get(this.head))) {
			this.head--;
		}
		
		for(int i = 0; i < offset; i++) {
			this.head--;
		}
	}
	
	/**
	 * Move head to the end of the first operand plus current head offset
	 */
	/*
	private void moveHeadToEndOfSecondOperandPlusPreviousHeadValueOffset() {
		
		int offset = this.tape.indexOf(DELIMITER) - this.head;
		// move head to beginning of tape
		while(this.head > 1) {
			this.head--;
			// if we pass the delimiter, stop there
			if(isDelimiter(this.tape.get(this.head))) {
				this.head--;
				break;
			}
		}
		
		// move to delimiter
		while(!isDelimiter(this.tape.get(this.head))) {
			this.head++;
		}
		
		// move to tape position right before delimiter 
		if(isDelimiter(this.tape.get(this.head))) {
			this.head--;
		}
		
		for(int i = 0; i < offset; i++) {
			this.head--;
		}
	}
	*/
	
	/**
	 * Move head to the end of the first operand
	 */
	private void moveHeadToEndOfFirstOperand() {
		// move head to beginning of tape
		while(this.head > 1) {
			this.head--;
			// if we pass the delimiter, stop there
			if(isDelimiter(this.tape.get(this.head))) {
				this.head--;
				break;
			}
		}
		
		// move to delimiter
		while(!isDelimiter(this.tape.get(this.head))) {
			this.head++;
		}
		
		// move to tape position right before delimiter 
		if(isDelimiter(this.tape.get(this.head))) {
			this.head--;
		}
	}
	
	/**
	 * Move the head to the end of the tape.
	 */
	private void moveHeadToEndOfTape() {
		while(this.head < this.tape.size() - 1) {
			this.head++;
		}
	}
	
	/**
	 * Validate halting state where the second operand is all 0's.
	 * @return
	 */
	private boolean isSecondOperandAllZeroes() {
		moveHeadToEndOfTape();
		boolean isAllZeroes = true;
		
		while(this.head >= 0 && isDelimiter(this.tape.get(this.head)) != true) {
			if(this.tape.get(this.head) == '1') {
				isAllZeroes = false;
				break;
			}
			
			this.head--;
		}
		
		return isAllZeroes;
	}
	
	/**
	 * Validate halting state where the second operand is all n's.
	 * @return
	 */
	private boolean isSecondOperandAllNull() {
		moveHeadToEndOfTape();
		boolean isAllNull = true;
		
		while(this.head >= 0 && isDelimiter(this.tape.get(this.head)) != true) {
			if(this.tape.get(this.head) != NULL_CHARACTER) {
				isAllNull = false;
				break;
			}
			
			this.head--;
		}
		
		return isAllNull;
	}

	/**
	 * Validate halting state where the second operand is all 0's.
	 * @return
	 */
	/*
	private boolean isMultiplicandAllZeroes() {
		moveHeadToEndOfFirstOperandMultiplication();
		boolean isAllZeroes = true;
		
		while(isDelimiter(this.tape.get(this.head)) != true && isMultiplicationDelimiter(this.tape.get(this.head)) != true) {
			if(this.tape.get(this.head) == ONE_PRIME_SYMBOL) {
				isAllZeroes = false;
				break;
			}
			
			this.head--;
		}
		
		return isAllZeroes;
	}
	*/

	/**
	 * Helper to check if a character is the delimiter.
	 * @param c
	 * @return
	 */
	private boolean isDelimiter(char c) {
		return c == DELIMITER;
	}
	
	/**
	 * Helper to check if a character is the multiplication delimiter.
	 * @param c
	 * @return
	 */
	/*
	private boolean isMultiplicationDelimiter(char c) {
		return c == MULTIPLY_DELIMITER;
	}
	*/
	
	/**
	 * Helper method to validate the tape.
	 * @param tape
	 * @return
	 */
	private boolean isValidTape(char[] tape) {	
		boolean isValid = true;
		for(char c : tape) {
			
			if(!validTapeCharacters.contains(c)) {
				isValid = false;
				break;
			}
		}
		
		return isValid;
	}
	
}
