//created by jmk9
import java.util.HashMap;

/**
 * Rule 1 - 6.
 */
public class localRewrite {


    /**
     * Method to check for english noun's plurality, returns true of noun is plural.
     * @param inputTokens pass the words
     * @param i which word
     * @return
     */
    public boolean pluralNounCheck(HashMap<Integer, Token> inputTokens, int i) {
        if (inputTokens.get(i).getEnglish().charAt(inputTokens.get(i).getEnglish().length() - 1) == 's') {
            return true;
        } else return false;
    }

    /**
     * Combine all the methods in the class step by step.
     * @param inputTokens pass the words
     * @return
     */
     public HashMap<Integer, Token> translateWords(HashMap<Integer, Token> inputTokens) {
         HashMap<Integer, Token> changedToken = possessivecheck(inputTokens);
         changedToken = extension(changedToken);
         changedToken = rule_three(changedToken);
         changedToken = rule_one(changedToken);
         changedToken = rule_two(changedToken);
         changedToken = rule_four(changedToken);
         changedToken = rule_five(changedToken);
         changedToken = rule_six(changedToken);
         return changedToken;
     }

    /**
     * Check whether "her" is possessive or a verb.
     * @param inputTokens pass the words
     * @return
     */
    public HashMap<Integer, Token> possessivecheck(HashMap<Integer, Token> inputTokens){
        for (int i = 0; i < inputTokens.size(); i++) {
            if (inputTokens.get(i).getGrammar().equals("noun") && i > 0) {
                if (inputTokens.get(i - 1).getEnglish().equals("her")) {
                    inputTokens.get(i - 1).setGrammar("possessive");
                    inputTokens.get(i - 1).setSpanish("su");
                }
            }
        }
        return inputTokens;
    }


    /**
     * Method determines gender of spanish noun.
     * @param inputTokens pass the words
     * @param i which one
     * @return
     */
    public boolean genderRules(HashMap<Integer, Token> inputTokens, int i) {
        String myNoun = inputTokens.get(i).getSpanish();
        if (pluralNounCheck(inputTokens, i))
            myNoun = myNoun.substring(1, myNoun.length() - 1);
        int nounLength = myNoun.length();
        if (myNoun.charAt(nounLength - 1) == 'a' ||
                myNoun.charAt(nounLength - 1) == 'd' ||
                myNoun.charAt(nounLength - 1) == 'z' ||
                myNoun.substring(nounLength - 3).equals("ión") ||
                myNoun.substring(nounLength - 2).equals("as") ||
                myNoun.substring(nounLength - 3).equals("des")) {
            if (myNoun.substring(nounLength - 3) == "ma") {
                return true;

            }
            else return false;
        }
        else{
            return true; //true means male noun
        }
    }




    /**
     * Method changes spanish adjective based on noun's plurality and gender.
     * @param inputTokens pass the words
     * @return spanishAdjective
     */
    public HashMap<Integer, Token> rule_one(HashMap<Integer, Token> inputTokens) {

        for (int i = 0; i < inputTokens.size(); i++) {
            if (inputTokens.get(i).getGrammar().equals("noun")) {
                boolean masculine = genderRules(inputTokens, i);
                if (masculine) {
                    if (i > 0) {
                        if (inputTokens.get(i - 1).getEnglish().equals("the") || inputTokens.get(i - 1).getEnglish().equals("The")) {
                            inputTokens.get(i - 1).setSpanish("el");
                            if (pluralNounCheck(inputTokens, i))
                                inputTokens.get(i - 1).setSpanish("los");
                        } else if (inputTokens.get(i - 1).getEnglish().equals("a") || inputTokens.get(i).getEnglish().equals("an")) {
                            inputTokens.get(i - 1).setSpanish("un");
                            if (pluralNounCheck(inputTokens, i))
                                inputTokens.get(i - 1).setSpanish("unos");
                        }
                    }
                } else {
                    if (i > 0) {
                        if (inputTokens.get(i - 1).getEnglish().equals("the") || inputTokens.get(i - 1).getEnglish().equals("The")) {
                            inputTokens.get(i - 1).setSpanish("la");
                            if (pluralNounCheck(inputTokens, i))
                                inputTokens.get(i - 1).setSpanish("las");
                        } else if (inputTokens.get(i - 1).getEnglish().equals("a") || inputTokens.get(i).getEnglish().equals("an")) {
                            inputTokens.get(i - 1).setSpanish("una");
                            if (pluralNounCheck(inputTokens, i))
                                inputTokens.get(i - 1).setSpanish("unas");
                        }
                    }
                    for (int j = 0; j < inputTokens.size(); j++) {
                        if (inputTokens.get(j).getGrammar().equals("adjective") && inputTokens.get(j).getSpanish().charAt(inputTokens.get(j).getSpanish().length() - 1) == 'o') {
                            String myString = inputTokens.get(j).getSpanish();
                            String subString = myString.substring(0, myString.length() - 1);
                            myString = subString + 'a';
                            inputTokens.get(j).setSpanish(myString);
                        }
                    }
                }

            }
        }

        return inputTokens;
    }

