import java.util.*;

public class CSVAnalyzer {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter CSV data line by line. Type 'END' to finish:");

        List<String> lines = new ArrayList<>();
        while (true) {
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("END")) break;
            lines.add(line);
        }

        if (lines.isEmpty()) {
            System.out.println("No data entered.");
            return;
        }

        String[][] data = parseCSV(lines);
        cleanAndValidateData(data);

        DataStats stats = analyzeData(data);
        printTable(data, stats);
        printSummaryReport(data, stats);
    }

    public static String[][] parseCSV(List<String> lines) {
        List<String[]> rows = new ArrayList<>();
        for (String line : lines) {
            List<String> fields = new ArrayList<>();
            int len = line.length();
            int i = 0;

            while (i < len) {
                if (line.charAt(i) == '"') {
                    i++;
                    StringBuilder field = new StringBuilder();
                    boolean closedQuote = false;
                    while (i < len) {
                        char ch = line.charAt(i);
                        if (ch == '"') {
                            if (i + 1 < len && line.charAt(i + 1) == '"') {
                                field.append('"');
                                i += 2;
                            } else {
                                closedQuote = true;
                                i++;
                                break;
                            }
                        } else {
                            field.append(ch);
                            i++;
                        }
                    }
                    if (!closedQuote) {
                        System.out.println("Warning: unmatched quotes in line: " + line);
                    }
                    fields.add(field.toString());
                    if (i < len && line.charAt(i) == ',') i++;
                } else {
                    int start = i;
                    while (i < len && line.charAt(i) != ',') i++;
                    fields.add(line.substring(start, i));
                    if (i < len && line.charAt(i) == ',') i++;
                }
            }
            rows.add(fields.toArray(new String[0]));
        }

        int maxCols = 0;
        for (String[] r : rows) if (r.length > maxCols) maxCols = r.length;
        String[][] result = new String[rows.size()][maxCols];
        for (int r = 0; r < rows.size(); r++) {
            String[] row = rows.get(r);
            for (int c = 0; c < maxCols; c++) {
                result[r][c] = (c < row.length) ? row[c] : "";
            }
        }
        return result;
    }

    public static void cleanAndValidateData(String[][] data) {
        int rows = data.length;
        int cols = data[0].length;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (data[r][c] == null) data[r][c] = "";
                else data[r][c] = data[r][c].trim();

                if (data[r][c].isEmpty()) {
                    data[r][c] = "MISSING";
                }
            }
        }
    }

    static class DataStats {
        int rows, cols;
        boolean[] isNumericColumn;
        double[] min;
        double[] max;
        double[] sum;
        int[] countValid;
        Map<Integer, Map<String, Integer>> categoricalCounts;
        int totalMissing;

        DataStats(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            isNumericColumn = new boolean[cols];
            min = new double[cols];
            max = new double[cols];
            sum = new double[cols];
            countValid = new int[cols];
            categoricalCounts = new HashMap<>();
            totalMissing = 0;

            Arrays.fill(min, Double.MAX_VALUE);
            Arrays.fill(max, -Double.MAX_VALUE);
            Arrays.fill(isNumericColumn, true);
        }
    }

    public static DataStats analyzeData(String[][] data) {
        int rows = data.length;
        int cols = data[0].length;
        DataStats stats = new DataStats(rows - 1, cols);

        for (int c = 0; c < cols; c++) {
            stats.categoricalCounts.put(c, new HashMap<>());
        }

        for (int r = 1; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                String val = data[r][c];
                if (val.equalsIgnoreCase("MISSING")) {
                    stats.totalMissing++;
                    continue;
                }
                if (stats.isNumericColumn[c]) {
                    if (isNumeric(val)) {
                        double num = Double.parseDouble(val);
                        if (num < stats.min[c]) stats.min[c] = num;
                        if (num > stats.max[c]) stats.max[c] = num;
                        stats.sum[c] += num;
                        stats.countValid[c]++;
                    } else {
                        stats.isNumericColumn[c] = false;
                        stats.categoricalCounts.get(c).put(val,
                                stats.categoricalCounts.get(c).getOrDefault(val, 0) + 1);
                    }
                } else {
                    stats.categoricalCounts.get(c).put(val,
                            stats.categoricalCounts.get(c).getOrDefault(val, 0) + 1);
                }
            }
        }
        return stats;
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        int dotCount = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '.') {
                dotCount++;
                if (dotCount > 1) return false;
            } else if (ch < '0' || ch > '9') {
                return false;
            }
        }
        return true;
    }

    public static void printTable(String[][] data, DataStats stats) {
        int rows = data.length;
        int cols = data[0].length;
        int[] colWidths = new int[cols];

        for (int c = 0; c < cols; c++) {
            int maxLen = 0;
            for (int r = 0; r < rows; r++) {
                if (data[r][c] != null) {
                    int len = data[r][c].length();
                    if (len > maxLen) maxLen = len;
                }
            }
            colWidths[c] = Math.max(maxLen, 10);
        }

        StringBuilder sb = new StringBuilder();

        printSeparator(colWidths, sb);

        sb.append("|");
        for (int c = 0; c < cols; c++) {
            sb.append(padCenter(data[0][c], colWidths[c])).append("|");
        }
        sb.append("\n");

        printSeparator(colWidths, sb);

        for (int r = 1; r < rows; r++) {
            sb.append("|");
            for (int c = 0; c < cols; c++) {
                String val = data[r][c];
                if ("MISSING".equalsIgnoreCase(val)) {
                    sb.append(padRight("[MISSING]", colWidths[c])).append("|");
                } else if (stats.isNumericColumn[c]) {
                    try {
                        double num = Double.parseDouble(val);
                        sb.append(padLeft(String.format("%.2f", num), colWidths[c])).append("|");
                    } catch (Exception e) {
                        sb.append(padRight(val, colWidths[c])).append("|");
                    }
                } else {
                    sb.append(padRight(val, colWidths[c])).append("|");
                }
            }
            sb.append("\n");
        }

        printSeparator(colWidths, sb);

        System.out.println("\nFormatted Table:\n" + sb.toString());
    }

    private static void printSeparator(int[] colWidths, StringBuilder sb) {
        sb.append("+");
        for (int w : colWidths) {
            for (int i = 0; i < w; i++) sb.append("-");
            sb.append("+");
        }
        sb.append("\n");
    }

    private static String padRight(String s, int n) {
        StringBuilder sb = new StringBuilder(s);
        while (sb.length() < n) sb.append(' ');
        return sb.toString();
    }

    private static String padLeft(String s, int n) {
        StringBuilder sb = new StringBuilder();
        while (sb.length() + s.length() < n) sb.append(' ');
        sb.append(s);
        return sb.toString();
    }

    private static String padCenter(String s, int n) {
        int left = (n - s.length()) / 2;
        int right = n - s.length() - left;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < left; i++) sb.append(' ');
        sb.append(s);
        for (int i = 0; i < right; i++) sb.append(' ');
        return sb.toString();
    }

    public static void printSummaryReport(String[][] data, DataStats stats) {
        System.out.println("Summary Report:");
        System.out.println("Total records processed (excluding header): " + stats.rows);
        System.out.println("Total missing fields: " + stats.totalMissing);

        System.out.println("\nColumn-wise statistics:");
        for (int c = 0; c < stats.cols; c++) {
            String colName = data[0][c];
            System.out.println("Column: " + colName);

            if (stats.isNumericColumn[c]) {
                if (stats.countValid[c] == 0) {
                    System.out.println("  No valid numeric data");
                } else {
                    double avg = stats.sum[c] / stats.countValid[c];
                    System.out.printf("  Min: %.2f, Max: %.2f, Avg: %.2f, Valid: %d\n",
                            stats.min[c], stats.max[c], avg, stats.countValid[c]);
                }
            } else {
                Map<String, Integer> catCounts = stats.categoricalCounts.get(c);
                System.out.println("  Unique categories: " + catCounts.size());
                int maxCount = 0;
                String mostCommon = "";
                for (Map.Entry<String, Integer> entry : catCounts.entrySet()) {
                    if (entry.getValue() > maxCount) {
                        maxCount = entry.getValue();
                        mostCommon = entry.getKey();
                    }
                }
                System.out.println("  Most common: " + mostCommon + " (" + maxCount + ")");
            }
        }

        int totalFields = stats.rows * stats.cols;
        int completeness = (int) (((double) (totalFields - stats.totalMissing) / totalFields) * 100);
        System.out.println("\nData completeness: " + completeness + "%");
    }
}
