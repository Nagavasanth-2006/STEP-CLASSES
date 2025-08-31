import java.util.*;

public class EmailAnalyzer {

    static class EmailInfo {
        String email;
        String username;
        String domain;
        String domainName;
        String extension;
        boolean isValid;

        EmailInfo(String email, String username, String domain, String domainName, String extension, boolean isValid) {
            this.email = email;
            this.username = username;
            this.domain = domain;
            this.domainName = domainName;
            this.extension = extension;
            this.isValid = isValid;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<EmailInfo> emailList = new ArrayList<>();

        System.out.println("Enter email addresses (type 'done' to finish):");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("done")) break;
            EmailInfo info = processEmail(input);
            emailList.add(info);
        }

        displayTable(emailList);
        analyzeEmails(emailList);
    }

    public static EmailInfo processEmail(String email) {
        boolean isValid = validateEmail(email);

        String username = "";
        String domain = "";
        String domainName = "";
        String extension = "";

        if (isValid) {
            int atIndex = email.indexOf('@');
            int dotIndex = email.indexOf('.', atIndex);

            username = email.substring(0, atIndex);
            domain = email.substring(atIndex + 1);
            int extIndex = domain.lastIndexOf('.');

            if (extIndex != -1) {
                domainName = domain.substring(0, extIndex);
                extension = domain.substring(extIndex + 1);
            }
        }

        return new EmailInfo(email, username, domain, domainName, extension, isValid);
    }

    public static boolean validateEmail(String email) {
        int at = email.indexOf('@');
        int lastAt = email.lastIndexOf('@');

        if (at == -1 || at != lastAt) return false;

        String username = email.substring(0, at);
        String domain = email.substring(at + 1);

        if (username.isEmpty() || domain.isEmpty()) return false;
        if (domain.indexOf('.') == -1) return false;

        return true;
    }

    public static void displayTable(List<EmailInfo> emailList) {
        System.out.println("\nEmail Analysis Table:");
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-25s | %-15s | %-20s | %-15s | %-10s | %-6s\n", 
            "Email", "Username", "Domain", "Domain Name", "Extension", "Valid");
        System.out.println("------------------------------------------------------------------------------------------");

        for (EmailInfo info : emailList) {
            System.out.printf("%-25s | %-15s | %-20s | %-15s | %-10s | %-6s\n",
                info.email, info.username, info.domain, info.domainName, info.extension,
                info.isValid ? "Yes" : "No");
        }

        System.out.println("------------------------------------------------------------------------------------------");
    }

    public static void analyzeEmails(List<EmailInfo> emailList) {
        int validCount = 0;
        int invalidCount = 0;
        int totalUsernameLength = 0;
        int validUsernameCount = 0;
        Map<String, Integer> domainCount = new HashMap<>();

        for (EmailInfo info : emailList) {
            if (info.isValid) {
                validCount++;
                totalUsernameLength += info.username.length();
                validUsernameCount++;

                domainCount.put(info.domain, domainCount.getOrDefault(info.domain, 0) + 1);
            } else {
                invalidCount++;
            }
        }

        String mostCommonDomain = "";
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : domainCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                mostCommonDomain = entry.getKey();
            }
        }

        double averageUsernameLength = validUsernameCount > 0 ? 
            (double) totalUsernameLength / validUsernameCount : 0;

        System.out.println("\nEmail Statistics:");
        System.out.println("------------------------------");
        System.out.println("Total Emails: " + emailList.size());
        System.out.println("Valid Emails: " + validCount);
        System.out.println("Invalid Emails: " + invalidCount);
        System.out.println("Most Common Domain: " + (mostCommonDomain.isEmpty() ? "N/A" : mostCommonDomain));
        System.out.printf("Average Username Length: %.2f\n", averageUsernameLength);
        System.out.println("------------------------------");
    }
}
