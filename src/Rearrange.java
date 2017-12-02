import java.util.HashMap;

/**
 * Created by xw37 on 03/03/17.
 */
public class Rearrange {


    /**
     * Method to drop the pronoun if the verb is in es/est** form.
     * @param changedToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_seven (HashMap <Integer, Token> changedToken) {
        for (int i = 0; i < changedToken.size(); i++) {
            if (changedToken.get(i).getGrammar().equals("verb")){
                if(changedToken.get(i).getSpanish().substring(0, 2).equals("es")) {
                    if(changedToken.get(i-1).getGrammar().equals("pronoun")){
                        for(int j = i - 1; j < changedToken.size() - 1; j++){
                            changedToken.put(j, changedToken.get(j + 1));
                        }

                        changedToken.remove(changedToken.size() - 1);


                    }
                }
            }
        }

        return changedToken;

    }

    /**
     * Method to change the pronoun if it is preceeded by a verb
     * @param changedToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_eight(HashMap<Integer, Token> changedToken) {
        for (int i = 0; i < changedToken.size() - 1; i++) {
            if (changedToken.get(i).getGrammar().equals("verb") && changedToken.get(i + 1).getGrammar().equals("pronoun") && !changedToken.get(i).getEnglish().equals("am") &&
                    !changedToken.get(i).getEnglish().equals("is") && !changedToken.get(i).getEnglish().equals("are")) {
                Token tmp_pronoun = changedToken.get(i + 1);
                if (tmp_pronoun.getEnglish().equals("him")) {
                    tmp_pronoun.setSpanish("lo");
                }
                if (tmp_pronoun.getEnglish().equals("her")) {
                    tmp_pronoun.setSpanish("la");
                }
                if (tmp_pronoun.getEnglish().equals("you")) {
                    tmp_pronoun.setSpanish("te");
                }
                if (tmp_pronoun.getEnglish().equals("me")) {
                    tmp_pronoun.setSpanish("me");
                }
                for (int j = i + 1; j > 0; j--) {
                    changedToken.put(j, changedToken.get(j - 1));
                }
                changedToken.put(0, tmp_pronoun);
            }
        }
        return changedToken;
    }

    /**
     * Method to rearrange sentence if there is negation
     * if no or not is present negation will switch places with the preceeding verb.
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_nine(HashMap<Integer, Token> inputToken) {
        for (int i = 1; i < inputToken.size(); i++) {
            if (inputToken.get(i).getGrammar().equals("negation")) {
                if (inputToken.get(i - 1).getGrammar().equals("verb")) {
                    Token tmp_token = inputToken.get(i);
                    inputToken.put(i, inputToken.get(i - 1));
                    inputToken.put(i - 1, tmp_token);
                }
            }
        }
        return inputToken;
    }

    /**
     * Method to replace prepositions with correct form.
     * @param changedToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rule_ten(HashMap<Integer, Token> changedToken) {
        for (int i = 0; i < changedToken.size() - 1; i++) {
            if (changedToken.get(i).getGrammar().equals("preposition")) {
                if (changedToken.get(i + 1).getSpanish().equals("tú")) {
                    changedToken.get(i + 1).setSpanish("ti");
                }
            }
            if (i < changedToken.size()) {
                if (changedToken.get(i).getEnglish().equals("with")) {
                    if (changedToken.get(i + 1).getEnglish().equals("me")) {
                        changedToken.get(i).setEnglish("with me");
                        changedToken.get(i).setSpanish("conmigo");
                        changedToken.remove(i + 1);
                        if (i + 1 < changedToken.size()) {
                            for (int j = i + 1; j < changedToken.size() - 1; j++) {
                                changedToken.put(j, changedToken.get(j + 1));
                            }
                        }
                    }
                    else if (changedToken.get(i + 1).getEnglish().equals("you")) {
                        changedToken.get(i).setEnglish("with you");
                        changedToken.get(i).setSpanish("contigo");
                        changedToken.remove(i + 1);
                        if (i + 1 < changedToken.size()) {
                            for (int j = i + 1; j < changedToken.size() - 1; j++) {
                                changedToken.put(j, changedToken.get(j + 1));
                            }
                        }
                    }
                }
            }
        }
        return changedToken;
    }

    /**
     * Method to apply rules seven through ten to the HashMap.
     * @param inputToken pass the words
     * @return
     */
    public HashMap<Integer, Token> rewrite(HashMap<Integer, Token> inputToken) {
        localRewrite localrewrite = new localRewrite();
        HashMap<Integer, Token> changedToken = localrewrite.translateWords(inputToken);
        changedToken = rule_seven(changedToken);
        changedToken = rule_eight(changedToken);
        changedToken = rule_nine(changedToken);
        changedToken = rule_ten(changedToken);
        return changedToken;
    }

    /**
     * Method to print altered spanish words in a translated sentence.
     * @param changedToken pass the words
     * @return
     */
    public String myWord(HashMap<Integer, Token> changedToken) {
        String newSentence = changedToken.get(0).getSpanish();
        if (changedToken.size() > 2) {
            for (int i = 1; i < changedToken.size(); i++) {
                newSentence = newSentence + " " + changedToken.get(i).getSpanish();
            }
        }
        else if (changedToken.size() == 2){
            newSentence = changedToken.get(0).getSpanish() + " " + changedToken.get(1).getSpanish();
        }
        newSentence = newSentence + ".";
        newSentence = newSentence.substring(0 , 1).toUpperCase() + newSentence.substring(1);
    return newSentence;
    }
}
