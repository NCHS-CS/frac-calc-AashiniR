// Aashini Ramesh
// Period 6
// Fraction Calculator Project
// This program acts as a basic calculator. It can add, subtract, divide and multiply numbers.
// These numbers can be whole numbers, mixed numbers, or improper fractions.

import java.util.*;
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

   // This method takes user input and reads the data before sending into the appropriate methods.
   // To do this, the method uses a Scanner object.
   public static String processExpression(String input) {
      Scanner parser = new Scanner(input); 
      String firstNumber = parser.next(); // Takes first token.
      String operator = parser.next(); // Takes second token (always the operator).
      String secondNumber = parser.next(); // Takes third token, what is being parsed.
      String wholeNumber = firstNumber; //value of whole number, turns to zero if there is a fraction
      String fraction1 = firstNumber; 
      String fraction2 = secondNumber; 
      String numerator = "0";
      String denominator = "1";
      String firstNumParsed;
      String secondNumParsed;

      if(secondNumber.equals("0") && operator.equals("/")){
         input = "You cannot divide by zero.";
         return input;
      }

      if((firstNumber.contains("/")) || (firstNumber.contains("_"))){ //if firstNum is a fraction...
         wholeNumber = "0";
         firstNumParsed = processFraction1(firstNumber, fraction1, input, wholeNumber, operator); 
      }

      else{
         firstNumParsed = "O" + operator + "W" + wholeNumber + "N" + numerator + "D" + denominator; 
      }

      if(secondNumber.contains("/") || (secondNumber.contains("_"))){ //if secondNum is a fraction....
         wholeNumber = "0"; //wholeNum will always be 0 if this is true as shown in directions, see line 92
         secondNumParsed = processFraction2(secondNumber, fraction2, input, wholeNumber, operator);
      }

      // this whole if statement is ignored if secondNum does not have a slash, and the code goes to the else statement. 
      else{
         wholeNumber = secondNumber;
         secondNumParsed = "O" + operator + "W" + wholeNumber + "N" + numerator + "D" + denominator; 
      }

      if(operator.equals("+")){ 
         // If its addition, call the addition method. 
         input = addFraction(firstNumParsed, secondNumParsed, input);
      }
      if(operator.equals("-")){
         // If its subtraction, call the subtraction method. 
         input = subFraction(firstNumParsed, secondNumParsed, input);
      }
      if(operator.equals("*")){
         // If its multiplication, call the multiplication method.
         input = multFraction(firstNumParsed, secondNumParsed, input);
      }
      if(operator.equals("/")){
         // If its division, call the divison method. 
         input = divideFraction(firstNumParsed, secondNumParsed, input);
      }
      return input;
   }

// This method divides the numbers. 
public static String divideFraction(String frac1, String frac2, String input){
   // parse frac1
   // uses firstNumParsed to identify the whole number, numerator, and denominator using substrings
   // converts the values from the string into ints.
   int whole1 = Integer.parseInt(frac1.substring(frac1.indexOf("W") + 1, frac1.indexOf("N"))); 
   int num1 = Integer.parseInt(frac1.substring(frac1.indexOf("N") + 1, frac1.indexOf("D")));
   int den1 = Integer.parseInt(frac1.substring(frac1.indexOf("D") + 1));

   // parse frac2
   // same idea, but uses secondNumParsed
   int whole2 = Integer.parseInt(frac2.substring(frac2.indexOf("W") + 1, frac2.indexOf("N")));
   int num2 = Integer.parseInt(frac2.substring(frac2.indexOf("N") + 1, frac2.indexOf("D")));
   int den2 = Integer.parseInt(frac2.substring(frac2.indexOf("D") + 1));
   
   if(den1 == 0 || den2 == 0){
      return "You cannot divide by zero.";
   }

   //convert mixed numbers to improper
   if (whole1 < 0) //if the mixed number is negative, 
      num1 = whole1 * den1 - num1; //-2_1/4 = (-2 * 4) MINUS 1 ---> -9. Denominator doesn't matter here since it stays the same in an improper fraction.
   else //else, if the mixed number is positive, 
      num1 = whole1 * den1 + num1; //2_1/4 = (2 * 4) PLUS 1    --->  9

   if (whole2 < 0) //if the second mixed number is negative: 
      num2 = whole2 * den2 - num2;
   else
      num2 = whole2 * den2 + num2;


   //Dividing fractions using keep, change, flip
   //a/b / c/d
   //a/b * d/c
   int finalNum = num1 * den2;
   int finalDen = den1 * num2;

   //simplifies fractions using the Euclidian Algorithim
   //the GCD is always positive, so the program takes the absolute value
   int absNum = Math.abs(finalNum); //takes the absolute value of the numerator simplified above
   int absDen = Math.abs(finalDen); //takes the absolute value of the denominator simplified above

   while (absDen != 0) {
      //This while loop works by saving the previous denominator, computing the remainder, and then moving the previous denominator up
      int previousDen = absDen; 
      absDen = absNum % absDen;
      absNum = previousDen;
   }
   int gcd = absNum; //When the loop ends, and the remainder is 0, the gcd becomes the numerator!

   finalNum = finalNum/gcd; //uses the identified GCD to simplify the ORIGINAL numerator and denominator
   finalDen = finalDen/gcd;

   //if the denominator is negative and the numerator isn't, the whole fraction is negative
   //the negative sign is always IN FRONT of the fraction, so the code is moving the values around without converting to String. 
   if(finalDen < 0){
      finalDen = -finalDen; 
      finalNum = -finalNum;
   }

   int whole = finalNum/finalDen;
   int remainder = Math.abs(finalNum % finalDen);

   if (remainder == 0){  //If there is no remainder, such as if the fraction was 4/2, then ONLY the value of whole will be returned
      return String.valueOf(whole);
   }

   if (whole != 0){ //if there IS a whole number, return the improper fraction as input
      return whole + " " + remainder + "/" + finalDen; //formatting the answer ex: 2 
   }

   
   return finalNum + "/" + finalDen; //if those two conditions above are not needed, just return num over den
}

