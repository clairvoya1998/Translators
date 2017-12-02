import java.util.ArrayList;
import java.util.HashMap;

public class Tokenization{

  /**
   * First step of translation.
   * @param tokens Dictionary
   * @param sentence needed to be translate
   */
  public HashMap<Integer, Token> firstTranslation(HashMap<Integer, Token> tokens, String sentence) {
    ArrayList<String> englishWords = new ArrayList<>();
    for (int i = 0; i < tokens.size(); i++) {
      englishWords.add(tokens.get(i).getEnglish());
    }
    // to check whether the word is in the dic
    HashMap<Integer, Token> words = new HashMap<>();
    String[] englishwords = sentence.split("\\s+");
    for (int i = 0; i < englishwords.length; i++) {
      Token subword = new Token();
      subword.setEnglish(englishwords[i]);
      words.put(i, subword);
    }


    for (int i = 0; i < words.size(); i++) {
      String word_english = words.get(i).getEnglish();
      word_english = (word_english.replaceAll("[^a-zA-Z]", "")).toLowerCase();
      if (word_english.equals("i")){
        word_english = "I";
        words.get(i).setEnglish("I");
      }

      if (englishWords.contains(word_english)) {
        int num = binarySearch2(word_english, tokens);
        words.get(i).setSpanish(tokens.get(num).getSpanish());
        words.get(i).setGrammar(tokens.get(num).getGrammar());
      }

      // Do the plural check
      else if (englishWords.contains(word_english.substring(0, word_english.length() - 1)) && word_english.charAt(word_english.length() - 1) == 's') {
        int num = binarySearch2(word_english, tokens);
        String newSpanish = tokens.get(num).getSpanish();
        if (newSpanish.charAt(newSpanish.length() - 1) == 'a' || newSpanish.charAt(newSpanish.length() - 1) == 'e' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'i' || newSpanish.charAt(newSpanish.length() - 1) == 'o' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'u') {
          words.get(i).setSpanish(tokens.get(num).getSpanish() + "s");
        }
        else words.get(i).setSpanish(newSpanish + "es");
        words.get(i).setGrammar(tokens.get(num).getGrammar());
      }

      else if (word_english.substring(word_english.length() - 3).equals("ies") && englishWords.contains(word_english.substring(0, word_english.length() - 3) + "y")) {
        word_english = word_english.substring(0, word_english.length() - 3) + "y";
        int num = binarySearch2(word_english, tokens);
        String newSpanish = tokens.get(num).getSpanish();
        if (newSpanish.charAt(newSpanish.length() - 1) == 'a' || newSpanish.charAt(newSpanish.length() - 1) == 'e' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'i' || newSpanish.charAt(newSpanish.length() - 1) == 'o' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'u') {
          words.get(i).setSpanish(tokens.get(num).getSpanish() + "s");
        }
        else words.get(i).setSpanish(newSpanish + "es");
        words.get(i).setGrammar(tokens.get(num).getGrammar());
      }

      else if (englishWords.contains(word_english.substring(0, word_english.length() - 2)) && word_english.substring(word_english.length() - 2).equals("es")) {
        int num = binarySearch2(word_english, tokens);
        String newSpanish = tokens.get(num).getSpanish();
        if (newSpanish.charAt(newSpanish.length() - 1) == 'a' || newSpanish.charAt(newSpanish.length() - 1) == 'e' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'i' || newSpanish.charAt(newSpanish.length() - 1) == 'o' ||
                newSpanish.charAt(newSpanish.length() - 1) == 'u') {
          words.get(i).setSpanish(tokens.get(num).getSpanish() + "s");
        }
        else words.get(i).setSpanish(newSpanish + "es");
        words.get(i).setGrammar(tokens.get(num).getGrammar());
      }
      else {
        words.get(i).setGrammar("not in the dictionary");
        words.get(i).setSpanish(word_english);
        System.out.println("Not in the dictionary");
      }
    }
    return words;
  }

