// Paul Lam
// Section AG, Neel jog
// A5 GrammarSolver
// GrammarSolver takes in grammar in the format of BNF and creates elements of
// gramar out of it.
import java.io.*;
import java.util.*;

public class GrammarSolver {

   private Map<String, String[]> grammarMap;

   // Purpose: Stores the given grammar for later use in generating grammar.
   // Pre: Grammar must not be empty.
   // There must not be two of the same nonterminals in grammar.
   // If conditions are violated, throw an IllegalArgumentException.
   // Post: Stores the given list of grammar for generating grammar later on.
   // Parameter: Grammar = The list of grammar use to generate grammar.
   public GrammarSolver(List <String> grammar) {
      if (grammar.size() == 0) {
         throw new IllegalArgumentException();
      }
      grammarMap = new TreeMap<> ();
      for (String str: grammar) {
         String[] arrOfStr = str.split("::=");
         String[] arrOfStr2 = arrOfStr[1].split("[|]");
         if (!grammarMap.containsKey(arrOfStr[0])) {
            grammarMap.put(arrOfStr[0], new String[arrOfStr2.length]);
         } else {
            throw new IllegalArgumentException();
         }
         for (int i = 0; i < arrOfStr2.length; i++) {
            String test = arrOfStr2[i];
            grammarMap.get(arrOfStr[0])[i] = test;
         }
      }
   }

   // Purpose: Checks to see if the given symbol is a non terminal.
   // Returns true if symbol is a non terminal and false if not.
   // Pre: No pre conditions.
   // Post: Returns true if the string is a non terminal.
   // Returns false if it is not.
   // Parameter: Symbol = the given string by the user to check if it
   // is a non terminal.
   public boolean grammarContains(String symbol) {
      if (grammarMap.containsKey(symbol)) {
         return true;
      }
      return false;
   }

   // Purpose: Randomly generates the given time of occurences of the given symbol,
   // and returns it in an array of strings.
   // Pre: The given symbol must be a part of grammar. The given time must be greater
   // than 0. If conditions are violated, throws an IllegalArgumentException.
   // Post: Returns the given number of occurences of the given symbol in an array
   // of strings.
   // Parameter: Symbol = the given string/nonterminal by the user.
   // Times = the number of occurences of the nonterminal.
   public String[] generate(String symbol, int times) {
      if (!grammarMap.containsKey(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] str = new String[times];
      for (int i = 0; i < times; i++) {
         String sentence = grammarGen(symbol);
         str[i] = sentence;
      }
      return str;
   }

   // Purpose: Generates grammar based off of the given symbol.
   // Pre: The given symbol must be a part of grammar.
   // Post: Returns the generated grammar based off of the given symbol
   // by the user.
   // Parameter: Symbol = the given string/nonterminal by the user.
   private String grammarGen(String symbol) {
      int randNum = randNum(grammarMap.get(symbol).length);
      String testCase = grammarMap.get(symbol)[randNum];
      String finalString = "";
      String[] symbolSplit = testCase.split("[ \t]+");
      for (String s: symbolSplit) {
         if (!grammarMap.containsKey(s)) {
            finalString += s + " ";
         } else {
            String temp = grammarGen(s) + " ";
            finalString += temp;
         }
      }
      return finalString.trim();
   }

   // Purpose: Gives the list of various grammar nonterminal symbols.
   // Pre: No pre conditions.
   // Post: Returns the list of all nonterminal symbols from grammar.
   // Parameter: No parameters.
   public String getSymbols() {
      return grammarMap.keySet().toString();
   }

   // Purpose: Generates a random number.
   // Pre: No pre conditions.
   // Post: Returns the random number.
   // Parameter: Num = the
   private int randNum(int num) {
      Random randomNum = new Random();
      return randomNum.nextInt(num);
   }
}