// This method multiplies numbers.
public static String multFraction(String frac1, String frac2, String input){
   //parse frac1
   //uses firstNumParsed to identify the whole number, numerator, and denominator using substrings
   //converts the values from the string into ints.
   int whole1 = Integer.parseInt(frac1.substring(frac1.indexOf("W") + 1, frac1.indexOf("N"))); 
   int num1 = Integer.parseInt(frac1.substring(frac1.indexOf("N") + 1, frac1.indexOf("D")));
   int den1 = Integer.parseInt(frac1.substring(frac1.indexOf("D") + 1));

   //parse frac2
   //same idea, but uses secondNumParsed
   int whole2 = Integer.parseInt(frac2.substring(frac2.indexOf("W") + 1, frac2.indexOf("N")));
   int num2 = Integer.parseInt(frac2.substring(frac2.indexOf("N") + 1, frac2.indexOf("D")));
   int den2 = Integer.parseInt(frac2.substring(frac2.indexOf("D") + 1));

   if(den1 == 0 || den2 == 0){
      return "You cannot divide by zero.";
   }

   //convert mixed numbers to improper
   if (whole1 < 0) //if the mixed number is negative, 
      num1 = whole1 * den1 - num1; //-2_1/4 = (-2 * 4) MINUS 1 ---> -9. Denominator doesn't matter here since it stays the same in an improper fraction.
   else //else, if the mixed number is positive, 
      num1 = whole1 * den1 + num1; //2_1/4 = (2 * 4) PLUS 1    --->  9

   if (whole2 < 0) //if the second mixed number is negative: 
      num2 = whole2 * den2 - num2;
   else
      num2 = whole2 * den2 + num2;

   int finalNum = num1 * num2;
   int finalDen = den1 * den2;

   //simplifies fractions using the Euclidian Algorithim
   //the GCD is always positive, so the program takes the absolute value
   int absNum = Math.abs(finalNum); //takes the absolute value of the numerator simplified above
   int absDen = Math.abs(finalDen); //takes the absolute value of the denominator simplified above

   while (absDen != 0) {
      //This while loop works by saving the previous denominator, computing the remainder, and then moving the previous denominator up
      int previousDen = absDen; 
      absDen = absNum % absDen;
      absNum = previousDen;
   }
   int gcd = absNum; //When the loop ends, and the remainder is 0, the gcd becomes the numerator!

   finalNum = finalNum/gcd; //uses the identified GCD to simplify the ORIGINAL numerator and denominator
   finalDen = finalDen/gcd;

   //if the denominator is negative and the numerator isn't, the whole fraction is negative
   //the negative sign is always IN FRONT of the fraction, so the code is moving the values around without converting to String. 
   if(finalDen < 0){
      finalDen = -finalDen; 
      finalNum = -finalNum;
   }

   int whole = finalNum/finalDen;
   int remainder = Math.abs(finalNum % finalDen);

   if (remainder == 0){  //If there is no remainder, such as if the fraction was 4/2, then ONLY the value of whole will be returned
      return String.valueOf(whole);
   }

   if (whole != 0){ //if there IS a whole number, return the improper fraction as input
      return whole + " " + remainder + "/" + finalDen; //formatting the answer ex: 2 
   }

   return finalNum + "/" + finalDen; //if those two conditions above are not needed, just return num over den
}

