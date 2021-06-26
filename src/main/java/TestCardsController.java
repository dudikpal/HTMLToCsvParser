import java.util.Scanner;

public class TestCardsController {

    private TestCardsService service = new TestCardsService(new TestCardsRepo());
    private Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {

        new TestCardsController().inputFileName();

    }

    public void inputFileName() {
        System.out.println("""
                A konvertálandó HTML fájlt a classPath-ra kell tenni!
                Kérem a fájl nevét kiterjesztés nélkül, majd nyomjon ENTER-t:""");
        String htmlFileName = scanner.nextLine();
        System.out.println("""
                Mi legyen a generált CSV fájl neve?
                Kérem írja be csak a nevet, kiterjesztés nélkül, majd nyomjon ENTER-t:""");
        String csvFileName = scanner.nextLine();
        convertHtmlToCsv(htmlFileName, csvFileName);
    }


    private void convertHtmlToCsv(String inputHtmlFileName, String outputCsvFileName) {
        service.convertHtmlToCsv(inputHtmlFileName, outputCsvFileName);
        System.out.println("Konvertálás kész");
    }

}
