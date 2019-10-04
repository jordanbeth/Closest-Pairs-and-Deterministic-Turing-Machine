package ep.programming.assignment1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Jordan Beth
 *
 */
public class IOHelper {
	
	private static final String ROOT_DIR = System.getProperty("user.dir");
	
	public static final String OUT_DIR = ROOT_DIR + "/output/";
	private static final String IN_DIR = ROOT_DIR + "/input/";
	
	private static int inputSize;
	
	public static double[][] getInputForProblem1(String fileName) {
	
		String inFile = IN_DIR + fileName;
		
		int pairCount = getNumberOfLines(inFile);
		
		double[][] inputPairs = new double[pairCount][2];
		int index = 0;
		
		try(BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(shouldIgnoreLine(line)) {
					continue;
				}
				
				String[] pairArr = line.split(",");
				if(pairArr.length != 2) {
					throw new RuntimeException("Input pair is in the wrong format. Check input file");
				}
				
				double p1 = Double.valueOf(pairArr[0]);
				double p2 = Double.valueOf(pairArr[1]);
				
				inputPairs[index++] = new double[] { p1, p2 };
			}
		} catch (IOException ex) {
			System.out.println("Could not read input file because of exception: IOException - " + ex.getMessage());
			ex.printStackTrace();
		}
		
		return inputPairs;
	}
	
	public static void writeOutput(String fileName, String output) {
		if (inputSize > 30) { // > 30 write to output file
			createOutputFiles(fileName, output);
		} else { // <= 30 print to console
			System.out.println(output);
		}
	}
	
	private static void createOutputFiles(String fileName, String output) {
		String outFile = OUT_DIR + fileName;
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)))) {
			writer.write(output);
		} catch (IOException ex) {
			System.out.println("Could not read write file because of exception: IOException - " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			System.out.println("Output complete! Check output directory for the file named " + fileName);
		}
	}
	
	private static int getNumberOfLines(String fileName) {
		int lines = 0;
		try(BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(shouldIgnoreLine(line)) {
					continue;
				}
				lines++;		
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		inputSize = lines;
		return lines;
	}
	
	
	public static char[][] getInputProblem2(String fileName) {
		
		String inFile = IN_DIR + fileName;
		int lineCount = getNumberOfLines(inFile);
		char[][] input = new char[lineCount][];
		
		int index = 0;
		try(BufferedReader reader = new BufferedReader(new FileReader(inFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if(shouldIgnoreLine(line)) {
					continue;
				}
				
				input[index++] = line.toCharArray();
			}
		} catch (IOException ex) {
			System.out.println("Could not read input file because of exception: IOException - " + ex.getMessage());
			ex.printStackTrace();
		}
		return input;
	}
	
	private static boolean shouldIgnoreLine(String line) {
		line = line.trim();
		return line.isEmpty() || line.startsWith("#");
	}
	
}
