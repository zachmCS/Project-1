import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Generation extends Automaton{

	


	public Generation(int ruleNum, boolean[] initState) {
		super(ruleNum, initState);
		// TODO Auto-generated constructor stub
	}

	public Generation(String fileName) throws FileNotFoundException {
		super(fileName);
		// TODO Auto-generated constructor stub
	}

	Rule rule = new Rule();
	
	
	public void generate(ArrayList<boolean[]> ECA, int ruleNum, boolean[] initState, ArrayList<Character> currentGen, char[] ruleSet, char falseSymbol, char trueSymbol) {
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
	
	
	
}
