
/**
 * 
 * @author Zachary Muller
 * Worked with Marcos Bernier and Marc Hanna
 */

import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Scanner;

public class Automaton {

	private ArrayList<boolean[]> ECA = new ArrayList<>(7);
	private char[] ruleSet = new char[8];
	private int ruleNum;
	private boolean[] initState = new boolean[10];
	private ArrayList<Character> currentGen = new ArrayList<Character>(initState.length);
	private char falseSymbol = '0', trueSymbol = '1';
	private String test; 
	
	String state;

	public Automaton(int ruleNum, boolean[] initState) {
		
		ECA.clear();
		this.ruleNum = ruleNum;
		this.initState = initState;
		ECA.add(initState);
		ruleSetter(ruleNum);

	}

	public Automaton(String fileName) throws FileNotFoundException {

		Scanner reader = new Scanner(new File(fileName));

		while (reader.hasNextLine()) {
			this.ruleNum = reader.nextInt();
			this.falseSymbol = reader.next().charAt(0);
			this.trueSymbol = reader.next().charAt(0);
			String initS = reader.next();
			initState = new boolean[initS.length()];
			
			for(int i = 0; i < initState.length; i++) {
				initState[i] = false;
			}
			
			ruleSetter(ruleNum);
			setTrueSymbol(trueSymbol);
			setFalseSymbol(falseSymbol);
			test = initS;
		}
		initState = convertor(test);
		new Automaton(ruleNum, initState);
		reader.close();
	}
	public boolean [] convertor(String genState) {
		
		this.state = genState;
		ECA.clear();
		
		for(int i = 0; i < state.length(); i++) {
			Character temp = state.charAt(i);
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
	public int getRuleNum( ) { // possibly move to rule

		return ruleNum;

	}

	public void ruleSetter(int ruleNum) { //possibly move to rule

		String binaryS = Integer.toBinaryString(ruleNum);
		int missingLength = 8 - binaryS.length(), i;
		// Add leading zeros
		for(i = 0; i < missingLength; i++) {
			binaryS = 0 + binaryS;
		}
		for (int j = ruleSet.length - 1; j >= 0; j--) {
            ruleSet[j] = falseSymbol;
        }
        for (i = ruleSet.length - 1; i >= 0; i--){
            if (binaryS.charAt(i) == '1'){
                ruleSet[i] = '1';
            }
            else{
                ruleSet[i] = '0';
            }
        }
	}	

	public void evolve(int numSteps) {

		for (int i = 0; i < numSteps; i++) {
			generate(ruleNum);
		}

	}

	public void generate(int ruleNum) { // move to generate
		int leftCell, middleCell, rightCell;
		ruleSetter(ruleNum);
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

	public int getTotalSteps() { //possibly move to a class

		return ECA.size() - 1;

	}

	public boolean[] getState(int stepNum) {

		if(ECA.size() - 1 < stepNum) {
			evolve(stepNum - ECA.size());
		}
		return ECA.get(stepNum);

	}

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
		System.out.println(binaryS);
		return binaryS;

	}

	public void save(String filename) throws FileNotFoundException {

		PrintWriter writer = new PrintWriter(filename);
		writer.print(toString());
		writer.close();

	}

	public char getFalseSymbol() {

		return falseSymbol;

	}

	public void setFalseSymbol(char falseSymbol) {

		this.falseSymbol = falseSymbol;

	}

	public char getTrueSymbol() {

		return trueSymbol;

	}

	public void setTrueSymbol(char trueSymbol) {

		this.trueSymbol = trueSymbol;

	}

}