  // one way to search the word.
  public int[] binarysearch0(String findString, HashMap<Integer, Token> tokens){
    int findascii = findString.charAt(0);
    boolean flag = false;
    int head = 0;
    int tail = tokens.size();
    int[] pinpoint = new int[2];
    int middle = tail;
    do {
      middle = (tail + head) / 2;

      int check = tokens.get(middle).getEnglish().charAt(0);

      if (check < findascii) head = middle + 1;
      if (check > findascii) tail = middle - 1;
      if (check == findascii) {
        flag = true;
        head = middle;
        tail = middle;
      }

    } while (!flag && head < tail);

    if (flag && middle > 0 && middle <= tokens.size()){
      // triger serial search
      boolean headFlag = false;
      boolean tailFlag = false;
      do {
        int newhead = head - 1;
        if (tokens.get(newhead).getEnglish().charAt(0) == findascii) {
          head = newhead;
        }
        else headFlag = true;
      } while (!headFlag);
      do {
        int newtail = tail + 1;
        if (newtail < tokens.size()) {
          if (tokens.get(newtail).getEnglish().charAt(0) == findascii)
            tail = newtail;
          else tailFlag = true;
        }
        else break;
      } while (!tailFlag);
    }
    if (flag) {
      pinpoint[0] = head;
      pinpoint[1] = tail;
    }
    else {
      pinpoint[0] = tokens.size() + 1;
      pinpoint[1] = -1;
    }
    return pinpoint;
  }



  public int[] binarySearch1(String findthestring, HashMap<Integer, Token> tokens) {
    int[] pinpoint = binarysearch0(findthestring, tokens);
    int head = pinpoint[0];
    int tail = pinpoint[1];

    if (findthestring.length() == 1 && tokens.get(pinpoint[0]).getEnglish().equals(findthestring)) {
      tail = head;
    }



    if (head < tail) {
      int findascii = findthestring.charAt(1);
      boolean flag = false;

      while (!flag && (head < tail)) {
        int middle = (tail + head) / 2;
        if (tokens.get(middle).getEnglish().length() > 1 && middle <= tokens.size()) {
          int check = tokens.get(middle).getEnglish().charAt(1);
          if (check < findascii) head = middle + 1;
          if (check > findascii) tail = middle - 1;
          if (check == findascii) {
            flag = true;
            head = middle;
            tail = middle;
          }
        }
        else head = head + 1;
      }



      if (flag && head > 0 && tail < tokens.size()){
        // triger serial search
        boolean headFlag = false;
        boolean tailFlag = false;

        do {
          int newhead = head - 1;
          if (newhead < tokens.size()) {
            if (tokens.get(newhead).getEnglish().length() > 1) {
              if (tokens.get(newhead).getEnglish().charAt(1) == findascii && tokens.get(newhead).getEnglish().charAt(0) == findthestring.charAt(0)) {
                head = newhead;
              } else break;
            } else break;
          }else break;
        } while (!headFlag);


        do {
          int newtail = tail + 1;
          if (newtail < tokens.size()) {
            if (tokens.get(newtail).getEnglish().charAt(1) == findascii && tokens.get(newtail).getEnglish().charAt(0) == findthestring.charAt(0))
              tail = newtail;
            else break;
          }else break;
        } while (!tailFlag);
      }

    }
      pinpoint[0] = head;
      pinpoint[1] = tail;
      return pinpoint;
    }

    // serial search
    public int binarySearch2(String findtheString, HashMap<Integer, Token> tokens){
      int[] pinpoint = binarySearch1(findtheString,tokens);
      int code = -1;
      if (pinpoint[0] == pinpoint[1]) code = pinpoint[0];
      else if (pinpoint[0] < pinpoint[1]){
        for (int i = pinpoint[0]; i <= pinpoint[1]; i++) {
          if (tokens.get(i).getEnglish().equals(findtheString)) {
            code = i;
            break;
          }
          else if (findtheString.charAt(findtheString.length() - 1) == 's') {
            String subString = findtheString.substring(0, findtheString.length() - 1);
            if (tokens.get(i).getEnglish().equals(subString)) {
              code  = i;
              break;
            }
          }

          else if (findtheString.length() > 4 && findtheString.substring(findtheString.length() - 3).equals("ses")) {
            String subString = findtheString.substring(0, findtheString.length() - 3);
            if (tokens.get(i).getEnglish().equals(subString)) {
              code = i;
              break;
            }
          }

        }
      }
      return code;
    }
}
