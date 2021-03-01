package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

     boolean postfixNotationIsNull = false;

    public String evaluate(String statement) {

        String result = null;

        // Check if input is correct
        if (statementCheck(statement)) {

            // list of elements from input line
            List<String> myListOfNumbersAndSymbols = arrayWithPattern(statement);

            // converting line in postfix notation
            StringBuilder postfixNotationResult = postfixNotation(myListOfNumbersAndSymbols);

            //method made arithmetic operation depending on input data and show it in postfix notation

            double resultDouble = reversePostfixNotationResult(postfixNotationResult.toString());

            // result in String type
            result = "" + resultDouble;

            // Remove the sign after the "." If the number is an integer.
            if (resultDouble % 1 == 0) result = "" + (int) resultDouble;

            if (postfixNotationIsNull) result = null;

        }

        // Check what we got in result
        System.out.println(result);

        return result;
    }
    // Method converting line in list by chosen pattern

    public  List<String> arrayWithPattern(String inputString) {

        Pattern patternForNumbersAndArithmeticSymbols = Pattern.compile("(\\d+(\\.\\d+)?)|([+\\-*/()])");

        List<String> listOfNumbersAndSymbols = new ArrayList<>();
        Matcher matcherForNumbersAndArithmeticSymbols = patternForNumbersAndArithmeticSymbols.matcher(inputString);

        while (matcherForNumbersAndArithmeticSymbols.find()) {
            listOfNumbersAndSymbols.add(matcherForNumbersAndArithmeticSymbols.group());
        }

        return listOfNumbersAndSymbols;
    }

    // Convert list in line in postfix notation form
    public  StringBuilder postfixNotation(List<String> listOfNumbersAndSymbols) {

        //
        //Implementation of reverse Postfix notation.
        // The result is written to string postfixNotationResult.
        StringBuilder postfixNotationResult = new StringBuilder();
        Stack<String> stackForArithmeticSymbols = new Stack<>();
        int symbolPriority;

        for (String symbol : listOfNumbersAndSymbols) {
            symbolPriority = getSymbolPriority(symbol);

            if (symbolPriority == 0) {
                postfixNotationResult.append(symbol);
            }
            if (symbolPriority == 1) {
                stackForArithmeticSymbols.push(symbol);
            }
            if (symbolPriority > 1) {
                postfixNotationResult.append(" ");
                while (!stackForArithmeticSymbols.empty()) {
                    if (getSymbolPriority(stackForArithmeticSymbols.peek()) >= symbolPriority) {
                        postfixNotationResult.append(stackForArithmeticSymbols.pop());
                    } else break;
                }
                stackForArithmeticSymbols.push(symbol);
            }
            if (symbolPriority == -1) {
                postfixNotationResult.append(" ");
                while (getSymbolPriority(stackForArithmeticSymbols.peek()) != 1) {
                    postfixNotationResult.append(stackForArithmeticSymbols.pop());
                }
                stackForArithmeticSymbols.pop();
            }
        }

        while (!stackForArithmeticSymbols.empty()) {
            postfixNotationResult.append(stackForArithmeticSymbols.pop());
        }

        // Checking the output of reverse postfix notation.
        System.out.println(postfixNotationResult);


        return postfixNotationResult;
    }

    // mathematical operations based on
    // the transmitted string in Postfix notation format.
    public  double reversePostfixNotationResult(String reversePostfixNotation) {

        List<String> listOfNumbersAndSymbolsToTransferToResult = arrayWithPattern(reversePostfixNotation);
        StringBuilder symbolToOperation = new StringBuilder();
        Stack<Double> stackWithDoubleNumbers = new Stack<>();

        for (int i = 0; i < listOfNumbersAndSymbolsToTransferToResult.size(); i++) {

            if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) continue;

            if (getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                while ((!listOfNumbersAndSymbolsToTransferToResult.get(i).equals(" ")) && getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) == 0) {
                    symbolToOperation.append(listOfNumbersAndSymbolsToTransferToResult.get(i++));

                    if (i == listOfNumbersAndSymbolsToTransferToResult.size()) break;
                    stackWithDoubleNumbers.push(Double.parseDouble(symbolToOperation.toString()));
                    symbolToOperation = new StringBuilder();
                }

            }

            // mathematical operations
            if (getSymbolPriority(listOfNumbersAndSymbolsToTransferToResult.get(i)) > 1) {
                double a = stackWithDoubleNumbers.pop(), b = stackWithDoubleNumbers.pop();

                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("+")) {
                    stackWithDoubleNumbers.push(b + a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("-")) {
                    stackWithDoubleNumbers.push(b - a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("*")) {
                    stackWithDoubleNumbers.push(b * a);
                }
                if (listOfNumbersAndSymbolsToTransferToResult.get(i).equals("/")) {
                    // Проверка деления на 0
                    if (a != 0) stackWithDoubleNumbers.push(b / a);
                    else {
                        stackWithDoubleNumbers.push(null);
                        break;
                    }
                }
            }
        }

        //  there will be one element on the stack - the result of the calculation.
        // If during the calculation there was an attempt to divide by 0, then method
        // return 0, and the final result of the program will benull.
        try {
            return stackWithDoubleNumbers.pop();
        }
        catch (NullPointerException exc) {
            postfixNotationIsNull = true;
            return 0;
        }
    }

    //  method returns the priority of the character passed to it.
    private  int getSymbolPriority(String symbol) {
        switch (symbol) {
            case "*":
            case "/":
                return 3;
            case "+":
            case "-":
                return 2;
            case "(":
                return 1;
            case ")":
                return -1;
            default:
                return 0;
        }
    }

    // Checking the correctness of the input.
    private  boolean statementCheck(String statementToCheck) {
        boolean isCorrect = true;

        // Checking for empty input.
        try {
            if (statementToCheck.equals("")) return false;
        }
        catch (NullPointerException exc) {
            return false;
        }

        // checking on duplicate symbols
        String statementWithoutNumbersNoSpace = statementToCheck.replaceAll("\\d", "");
        String statementWithoutNumbersAndSpace = statementToCheck.replaceAll("\\d", " ");
        char[] statementWithoutNumbersCharArray = statementWithoutNumbersAndSpace.toCharArray();
        char[] someChar = statementWithoutNumbersNoSpace.trim().toCharArray();

        if (statementWithoutNumbersNoSpace.length() > 1) {
            for (int i = 0; i < statementWithoutNumbersCharArray.length - 1; i++) {
                if (statementWithoutNumbersCharArray[i] != ' ') {
                    if (statementWithoutNumbersCharArray[i] == statementWithoutNumbersCharArray[i + 1]) {
                        isCorrect = false;
                        break;
                    }
                }
            }

            // Check for redundancy of opening / closing parentheses.
            int a = 0, b = 0;
            for (char c : someChar) {
                if (c == '(') a++;
                if (c == ')') b++;
            }
            if (a != b) return false;
        }

        // Checking for invalid characters.
        for (char statementSymbol : someChar) {
            switch (statementSymbol) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')':
                case ' ':
                case '.':
                    break;
                default:
                    return false;
            }
        }
        // end
        return isCorrect;
    }
}