    /**
     * Rule two, the article/adjective must agree with the noun in number.
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_two(HashMap<Integer, Token> inputToken) {
        for (int i = 0; i < inputToken.size(); i++) {
            if (inputToken.get(i).getGrammar().equals("noun")) {
                if (pluralNounCheck(inputToken, i)) {
                    for (int j = 0; j < inputToken.size(); j++) {
                        if (inputToken.get(j).getGrammar().equals("adjective")) {
                            String mySpanish = inputToken.get(j).getSpanish();
                            if (mySpanish.charAt(mySpanish.length() - 1) == 'a' || mySpanish.charAt(mySpanish.length() - 1) == 'e' ||
                                    mySpanish.charAt(mySpanish.length() - 1) == 'i' || mySpanish.charAt(mySpanish.length() - 1) == 'o' ||
                                    mySpanish.charAt(mySpanish.length() - 1) == 'u') {
                                mySpanish = mySpanish + 's';

                            } else{
                                mySpanish = mySpanish + "es";
                            }
                            inputToken.get(j).setSpanish(mySpanish);
                        }

                    }
                    break;
                }
            }
        }
        return inputToken;
    }

    /**
     * For instance, in Spanish adjective always follows the noun.
     * @param inputToken Get the sentence.
     * @return Pass the hashmap.
     */
    public HashMap<Integer, Token> rule_three(HashMap<Integer, Token> inputToken) {
        for (int i = 0; i < inputToken.size() - 1; i++) {
            if (inputToken.get(i).getGrammar().equals("adjective")) {
                if (inputToken.get(i + 1).getGrammar().equals("noun")) {
                    Token tmp_token = inputToken.get(i);
                    inputToken.put(i, inputToken.get(i + 1));
                    inputToken.put(i + 1, tmp_token);
                }
            }
        }
        return inputToken;
    }

