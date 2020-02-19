@SuppressWarnings("unused")
public class Rule {

	private String binaryS;
	private String[] set = { "111", "110", "101", "100", "011", "010", "001", "000" };
	// boolean array that converts the binary string to false/true
	// TODO: use to compare to set array
	private boolean[] setSymbol = new boolean[8];
	

	public Rule(int ruleNumber) {

		binaryS = Integer.toBinaryString(ruleNumber);
		int missingLength = 8 - binaryS.length(), i = 0;
		// Add leading zeros
		for (i = 0; i < missingLength; i++) {
			binaryS = 0 + binaryS;
		}

		for (i = 0; i < 8; i++) {
			if (binaryS.charAt(i) == '0') {
				setSymbol[i] = false;
			} else {
				setSymbol[i] = true;
			}
		}
	}
	public String binaryStr (int ruleNum) {
		binaryS = Integer.toBinaryString(ruleNum);
		int missingLength = 8 - binaryS.length(), i = 0;
		// Add leading zeros
		for (i = 0; i < missingLength; i++) {
			binaryS = 0 + binaryS;
		}

		for (i = 0; i < 8; i++) {
			if (binaryS.charAt(i) == '0') {
				setSymbol[i] = false;
			} else {
				setSymbol[i] = true;
			}
		}
		return binaryS;
	}
	
	public boolean[] getSymbols() {

		return setSymbol;

	}
	
	public String[] getSet() {
		
		return set;
		
	}

}
