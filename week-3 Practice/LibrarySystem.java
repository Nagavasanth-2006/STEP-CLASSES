import java.util.ArrayList;
import java.util.Scanner;

class Book {
    private String title;
    private String author;
    private String isbn;
    private double price;
    private int quantity;

    public Book(String title, String author, String isbn, double price, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getTotalValue() {
        return price * quantity;
    }

    public void display() {
        System.out.println("Title: " + title + ", Author: " + author + ", ISBN: " + isbn +
                ", Price: $" + price + ", Quantity: " + quantity);
    }
}

class Library {
    private String libraryName;
    private ArrayList<Book> books;

    public Library(String libraryName) {
        this.libraryName = libraryName;
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void searchBook(String keyword) {
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(keyword.toLowerCase())) {
                book.display();
            }
        }
    }

    public void displayInventory() {
        for (Book book : books) {
            book.display();
        }
    }

    public double calculateTotalValue() {
        double total = 0;
        for (Book book : books) {
            total += book.getTotalValue();
        }
        return total;
    }
}

public class LibrarySystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library("City Library");

        while (true) {
            System.out.println("\n1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Calculate Total Value");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Enter price: ");
                double price = scanner.nextDouble();
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                scanner.nextLine();
                Book book = new Book(title, author, isbn, price, quantity);
                library.addBook(book);
                System.out.println("Book added.");
            } else if (choice == 2) {
                System.out.print("Enter title or author to search: ");
                String keyword = scanner.nextLine();
                library.searchBook(keyword);
            } else if (choice == 3) {
                library.displayInventory();
            } else if (choice == 4) {
                double totalValue = library.calculateTotalValue();
                System.out.println("Total value of all books: $" + totalValue);
            } else if (choice == 5) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }
}