    /**
     * Possessives must agree with the number.
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_four(HashMap<Integer, Token> inputToken) {
        for (int i = 0; i < inputToken.size(); i++) {
            if(inputToken.get(i).getGrammar().equals("noun")) {
                if (pluralNounCheck(inputToken, i) && i > 0) {
                        if (inputToken.get(i - 1).getGrammar().equals("possessive")) {
                            inputToken.get(i - 1).setSpanish(inputToken.get(i - 1).getSpanish() + "s");

                        }
                    }
                }
            }
        return inputToken;
        }


    /**
     * Spanish differentiates “are” based on the person.
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_five(HashMap<Integer, Token> inputToken) {
        for (int i = 0; i < inputToken.size() - 1; i++) {
            if (inputToken.get(i).getEnglish().equals("i") && inputToken.get(i + 1).getEnglish().equals("am")) {
                inputToken.get(i).setSpanish("yo");
                inputToken.get(i + 1).setSpanish("soy");
            }
            if (inputToken.get(i).getEnglish().equals("he") && inputToken.get(i + 1).getEnglish().equals("is")) {
                inputToken.get(i).setSpanish("él");
                inputToken.get(i + 1).setSpanish("es");
            }
            if (inputToken.get(i).getEnglish().equals("she") && inputToken.get(i + 1).getEnglish().equals("is")) {
                inputToken.get(i).setSpanish("ello");
                inputToken.get(i + 1).setSpanish("es");
            }
            if (inputToken.get(i).getEnglish().equals("it") && inputToken.get(i + 1).getEnglish().equals("is")) {
                inputToken.get(i).setSpanish("eso");
                inputToken.get(i + 1).setSpanish("es");
            }
            if (inputToken.get(i).getEnglish().equals("you") && inputToken.get(i + 1).getEnglish().equals("are")) {
                inputToken.get(i).setSpanish("tú");
                inputToken.get(i + 1).setSpanish("eres");
            }
            if (inputToken.get(i).getEnglish().equals("we") && inputToken.get(i + 1).getEnglish().equals("are")) {
                inputToken.get(i).setSpanish("nosotros");
                inputToken.get(i + 1).setSpanish("somos");
            }
            if (inputToken.get(i).getEnglish().equals("they") && inputToken.get(i + 1).getEnglish().equals("are")) {
                inputToken.get(i).setSpanish("ellos");
                inputToken.get(i + 1).setSpanish("son");
            }
        }


        return inputToken;
    }

    /**
     * Soy/eres/somos/es/son becomes “est**” based on the noun/pronoun when followed by a verb or “with”
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_six(HashMap<Integer, Token> inputToken) {
        for (int i = 0; i < inputToken.size(); i++) {
            inputToken = rule6(inputToken, i);
        }
        return inputToken;
    }

    //method to extend the present tense for verbs
    public HashMap<Integer, Token> extension(HashMap<Integer, Token> inputToken){
      for (int i = 1; i < inputToken.size(); i++) {
        String myVerb = inputToken.get(i).getSpanish();
        int myLength = inputToken.get(i).getEnglish().length();
          myVerb = myVerb.substring(0, myVerb.length() - 2);
          if(inputToken.get(i).getGrammar().equals("verb")){
            if(myLength > 3 &&!inputToken.get(i).getEnglish().substring(myLength - 3).equals("ing")){
                if(inputToken.get(i-1).getEnglish().equals("I")){
                    inputToken.get(i).setSpanish(myVerb + "o");
                }
                if(inputToken.get(i-1).getEnglish().equals("you")){
                    inputToken.get(i).setSpanish(myVerb + "as");
                }
                if(inputToken.get(i-1).getEnglish().equals("she") || inputToken.get(i-1).getEnglish().equals("he")){
                    myVerb = myVerb.substring(0, myVerb.length() - 2);
                    inputToken.get(i).setSpanish(myVerb + "a");
                }
            }
          }
      }
      return inputToken;
    }
    /**
     * rule 6
     * @param inputToken pass the words
     * @param i which one
     * @return
     */
    public HashMap<Integer, Token> rule6(HashMap<Integer, Token> inputToken, int i) {
        if (inputToken.get(i).getGrammar().equals("verb") && i < inputToken.size()) {
            if (inputToken.get(i).getEnglish().equals("am") || inputToken.get(i).getEnglish().equals("are") || inputToken.get(i).getEnglish().equals("is")) {
                if (inputToken.get(i + 1).getGrammar().equals("verb") || inputToken.get(i + 1).getEnglish().equals("with") || inputToken.get(i + 1).getGrammar().equals("negation") &&
                        inputToken.get(i + 2).getGrammar().equals("verb")) {

                    if (i > 0) {
                        if (inputToken.get(i - 1).getGrammar().equals("noun") || inputToken.get(i - 1).getGrammar().equals("pronoun")) {
                            String spanishAuxiliary = inputToken.get(i).getSpanish();


                            if (inputToken.get(i).getEnglish().equals("am") && inputToken.get(i - 1).getEnglish().equals("I")) {
                                spanishAuxiliary = "estoy";
                            }

                            if (inputToken.get(i).getEnglish().equals("is")) {
                                spanishAuxiliary = "está";
                            }

                            if (inputToken.get(i).getEnglish().equals("are")) {
                                if (inputToken.get(i - 1).getEnglish().equals("you")) {
                                    spanishAuxiliary = "estás";
                                } else if (inputToken.get(i - 1).getEnglish().equals("we")) {
                                    spanishAuxiliary = "estamos";
                                } else spanishAuxiliary = "están";
                            }

                            inputToken.get(i).setSpanish(spanishAuxiliary);
                        }
                    }
                }
            }
        }
        return inputToken;
    }
}
