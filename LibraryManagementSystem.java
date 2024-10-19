import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class LibraryManagementSystem {
    private JFrame frame;
    private JPanel panel;
    private JTextField bookNameField, authorNameField, bookIDField, priceField, quantityField;
    private JTextArea outputArea;
    private HashMap<String, Book> bookMap;

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        system.createUI();
    }

    // Constructor initializing book list
    public LibraryManagementSystem() {
        bookMap = new HashMap<>();
    }

    // UI Setup
    public void createUI() {
        frame = new JFrame("Library Management System");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        // Book Name
        panel.add(new JLabel("Book Name:"));
        bookNameField = new JTextField();
        panel.add(bookNameField);

        // Author Name
        panel.add(new JLabel("Author Name:"));
        authorNameField = new JTextField();
        panel.add(authorNameField);

        // Book ID
        panel.add(new JLabel("Book ID:"));
        bookIDField = new JTextField();
        panel.add(bookIDField);

        // Price
        panel.add(new JLabel("Price:"));
        priceField = new JTextField();
        panel.add(priceField);

        // Quantity
        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        // Add Book Button
        JButton addBookButton = new JButton("Add Book");
        panel.add(addBookButton);
        addBookButton.addActionListener(new AddBookListener());

        // View Book List Button
        JButton viewBooksButton = new JButton("View Books");
        panel.add(viewBooksButton);
        viewBooksButton.addActionListener(new ViewBooksListener());

        // Issue Book Button
        JButton issueBookButton = new JButton("Issue Book");
        panel.add(issueBookButton);
        issueBookButton.addActionListener(new IssueBookListener());

        // Return Book Button
        JButton returnBookButton = new JButton("Return Book");
        panel.add(returnBookButton);
        returnBookButton.addActionListener(new ReturnBookListener());

        // Output Area
        outputArea = new JTextArea(10, 50);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    // Add Book Listener
    class AddBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String bookName = bookNameField.getText();
            String authorName = authorNameField.getText();
            String bookID = bookIDField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Book book = new Book(bookName, authorName, bookID, price, quantity);
            bookMap.put(bookID, book);

            outputArea.append("Book added successfully: " + book + "\n");
            clearFields();
        }
    }

    // View Books Listener
    class ViewBooksListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            outputArea.setText("Books Available:\n");
            for (Book book : bookMap.values()) {
                outputArea.append(book + "\n");
            }
        }
    }

    // Issue Book Listener
    class IssueBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String bookID = bookIDField.getText();
            Book book = bookMap.get(bookID);
            if (book != null && book.getQuantity() > 0) {
                book.issueBook();
                outputArea.append("Book issued successfully: " + book.getBookName() + "\n");
            } else {
                outputArea.append("Book not available or out of stock.\n");
            }
            clearFields();
        }
    }

    // Return Book Listener
    class ReturnBookListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String bookID = bookIDField.getText();
            Book book = bookMap.get(bookID);
            if (book != null) {
                book.returnBook();
                outputArea.append("Book returned successfully: " + book.getBookName() + "\n");
            } else {
                outputArea.append("Book not found.\n");
            }
            clearFields();
        }
    }

    // Helper Method to Clear Fields
    private void clearFields() {
        bookNameField.setText("");
        authorNameField.setText("");
        bookIDField.setText("");
        priceField.setText("");
        quantityField.setText("");
    }
}

// Book Class representing individual book details
class Book {
    private String bookName;
    private String authorName;
    private String bookID;
    private double price;
    private int quantity;

    public Book(String bookName, String authorName, String bookID, double price, int quantity) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.bookID = bookID;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBookName() {
        return bookName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void issueBook() {
        quantity--;
    }

    public void returnBook() {
        quantity++;
    }

    @Override
    public String toString() {
        return "Book Name: " + bookName + ", Author: " + authorName + ", ID: " + bookID + ", Price: " + price + ", Quantity: " + quantity;
    }
}
