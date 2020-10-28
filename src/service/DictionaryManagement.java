package service;

import java.io.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;

import static service.UsefulFunction.projectPath;

public class DictionaryManagement {
    public static Dictionary data = new Dictionary();
    private static String inputfile = projectPath + "/src/db/dict/av.txt";
    private static String outputfile = projectPath + "/src/db/dict/av.txt";

    public static String getInputfile() {
        return inputfile;
    }

    public static String getOutputfile() {
        return outputfile;
    }

    public static void setInputfile(String inputfile1) {
        inputfile = new String(inputfile1);
    }

    public static void setOutputfile(String outputfile1) {
        outputfile = new String(outputfile1);
    }

    /** abcxyz. */
    public static void insertFromCommandline() {
        Scanner input = new Scanner(System.in);
        Word newWord = new Word();
        newWord.setWord_target(input.nextLine());
        if (newWord.getWord_target().equals(""))
            return;
        newWord.setWord_explain(input.nextLine());
        if (newWord.getWord_explain().equals(""))
            return;
        data.setWord(newWord);
    }

    public static void insertWord(Word word) {
        data.setWord(word);
    }

    /** Version 2. */
    public static void dictionaryImportFromFile() throws Exception {
        File file = new File(inputfile);
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String[] line = input.nextLine().split("##");
            Word word = new Word();
            word.setWord_target(line[0]);
            word.setWord_explain(line[1]);
            data.setWord(word);
        }
        input.close();
    }

    public static void dictionaryExportToFile() throws Exception {
        ArrayList<Word> all = data.getallWord();
        PrintWriter printer = new PrintWriter(new FileWriter(outputfile));
        for (Word word : all)
            if (!word.getWord_explain().equals("")) {
                printer.print(word.getWord_target() + "##" + word.getWord_explain());
            }
        printer.close();
    }

    /** Version 2. */
    public static String dictionaryLookup(String target) {
        String explain = data.getWord(target).getWord_explain();
        if (explain.equals(""))
            explain = "No data";
        //System.out.print(explain + "\n");
        return explain;
    }

    public static void remove(String target) {
        data.getWord(target).delete();
    }

    /** Version 3. */
    public static void editWord(String target, String newExplain) {
        data.getWord(target).setWord_explain(newExplain);
    }

    public static ArrayList<Word> dictionarySearcher(String target, int limit) {
        return data.getallWordFromTarget(target, limit);
    }
}