//This method adds numbers.
public static String addFraction(String frac1, String frac2, String input) {
   //parse frac1
   //uses firstNumParsed to identify the whole number, numerator, and denominator using substrings
   //converts the values into integers.
   int whole1 = Integer.parseInt(frac1.substring(frac1.indexOf("W") + 1, frac1.indexOf("N"))); 
   int num1 = Integer.parseInt(frac1.substring(frac1.indexOf("N") + 1, frac1.indexOf("D")));
   int den1 = Integer.parseInt(frac1.substring(frac1.indexOf("D") + 1));

   //parse frac2
   //same idea, but uses secondNumParsed
   int whole2 = Integer.parseInt(frac2.substring(frac2.indexOf("W") + 1, frac2.indexOf("N")));
   int num2 = Integer.parseInt(frac2.substring(frac2.indexOf("N") + 1, frac2.indexOf("D")));
   int den2 = Integer.parseInt(frac2.substring(frac2.indexOf("D") + 1));

   if(den1 == 0 || den2 == 0){
      return "You cannot divide by zero.";
   }
   //convert mixed numbers to improper
   if (whole1 < 0) //if the mixed number is negative, 
      num1 = whole1 * den1 - num1; //-2_1/4 = (-2 * 4) MINUS 1 ---> -9. Denominator doesn't matter here since it stays the same in an improper fraction.
   else //else, if the mixed number is positive, 
      num1 = whole1 * den1 + num1; //2_1/4 = (2 * 4) PLUS 1    --->  9

   if (whole2 < 0) //if the second mixed number is negative: 
      num2 = whole2 * den2 - num2;
   else
      num2 = whole2 * den2 + num2;

   //Simply adding the fractions using this formula for finding common denominators:
   //a/b + c/d = (ad + bc)/bd
   int num = num1 * den2 + num2 * den1;
   int den = den1 * den2;

   //simplifies fractions using the Euclidian Algorithim
   //the GCD is always positive, so the program takes the absolute value
   int absNum = Math.abs(num); //takes the absolute value of the numerator simplified above
   int absDen = Math.abs(den); //takes the absolute value of the denominator simplified above

   while (absDen != 0) {
      //This while loop works by saving the previous denominator, computing the remainder, and then moving the previous denominator up
      int previousDen = absDen; 
      absDen = absNum % absDen;
      absNum = previousDen;
   }
   int gcd = absNum; //When the loop ends, and the remainder is 0, the gcd becomes the numerator!

   num = num/gcd; //uses the identified GCD to simplify the ORIGINAL numerator and denominator
   den = den/gcd;

   //if the denominator is negative and the numerator isn't, the whole fraction is negative
   //the negative sign is always IN FRONT of the fraction, so the code is moving the values around without converting to String. 
   if(den < 0){
      den = -den; 
      num = -num;
   }

   int whole = num/den; //sets the whole number as the simplified num/den
   //the code casts it to int and this is okay because the decimal doesn't matter for the whole
   //for example, for num = 9, den = 4, 9/4 will simply have a whole number of 2. 

   //this variable is numerator of the simplified improper fraction
   //it uses the simplified numerator and denominator to figure out 
   int remainder = Math.abs(num % den); 
   
   if (remainder == 0){  //If there is no remainder, such as if the fraction was 4/2, then ONLY the value of whole will be returned
      return String.valueOf(whole);
   }

   if (whole != 0){ //if there IS a whole number, return the improper fraction as input
      return whole + " " + remainder + "/" + den; //formatting the answer ex: 2 
   }

   return num + "/" + den; //if those two conditions above are not needed, just return num over den
}

