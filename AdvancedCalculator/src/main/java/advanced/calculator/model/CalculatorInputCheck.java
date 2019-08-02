package advanced.calculator.model;

import java.util.LinkedList;

/**
 * The class is singleton class with only one public method. Through the public
 * method {@code getListOfTokens(String)} object of this class takes a String
 * and checks the input. If input is OK it returns, also through the same
 * method, the linked list that contains tokens strings. Every operator and
 * number can be returned as token. If there is illegal arguments in input
 * string, the IllegalArgumentException is thrown. Illegal arguments are
 * letters,
 * 
 * @see {@link #getListOfTokens(String)}
 * @author Igor Stojanovic
 *
 */
final class CalculatorInputCheck {
	private static CalculatorInputCheck instanceVariable;
	private static final String OPERATORS = "+-*%^/()";
	
	private LinkedList<String> clearedInput;
	private String input;
	ParenthesesEqualityTokenCheck parenthesesCheck = new ParenthesesEqualityTokenCheck();
	OperatorsTokenCheck operatorsCheck = new OperatorsTokenCheck();

	private CalculatorInputCheck() {
	}

	/**
	 * To check string and get list of tokens for that string, see
	 * {@link #getListOfTokens(String)};
	 */
	public static CalculatorInputCheck getInstance() {
		if (instanceVariable == null) {
			instanceVariable = new CalculatorInputCheck();
		}
		return instanceVariable;
	}

	/*
	 * This method will: 
	 * 1. replace all blank spaces and if nothing is left, it will
	 * return 0. 
	 * 2. for every character in input string it will : 
	 * 	2.1 check if it is number. If it's last character it will 
	 *      create a token of it, if it is not, it will append it 
	 *      to the helper StringBulder for later creation of token.
	 *  2.2 check if it is decimal dot. If input is like: .3 or 3. 
	 *  	or 3..5 it will throw an exception. If decimal dot is 
	 *  	first or last, it will throw an exception. 
	 *  2.3 for every new operator, it will first create token of 
	 *  	the leftover numbers in helper and then create new 
	 *  	token of operator.
	 */
	private void inputCheck() {
		clearedInput = new LinkedList<String>(); // for every new input, new list is created.
		StringBuilder helper = new StringBuilder();

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);

			if (isNum(c)) {
				helper.append(c);
				if (i == input.length() - 1) {
					addAndClearHelper(helper);
				}
			} else if (c == '.') {
				if (i == 0 || i == input.length() - 1) {
					throw new IllegalArgumentException("First or last character can't be decimal dot!");
				} else {
					char before = input.charAt(i - 1);
					char after = input.charAt(i + 1);
					if (!isNum(before) || !isNum(after)) {
						throw new IllegalArgumentException(
								"Decimal number need to be like: 3.5 . Not allowed: .3 ; 3.");
					}
				}
				helper.append(c);
			} else if (OPERATORS.indexOf(c) != -1) {
				if (helper.length() != 0) {
					addAndClearHelper(helper);
				}
				helper.append(c);
				addAndClearHelper(helper);
			} else {
				throw new IllegalArgumentException("Function can contain only numbers and operands : +-*/%^()!");
			}
		}

		parenthesesCheck.check(clearedInput);
		operatorsCheck.check(clearedInput);
	}
	
	public static void main(String[] args) {
		CalculatorInputCheck calc = CalculatorInputCheck.getInstance();
		System.out.println(calc.getListOfTokens("5."));
	}
	
	private void addAndClearHelper(StringBuilder helper) {
		clearedInput.add(helper.toString());
		helper.delete(0, helper.length());
	}
	
	/*
	 * returns true if char c is number, and false if it's not.
	 */
	private boolean isNum(char c) {
		return (c >= '0' && c <= '9');
	}

	/*
	 * This method checks if operators are in good order. It will throw an
	 * IllegalArgumentException: if first token is an forbidden-alone argument, if
	 * forbidden-alone argument is on only one argument and if two operators are
	 * next to each other. Forbidden-alone arguments are: '*' , '/' , '^'
	 */

	/**
	 * Takes a string and transforms it in to LinkedList of string tokens.
	 * <br>Tokens can be:  
	 * <br>numbers: [ 1, 1.5, 100, 20.7 ... X, X.Y ], 
	 * <br>operators: [ (, ), +, -, *, /, %, ^ ]. 
	 * <br>If string contains anything 
	 * except numbers or operators that are presented in the last sentence, 
	 * IllegalArgumentException will be thrown. If tokens are not arranged in
	 * an order appropriate to mathematics standards, IllegalArgumentException 
	 * will be thrown.
	 * <pre>
	 * Example of valid input:<br>
	 * input: 2+5          -> result:  [ "2", "+", "5" ]
	 * input: (2+5)        -> result:  [ "(", "2", "+", "5", ")" ]
	 * input: (2+5)+2      -> result:  [ "(", "2", "+", "5", ")", "+", "2" ]
	 * input: (2+5)+(2-5)  -> result:  [ "(", "2", "+", "5", ")", "+", "(", "2", "-", "5", ")" ]
	 * input: (2^2*7)/2    -> result:  [ "(", "2", "^", "2", "*", "7", ")", "/", "2" ]
	 * input: 2.3+5-1.8    -> result:  [ "2.3", "+", "5", "-", "1.8" ]<br>
	 * Example of bad input:<br>
	 * input: *5           -> result:  IllegalArgumentException[ operator in the first place ]
	 * input: (2+5         -> result:  IllegalArgumentException[ not closed parentheses ]
	 * input: 2+5)+2       -> result:  IllegalArgumentException[ not opened parentheses ]
	 * input: (2^*7)/2     -> result:  IllegalArgumentException[ operator by operator  ]
	 * input: .3+5-.8      -> result:  IllegalArgumentException[ wrong decimal number input ]
	 * </pre>
	 * @param input - String containing mathematical function for calculation
	 * @return LinkedList<String> - tokens in linked list. Every token is 
	 * 			either string presentation of number or of operation.
	 * @throws IllegalArgumentException if
	 */
	public LinkedList<String> getListOfTokens(String input) throws IllegalArgumentException {
		if (input == null) {
			this.input = "0";
		} else {
			this.input = isEmpty(input);
		}
		
		inputCheck();

		LinkedList<String> duplicate = clearedInput;
		clearedInput = null;
		return duplicate;
	}
	
	/*
	 * First replaces all blank spaces and then checks if input string is 
	 * empty(containing no characters). If input string is empty then returns 
	 * only string with one character, that is zero, else returns input without blank spaces.
	 */
	private String isEmpty(String input) {
		String helper;
		
		helper = input.replace(" ", "");
		if(helper.equals("")) {
			helper = "0";
		}
		return helper;
	}
}
