
/**
 * 
 * @author Zachary Muller
 * Worked with Marcos Bernier and Marc Hanna
 */

import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 * This is the Automaton class which is the "main" class out of all the classes. Most methods
 * are created and executed in this class. 
 */
public class Automaton {

	/**
	 * These are the false and true symbols
	 */
	protected char falseSymbol = '0', trueSymbol = '1';
	/**
	 * This is the empty ruleSet for the ECA. Has not been assigned any values yet. 
	 */
	protected char[] ruleSet = new char[8];
	/**
	 * This is the ECA itself. 
	 */
	protected ArrayList<boolean[]> ECA = new ArrayList<>(7);
	/**
	 * This is the rule number that has not been initialized yet. 
	 */
	protected int ruleNum;
	/**
	 * This is the empty initial state that has not been assigned any values yet. 
	 */
	protected boolean[] initState = new boolean[10];
	/**
	 * This is the character arraylist that is empty right now but is used for the
	 * current generation. 
	 */
	protected ArrayList<Character> currentGen = new ArrayList<Character>(initState.length);
	/**
	 * This is the initial state but in string form. 
	 */
	 
	/**
	 * These are the rule and generation objects. 
	 */
	Generation generator;
	Rule rule = new Rule();
	/**
	 * This is the automaton constructor that sets a couple values to the parameters
	 * and adds the initial state to the ECA arraylist. 
	 * @param ruleNum
	 * @param initState
	 */
	public Automaton(int ruleNum, boolean[] initState) {
		
		ECA.clear();
		this.ruleNum = ruleNum;
		this.initState = initState;
		ECA.add(initState);
		rule.ruleSetter(ruleNum, ruleSet, falseSymbol, trueSymbol);
		generator = new Generation();

	}
	/**
	 * This is another automaton constructor that reads from a file and sets value to their 
	 * values in the file. 
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public Automaton(String fileName) throws FileNotFoundException {

		Scanner reader = new Scanner(new File(fileName));

	
			this.ruleNum = reader.nextInt();
			this.falseSymbol = reader.next().charAt(0);
			this.trueSymbol = reader.next().charAt(0);
			String initS = reader.next();
			initState = new boolean[initS.length()];
			
			for(int i = 0; i < initState.length; i++) {
				initState[i] = false;
			
			
			rule.ruleSetter(ruleNum, ruleSet, falseSymbol, trueSymbol);
			setTrueSymbol(trueSymbol);
			setFalseSymbol(falseSymbol);
		}
		initState = convertor(initS);
		new Automaton(ruleNum, initState);
		generator = new Generation();
		reader.close();
	}
	/**
	 * This method converts elements in initial state to true and false based
	 * off the element at the index of genState.
	 * @param genState
	 * @return
	 */
	public boolean [] convertor(String genState) {
		
		ECA.clear();
		
		for(int i = 0; i < genState.length(); i++) {
			Character temp = genState.charAt(i);
			if(temp.equals(trueSymbol)) {
				initState[i] = true;
			}
			else {
				initState[i] = false;
			}
		}
		ECA.add(initState);
		return initState;
	}
	/**
	 * This method returns the rule number. 
	 * @return
	 */
	public int getRuleNum( ) { 

		return ruleNum;

	}
	/**
	 * This method evolves the ECA. 
	 * @param numSteps
	 */
	public void evolve(int numSteps) {
		
		for (int i = 0; i < numSteps; i++) {
			generate(ruleNum);
			//generator.generate(ECA, ruleNum, initState, currentGen, ruleSet, falseSymbol, trueSymbol);
		}
	}
	/**
	 * This method generates the next line for the ECA. 
	 * @param ruleNum
	 */
	public void generate(int ruleNum) { // move to generate
		int leftCell, middleCell, rightCell;
		rule.ruleSetter(ruleNum, ruleSet, falseSymbol, trueSymbol);
		for (int i = 0; i < initState.length; i++) {
			if (i == 0) {
				leftCell = initState.length - 1;
				middleCell = i;
				rightCell = i + 1;

			} else if (i == initState.length - 1) {
				leftCell = i - 1;
				middleCell = i;
				rightCell = 0;

			} else {
				leftCell = i - 1;
				middleCell = i;
				rightCell = i + 1;
			}

			if (initState[middleCell] == true && initState[leftCell] == true && initState[rightCell] == true) {
				currentGen.add(i, (ruleSet[0]));
			} else if (initState[middleCell] == true && initState[leftCell] == true && initState[rightCell] == false) {
				currentGen.add(i, (ruleSet[1]));
			} else if (initState[middleCell] == false && initState[leftCell] == true && initState[rightCell] == true) {
				currentGen.add(i, (ruleSet[2]));
			} else if (initState[middleCell] == false && initState[leftCell] == true && initState[rightCell] == false) {
				currentGen.add(i, (ruleSet[3]));
			} else if (initState[middleCell] == true && initState[leftCell] == false && initState[rightCell] == true) {
				currentGen.add(i, (ruleSet[4]));
			} else if (initState[middleCell] == true && initState[leftCell] == false && initState[rightCell] == false) {
				currentGen.add(i, (ruleSet[5]));
			} else if (initState[middleCell] == false && initState[leftCell] == false && initState[rightCell] == true) {
				currentGen.add(i, (ruleSet[6]));
			} else if (initState[middleCell] == false && initState[leftCell] == false && initState[rightCell] == false) {
				currentGen.add(i, (ruleSet[7]));
			}
		}
		initState = binToBool(currentGen);
		ECA.add(initState);
		currentGen.clear();
	}
	/**
	 * This method converts binary elements from currentGen to false and true with respect to
	 * 0 and 1.
	 * @param currentGen
	 * @return
	 */
	public boolean[] binToBool(ArrayList<Character> currentGen) {
		boolean[] temp = new boolean[currentGen.size()];
		for (int i = 0; i < currentGen.size(); i++) {
			char currentState = currentGen.get(i);
			if (currentState == '0') {
				temp[i] = false;
			} else {
				temp[i] = true;
			}
		}
		return temp;
	}
	/**
	 * This method returns the total steps.
	 * @return
	 */
	public int getTotalSteps() { //possibly move to a class

		return ECA.size() - 1;

	}
	/**
	 * This method gets the current state of the ECA.
	 * @param stepNum
	 * @return
	 */
	public boolean[] getState(int stepNum) {

		if(ECA.size() - 1 < stepNum) {
			evolve(stepNum - ECA.size());
		}
		return ECA.get(stepNum);

	}
	/**
	 * This method gets the current state of the ECA but converts to string by using the 
	 * true and false symbols for the ECA.
	 * @param stepNum
	 * @return
	 */
	public String getStateString(int stepNum) {

		String stateToString = "";
		boolean[] state = getState(stepNum);

		for (int i = 0; i < state.length; i++) {
			if (state[i] == true) {
				stateToString = stateToString + "" + trueSymbol;
			} else {
				stateToString = stateToString + "" + falseSymbol;
			}
		}
		return stateToString;
	}
	/**
	 * This method converts the current state of the ECA to string and returns it. 
	 */
	@Override
	public String toString() {

		String binaryS = "";

		for (int i = 0; i < ECA.size(); i++) {
			if (i < ECA.size() - 1) {
				binaryS = binaryS + getStateString(i) + "\n";
			} else {
				binaryS = binaryS + getStateString(i);
			}
		}
		return binaryS;

	}
	/**
	 * This method writes the toString method to a file. 
	 * @param filename
	 * @throws FileNotFoundException
	 */
	public void save(String filename) throws FileNotFoundException {

		PrintWriter writer = new PrintWriter(filename);
		writer.print(toString());
		writer.close();

	}
	/**
	 * This method gets the false symbol for the ECA.
	 * @return
	 */
	public char getFalseSymbol() {

		return falseSymbol;

	}
	/**
	 * This method sets the false symbol for the ECA.
	 * @param falseSymbol
	 */
	public void setFalseSymbol(char falseSymbol) {

		this.falseSymbol = falseSymbol;

	}
	/**
	 * This method gets the true symbol for the ECA.
	 * @return
	 */
	public char getTrueSymbol() {

		return trueSymbol;

	}
	/**
	 * This method sets the true symbol for the ECA.
	 * @param trueSymbol
	 */
	public void setTrueSymbol(char trueSymbol) {

		this.trueSymbol = trueSymbol;

	}

}
