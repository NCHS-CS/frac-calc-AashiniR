// Aashini Ramesh
// Period 6
// Fraction Calculator Project

import java.util.*;

// TODO: Description of what this program does goes here.
//This program acts as a basic calculator. 
public class FracCalc {

   // It is best if we have only one console object for input
   public static Scanner console = new Scanner(System.in);
   
   // This main method will loop through user input and then call the
   // correct method to execute the user's request for help, test, or
   // the mathematical operation on fractions. or, quit.
   // DO NOT CHANGE THIS METHOD!!
   public static void main(String[] args) {
   
      // initialize to false so that we start our loop
      boolean done = false;
      
      // When the user types in "quit", we are done.
      while (!done) {
         // prompt the user for input
         String input = getInput();
         
         // special case the "quit" command
         if (input.equalsIgnoreCase("quit")) {
            done = true;
         } else if (!UnitTestRunner.processCommand(input, FracCalc::processCommand)) {
        	   // We allowed the UnitTestRunner to handle the command first.
            // If the UnitTestRunner didn't handled the command, process normally.
            String result = processCommand(input);
            
            // print the result of processing the command
            System.out.println(result);
         }
      }
      
      System.out.println("Goodbye!");
      console.close();
   }

   // Prompt the user with a simple, "Enter: " and get the line of input.
   // Return the full line that the user typed in.
   public static String getInput() {
      // TODO: Implement this method
       System.out.print("Enter: ");
       String input = console.nextLine();
       return input;

   }
   
   // processCommand will process every user command except for "quit".
   // It will return the String that should be printed to the console.
   // This method won't print anything.
   // DO NOT CHANGE THIS METHOD!!!
   public static String processCommand(String input) {

      if (input.equalsIgnoreCase("help")) {
         return provideHelp();
      }
      
      // if the command is not "help", it should be an expression.
      // Of course, this is only if the user is being nice.
      return processExpression(input);
   }
   
   // Lots work for this project is handled in here.
   // Of course, this method will call LOTS of helper methods
   // so that this method can be shorter.
   // This will calculate the expression and RETURN the answer.
   // This will NOT print anything!
   // Input: an expression to be evaluated
   //    Examples: 
   //        1/2 + 1/2
   //        2_1/4 - 0_1/8
   //        1_1/8 * 2
   // Return: the fully reduced mathematical result of the expression
   //    Value is returned as a String. Results using above examples:
   //        1
   //        2 1/8
   //        2 1/4

   
   public static String processExpression(String input) {
      Scanner parser = new Scanner(input);
      String firstNum = parser.next(); //takes first token
      String operator = parser.next(); //takes second token (always the operator)
      String secondNum = parser.next();//takes third token, what is being parsed
      String wholeNum = secondNum; //value of whole number, turns to zero if there is a fraction
      String frac = secondNum;

      if(secondNum.contains("/")){ //if secondNum is a fraction....
         wholeNum = "0"; //wholeNum will always be 0 if this is true as shown in directions

         if(secondNum.contains("_")){ //need to account for mixed numbers
            //if its a mixed number, the whole number is the one before the underscore
            wholeNum = secondNum.substring(0, secondNum.indexOf("_")); //sets wholeNum to from 0 to everything before _
            frac = secondNum.substring(secondNum.indexOf("_") + 1); // takes everything after _ + 1 since the start is inclusive, and we dont want the _ in our string
         }
         //in a mixed number scenario, frac is now parsed into everything after the underscore
         //whole number is now parsed into everything before the underscore

         String numerator = processNumerator(frac); //set the final value of numerator to numerator of frac
         String denominator = processDenominator(frac); //set the final value of denominator to denominator of frac

         //this if statement handles the case if both the num and den are negative, since that would end up being positive
         if(numerator.contains("-") && denominator.contains("-")){
            numerator = numerator.substring(1); //simply changes the string into everything after the negative making it "positive"
            denominator = denominator.substring(1); //^^
         }
         input = "Op:" + operator + " Whole:" + wholeNum + " Num:" + numerator + " Den:" + denominator; //changes the final value of input if secondNum is a fraction
         return input; //returns input!
      }

      //this whole if statement is ignored if secondNum does not have a slash.
      //the input value below is set if there is no slash in the second token
      //num and den are set as the directions in CP2 say
      input = "Op:" + operator + " Whole:" + wholeNum + " Num:0" + " Den:1"; 
      return input;
   }

   public static String processNumerator(String num){
      num = num.substring(0, num.indexOf('/')); //takes everything before the /, aka the numerator!
      return num;
   }

   public static String processDenominator(String den){
      den = den.substring(den.indexOf("/") + 1); //takes everything before the /, aka the denominator
      return den;
   }
   


   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      String help = "You must change this text.\n";
      help += "Students, you need to provide actual helpful text here!";
      
      return help;
   }
}

