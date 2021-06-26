import java.util.ArrayList;
import java.util.List;

public class TestCard {

    private String question;
    private List<Answer> answers;
    private List<String> code = new ArrayList<>();


    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public List<String> getCode() {
        return code;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void setCode(List<String> code) {
        this.code = code;
    }
}
