//2-practice pro-4
import java.util.*;

public class StringUtilityApp {
    private static StringBuilder output = new StringBuilder();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== STRING UTILITY APPLICATION ===");

        boolean exit = false;
        while (!exit) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Text Analysis");
            System.out.println("2. String Transformation");
            System.out.println("3. ASCII Operations");
            System.out.println("4. Performance Testing");
            System.out.println("5. String Comparison Analysis");
            System.out.println("6. Custom String Algorithms");
            System.out.println("7. Exit");

            int choice = getIntInput("Enter choice (1-7): ");

            switch (choice) {
                case 1:
                    System.out.print("Enter text for analysis:\n> ");
                    scanner.nextLine(); // consume newline
                    String text1 = scanner.nextLine();
                    performTextAnalysis(text1);
                    break;
                case 2:
                    System.out.print("Enter text for transformations:\n> ");
                    scanner.nextLine(); // consume newline
                    String text2 = scanner.nextLine();
                    System.out.println("Enter operations separated by comma (trim, uppercase, lowercase, replaceSpaces, removeDigits):");
                    String opsLine = scanner.nextLine();
                    String[] ops = opsLine.split(",");
                    for (int i = 0; i < ops.length; i++) ops[i] = ops[i].trim();
                    String transformed = performTransformations(text2, ops);
                    System.out.println("Transformed text: " + transformed);
                    break;
                case 3:
                    System.out.print("Enter text for ASCII operations:\n> ");
                    scanner.nextLine(); // consume newline
                    String text3 = scanner.nextLine();
                    performASCIIOperations(text3);
                    break;
                case 4:
                    int iters = getIntInput("Enter number of iterations for performance test: ");
                    performPerformanceTest(iters);
                    break;
                case 5:
                    System.out.println("Enter strings to compare (comma separated):");
                    scanner.nextLine(); // consume newline
                    String input = scanner.nextLine();
                    String[] strings = input.split(",");
                    for (int i = 0; i < strings.length; i++) strings[i] = strings[i].trim();
                    performComparisonAnalysis(strings);
                    break;
                case 6:
                    System.out.print("Enter text for custom algorithms:\n> ");
                    scanner.nextLine(); // consume newline
                    String text6 = scanner.nextLine();
                    performCustomAlgorithms(text6);
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting application.");
                    break;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
        scanner.close();
    }

    public static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // 1. Text Analysis
    public static void performTextAnalysis(String text) {
        output.setLength(0);
        output.append("Text Analysis:\n");
        output.append("Length (chars): ").append(text.length()).append("\n");

        // Word count
        String[] words = text.trim().split("\\s+");
        output.append("Word count: ").append(words.length).append("\n");

        // Sentence count (simple split on ., !, ?)
        String[] sentences = text.split("[.!?]+");
        output.append("Sentence count: ").append(sentences.length).append("\n");

        // Paragraph count (split on double newlines)
        String[] paragraphs = text.split("\\n\\n+");
        output.append("Paragraph count: ").append(paragraphs.length).append("\n");

        // Character frequency
        Map<Character, Integer> freq = new HashMap<>();
        for (char ch : text.toCharArray()) {
            freq.put(ch, freq.getOrDefault(ch, 0) + 1);
        }
        output.append("Character frequencies:\n");
        freq.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(e -> output.append("'" + e.getKey() + "': " + e.getValue() + "\n"));

        // Most common words
        Map<String, Integer> wordFreq = new HashMap<>();
        for (String w : words) {
            String lw = w.toLowerCase().replaceAll("\\W", "");
            if (!lw.isEmpty()) {
                wordFreq.put(lw, wordFreq.getOrDefault(lw, 0) + 1);
            }
        }
        output.append("Most common words:\n");
        wordFreq.entrySet().stream()
            .sorted((a,b) -> b.getValue() - a.getValue())
            .limit(5)
            .forEach(e -> output.append(e.getKey() + ": " + e.getValue() + "\n"));

        displayResults();
    }

    // 2. String Transformation
    public static String performTransformations(String text, String[] operations) {
        StringBuilder sb = new StringBuilder(text);
        for (String op : operations) {
            switch (op.toLowerCase()) {
                case "trim":
                    text = text.trim();
                    sb = new StringBuilder(text);
                    break;
                case "uppercase":
                    text = sb.toString().toUpperCase();
                    sb = new StringBuilder(text);
                    break;
                case "lowercase":
                    text = sb.toString().toLowerCase();
                    sb = new StringBuilder(text);
                    break;
                case "replacespaces":
                    text = sb.toString().replace(' ', '_');
                    sb = new StringBuilder(text);
                    break;
                case "removedigits":
                    text = sb.toString().replaceAll("\\d", "");
                    sb = new StringBuilder(text);
                    break;
                default:
                    System.out.println("Unknown operation: " + op);
            }
        }
        return sb.toString();
    }

