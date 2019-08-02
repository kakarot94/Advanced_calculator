package advanced.calculator.model;

import java.util.LinkedList;

final class ParenthesesEqualityTokenCheck implements TokenCheck {

	/*
	 * It increments variable parentesesCounter when token is equal to '(', and
	 * decrements it when token is ')'. If variable parentesesCounter in any time
	 * becomes less then zero, IllegalArgumentException is thrown. If variable
	 * parentesesCounter is greater then zero after for loop,
	 * IllegalArgumentException is thrown.
	 */
	/**
	 * If number of open and closed parentheses are not equal, the
	 * IllegalArgumentException will be thrown, if closed parentheses is in front of
	 * open parentheses IllegalArgumentException is thrown.
	 */
	@Override
	public void check(LinkedList<String> stringTokens) {
		String stringHelper;
		int parenthesesCounter = 0;

		for (int i = 0; i < stringTokens.size(); i++) {
			stringHelper = stringTokens.get(i);
			if (stringHelper.indexOf("(") != -1) {
				parenthesesCounter++;
			} else if (stringHelper.indexOf(")") != -1) {
				parenthesesCounter--;
				if (parenthesesCounter < 0) {
					break;
				}
			}
		}
		if (parenthesesCounter != 0) {
			throw new IllegalArgumentException("Wrong input of parentheses");
		}
	}
}
