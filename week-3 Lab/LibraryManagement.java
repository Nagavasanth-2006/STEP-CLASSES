class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    private static int bookCounter = 0;
    private static int totalBooks = 0;
    private static int availableBooks = 0;

    public Book(String title, String author) {
        this.bookId = generateBookId();
        this.title = title;
        this.author = author;
        this.isAvailable = true;
        totalBooks++;
        availableBooks++;
    }

    private static String generateBookId() {
        bookCounter++;
        return "B" + String.format("%03d", bookCounter);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public String getBookId() {
        return bookId;
    }

    public void issueBook() {
        if (isAvailable) {
            isAvailable = false;
            availableBooks--;
        }
    }

    public void returnBook() {
        if (!isAvailable) {
            isAvailable = true;
            availableBooks++;
        }
    }

    public void displayBookInfo() {
        System.out.println("Book ID    : " + bookId);
        System.out.println("Title      : " + title);
        System.out.println("Author     : " + author);
        System.out.println("Available  : " + isAvailable);
        System.out.println("---------------------------");
    }

    public static int getTotalBooks() {
        return totalBooks;
    }

    public static int getAvailableBooks() {
        return availableBooks;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private String[] booksIssued;
    private int bookCount;

    private static int memberCounter = 0;

    public Member(String memberName) {
        this.memberId = generateMemberId();
        this.memberName = memberName;
        this.booksIssued = new String[5];
        this.bookCount = 0;
    }

    private static String generateMemberId() {
        memberCounter++;
        return "M" + String.format("%03d", memberCounter);
    }

    public void borrowBook(Book book) {
        if (bookCount >= booksIssued.length) {
            System.out.println(memberName + " has already issued maximum books.");
            return;
        }

        if (book.isAvailable()) {
            book.issueBook();
            booksIssued[bookCount++] = book.getBookId();
            System.out.println(memberName + " borrowed book " + book.getBookId());
        } else {
            System.out.println("Book " + book.getBookId() + " is not available.");
        }
    }

    public void returnBook(String bookId, Book[] books) {
        int index = -1;
        for (int i = 0; i < bookCount; i++) {
            if (booksIssued[i].equals(bookId)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            System.out.println(memberName + " has not issued book " + bookId);
            return;
        }

        for (int i = 0; i < books.length; i++) {
            if (books[i] != null && books[i].getBookId().equals(bookId)) {
                books[i].returnBook();
                System.out.println(memberName + " returned book " + bookId);
                break;
            }
        }

        for (int i = index; i < bookCount - 1; i++) {
            booksIssued[i] = booksIssued[i + 1];
        }
        booksIssued[--bookCount] = null;
    }

    public void displayMemberInfo() {
        System.out.println("Member ID   : " + memberId);
        System.out.println("Name        : " + memberName);
        System.out.print("Books Issued: ");
        for (int i = 0; i < bookCount; i++) {
            System.out.print(booksIssued[i] + " ");
        }
        System.out.println("\n---------------------------");
    }
}

public class LibraryManagement {
    public static void main(String[] args) {
        Book[] books = new Book[5];
        books[0] = new Book("The Alchemist", "Paulo Coelho");
        books[1] = new Book("1984", "George Orwell");
        books[2] = new Book("To Kill a Mockingbird", "Harper Lee");
        books[3] = new Book("Moby Dick", "Herman Melville");
        books[4] = new Book("The Great Gatsby", "F. Scott Fitzgerald");

        Member[] members = new Member[2];
        members[0] = new Member("Alice");
        members[1] = new Member("Bob");

        members[0].borrowBook(books[0]);
        members[0].borrowBook(books[1]);
        members[1].borrowBook(books[1]); // Should be unavailable

        members[0].returnBook("B001", books);
        members[1].borrowBook(books[0]); // Now available

        System.out.println("\n--- Book Inventory ---");
        for (Book book : books) {
            book.displayBookInfo();
        }

        System.out.println("--- Member Info ---");
        for (Member member : members) {
            member.displayMemberInfo();
        }

        System.out.println("Total Books    : " + Book.getTotalBooks());
        System.out.println("Available Books: " + Book.getAvailableBooks());
    }
}