//This method subtracts numbers.
//This method is the EXACT same code as addFraction, with a few key details changed.
public static String subFraction(String frac1, String frac2, String input){ 
   //parse frac1
   //uses firstNumParsed to identify the whole number, numerator, and denominator using substrings
   //converts the values to integers. 
   int whole1 = Integer.parseInt(frac1.substring(frac1.indexOf("W") + 1, frac1.indexOf("N"))); 
   int num1 = Integer.parseInt(frac1.substring(frac1.indexOf("N") + 1, frac1.indexOf("D")));
   int den1 = Integer.parseInt(frac1.substring(frac1.indexOf("D") + 1));

   //parse frac2
   //same idea, but uses secondNumParsed
   int whole2 = Integer.parseInt(frac2.substring(frac2.indexOf("W") + 1, frac2.indexOf("N")));
   int num2 = Integer.parseInt(frac2.substring(frac2.indexOf("N") + 1, frac2.indexOf("D")));
   int den2 = Integer.parseInt(frac2.substring(frac2.indexOf("D") + 1));

   if(den1 == 0 || den2 == 0){
      return "You cannot divide by zero.";
   }
   //convert mixed numbers to improper
   if (whole1 < 0) //if the mixed number is negative, 
      num1 = whole1 * den1 - num1; //-2_1/4 = (-2 * 4) MINUS 1 ---> -9. Denominator doesn't matter here since it stays the same in an improper fraction.
   else //else, if the mixed number is positive, 
      num1 = whole1 * den1 + num1; //2_1/4 = (2 * 4) PLUS 1    --->  9

   if (whole2 < 0) //if the second mixed number is negative: 
      num2 = whole2 * den2 - num2;
   else
      num2 = whole2 * den2 + num2;

   //Simply subtracting the fractions using this formula for finding common denominators:
   //a/b + c/d = (ad - bc)/bd
   int num = num1 * den2 - num2 * den1;
   int den = den1 * den2;

   //simplifies fractions using the Euclidian Algorithim
   //the GCD is always positive, so the program takes the absolute value
   int absNum = Math.abs(num); //takes the absolute value of the numerator simplified above
   int absDen = Math.abs(den); //takes the absolute value of the denominator simplified above

   while (absDen != 0) {
      //This while loop works by saving the previous denominator, computing the remainder, and then moving the previous denominator up
      int previousDen = absDen; 
      absDen = absNum % absDen;
      absNum = previousDen;
   }
   int gcd = absNum; //When the loop ends, and the remainder is 0, the gcd becomes the numerator!

   num = num/gcd; //uses the identified GCD to simplify the ORIGINAL numerator and denominator
   den = den/gcd;


   int whole = num/den; //sets the whole number as the simplified num/den
   //the code casts it to int and this is okay because the decimal doesn't matter for the whole
   //for example, for num = 9, den = 4, 9/4 will simply have a whole number of 2. 

   //this variable is numerator of the simplified improper fraction
   //it uses the simplified numerator and denominator to figure out 
   int remainder = Math.abs(num % den); 
   
   if (remainder == 0){  //If there is no remainder, such as if the fraction was 4/2, then ONLY the value of whole will be returned
      return String.valueOf(whole);
   }

   if (whole != 0){ //if there IS a whole number, return the improper fraction as input
      return whole + " " + remainder + "/" + den; //formatting the answer ex: 2 
   }
   
   return num + "/" + den; //if those two conditions above are not needed, just return num over den
}


//This method parses the first fraction into a specific format that can be used later. 
   public static String processFraction1(String firstNum, String frac, String input, String wholeNum, String operator){
      if(firstNum.contains("_")){ //need to account for mixed numbers
         //if its a mixed number, the whole number is the one before the underscore
         wholeNum = firstNum.substring(0, firstNum.indexOf("_")); //sets wholeNum to from 0 to everything before _
         frac = firstNum.substring(firstNum.indexOf("_") + 1); // takes everything after _ + 1 since the start is inclusive, and we dont want the _ in our string
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
         input = "O" + operator + "W" + wholeNum + "N" + numerator + "D" + denominator; //changes the final value of input if secondNum is a fraction
         return input; //returns input!
   }

//This method parses the second fraction into a specific format that can be used later. 
   public static String processFraction2(String secondNum, String frac, String input, String wholeNum, String operator){
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
         input = "O" + operator + "W" + wholeNum + "N" + numerator + "D" + denominator; //changes the final value of input if secondNum is a fraction
         return input; //returns input!
   }
  
// This method simply identifies the numerator. 
   public static String processNumerator(String num){
      num = num.substring(0, num.indexOf('/')); //takes everything before the /, aka the numerator!
      return num;
   }

// This method simply identifies the denominator. 
   public static String processDenominator(String den){
      den = den.substring(den.indexOf("/") + 1); //takes everything before the /, aka the denominator
      return den;
   }

   // Returns a string that is helpful to the user about how
   // to use the program. These are instructions to the user.
   public static String provideHelp() {
      // TODO: Update this help text!
     
      String help = "Hi User, looks like you need some help. This is how to use the program: \n";
      help += "This program can add, subtract, multiply and divide whole numbers, mixed numbers, and improper fractions!\nHowever, this can be a bit tricky to use. Think of what you want to compute.\nThen, enter the first number, a space, the operator, and then the second number.\nIf you want to add improper fractions, use an underscore! For example, 1_1/2 + 1/2.\nEverything else is pretty intuitive. Fractions are seperated by a slash, and whole numbers simply need to be entered normally.\nHopefully this increased your understanding!";
      
      return help;
   }
}