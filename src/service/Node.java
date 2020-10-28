package service;

import java.util.ArrayList;

public class Node {
    private ArrayList<Node> edge = new ArrayList<Node>();
    private Word word = new Word();

    protected Word getWord() {
        return word;
    }

    protected void setWord(Word word) {
        this.word = new Word(word);
    }

    private void safe() {
        if (!OK()) {
            for (char c = 'a'; c <= 'z' + 1; ++c)
                edge.add(new Node());
            edge.add(new Node());
        }
    }

    protected boolean OK() {
        return !edge.isEmpty();
    }

    protected Node jumpto(char a) {
        safe();
        if (a < 'a' || 'z' < a)
            a = 'z' + 1;
        return edge.get(a - 'a');
    }

    protected boolean hasWord() {
        return !word.getWord_target().equals("");
    }
}
