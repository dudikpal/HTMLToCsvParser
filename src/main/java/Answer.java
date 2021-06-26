public class Answer {

    private String text;
    private boolean isRightAnswer;

    public Answer(String text, boolean isRightAnswer) {
        this.text = text;
        this.isRightAnswer = isRightAnswer;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isRightAnswer() {
        return isRightAnswer;
    }

    public void setRightAnswer(boolean rightAnswer) {
        isRightAnswer = rightAnswer;
    }
}
