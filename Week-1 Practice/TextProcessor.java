//program 4
import java.util.Scanner;
import java.util.Arrays;

public class TextProcessor {

    public static String cleanInput(String input) {
        return input.trim().replaceAll("\\s+", " ");
    }

    public static void analyzeText(String text) {
        String[] words = text.split(" ");
        int wordCount = words.length;
        int charCount = text.replace(" ", "").length();
        System.out.println("\n--- TEXT ANALYSIS ---");
        System.out.println("Words: " + wordCount);
        System.out.println("Characters (no spaces): " + charCount);
    }

    public static String[] getWordsSorted(String text) {
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z ]", "").split(" ");
        Arrays.sort(words);
        return words;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== TEXT PROCESSOR ===");
        System.out.print("Enter a paragraph of text:\n> ");
        String input = scanner.nextLine();

        String cleaned = cleanInput(input);
        System.out.println("\nCleaned Text:\n" + cleaned);

        analyzeText(cleaned);

        String[] sortedWords = getWordsSorted(cleaned);
        System.out.println("\n--- SORTED WORDS ---");
        for (String word : sortedWords) {
            System.out.println(word);
        }

        System.out.print("\nSearch for a word: ");
        String searchWord = scanner.next().toLowerCase();
        boolean found = Arrays.asList(sortedWords).contains(searchWord);
        System.out.println("Found: " + found);

        scanner.close();
    }
}
