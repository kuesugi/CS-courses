package cs445.a2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
// CS445 2017 Fall
/**
 * This class uses two stacks to evaluate an infix arithmetic expression from an
 * InputStream. It should not create a full postfix expression along the way; it
 * should convert and evaluate in a pipelined fashion, in a single pass.
 */
public class InfixExpressionEvaluator {
    // Tokenizer to break up our input into tokens
    StreamTokenizer tokenizer;

    // Stacks for operators (for converting to postfix) and operands (for
    // evaluating)
    StackInterface<Character> operatorStack;
    StackInterface<Double> operandStack;
    StackInterface<Character> traceElem;

    /**
     * Initializes the evaluator to read an infix expression from an input
     * stream.
     * @param input the input stream from which to read the expression
     */
    public InfixExpressionEvaluator(InputStream input) {
        // Initialize the tokenizer to read from the given InputStream
        tokenizer = new StreamTokenizer(new BufferedReader(
                        new InputStreamReader(input)));

        // StreamTokenizer likes to consider - and / to have special meaning.
        // Tell it that these are regular characters, so that they can be parsed
        // as operators
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        // Allow the tokenizer to recognize end-of-line, which marks the end of
        // the expression
        tokenizer.eolIsSignificant(true);

        // Initialize the stacks
        operatorStack = new ArrayStack<Character>();
        operandStack = new ArrayStack<Double>();
        traceElem = new ArrayStack<Character>();
    }

