import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Created by xw37 on 28/02/17.
 */

/**
 * Main class.
 */
public class Translation {

    /**
     * Call all the methods and divide how to input.
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {
//        try {
//
//        } catch(IndexOutOfBoundsException e) {
//            System.err.println("IndexOutOfBoundException:" + e.getMessage());
//        } catch (IOException e) {
//          System.err.println("Caught IOException:" + e.getMessage());
//        }
        /* In order to be compatible with the automatic checker
         * execute your program from the command line by calling the sentence.
         */
//        if (args.length < 2) {
//            System.out.println("Java WordAnalyser input.txt output.csv");
//            return;
//        }




        int check;
        do {
            System.out.println("Translate a file or a sentence? Enter 1 for file, 2 for sentence, 3 to add new word to the dictionary, 0 to quit the program.");
            check = EasyIn.getInt();
            if (check != 1 && check != 2 && check != 3 && check !=0) System.out.println("Invalid input, please try again.");

        } while(check != 1 && check != 2 && check != 3 && check !=0);

        // load the dictionary into the program
        TokenReader tokenReader = new TokenReader();
        HashMap<Integer, Token> myDictionary =  tokenReader.loadDictionary();
        if (check == 1) {
            System.out.println("Please put all your files into folder text!");
            ArrayList<String> sentences = files();
            ArrayList<String> newcontent = fileTranslate(myDictionary, sentences);
            PrintWriter writer = new PrintWriter("newText.csv");
            for (int i = 0; i < newcontent.size(); i++) {
                writer.println(newcontent.get(i));
            }
            writer.close();
            System.out.println("Check file newText.csv");

        }

        else if (check == 2) {
            System.out.println("Please enter the sentence.");
            String sentence = EasyIn.getString();
            String spanishLine = translateSentences(myDictionary, sentence);
            System.out.println(spanishLine);
        }

        else if (check == 3) {
            System.out.println("Add something to the dictionary? (enter yes to implement) ");
            String dicCheck = EasyIn.getString();
            if (dicCheck.equals("yes")) {
                boolean flag = true;
                Token newToken = new Token();
                do {
                    System.out.println("Enter English:");
                    String englishWord = EasyIn.getString();
                    System.out.println("Enter Spanish:");
                    String spanishWord = EasyIn.getString();
                    System.out.println("Enter Grammar:");
                    String grammarWord = EasyIn.getString();
                    System.out.println("You new input word is:" + englishWord + "," + spanishWord + "," + grammarWord + ". Is it right? (Press 1 for right, 0 for wrong)");
                    int checkRight = EasyIn.getInt();
                    if (checkRight == 1) {

                        newToken.setEnglish(englishWord);
                        newToken.setSpanish(spanishWord);
                        newToken.setGrammar(grammarWord);
                        TokenReader tokenReader1 = new TokenReader();
                        tokenReader1.writeToDictionary(newToken);
                        flag = false;
                    }
                } while (flag);
            }
            else {
                System.out.println("Quit the system.");
                System.exit(0);
            }
        }

        else if (check == 0) {
            System.out.println("Quit the system.");
            System.exit(0);
        }
    }

    /**
     * Grab all the files inside a folder.
     * @return a list of sentences
     * @throws IOException
     */
    public static ArrayList<String> files() throws IOException{
        File[] fileArray = new File("text").listFiles();
        ArrayList<String> sentences = new ArrayList<>();
        for (File f : fileArray) {
            // The input files must end with .txt.
            if (f.getName().endsWith(".txt")) {
                Scanner sc = new Scanner(f);
                sc.useDelimiter("\\s+");
                while (sc.hasNextLine()) {
                    String subline = sc.nextLine();
                    if (!subline.isEmpty()) {
                        sentences.add(subline);
                    }
                }
                sc.close();
            }
        }
        return sentences;
    }

    /**
     * Translate the sentence, call tokenization, rearrange.
     * @param myDic dictionary
     * @param sentence translated sentence,
     * @return
     */
    public static String translateSentences(HashMap<Integer, Token> myDic, String sentence){
        Tokenization tokenization = new Tokenization();
        HashMap<Integer, Token> myLine = tokenization.firstTranslation(myDic, sentence);
        Rearrange rearrange = new Rearrange();
        myLine = rearrange.rewrite(myLine);
        String newline = rearrange.myWord(myLine);
        return newline;
    }

    /**
     * Looping if multiple sentences.
     * @param myDic dictionary
     * @param content a list of translated sentences.
     * @return
     */
    public static ArrayList<String> fileTranslate(HashMap<Integer, Token> myDic, ArrayList<String> content) {
        ArrayList<String> newcontent = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            newcontent.add(translateSentences(myDic, content.get(i)));
        }
        return newcontent;
    }

}
