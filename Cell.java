@SuppressWarnings("unused")
public class Cell extends Automaton{

	private boolean[] cellArr;

	public Cell(int ruleNum, boolean[] initState) {

		super(ruleNum, initState);
		cellArr = initState;

	}

	// false is white, black is true, false is 0, true is 1
	private int numCells = cellArr.length;
	private String[] nextLine = new String[numCells];
	private boolean[] nextLineTF = new boolean[numCells];
	// Cells give back true or false
	private boolean leftCell, middleCell, rightCell;
	private String newCell;
	private String sLeftCell = "", sMiddleCell = "", sRightCell = "", combinedCell = "";
	private String [] set = { "111", "110", "101", "100", "011", "010", "001", "000" };

	// Convert false and true to 0 and 1 respectively
	public void convertToString(boolean leftCell, boolean middleCell, boolean rightCell) {
		
		if (leftCell == false) {
			sLeftCell = "0";
		} else {
			sLeftCell = "1";
		}

		if (middleCell == false) {
			sMiddleCell = "0";
		} else {
			sMiddleCell = "1";
		}

		if (rightCell == false) {
			sRightCell = "0";
		} else {
			sRightCell = "1";
		}
	}
	//TODO: in driver class this will be Rule.getSymbols, Rule.getSet
	public void setCell(boolean[] setSymbol, String[] set, String falseSymbol, String trueSymbol) {
		
		for (int i = 0; i < numCells; i++) {
			if (i == 0) {
				leftCell = cellArr[numCells - 1];
				middleCell = cellArr[0];
				rightCell = cellArr[1];

			} else if (i == numCells - 1) {
				leftCell = cellArr[i - 1];
				middleCell = cellArr[i];
				rightCell = cellArr[0];	

			} else {
				leftCell = cellArr[i - 1];
				middleCell = cellArr[i];
				rightCell = cellArr[i + 1];
			}
			setNewCell(setSymbol, set, falseSymbol, trueSymbol);
		}
		
		for(int i = 0; i < nextLine.length; i++) {
			nextLine[i] = falseSymbol;
		}
		//generations
	}
	//TODO: in driver class this will be Rule.getSymbols, Rule.getSet
	public String[] setNewCell(boolean[] setSymbol, String[] set, String falseSymbol, String trueSymbol) {
		
		convertToString(leftCell, middleCell, rightCell);
		combinedCell = sLeftCell + sMiddleCell + sRightCell;
		int i, indexOfMatch = 0, k = 0;
		
		for(i = 0; i < set.length; i++) {
			if(combinedCell.equals(set[i])) {
				indexOfMatch = i;
			}
		}
		
		if(setSymbol[indexOfMatch] == false) {
			nextLine[k] = falseSymbol;
			k++;
		} else {
			nextLine[k] = trueSymbol;
			k++;
		}
		return nextLine;
	}
	
	public boolean [] currentState(String[] nextLine, String falseSymbol, String trueSymbol) {
		
		for(int i = 0; i < nextLine.length; i++) {
			if(nextLine[i].equals(falseSymbol)) {
				nextLineTF[i] = false;
			} else {
				nextLineTF[i] = true;
			}
		}
		return nextLineTF;
	}
}
