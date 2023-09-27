import java.util.Scanner;

public class Calculator{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a mathematical expression of no more than 3 numbers:");
        String input = scanner.nextLine();

        try{
            ExpressionParser parser = new ExpressionParser();
            double result = parser.evaluate(input);
            System.out.println("Result: " + result);
        }catch(IllegalArgumentException e) {
            System.err.println("Invalid input: " + e.getMessage());
        }
    }
}

class ExpressionParser {
    public double evaluate(String input){
        String[]tokens = input.split(" ");
        if(tokens.length<3 || tokens.length>5 ){
            throw new IllegalArgumentException("Invalid expression format: Put spaces between operators and numbers//Put less than 4 numbers");
        }

        double result = 0;
        double currentNumber = 0; 
        char currentOperator = '+';

        for (String token: tokens){
            if (isOperator(token)){
                //If the token is an operator, update the currentOperator
                currentOperator = token.charAt(0);
            }else{
                //if the token is a number, parse it and apply the currentOperator to the result
                int number = Integer.parseInt(token);
                if (number<1 || number>10){
                    throw new IllegalArgumentException("Numbers must be between 1 and 10 inclusive.");
                }

                switch (currentOperator){
                    case '+':
                        result+= currentNumber;
                        currentNumber = number; 
                        break;
                    case '-':
                        result += currentNumber;
                        currentNumber =- number;
                        break;
                    case '*':
                        currentNumber *= number;
                        break;
                    case '/':
                        if(number == 0){
                            throw new IllegalArgumentException("Division by zero is not allowed.");
                        }
                        //perform double division
                        currentNumber = currentNumber / (double) number;
                        break;
                    default: 
                        throw new IllegalArgumentException("Invalid operator: " +  currentOperator);
                }

            }
        }

        //add the last number to the result to complete the evaluation
        result += currentNumber;
        return result;
    }

    private boolean isOperator(String token){
        //Check if the token is one of the supported operators (+, - , *, /)
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }
}