    // 3. ASCII Operations
    public static void performASCIIOperations(String text) {
        output.setLength(0);
        output.append("ASCII Operations:\n");
        for (char ch : text.toCharArray()) {
            int code = (int) ch;
            output.append("Char: '").append(ch).append("' ASCII: ").append(code);
            output.append(" Type: ").append(classifyCharacter(ch));

            if (Character.isLetter(ch)) {
                char upper = Character.toUpperCase(ch);
                char lower = Character.toLowerCase(ch);
                output.append(" | Upper: '").append(upper).append("'(").append((int) upper).append(")");
                output.append(" Lower: '").append(lower).append("'(").append((int) lower).append(")");
                output.append(" Diff: ").append(Math.abs((int)upper - (int)lower));
            }
            output.append("\n");
        }
        displayResults();

        // Simple Caesar cipher demo
        System.out.println("Enter shift amount for Caesar cipher:");
        int shift = getIntInput("> ");
        String encrypted = caesarCipher(text, shift);
        System.out.println("Encrypted: " + encrypted);
        String decrypted = caesarCipher(encrypted, -shift);
        System.out.println("Decrypted: " + decrypted);
    }

    public static String classifyCharacter(char ch) {
        if (Character.isUpperCase(ch)) return "Uppercase Letter";
        else if (Character.isLowerCase(ch)) return "Lowercase Letter";
        else if (Character.isDigit(ch)) return "Digit";
        else return "Special Character";
    }

    public static String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        for (char ch : text.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char shifted = (char) ((ch - base + shift + 26) % 26 + base);
                result.append(shifted);
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }

    // 4. Performance Testing
    public static void performPerformanceTest(int iterations) {
        output.setLength(0);
        output.append("Performance Test with ").append(iterations).append(" iterations\n");

        long start, end;

        // String concat
        start = System.nanoTime();
        String s = "";
        for (int i = 0; i < iterations; i++) {
            s += "x";
        }
        end = System.nanoTime();
        output.append("String concat time: ").append(end - start).append(" ns\n");

        // StringBuilder concat
        start = System.nanoTime();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iterations; i++) {
            sb.append("x");
        }
        end = System.nanoTime();
        output.append("StringBuilder append time: ").append(end - start).append(" ns\n");

        // StringBuffer concat
        start = System.nanoTime();
        StringBuffer sbf = new StringBuffer();
        for (int i = 0; i < iterations; i++) {
            sbf.append("x");
        }
        end = System.nanoTime();
        output.append("StringBuffer append time: ").append(end - start).append(" ns\n");

        displayResults();
    }

    // 5. String Comparison Analysis
    public static void performComparisonAnalysis(String[] strings) {
        output.setLength(0);
        output.append("String Comparison Analysis:\n");
        for (int i = 0; i < strings.length; i++) {
            for (int j = i + 1; j < strings.length; j++) {
                String s1 = strings[i];
                String s2 = strings[j];
                output.append("Comparing '").append(s1).append("' and '").append(s2).append("':\n");
                output.append("== : ").append(s1 == s2).append("\n");
                output.append("equals(): ").append(s1.equals(s2)).append("\n");
                output.append("equalsIgnoreCase(): ").append(s1.equalsIgnoreCase(s2)).append("\n");
                output.append("compareTo(): ").append(s1.compareTo(s2)).append("\n");
                output.append("compareToIgnoreCase(): ").append(s1.compareToIgnoreCase(s2)).append("\n\n");
            }
        }
        displayResults();
    }

    // 6. Custom String Algorithms
    public static void performCustomAlgorithms(String text) {
        output.setLength(0);
        output.append("Custom Algorithms:\n");
        output.append("Is palindrome? ").append(isPalindrome(text) ? "Yes" : "No").append("\n");

        System.out.println("Enter another string to check for anagram:");
        String other = scanner.nextLine();
        output.append("Is anagram with '").append(other).append("'? ").append(isAnagram(text, other) ? "Yes" : "No").append("\n");

        System.out.println("Enter pattern to find occurrences:");
        String pattern = scanner.nextLine();
        List<Integer> positions = findPatternOccurrences(text, pattern);
        output.append("Pattern '").append(pattern).append("' found at positions: ").append(positions).append("\n");

        displayResults();
    }

    public static boolean isPalindrome(String text) {
        String filtered = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return filtered.equals(new StringBuilder(filtered).reverse().toString());
    }

    public static boolean isAnagram(String s1, String s2) {
        char[] a1 = s1.toLowerCase().replaceAll("\\s+", "").toCharArray();
        char[] a2 = s2.toLowerCase().replaceAll("\\s+", "").toCharArray();
        Arrays.sort(a1);
        Arrays.sort(a2);
        return Arrays.equals(a1, a2);
    }

    public static List<Integer> findPatternOccurrences(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i <= text.length() - pattern.length(); i++) {
            if (text.regionMatches(i, pattern, 0, pattern.length())) {
                positions.add(i);
            }
        }
        return positions;
    }

    // Display accumulated results
    public static void displayResults() {
        System.out.println("\n" + output.toString());
    }
}
