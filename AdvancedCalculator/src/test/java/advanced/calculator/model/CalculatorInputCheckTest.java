package advanced.calculator.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

class CalculatorInputCheckTest {
	CalculatorInputCheck calc;

	@BeforeEach
	void init() {
		this.calc = CalculatorInputCheck.getInstance();
	}

	@Nested
	class ReturningZero {

		String expectedTokens = "0";

		/*
		 * 
		 */
		@DisplayName("Input = null")
		@Test
		void inputIsNull() {
			LinkedList<String> actualTokens = calc.getListOfTokens(null);
			assertEquals(expectedTokens, actualTokens.get(0));
		}

		/*
		 * This test contains three tests. In first test, input is empty string. In
		 * second, input is string containing one space character. In third test input
		 * is string containing two space characters.
		 */
		@DisplayName("Input = empty string or blank spaces only")
		@RepeatedTest(3)
		void inputIsEmptyString(RepetitionInfo info) {
			LinkedList<String> actualTokens = null;
			if (info.getCurrentRepetition() == 1) {
				actualTokens = calc.getListOfTokens("");
			} else if (info.getCurrentRepetition() == 2) {
				actualTokens = calc.getListOfTokens(" ");
			} else if (info.getCurrentRepetition() == 3) {
				actualTokens = calc.getListOfTokens("  ");
			}

			assertEquals(expectedTokens, actualTokens.get(0));
		}
	}

	/*
	 * This test contains five test, in first test, input is string containing one
	 * digit, in second is string containing two digit, and so on.
	 * CalculatorInputCheck needs to "see" that input is only one number no mether
	 * how many digits string contains.
	 */
	@DisplayName("Input = \"number\"")
	@RepeatedTest(5)
	void inputIsNumber(RepetitionInfo info) {
		String expectedTokens = null;
		LinkedList<String> actualTokens = null;

		if (info.getCurrentRepetition() == 1) {
			expectedTokens = "0";
			actualTokens = calc.getListOfTokens("0");
		} else if (info.getCurrentRepetition() == 2) {
			expectedTokens = "10";
			actualTokens = calc.getListOfTokens("10");
		} else if (info.getCurrentRepetition() == 3) {
			expectedTokens = "100";
			actualTokens = calc.getListOfTokens("100");
		} else if (info.getCurrentRepetition() == 4) {
			expectedTokens = "10000";
			actualTokens = calc.getListOfTokens("10000");
		} else if (info.getCurrentRepetition() == 5) {
			expectedTokens = "1000000";
			actualTokens = calc.getListOfTokens("1000000");
		}

		assertEquals(expectedTokens, actualTokens.get(0));
	}

	/*
	 * This test contains five test, in first test, input is string containing one
	 * digit, in second is string containing two digit, and so on.
	 * CalculatorInputCheck needs to "see" that input is only one number no meter
	 * how many digits string contains.
	 */
	@DisplayName("Input = \"decimal number\"")
	@RepeatedTest(3)
	void inputIsDecimalNumber(RepetitionInfo info) {
		String expectedTokens = null;
		LinkedList<String> actualTokens = null;

		if (info.getCurrentRepetition() == 1) {
			expectedTokens = "3.2";
			actualTokens = calc.getListOfTokens("3.2");
			assertEquals(expectedTokens, actualTokens.get(0));
		} else if (info.getCurrentRepetition() == 2) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens(".5"));
		} else if (info.getCurrentRepetition() == 3) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens("5."));
		}
	}

	@DisplayName("Valid input test")
	@RepeatedTest(6)
	void goodInputTest(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			assertEquals(tokens("2", "+", "5"), calc.getListOfTokens("2+5"));
		} else if (info.getCurrentRepetition() == 2) {
			assertEquals(tokens("(", "2", "+", "5", ")"), calc.getListOfTokens("(2+5)"));
		} else if (info.getCurrentRepetition() == 3) {
			assertEquals(tokens("(", "2", "+", "5", ")", "+", "2"), calc.getListOfTokens("(2+5)+2"));
		} else if (info.getCurrentRepetition() == 4) {
			assertEquals(tokens("(", "2", "+", "5", ")", "+", "(", "2", "-", "5", ")"), calc.getListOfTokens("(2+5)+(2-5)"));
		} else if (info.getCurrentRepetition() == 5) {
			assertEquals(tokens("(", "2", "^", "2", "*", "7", ")", "/", "2", "+", "(", "%", "3", ")"), calc.getListOfTokens("(2^2*7)/2+(%3)"));
		} else if (info.getCurrentRepetition() == 6) {
			assertEquals(tokens("2.3", "+", "5", "-", "1.8"), calc.getListOfTokens("2.3+5-1.8"));
		}
	}

	@DisplayName("Wrong input test")
	@RepeatedTest(5)
	void wrongInputTest(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens("*5"));
		} else if (info.getCurrentRepetition() == 2) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens("(2+5"));
		} else if (info.getCurrentRepetition() == 3) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens("5+2)+2"));
		} else if (info.getCurrentRepetition() == 4) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens("(2^*7)/2"));
		} else if (info.getCurrentRepetition() == 5) {
			assertThrows(IllegalArgumentException.class, () -> calc.getListOfTokens(".3+5-.8"));
		}
	}

	LinkedList<String> tokens(String... tokens) {
		LinkedList<String> tokensList = new LinkedList<String>();
		for (String token : tokens) {
			tokensList.add(token);
		}
		return tokensList;
	}
}
