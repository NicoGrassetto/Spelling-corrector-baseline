import java.io.*;
import java.util.Arrays;

/** Create a context-free spell checker.
 * @author Nico Grassetto
 * @version 1.0
 * @since 1.0
 */
public class ContextFreeSpellChecker {

    final private Trie TRIE = new Trie();
    private FileReader fileReader;
    private double penalty = 0.5;//TODO double check
    private String keyboardLayoutPath;
    private KeyboardParser keyboardParser;
    private String dictionaryTextFilePath = "words_alpha.txt";

    /**
     * Construct the spell checker initialiser.
     * @param dictionaryTextFile A text file containing a dictionary of words. Make sure to adapt the keyboard layout accordingly. The used format is a word on each line.
     * @param keyboardLayoutPath A text file containing your keyboard layout. Make sure the layout fits the English layout's format provided alongside this code.
     */
    public ContextFreeSpellChecker(String dictionaryTextFile, String keyboardLayoutPath) {
        // Initialise the keyboard layout that will be used for similarity matching.
        this.dictionaryTextFilePath = dictionaryTextFile;
        this.keyboardLayoutPath = keyboardLayoutPath;
        this.keyboardParser = new KeyboardParser(this.keyboardLayoutPath);

        // We load the file in and then parse it to build the dictionary trie.
        try {
            this.fileReader = new FileReader(dictionaryTextFile);
            BufferedReader  in = new BufferedReader(this.fileReader);
            while (in.ready()) {
                //System.out.println(in.readLine());
                this.TRIE.insert(in.readLine());
            }
            //System.out.println("Words successfully inserted");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during trie insertion.");
        }
    }

    /**
     * Construct the spell checker initialiser. Be aware that by default the keyboard layout that will be used for similarity matching is QWERTY and the dictionary's language is English.
     */
    public ContextFreeSpellChecker() {
        // Initialise the keyboard layout that will be used for similarity matching.
        this.keyboardLayoutPath = "qwerty.txt";
        this.keyboardParser = new KeyboardParser(this.keyboardLayoutPath);

        // We load the file in and then parse it to build the dictionary trie.
        try {
            this.fileReader = new FileReader(this.dictionaryTextFilePath);
            BufferedReader  in = new BufferedReader(this.fileReader);
            while (in.ready()) {
                //System.out.println(in.readLine());
                this.TRIE.insert(in.readLine());
            }
            //System.out.println("Words successfully inserted");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during trie insertion.");
        }
    }

    /**
     * Check if a word is correctly spelled by computing its similarity to each word in the dictionary using Levenshtein's distance.
     * @param word An English word.
     * @return Return the closest word given LD and an empty string in case an error occurred.
     */
    public String LDCheck(String word) {
        String bestMatch = "";
        double bestMatchNumber = Integer.MAX_VALUE;

        // We go through the dictionary text file which takes O(n) (might take a few seconds in the worst case)
        if (this.TRIE.containsNode(word.toLowerCase())){
            return word;
        }
        try {
            this.fileReader = new FileReader(this.dictionaryTextFilePath);
            BufferedReader  in = new BufferedReader(this.fileReader);
            while (in.ready()) {
                if(levenshteinDistance(word.toLowerCase(), in.readLine()) < bestMatchNumber) {
                    bestMatch = in.readLine();
                    bestMatchNumber = levenshteinDistance(word.toLowerCase(), in.readLine());
                }
            }
            return bestMatch;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during search");
            return "error";
        }
    }

    private double indexBasedDistance(String word1, String word2) {
        double distance = 0;

        String shorterWord = "";
        String longerWord = "";
        if (word1.length() >= word2.length()) {
            longerWord = word1;
            shorterWord = word2;
        } else {
            longerWord = word2;
            shorterWord = word1;
        }

        for (int i = 0; i < longerWord.length(); i++) {
            if (i < shorterWord.length()) {
                if (!(longerWord.charAt(i) == shorterWord.charAt(i))) {
                    if (this.keyboardParser.getKeyboard().isNeighbour(longerWord.charAt(i) + "", shorterWord.charAt(i) + "")) {
                        distance += 0.5;
                    } else {
                        distance++;
                    }
                }
            } else {
                distance+= longerWord.length() - i;
            }
        }
        return distance;
    }
    /**
     * Check if a word is correctly spelled by computing its similarity to each word in the dictionary using an index-based similarity.
     * @param word An English word.
     */
    public String IndexBasedCheck(String word) {
        /* *
         * Rule #1 If character is neighbour of the character for dictionary word the probability of matching should be less impacted. (-0.5)
         * Rule #2 If character matches probability should be impacted in a positive way. (+0.0)
         * Rule #3 If character is NOT neighbour of the character for dictionary word the probability of matching should be impacted. (-1.0)
         *
         * */
        String bestMatch = "";
        double bestMatchNumber = Integer.MAX_VALUE;

        // We go through the dictionary text file which takes O(n) (might take a few seconds in the worst case)
        if (this.TRIE.containsNode(word.toLowerCase())){
            return word;
        }
        try {
            this.fileReader = new FileReader(this.dictionaryTextFilePath);
            BufferedReader  in = new BufferedReader(this.fileReader);
            while (in.ready()) {
                if(indexBasedDistance(word.toLowerCase(), in.readLine()) < bestMatchNumber) {
                    bestMatch = in.readLine();
                    bestMatchNumber = indexBasedDistance(word.toLowerCase(), in.readLine());
                }
            }
            return bestMatch;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred during search");
            return "error";
        }
    }

    /**
     * Utility function that outputs the LD of two words.
     * @param word1 A word
     * @param word2 A word
     * @return The LD distance of the words.
     */
    private double levenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(dp[i - 1][j - 1] + costOfSubstitution(word1.charAt(i - 1), word2.charAt(j - 1)), dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }
        return dp[word1.length()][word2.length()];
    }

    /**
     * Utility function.
     * @param a A character
     * @param b A character
     * @return Either 1 or 0 depending on if a == b
     */
    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    /**
     * Utility function.
     */
    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }
}
