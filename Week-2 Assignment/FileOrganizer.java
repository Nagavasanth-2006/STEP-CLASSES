import java.util.*;

public class FileOrganizer {

    static class FileInfo {
        String originalName;
        String baseName;
        String extension;
        String category;
        String subcategory = "";
        String newName;
        boolean validName = true;
        boolean unknownType = false;
        int priority = 0;

        FileInfo(String originalName, String baseName, String extension) {
            this.originalName = originalName;
            this.baseName = baseName;
            this.extension = extension;
        }
    }

    static Map<String, String> extensionCategory = new HashMap<>();
    static {
        extensionCategory.put("txt", "Documents");
        extensionCategory.put("doc", "Documents");
        extensionCategory.put("docx", "Documents");
        extensionCategory.put("pdf", "Documents");

        extensionCategory.put("jpg", "Images");
        extensionCategory.put("jpeg", "Images");
        extensionCategory.put("png", "Images");
        extensionCategory.put("gif", "Images");

        extensionCategory.put("mp3", "Audio");
        extensionCategory.put("wav", "Audio");

        extensionCategory.put("mp4", "Video");
        extensionCategory.put("mkv", "Video");

        extensionCategory.put("java", "Code");
        extensionCategory.put("py", "Code");
        extensionCategory.put("c", "Code");
        extensionCategory.put("cpp", "Code");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<FileInfo> files = new ArrayList<>();

        System.out.println("Enter file names with extensions, one per line. Type 'END' to finish:");
        while (true) {
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("END")) break;
            FileInfo fi = extractFileComponents(input);
            files.add(fi);
        }

        categorizeFiles(files);
        simulateContentAnalysis(files);
        generateNewFileNames(files);

        displayReport(files);
        generateBatchRenameCommands(files);
    }

    static FileInfo extractFileComponents(String filename) {
        int lastDot = filename.lastIndexOf('.');
        if (lastDot == -1 || lastDot == 0 || lastDot == filename.length() - 1) {
            FileInfo fi = new FileInfo(filename, filename, "");
            fi.validName = false;
            return fi;
        }
        String base = filename.substring(0, lastDot);
        String ext = filename.substring(lastDot + 1).toLowerCase();

        if (!isValidFileName(base) || !isValidExtension(ext)) {
            FileInfo fi = new FileInfo(filename, base, ext);
            fi.validName = false;
            return fi;
        }
        return new FileInfo(filename, base, ext);
    }

    static boolean isValidFileName(String name) {
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            if (!(Character.isLetterOrDigit(ch) || ch == '_' || ch == '-' || ch == ' ')) {
                return false;
            }
        }
        return true;
    }

    static boolean isValidExtension(String ext) {
        for (int i = 0; i < ext.length(); i++) {
            char ch = ext.charAt(i);
            if (!(Character.isLetterOrDigit(ch))) {
                return false;
            }
        }
        return true;
    }

    static void categorizeFiles(List<FileInfo> files) {
        for (FileInfo fi : files) {
            if (!fi.validName) continue;
            String cat = extensionCategory.get(fi.extension);
            if (cat == null) {
                fi.category = "Unknown";
                fi.unknownType = true;
            } else {
                fi.category = cat;
            }
        }
    }

    static void simulateContentAnalysis(List<FileInfo> files) {
        for (FileInfo fi : files) {
            if (fi.category == null || fi.category.equals("Unknown")) continue;
            if (fi.extension.equals("txt") || fi.category.equals("Documents")) {
                String content = simulateFileContent(fi.baseName);
                fi.subcategory = analyzeContentForSubcategory(content);
                fi.priority = calculatePriority(fi.baseName, content);
            }
        }
    }

    static String simulateFileContent(String baseName) {
        String lower = baseName.toLowerCase();
        if (lower.contains("resume") || lower.contains("cv")) return "resume content with keywords";
        if (lower.contains("report")) return "annual financial report content";
        if (lower.contains("code") || lower.contains("program")) return "source code content";
        if (lower.contains("draft")) return "draft document content";
        return "";
    }

    static String analyzeContentForSubcategory(String content) {
        content = content.toLowerCase();
        if (content.contains("resume")) return "Resume";
        if (content.contains("report")) return "Report";
        if (content.contains("code")) return "Code";
        if (content.contains("draft")) return "Draft";
        return "";
    }

    static int calculatePriority(String baseName, String content) {
        int score = 0;
        if (baseName.toLowerCase().contains("urgent")) score += 5;
        if (content.toLowerCase().contains("important")) score += 3;
        if (baseName.length() > 10) score += 1;
        return score;
    }

    static void generateNewFileNames(List<FileInfo> files) {
        Map<String, Integer> nameCount = new HashMap<>();
        String dateStr = java.time.LocalDate.now().toString().replaceAll("-", "");

        for (FileInfo fi : files) {
            if (!fi.validName) {
                fi.newName = fi.originalName;
                continue;
            }
            String cat = (fi.category == null) ? "Other" : fi.category;
            String sub = (fi.subcategory.isEmpty()) ? "" : "_" + fi.subcategory;
            String baseNew = cat + sub + "_" + dateStr;

            int count = nameCount.getOrDefault(baseNew, 0);
            count++;
            nameCount.put(baseNew, count);

            String numSuffix = (count > 1) ? ("_" + count) : "";
            String newName = baseNew + numSuffix + "." + fi.extension;

            if (!isValidFileName(newName.replace("." + fi.extension, ""))) {
                newName = "file_" + count + "." + fi.extension;
            }
            fi.newName = newName;
        }
    }

    static void displayReport(List<FileInfo> files) {
        System.out.println("\nFile Organization Report:");
        System.out.printf("%-25s %-15s %-25s %-15s %-10s\n", "Original Filename", "Category", "Subcategory", "New Filename", "Priority");

        Map<String, Integer> categoryCounts = new HashMap<>();
        List<String> attentionFiles = new ArrayList<>();

        for (FileInfo fi : files) {
            System.out.printf("%-25s %-15s %-25s %-15s %-10d\n",
                    fi.originalName,
                    (fi.category == null ? "N/A" : fi.category),
                    (fi.subcategory.isEmpty() ? "-" : fi.subcategory),
                    fi.newName,
                    fi.priority);

            if (fi.validName && !fi.unknownType) {
                categoryCounts.put(fi.category, categoryCounts.getOrDefault(fi.category, 0) + 1);
            } else {
                attentionFiles.add(fi.originalName);
            }
        }

        System.out.println("\nCategory-wise File Counts:");
        for (Map.Entry<String, Integer> entry : categoryCounts.entrySet()) {
            System.out.printf("%-15s : %d\n", entry.getKey(), entry.getValue());
        }

        if (!attentionFiles.isEmpty()) {
            System.out.println("\nFiles needing attention:");
            for (String f : attentionFiles) {
                System.out.println(" - " + f);
            }
        }

        System.out.println("\nStatistics:");
        System.out.println("Total files: " + files.size());
        System.out.println("Valid files: " + (files.size() - attentionFiles.size()));
        System.out.println("Invalid or unknown files: " + attentionFiles.size());
    }

    static void generateBatchRenameCommands(List<FileInfo> files) {
        System.out.println("\nBatch Rename Commands (simulate):");
        for (FileInfo fi : files) {
            if (!fi.validName || fi.originalName.equals(fi.newName)) continue;
            System.out.printf("rename \"%s\" \"%s\"\n", fi.originalName, fi.newName);
        }
    }
}

