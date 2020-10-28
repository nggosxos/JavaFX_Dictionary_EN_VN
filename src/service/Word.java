package service;

public class Word {
    private String word_target;
    private String word_explain;

    public Word() {
        this.word_target = this.word_explain = "";
        safe();
    }

    public Word(String word_target, String word_explain) {
        this.word_target = new String(word_target);
        this.word_explain = new String(word_explain);
        safe();
    }

    protected Word(Word other) {
        this.word_target = new String(other.word_target);
        this.word_explain = new String(other.word_explain);
        safe();
    }

    public String getWord_target() {
        return word_target;
    }

    protected String getWord_explain() {
        return word_explain;
    }

    protected void setWord_target(String word_target) {
        this.word_target = new String(word_target);
        safe();
    }

    protected void setWord_explain(String word_explain) {
        this.word_explain = new String(word_explain);
        safe();
    }

    private void safe() {
        word_target = word_target.toLowerCase();
    }

    protected void delete() {
        word_target = word_explain = "";
    }
}