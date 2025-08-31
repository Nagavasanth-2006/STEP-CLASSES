//2-Practice pro -1
public class StringBuiltInMethods {
    public static void main(String[] args) {
        String sampleText = " Java Programming is Fun and Challenging! ";

        System.out.println("Original length (including spaces): " + sampleText.length());

        String trimmedText = sampleText.trim();
        System.out.println("Trimmed length: " + trimmedText.length());

        System.out.println("Character at index 5: " + trimmedText.charAt(5));

        String substring = trimmedText.substring(trimmedText.indexOf("Programming"), trimmedText.indexOf("Programming") + "Programming".length());
        System.out.println("Extracted substring: " + substring);

        System.out.println("Index of 'Fun': " + trimmedText.indexOf("Fun"));

        System.out.println("Contains 'Java': " + trimmedText.contains("Java"));

        System.out.println("Starts with 'Java': " + trimmedText.startsWith("Java"));

        System.out.println("Ends with '!': " + trimmedText.endsWith("!"));

        System.out.println("Uppercase: " + trimmedText.toUpperCase());

        System.out.println("Lowercase: " + trimmedText.toLowerCase());

        int vowelCount = countVowels(trimmedText);
        System.out.println("Number of vowels: " + vowelCount);

        System.out.print("Positions of character 'a': ");
        findAllOccurrences(trimmedText, 'a');
    }

    public static int countVowels(String text) {
        int count = 0;
        String vowels = "aeiouAEIOU";
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (vowels.indexOf(ch) != -1) {
                count++;
            }
        }
        return count;
    }

    public static void findAllOccurrences(String text, char target) {
        boolean found = false;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == target) {
                System.out.print(i + " ");
                found = true;
            }
        }
        if (!found) {
            System.out.print("None");
        }
        System.out.println();
    }
}
