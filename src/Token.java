/**
 * Created by xw37 on 16/02/17.
 */

/**
 * Object Token, store the enlish, spainsh and grammar.
 */
public class Token{

    private String english;
    private String spanish;
    private String grammar;

    /**
     * Empty object.
     */
    public Token() {
    }

    /**
     * Object.
     * @param english English word
     * @param spanish Spanish word
     * @param grammar Grammar check
     */
    public Token(String english, String spanish, String grammar) {
        setEnglish(english);
        this.spanish = spanish;
        this.grammar = grammar;
    }

    /**
     * Getter of English word.
     * @return the word(English)
     */
    public String getEnglish() {
        return english;
    }

    /**
     * Setter of English word.
     * @param english get the word
     */
    public void setEnglish(String english) {
        this.english = english;
    }

    /**
     * Getter of Spanish word.
     * @return the word(Spanish)
     */
    public String getSpanish() {
        return spanish;
    }

    /**
     * Setter of Spanish word.
     * @param spanish get the word
     */
    public void setSpanish(String spanish) {
        this.spanish = spanish;
    }

    /**
     * Getter of word's grammar
     * @return The grammar
     */
    public String getGrammar() {
        return grammar;
    }

    /**
     * Setter of word's grammar.
     * @param grammar get the grammar
     */
    public void setGrammar(String grammar) {
        this.grammar = grammar;
    }

}
