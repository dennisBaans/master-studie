package calculator;

import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;

/**
 * Beschreibe doch hier, wie man das Programm benutzt 
 */
class Calculator{

    static Scanner scanner = new Scanner(System.in);
    public static void main(String arg[]){
        startCalculator();
    }

    /**
     * 
     */
    static void startCalculator(){
        System.out.println("Calculator Start");
        String input = readInputFromCLI();
        //String[] arrInput = parseInputToArray(input);
        List<String> tokens = parseInputToArray(input);
        double result = calcController(tokens);
        System.out.println("Ergebnis: " + result);
        scanner.close();
    }

    /**
     * 
     * @return
     */
    static String readInputFromCLI(){
        System.out.println("Enter your calculation (e.g. 2+4*2):");
        return scanner.nextLine(); 
    }

    /**
     * 
     * @param input
     * @return
     */
    static List<String> parseInputToArray(String input){
        List<String> tokens = new ArrayList<String>();
        // Split the String in tokens on (delims) +,-,/ or * and return the delim
        StringTokenizer tokenizer = new StringTokenizer(input, "+-*/", true);
        while(tokenizer.hasMoreTokens()){
            // Save the next token and remove whitespace
            tokens.add(tokenizer.nextToken().trim());
        }
        return tokens;
    }

    /**
     * 
     * @param tokens
     * @return
     * @throws Exception 
     */
    static double calcController(List<String> tokens){
        // Calculate first * and /, than + and -
        String[][] arithmetics = {{"*","/"},{"+","-"}};
        try{
            List<String> resultTokens = tokens;
            for(String[] pair : arithmetics ){
                resultTokens = loopTokensWithArithmeticPair(pair, resultTokens);
            }
            // If there is just one Element in tokens we are done
            if(resultTokens.size() == 1){
                return Double.parseDouble(resultTokens.get(0));
            }else{
                throw(new Exception("something went wrong"));
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Invalid input");
            startCalculator();
        }
        // should never be reached...
        return 0;
    }

    /**
     * 
     * @param pair
     * @param tokens
     * @return
     */
    static List<String> loopTokensWithArithmeticPair(String[] pair, List<String> tokens){
        List<String> newTokens = new ArrayList<String>();
        String lastToken = null;

        // iterate throgh all tokens
        for(int index = 0; index < tokens.size(); index++){
            String token = tokens.get(index);
            Boolean match = false;

            // check if there is one of the passed arithmetics
            for(String arithmetic : pair){
                if(token.equals(arithmetic)) match = true;
            }

            // Calculate if there is a match
            if(match){
                index++;
                double leftNum = Double.parseDouble(lastToken); 
                double rightNum = Double.parseDouble(tokens.get(index)); 
                String result = String.valueOf(calc(leftNum, rightNum, token));
                lastToken = result;
            }else{
                if(lastToken != null && !lastToken.isEmpty()) newTokens.add(lastToken);
                lastToken = token; 
            }
        }
        newTokens.add(lastToken);
        return newTokens;
    }

    /**
     * 
     * @param left 
     * @param right
     * @param arithmetic Das Arethmetische zeichen für die Berechnung +,-,* oder /
     * @return Das Ergebnis der Berechnung wird zurückgegeben.
     */
    static double calc(double left, double right, String arithmetic){
        double result = 0.0;
        try{
            switch(arithmetic){
                case "*":
                    result = left * right;
                break;
                case "/":
                    result = left / right;
                break;
                case "+":
                    result = left + right;
                break;
                case "-":
                    result = left - right;
                break;
            }
        }catch(Exception e){
            System.out.println(e);
            System.out.println("Invalid input");
            startCalculator();
        }
        return result;

    }
}