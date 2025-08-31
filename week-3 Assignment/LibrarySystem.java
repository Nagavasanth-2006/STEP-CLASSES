import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class Book {
    String bookId, title, author, isbn, category;
    boolean isIssued;
    LocalDate issueDate, dueDate;
    int timesIssued = 0;

    Book(String bookId, String title, String author, String isbn, String category) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.isIssued = false;
    }
}

abstract class Member {
    String memberId, memberName, memberType;
    List<Book> booksIssued = new ArrayList<>();
    double totalFines;
    LocalDate membershipDate;

    static int totalMembers = 0;
    static int totalBooks = 0;
    static String libraryName = "Central Library";
    static double finePerDay = 2.0;

    Member(String memberId, String memberName, String memberType, LocalDate membershipDate) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberType = memberType;
        this.membershipDate = membershipDate;
        totalMembers++;
    }

    abstract int getMaxBooksAllowed();
    abstract int getBorrowDays();

    void issueBook(Book book, LocalDate issueDate) {
        if (booksIssued.size() < getMaxBooksAllowed() && !book.isIssued) {
            book.isIssued = true;
            book.issueDate = issueDate;
            book.dueDate = issueDate.plusDays(getBorrowDays());
            booksIssued.add(book);
            book.timesIssued++;
        }
    }

    void returnBook(Book book, LocalDate returnDate) {
        if (booksIssued.contains(book)) {
            long overdueDays = ChronoUnit.DAYS.between(book.dueDate, returnDate);
            if (overdueDays > 0) {
                totalFines += overdueDays * finePerDay;
            }
            book.isIssued = false;
            book.issueDate = null;
            book.dueDate = null;
            booksIssued.remove(book);
        }
    }

    void renewBook(Book book) {
        if (booksIssued.contains(book)) {
            book.dueDate = LocalDate.now().plusDays(getBorrowDays());
        }
    }

    void calculateFine() {
        for (Book book : booksIssued) {
            long overdueDays = ChronoUnit.DAYS.between(book.dueDate, LocalDate.now());
            if (overdueDays > 0) {
                totalFines += overdueDays * finePerDay;
            }
        }
    }
}

class Student extends Member {
    Student(String memberId, String memberName, LocalDate membershipDate) {
        super(memberId, memberName, "Student", membershipDate);
    }

    int getMaxBooksAllowed() {
        return 3;
    }

    int getBorrowDays() {
        return 14;
    }
}

class Faculty extends Member {
    Faculty(String memberId, String memberName, LocalDate membershipDate) {
        super(memberId, memberName, "Faculty", membershipDate);
    }

    int getMaxBooksAllowed() {
        return 6;
    }

    int getBorrowDays() {
        return 28;
    }
}

class General extends Member {
    General(String memberId, String memberName, LocalDate membershipDate) {
        super(memberId, memberName, "General", membershipDate);
    }

    int getMaxBooksAllowed() {
        return 2;
    }

    int getBorrowDays() {
        return 7;
    }
}

class Library {
    List<Book> books = new ArrayList<>();
    List<Member> members = new ArrayList<>();

    void addBook(Book book) {
        books.add(book);
        Member.totalBooks++;
    }

    void addMember(Member member) {
        members.add(member);
    }

    List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.title.toLowerCase().contains(keyword.toLowerCase()) ||
                book.author.toLowerCase().contains(keyword.toLowerCase()) ||
                book.category.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    void reserveBook(Member member, Book book) {
        if (!book.isIssued && member.booksIssued.size() < member.getMaxBooksAllowed()) {
            member.issueBook(book, LocalDate.now());
        }
    }

    static void generateLibraryReport(List<Member> members, List<Book> books) {
        System.out.println("Library Name: " + Member.libraryName);
        System.out.println("Total Members: " + Member.totalMembers);
        System.out.println("Total Books: " + Member.totalBooks);
        for (Member m : members) {
            System.out.println(m.memberName + " - " + m.memberType + " - Books Issued: " + m.booksIssued.size() + " - Fine: $" + m.totalFines);
        }
    }

    static List<Book> getOverdueBooks(List<Member> members) {
        List<Book> overdueBooks = new ArrayList<>();
        for (Member m : members) {
            for (Book b : m.booksIssued) {
                if (b.dueDate != null && b.dueDate.isBefore(LocalDate.now())) {
                    overdueBooks.add(b);
                }
            }
        }
        return overdueBooks;
    }

    static List<Book> getMostPopularBooks(List<Book> books) {
        books.sort((a, b) -> Integer.compare(b.timesIssued, a.timesIssued));
        return books.subList(0, Math.min(5, books.size()));
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Library library = new Library();

        Book b1 = new Book("B1", "Java Programming", "John Doe", "12345", "Programming");
        Book b2 = new Book("B2", "Data Structures", "Jane Smith", "67890", "Computer Science");
        Book b3 = new Book("B3", "Database Systems", "Mike Ross", "11111", "Databases");

        library.addBook(b1);
        library.addBook(b2);
        library.addBook(b3);

        Member m1 = new Student("M1", "Alice", LocalDate.of(2024, 5, 1));
        Member m2 = new Faculty("M2", "Dr. Bob", LocalDate.of(2023, 1, 15));
        Member m3 = new General("M3", "Charlie", LocalDate.of(2023, 3, 20));

        library.addMember(m1);
        library.addMember(m2);
        library.addMember(m3);

        m1.issueBook(b1, LocalDate.now().minusDays(16));
        m2.issueBook(b2, LocalDate.now().minusDays(30));
        m3.issueBook(b3, LocalDate.now().minusDays(10));

        m1.calculateFine();
        m2.calculateFine();
        m3.calculateFine();

        Library.generateLibraryReport(library.members, library.books);

        List<Book> overdue = Library.getOverdueBooks(library.members);
        System.out.println("\nOverdue Books:");
        for (Book b : overdue) {
            System.out.println(b.title + " - Due on: " + b.dueDate);
        }

        List<Book> popular = Library.getMostPopularBooks(library.books);
        System.out.println("\nMost Popular Books:");
        for (Book b : popular) {
            System.out.println(b.title + " - Issued: " + b.timesIssued + " times");
        }
    }
}
