package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ContextFreeSpellchecker implements Spellchecker {
    private Trie trie = new Trie();
    private FileReader fileReader;
    public ContextFreeSpellchecker(String dicionaryTextFile) {
        try {
            this.fileReader = new FileReader(dicionaryTextFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("There was a problem during parsing...");
        }
        parse();
    }

    public ContextFreeSpellchecker() {
        try {
            this.fileReader = new FileReader("/Users/nicograssetto/Desktop/Baseline Spellchecker/src/com/company/words_alpha.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("There was a problem during parsing...");
        }
        parse();
    }

    private void parse() {
        try {
            BufferedReader in = new BufferedReader(this.fileReader);
            while (in.) {
                trie.insert(scanner.next());
                System.out.println(scanner.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("There was a problem during parsing...");
        }
    }

    @Override
    public String check() {
        return null;
    }
}
