import java.io.FileNotFoundException;

@SuppressWarnings("unused")
public class Rule {

	

	public Rule() {
		
	}

	public void ruleSetter(int ruleNum, char[] ruleSet, char falseSymbol, char trueSymbol) {
		
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
}