    /**
     * Parses and evaluates the expression read from the provided input stream,
     * then returns the resulting value
     * @return the value of the infix expression that was parsed
     */
    public Double evaluate() throws ExpressionError {
        // Get the first token. If an IO exception occurs, replace it with a
        // runtime exception, causing an immediate crash.
        try {
            tokenizer.nextToken();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Continue processing tokens until we find end-of-line
        while (tokenizer.ttype != StreamTokenizer.TT_EOL) {
            // Consider possible token types
            switch (tokenizer.ttype) {
                case StreamTokenizer.TT_NUMBER:
                    // If the token is a number, process it as a double-valued
                    // operand
                    handleOperand((double)tokenizer.nval);
                    break;
                case '+':
                case '-':
                case '*':
                case '/':
                case '^':
                case '%':
                    // If the token is any of the above characters, process it
                    // is an operator
                    handleOperator((char)tokenizer.ttype);
                    break;
                case '(':
                case '<':
                    // If the token is open bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleOpenBracket((char)tokenizer.ttype);
                    break;
                case ')':
                case '>':
                    // If the token is close bracket, process it as such. Forms
                    // of bracket are interchangeable but must nest properly.
                    handleCloseBracket((char)tokenizer.ttype);
                    break;
                case StreamTokenizer.TT_WORD:
                    // If the token is a "word", throw an expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    tokenizer.sval);
                default:
                    // If the token is any other type or value, throw an
                    // expression error
                    throw new ExpressionError("Unrecognized token: " +
                                    String.valueOf((char)tokenizer.ttype));
            }

            // Read the next token, again converting any potential IO exception
            try {
                tokenizer.nextToken();
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }

        handleRemainingOperators();

        return operandStack.pop();
    }

    void handleOperand(double operand) {
       operandStack.push(operand);
       traceElem.push('1');                  // '1' means this elem is an operand, or a number 
                                             // why traceElem.push((char)1); fails?  
    }

    void handleOperator(char operator) {
            traceElem.push(operator);
            if (operatorStack.isEmpty())
                operatorStack.push(operator);
            else{
                while ( !(operatorStack.isEmpty()) && precedence(operator) <= precedence(operatorStack.peek()) ){
                    if ( !(operandStack.isEmpty()) )
                        if ( operandStack.peek()==0 && operatorStack.peek()=='/')
                            throw new ExpressionError("Invalid Expression: 0 CANNOT be the denominator!");
                    try{  
                        operandStack.push(calculate( operandStack.pop(), operandStack.pop(), operatorStack.pop()));
                    }
                    catch (Exception excp){
                        throw new ExpressionError("Invalid Expression: cannot evaluate with wrong operators use, eg.++2, +61- ");
                    }
                }
            operatorStack.push(operator);
            }
    }

    void handleOpenBracket(char openBracket) {
        operatorStack.push(openBracket);

        // to throw expression error like 1+1(2)
        if (traceElem.isEmpty())
            traceElem.push(openBracket);
        else{
            char lastElem = traceElem.peek();
            traceElem.push(openBracket);
            if (lastElem == '1')
            throw new ExpressionError("Invalid Expression: wrong bracket use. eg. 10+1(2), 1+1<<");
        }
    }

    void handleCloseBracket(char closeBracket) {
        if (operatorStack.isEmpty() || operandStack.isEmpty())
            throw new ExpressionError ("Invalid Expression: wrong brackets use/missing bracket");

        //if closeBracket.equals('<') ... break;if('(') ... break;
        switch (closeBracket){
            case '>':     
                    char lastOperator = operatorStack.pop();
                //    if (lastOperator == '<' && operandStack.peek )          //this error is checked in the beginning of this method
                 //       throw new ExpressionError("Invalid Expression: wrong brackets use");    //eg. infix: <>
                    while ( !(lastOperator == '<')){
                        if (lastOperator == '(')
                            throw new ExpressionError("Invalid Expression: brackets are unpaired");
                        if (lastOperator == '>' || lastOperator == ')')   //former close brackets should be pop()ed  eg. infix: >9+2>
                            throw new ExpressionError("Invalid Expression: wrong brackets use"); 
                        try{
                        operandStack.push( calculate(operandStack.pop(), operandStack.pop(), lastOperator) );
                        }
                        catch (Exception excp){
                            throw new ExpressionError("Invalid Expression: missing a number");
                        }
                        if( operatorStack.isEmpty() )   //if the very last operator is still not <, then there is an exception
                                throw new ExpressionError("Invalid Expression: missing bracket");
                        if ( (!operandStack.isEmpty()) )
                        lastOperator = operatorStack.pop(); 
                    }
                    break;  //if the last operator is '<', then break

            case ')':
                    char lastoperator = operatorStack.pop();
               //     if (lastoperator == '(')          //this error is checked in the beginning of this method
               //         throw new ExpressionError("Invalid Expression: wrong brackets use");    //eg. infix: ()
                      while ( !(lastoperator == '(')){
                        if (lastoperator == '<')
                            throw new ExpressionError("Invalid Expression: brackets are unpaired");
                        if (lastoperator == '>' || lastoperator == ')')   //former close brackets should be pop()ed  
                            throw new ExpressionError("Invalid Expression: wrong brackets use"); 
                        try{
                        operandStack.push( calculate(operandStack.pop(), operandStack.pop(), lastoperator) );
                        }
                        catch (Exception excp){
                            throw new ExpressionError("Invalid Expression: missing a number");
                        }
                        if( operatorStack.isEmpty() )    //if the very last operator is still not (, then there is an exception
                                throw new ExpressionError("Invalid Expression: missing bracket");
                        if ( (!operandStack.isEmpty()) )
                        lastoperator = operatorStack.pop();
                    }   
                    break;   //if the last operator is '(', then break
                    //paired bracket has been pop()ed, then finish handleCloseBrackets
        }
    }

    void  handleRemainingOperators() {
        while ( !(operatorStack.isEmpty() )) {     // while (have operators)
            double top = operandStack.pop(); 
            if (operandStack.isEmpty())                 
                throw new ExpressionError("Invalid Expression: wrong operators/brackets use, eg.+2*, 6+1<<");
            // separate these two .pop()s in order to check if there is any wrong use of operators/brackets
            double bot = operandStack.pop();
            operandStack.push( calculate( top, bot, operatorStack.pop()) );
            }
    }
       
    private int precedence(char operator){
         switch (operator){
            case '+': return 1;
            case '-': return 1;
            case '*': return 2;
            case '/': return 2;
            case '%': return 2;                   // % has the same precedence with * and /
            case '^': return 3;
            case '<': return 0;                   // < and ( are used interchangeably, so equal precedence 
            case '(': return 0;                   // close brackets are handled by handleCloseBracket
        }
        return 0;                                 // need a return
    }

    private double calculate(double top, double bot, char op){
        switch (op) {
            case '+':   return bot + top;
            case '-':   return bot - top;         //former - latter
            case '*':   return bot * top;
            case '/':   
                if (top == 0)                     //bot / top, top CANNOT be zero
                    throw new ExpressionError ("Invalid Expression: number / 0. 0 CANNOT be denominator!");
                return bot / top;
            case '^':   return Math.pow(bot, top);//use existing method Math.pow()
            case '%':   return bot % top;       
        }
        return 0;
    }
    
    public static void main(String[] args) {
        System.out.println("Infix expression:");
        InfixExpressionEvaluator evaluator =
                        new InfixExpressionEvaluator(System.in);
        Double value = null;
        try {
            value = evaluator.evaluate();
        } catch (ExpressionError e) {
            System.out.println("ExpressionError: " + e.getMessage());
        }
        if (value != null) {
            System.out.println(value);
        } else {
            System.out.println("Evaluator returned null");
        }
    }
}
