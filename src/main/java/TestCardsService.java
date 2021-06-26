public class TestCardsService {

    private TestCardsRepo repo;


    public TestCardsService(TestCardsRepo repo) {
        this.repo = repo;
    }



    public void convertHtmlToCsv(String inputHtmlFileName, String outputCsvFileName) {
        repo.createTestCardsFromHTML(inputHtmlFileName + ".html");
        repo.saveTestCardsToCsv("src/main/resources/" + outputCsvFileName + ".csv");
    }


}
