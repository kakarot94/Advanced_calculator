package advanced.calculator.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;

class ParenthesesEqualityTokenCheckTest {

	private ParenthesesEqualityTokenCheck parenthesesTest;
	private LinkedList<String> tokensList;

	@BeforeEach
	void init() {
		parenthesesTest = new ParenthesesEqualityTokenCheck();
	}

	private void initTokensList(String s1, String s2, String s3, String s4) {
		tokensList = new LinkedList<String>();
		tokensList.add(s1);
		tokensList.add(s2);
		tokensList.add(s3);
		tokensList.add(s4);
	}

	@DisplayName("One open perentheses check")
	@Test
	void testGetListOfTokensNo1() {
		initTokensList("(", "", "", "");
		assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
	}

	@DisplayName("One closed perentheses check")
	@Test
	void testGetListOfTokensNo2() {
		initTokensList(")", "", "", "");
		assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
	}

	@DisplayName("More then one opened perentheses")
	@RepeatedTest(3)
	void testGetListOfTokensNo3(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			initTokensList("(", "(", "", ")");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 2) {
			initTokensList("(", ")", "(", "");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 3) {
			initTokensList("(", "(", "", "");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		}
	}

	@DisplayName("More then one closed perentheses")
	@RepeatedTest(3)
	void testGetListOfTokensNo4(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			initTokensList("(", ")", ")", "");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 2) {
			initTokensList(")", "(", "", ")");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 3) {
			initTokensList(")", ")", "", "");
			assertThrows(IllegalArgumentException.class, () -> parenthesesTest.check(tokensList));
		}
	}

	@DisplayName("Equal number of opened and closed perentheses")
	@RepeatedTest(3)
	void testGetListOfTokensNo6(RepetitionInfo info) {
		if (info.getCurrentRepetition() == 1) {
			initTokensList("(", "(", ")", ")");
			assertDoesNotThrow(() -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 2) {
			initTokensList("(", ")", "(", ")");
			assertDoesNotThrow(() -> parenthesesTest.check(tokensList));
		} else if (info.getCurrentRepetition() == 3) {
			initTokensList("(", ")", "", "");
			assertDoesNotThrow(() -> parenthesesTest.check(tokensList));
		}
	}

}
