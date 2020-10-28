package service;

import  java.util.ArrayList;

public class Dictionary {
    protected static final int Inf = 1000000000;
    private final Node head = new Node();

    private Node NodeofTarget(final String target) {
        Node p = head;
        for (int i = 0; i < target.length(); ++i)
            p = p.jumpto(target.charAt(i));
        return p;
    }

    protected void setWord(Word word) {
        Node p = NodeofTarget(word.getWord_target());
        p.setWord(word);
    }

    protected Word getWord(String target) {
        Node p = NodeofTarget(target);
        return p.getWord();
    }

    private void WordFromNode(Node p, ArrayList<Word> res, int limit) {
        if (p.hasWord())
            res.add(p.getWord());
        if (res.size() > limit)
            return;
        if (p.OK())
            for (char c = 'a'; c <= 'z' + 1; ++c)
                WordFromNode(p.jumpto(c), res, limit);
    }

    protected ArrayList<Word> getallWord() {
        return getallWordFromTarget("", Inf);
    }

    protected ArrayList<Word> getallWordFromTarget(String target, int limit) {
        ArrayList<Word> res = new ArrayList<Word>();
        WordFromNode(NodeofTarget(target), res, limit);
        return res;
    }
}