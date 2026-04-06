package expensetracker;

import java.io.*;
import java.util.*;

class Expense implements Serializable {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }

    public String toString() {
        return category + " - ₹" + amount;
    }
}

public class ExpenseTracker {
    static List<Expense> expenses = new ArrayList<>();
    static final String FILE_NAME = "expenses.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadExpenses();

        while (true) {
            System.out.println("\n==== Expense Tracker ====");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Show Total");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addExpense(sc);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    showTotal();
                    break;
                case 4:
                    saveExpenses();
                    System.out.println("Data saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void addExpense(Scanner sc) {
        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter amount: ");
        double amount = sc.nextDouble();

        expenses.add(new Expense(category, amount));
        System.out.println("Expense added!");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
            return;
        }

        System.out.println("\n--- Expenses ---");
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }

    static void showTotal() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.amount;
        }
        System.out.println("Total Expense: ₹" + total);
    }

    static void saveExpenses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Expense e : expenses) {
                pw.println(e.category + "," + e.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    static void loadExpenses() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(",");
                expenses.add(new Expense(data[0], Double.parseDouble(data[1])));
            }
        } catch (Exception e) {
            System.out.println("Error loading file.");
        }
    }
}