import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestCardsRepo {

    private List<TestCard> testCards = new ArrayList<>();
    private TestCard testCard;
    private final String SEPARATOR = "-----";


    public List<TestCard> createTestCardsFromHTML(String file) {

        InputStream is = this.getClass().getResourceAsStream(file);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))){

            String line;

            while ((line = br.readLine()) != null) {
                testCard = new TestCard();
                addQuestions(br, line);
                line = br.readLine(); // <ul> sorra lép
                line = addCodeIfExist(br, line);
                addAnswers(br, line);
                br.readLine(); // </ul> -re lép
                testCards.add(testCard);
            }

            return testCards;
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file, ", ioe);
        }
    }


    public void saveTestCardsToCsv(String outputCsvFileName) {

        try (BufferedWriter bw = Files.newBufferedWriter(Path.of(outputCsvFileName))){

            for (TestCard card : testCards) {
                bw.write(card.getQuestion());
                bw.write(SEPARATOR);
                bw.write(indexOfRightAnswer(card.getAnswers()));
                bw.write(SEPARATOR);
                bw.write(mapAnswers(card.getAnswers()));
                if (!card.getCode().isEmpty()) {
                    bw.write(mapCode(card.getCode()));
                }
                bw.newLine();
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot write file, ", ioe);
        }
    }


    private void addAnswers(BufferedReader br, String line) throws IOException {
        if (line.contains("<ul>")) {
            List<Answer> answers = new ArrayList<>();

            for (int i = 0; i < 4; i++) {
                line = br.readLine();

                if (line.contains("checked")) {
                    answers.add(new Answer(answerParser(br.readLine()), true));
                } else {
                    answers.add(new Answer(answerParser(line), false));
                }
            }
            testCard.setAnswers(answers);
        }
    }

    private String addCodeIfExist(BufferedReader br, String line) throws IOException {

        if (line.contains("sourceCode")) {
            List<String> codeLines = new ArrayList<>();

            while (!line.contains("<ul>")) {
                codeLines.add(quotesDuplicator(line));
                line = br.readLine();
            }
            testCard.setCode(codeLines);
        }
        return line;
    }

    private void addQuestions(BufferedReader br, String line) throws IOException {

        if (line.contains("<h1")) {
            String question = questionParser(br.readLine());
            testCard.setQuestion(question);
        }
    }


    private String quotesDuplicator(String line) {
        return line.replaceAll("\\\"", "\\\"\\\"");
    }


    private String answerParser(String input) {
        Pattern p = Pattern.compile("[^<li>]\\w+.+(?=<\\/li)");
        Matcher m = p.matcher(input);
        m.find();
        return m.group();
    }


    private String questionParser(String input) {
        Pattern p = Pattern.compile("(?<=<p>).+(?=<\\/p>)");
        Matcher m = p.matcher(input);
        m.find();
        return m.group();
    }


    public List<TestCard> getTestCards() {
        return testCards;
    }


    private String mapCode(List<String> codeLines) {
        return codeLines.stream()
                .map(line -> ("\"").concat(line.trim()).concat("\""))
                .collect(Collectors.joining("\n"));
    }


    private String mapAnswers(List<Answer> answers) {
        return answers.stream()
                .map(answer -> answer.getText().trim().concat(SEPARATOR))
                .collect(Collectors.joining());
    }


    private String indexOfRightAnswer(List<Answer> answers) {
        return IntStream.range(0, answers.size())
                .filter(index -> answers.get(index).isRightAnswer())
                .mapToObj(index -> String.valueOf(index + 1))
                .collect(Collectors.joining());
    }


}
