//2-practice pro -3
import java.util.*;

public class Stringchange {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence with mixed formatting:");
        String input = scanner.nextLine();

        String trimmed = input.trim();
        System.out.println("Trimmed: '" + trimmed + "'");

        String replacedSpaces = trimmed.replace(' ', '_');
        System.out.println("Spaces replaced with underscores: " + replacedSpaces);

        String removedDigits = replacedSpaces.replaceAll("\\d", "");
        System.out.println("Digits removed: " + removedDigits);

        String[] words = removedDigits.split("_");
        System.out.println("Split into words: " + Arrays.toString(words));

        String joined = String.join(" | ", words);
        System.out.println("Joined with ' | ': " + joined);

        String noPunctuation = removePunctuation(trimmed);
        System.out.println("Without punctuation: " + noPunctuation);

        String capitalized = capitalizeWords(noPunctuation);
        System.out.println("Capitalized each word: " + capitalized);

        String reversed = reverseWordOrder(noPunctuation);
        System.out.println("Reversed word order: " + reversed);

        System.out.println("Word frequencies:");
        countWordFrequency(noPunctuation);

        scanner.close();
    }

    public static String removePunctuation(String text) {
        return text.replaceAll("[\\p{Punct}]", "");
    }

    public static String capitalizeWords(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (w.length() > 0) {
                sb.append(Character.toUpperCase(w.charAt(0)));
                if (w.length() > 1) {
                    sb.append(w.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    public static String reverseWordOrder(String text) {
        String[] words = text.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]);
            if (i != 0) sb.append(" ");
        }
        return sb.toString();
    }

    public static void countWordFrequency(String text) {
        String[] words = text.toLowerCase().split("\\s+");
        Map<String, Integer> freqMap = new LinkedHashMap<>();
        for (String w : words) {
            freqMap.put(w, freqMap.getOrDefault(w, 0) + 1);
        }
        freqMap.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
