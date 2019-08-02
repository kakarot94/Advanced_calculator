package advanced.calculator.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

class OperatorsTokenCheckTest {
	private OperatorsTokenCheck operatorsTest;
	private LinkedList<String> tokensList;

	@BeforeEach
	void init() {
		operatorsTest = new OperatorsTokenCheck();
	}
	
	private void initTokensList(String s1, String s2, String s3, String s4) {
		tokensList = new LinkedList<String>();
		tokensList.add(s1);
		tokensList.add(s2);
		tokensList.add(s3);
		tokensList.add(s4);
	}

	@DisplayName("Addition test")
	@Test
	void additionTest() {
		initTokensList("5", "+", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName("Sebtract test")
	@Test
	void subtractTest() {
		initTokensList("5", "-", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName("Multiply test")
	@Test
	void multiplyTest() {
		initTokensList("5", "*", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName("Divide test")
	@Test
	void divideTest() {
		initTokensList("5", "/", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName(" % test")
	@Test
	void rootTest() {
		initTokensList("5", "%", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName(" ^ test")
	@Test
	void squarTest() {
		initTokensList("5", "^", "5", "");
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}

	@DisplayName("More then one operation token per number")
	@RepeatedTest(4)
	void moreThenOneOperatorPerNumber(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			initTokensList("5", "^", "*", "5");
			assertThrows(IllegalArgumentException.class, () -> operatorsTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 2) {
			initTokensList("5", "^", "^", "5");
			assertThrows(IllegalArgumentException.class, () -> operatorsTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 3) {
			initTokensList("5", "-", "+", "5");
			assertThrows(IllegalArgumentException.class, () -> operatorsTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 4) {
			initTokensList("5", "+", "+", "5");
			assertThrows(IllegalArgumentException.class, () -> operatorsTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 4) {
			initTokensList("5", "%", "-", "5");
			assertThrows(IllegalArgumentException.class, () -> operatorsTest.check(tokensList));
		}
	}
	
	@DisplayName("First token is operator")
	@Test
	void firstTokenIsOperator() {
		initTokensList("+", "5", "5", "5"); // it works for every operator, even for -
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}
	
	@DisplayName("Last token is operator")
	@Test
	void lastTokenIsOperator() {
		initTokensList("5", "5", "5", "-"); // it works for every operator
		assertDoesNotThrow(() -> operatorsTest.check(tokensList));
	}
}
