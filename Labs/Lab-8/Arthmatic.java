public class Arthmatic {
	
	
	public int multiply(int operand1, int operand2) {
		return operand1 * operand2;
	}
	
	public int subtract(int operand1, int operand2) {
		return operand1 - operand2;
	}
	
	public int add(int operand1, int operand2) {
		return operand1 + operand2;
	}
	
	public int divide(int operand1, int operand2) {
		if(divideByZero(operand2)) {
			//return a integer flag
			return Integer.MAX_VALUE;
		}else {
			return operand1/operand2;
		}
	}
	
	public boolean divideByZero(int operand2){
		if(operand2 == 0) {
			return true;
		}else {
			return false;
		}
	}

}
