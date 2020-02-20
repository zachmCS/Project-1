import java.util.ArrayList;
/**
 * 
 * @author zachL
 * The class Generation contains a method that evolves the ECA by setting left, middle,
 * and right cells based off where they are in a boolean array then it generates based off
 * a rule set. 
 */
public class Generation{
	
	public Generation() {
		
	}

	Rule rule = new Rule();
	
	/**
	 * This method is the generator for the ECA.
	 * @param ECA
	 * @param ruleNum
	 * @param initState
	 * @param currentGen
	 * @param ruleSet
	 * @param falseSymbol
	 * @param trueSymbol
	 */
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
	
	
	
}
