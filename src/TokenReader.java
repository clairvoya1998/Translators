import java.io.*;
import java.util.*;

/**
 * Created by xw37 on 16/02/17.
 */

/**
 * Process the dictionary.
 */
public class TokenReader {

    // Store all the elements in the dictionary into an object arraylist.

    /**
     * Load the dictionary.
     * @return the dictionary
     * @throws IOException in case cannot find the file
     */
    public HashMap<Integer, Token> loadDictionary() throws IOException {
        HashMap<Integer, Token> myDictionary = new HashMap<>();
        ArrayList<Token> tokens = new ArrayList<>();
        //Scanner sc = new Scanner(new File("dictionary.csv"));
        File file = new File("dictionary.csv");
        //System.out.println(file.exists());
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] myString = sc.nextLine().split(",");
            //System.out.println(myString.toString());
            if (myString.length == 3) {
                Token tmpToken = new Token(myString[0], myString[1], myString[2]);
                tokens.add(tmpToken);
            }
        }

        // Use comparator to sort the dictionary in alphabetical order.
        Collections.sort(tokens, new Comparator<Token>() {
            @Override
            public int compare(Token token, Token t1) {
                return token.getEnglish().compareTo(t1.getEnglish());
            }
        });

        // Change arraylist to hashmap.
        for (int i = 0; i < tokens.size(); i++){
            myDictionary.put(i, tokens.get(i));
        }
        return myDictionary;
    }

    /**
     * Implement the dictionary.
     * @param token new word.
     * @throws IOException
     */
    public void writeToDictionary(Token token) throws  IOException{
        PrintWriter output = new PrintWriter(new FileWriter("dictionary.csv", true));
        String newLine = token.getEnglish() + "," + token.getSpanish() + "," + token.getGrammar();
        output.println("");
        output.print(newLine);
        output.close();
    }


}
