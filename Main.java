import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ContextFreeSpellChecker checker = new ContextFreeSpellChecker();

        System.out.println("Possible replacement word for rituialisn based LD:\n" + checker.LDCheck("rituialisn"));
        System.out.println("Possible replacement word for English based on LD:\n" + checker.LDCheck("English"));

        System.out.println("Possible replacement word for rituialisn: based on IBD\n" + checker.IndexBasedCheck("rituialisn"));
        System.out.println("Possible replacement word for English: based on IBD\n" + checker.IndexBasedCheck("English"));

        //System.out.println("LD test:\n" + checker.levenshteinDistance("English", "english") + "\n" + checker.levenshteinDistance("Nico", "Marco"));
    }
}
