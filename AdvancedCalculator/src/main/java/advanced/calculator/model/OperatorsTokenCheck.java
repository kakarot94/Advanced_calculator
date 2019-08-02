package advanced.calculator.model;

import java.util.LinkedList;

final class OperatorsTokenCheck implements TokenCheck {

	private static final String OPERATORS = "+-*%^/()";
	private static final String NON_PARENTHESES = "+-*/%^";
	private static final String FORBIDDEN_ALONE_OPERATORS = "*/^";
	private LinkedList<String> stringTokens;

	/**
	 * IllegalArgumentException will be thrown if:<br>
	 * - all tokens are operators;<br>
	 * - forbidden alone operators doesn't have numbers on both sides;<br>
	 * - more then one operators are set to one number.<br>
	 * forbidden alone operators are: " * ", " / ", " ^ "
	 */
	@Override
	public void check(LinkedList<String> stringTokens) throws IllegalArgumentException {
		this.stringTokens = stringTokens;

		areAllTokensOperators();
		operatorsCheck();
	}

	/*
	 * This method checks if operators in good order. It will throw an
	 * IllegalArgumentException: if first token is an forbidden-alone argument, if
	 * forbidden-alone argument is on only one argument and if two operators are
	 * next to each other. Forbidden-alone arguments are: '*' , '/' , '^'
	 */
	private void operatorsCheck() {
		areAllTokensOperators();

		String stringHelper;
		for (int i = 0; i < stringTokens.size(); i++) {
			stringHelper = stringTokens.get(i);
			if (OPERATORS.indexOf(stringHelper) != -1) {
				if (i == 0) {
					if (FORBIDDEN_ALONE_OPERATORS.indexOf(stringHelper) != -1) {
						throw new IllegalArgumentException("First place can be only '(', '-', '+', '%' or number");
					}
				} else if (FORBIDDEN_ALONE_OPERATORS.indexOf(stringHelper) != -1
						&& OPERATORS.indexOf(stringTokens.get(i - 1)) != -1) {
					if(!stringTokens.get(i-1).equals(")")){
						throw new IllegalArgumentException("Can not have operators '*', '/', '^' on only one operand");
					}
				} else if (NON_PARENTHESES.indexOf(stringHelper) != -1
						&& NON_PARENTHESES.indexOf(stringTokens.get(i - 1)) != -1) {
					throw new IllegalArgumentException("Can not have multiple operands on one number");
				}
			}
		}
	}

	/*
	 * If even one token is number, the method will not throw
	 * IllegalArgumentException.
	 */
	private void areAllTokensOperators() {
		for (int i = 0; i < stringTokens.size(); i++) {
			if (OPERATORS.indexOf(stringTokens.get(i)) == -1) {
				break;
			}
			if (stringTokens.size() - 1 == i && OPERATORS.indexOf(stringTokens.get(i)) != -1) {
				throw new IllegalArgumentException("There are no operands in this function");
			}
		}
	}
}
