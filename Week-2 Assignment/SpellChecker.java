import java.util.*;

public class SpellChecker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter sentence:");
        String sentence = scanner.nextLine();

        System.out.println("Enter number of dictionary words:");
        int n = scanner.nextInt();
        scanner.nextLine(); // consume newline

        String[] dictionary = new String[n];
        System.out.println("Enter dictionary words:");
        for (int i = 0; i < n; i++) {
            dictionary[i] = scanner.nextLine().toLowerCase();
        }

        List<String> words = splitSentence(sentence);
        List<Result> results = new ArrayList<>();

        for (String word : words) {
            String suggestion = findClosestMatch(word.toLowerCase(), dictionary);
            int distance = suggestion.equals(word.toLowerCase()) ? 0 : stringDistance(word.toLowerCase(), suggestion);
            String status = distance == 0 ? "Correct" : "Misspelled";

            results.add(new Result(word, suggestion, distance, status));
        }

        displayResults(results);
    }

    static class Result {
        String word;
        String suggestion;
        int distance;
        String status;

        Result(String word, String suggestion, int distance, String status) {
            this.word = word;
            this.suggestion = suggestion;
            this.distance = distance;
            this.status = status;
        }
    }

    public static List<String> splitSentence(String sentence) {
        List<String> words = new ArrayList<>();
        int start = -1;

        for (int i = 0; i < sentence.length(); i++) {
            char ch = sentence.charAt(i);
            if (Character.isLetter(ch)) {
                if (start == -1) start = i;
            } else {
                if (start != -1) {
                    words.add(sentence.substring(start, i));
                    start = -1;
                }
            }
        }

        if (start != -1) {
            words.add(sentence.substring(start));
        }

        return words;
    }

    public static int stringDistance(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) dp[i][0] = i;
        for (int j = 0; j <= len2; j++) dp[0][j] = j;

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[len1][len2];
    }

    public static String findClosestMatch(String word, String[] dictionary) {
        String closest = word;
        int minDistance = Integer.MAX_VALUE;

        for (String dictWord : dictionary) {
            int distance = stringDistance(word, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                closest = dictWord;
            }
        }

        return (minDistance <= 2) ? closest : word;
    }

    public static void displayResults(List<Result> results) {
        System.out.println("\nSpell Check Report:");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("%-15s | %-15s | %-8s | %-10s\n", "Word", "Suggestion", "Distance", "Status");
        System.out.println("-------------------------------------------------------------");

        for (Result res : results) {
            System.out.printf("%-15s | %-15s | %-8d | %-10s\n", res.word, res.suggestion, res.distance, res.status);
        }

        System.out.println("-------------------------------------------------------------");
    }
}
