import java.util.*;

public class PasswordManager {

    static class PasswordInfo {
        String password;
        int length;
        int upper;
        int lower;
        int digit;
        int special;
        int score;
        String strength;

        PasswordInfo(String password, int upper, int lower, int digit, int special, int score, String strength) {
            this.password = password;
            this.length = password.length();
            this.upper = upper;
            this.lower = lower;
            this.digit = digit;
            this.special = special;
            this.score = score;
            this.strength = strength;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<PasswordInfo> passwordList = new ArrayList<>();

        System.out.println("Enter number of passwords to analyze:");
        int n = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < n; i++) {
            System.out.println("Enter password #" + (i + 1) + ":");
            String pwd = scanner.nextLine();
            passwordList.add(analyzePassword(pwd));
        }

        System.out.println("\nPassword Analysis Table:");
        displayTable(passwordList);

        System.out.println("\nGenerate Strong Password:");
        System.out.print("Enter desired length: ");
        int length = scanner.nextInt();
        String strongPassword = generateStrongPassword(length);
        System.out.println("Generated Strong Password: " + strongPassword);
    }

    public static PasswordInfo analyzePassword(String pwd) {
        int upper = 0, lower = 0, digit = 0, special = 0;

        for (int i = 0; i < pwd.length(); i++) {
            char ch = pwd.charAt(i);
            int ascii = (int) ch;

            if (ascii >= 65 && ascii <= 90) upper++;
            else if (ascii >= 97 && ascii <= 122) lower++;
            else if (ascii >= 48 && ascii <= 57) digit++;
            else if (ascii >= 33 && ascii <= 126) special++;
        }

        int score = 0;

        if (pwd.length() > 8) {
            score += 2 * (pwd.length() - 8);
        }

        if (upper > 0) score += 10;
        if (lower > 0) score += 10;
        if (digit > 0) score += 10;
        if (special > 0) score += 10;

        String[] commonPatterns = {"123", "abc", "qwerty", "password", "admin"};
        for (String pattern : commonPatterns) {
            if (pwd.toLowerCase().contains(pattern)) {
                score -= 10;
            }
        }

        String strength;
        if (score <= 20) strength = "Weak";
        else if (score <= 50) strength = "Medium";
        else strength = "Strong";

        return new PasswordInfo(pwd, upper, lower, digit, special, score, strength);
    }

    public static String generateStrongPassword(int length) {
        if (length < 4) return "Length must be >= 4";

        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specials = "!@#$%^&*()-_=+[]{}|;:,.<>?/";

        sb.append(upper.charAt(rand.nextInt(upper.length())));
        sb.append(lower.charAt(rand.nextInt(lower.length())));
        sb.append(digits.charAt(rand.nextInt(digits.length())));
        sb.append(specials.charAt(rand.nextInt(specials.length())));

        String allChars = upper + lower + digits + specials;

        for (int i = 4; i < length; i++) {
            sb.append(allChars.charAt(rand.nextInt(allChars.length())));
        }

        List<Character> charList = new ArrayList<>();
        for (char ch : sb.toString().toCharArray()) {
            charList.add(ch);
        }

        Collections.shuffle(charList);

        StringBuilder shuffled = new StringBuilder();
        for (char ch : charList) {
            shuffled.append(ch);
        }

        return shuffled.toString();
    }

    public static void displayTable(List<PasswordInfo> list) {
        System.out.println("------------------------------------------------------------------------------------------------------");
        System.out.printf("%-20s | %-6s | %-6s | %-7s | %-6s | %-13s | %-6s | %-8s\n",
                "Password", "Length", "Upper", "Lower", "Digits", "Special Chars", "Score", "Strength");
        System.out.println("------------------------------------------------------------------------------------------------------");

        for (PasswordInfo info : list) {
            System.out.printf("%-20s | %-6d | %-6d | %-7d | %-6d | %-13d | %-6d | %-8s\n",
                    info.password, info.length, info.upper, info.lower, info.digit, info.special, info.score, info.strength);
        }

        System.out.println("------------------------------------------------------------------------------------------------------");
    }
}
