import com.coinmanagnment.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);
        CoinManagnment cm = new CoinManagnment();
        cm.loadFromDatabase();

        while (true) {
            System.out.println("\n========= COIN MANAGEMENT SYSTEM =========");
            System.out.println("1. Add Coin");
            System.out.println("2. Search by Country");
            System.out.println("3. Search by Year Of Minting");
            System.out.println("4. Search by Current Value");
            System.out.println("5. Search by Denomination and Country");
            System.out.println("6. Search by Country and Year");
            System.out.println("7. Search by Country, Denomination and Year");
            System.out.println("8. Search by Acquire Date and Country");
            System.out.println("9. Update Coin Value");
            System.out.println("10. Remove Coin");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter Country: ");
                    String country = sc.nextLine();
                    System.out.print("Enter Denomination: ");
                    int denomination = sc.nextInt();
                    System.out.print("Enter Year Of Minting: ");
                    int yom = sc.nextInt();
                    System.out.print("Enter Current Value: ");
                    int currVal = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter Acquire Date (yyyy-mm-dd): ");
                    Date acquireDate = Date.valueOf(sc.nextLine());

                    Coin newCoin = new Coin(country, denomination, currVal, yom, acquireDate);
                    Coin added = cm.addCoin(newCoin);
                    if (added != null)
                        System.out.println("✅ Coin Added Successfully:\n" + added);
                }

                case 2 -> {
                    System.out.print("Enter Country to Search: ");
                    String country = sc.nextLine();
                    List<Coin> res = cm.searchByCountry(country);
                    res.forEach(System.out::println);
                }

                case 3 -> {
                    System.out.print("Enter Year of Minting: ");
                    int year = sc.nextInt();
                    List<Coin> res = cm.searchByYearOfMinting(year);
                    res.forEach(System.out::println);
                }

                case 4 -> {
                    System.out.print("Enter Current Value to Search: ");
                    int val = sc.nextInt();
                    List<Coin> res = cm.searchByCurrentValue(val);
                    res.forEach(System.out::println);
                }

                case 5 -> {
                    System.out.print("Enter Denomination: ");
                    int deno = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Country: ");
                    String country = sc.nextLine();
                    List<Coin> res = cm.searchByDenominationAndCountry(deno, country);
                    res.forEach(System.out::println);
                }

                case 6 -> {
                    System.out.print("Enter Country: ");
                    String country = sc.nextLine();
                    System.out.print("Enter Year: ");
                    int year = sc.nextInt();
                    List<Coin> res = cm.searchByCountryAndYear(country, year);
                    res.forEach(System.out::println);
                }

                case 7 -> {
                    System.out.print("Enter Country: ");
                    String country = sc.nextLine();
                    System.out.print("Enter Denomination: ");
                    int denomination = sc.nextInt();
                    System.out.print("Enter Year: ");
                    int year = sc.nextInt();
                    List<Coin> res = cm.searchByCountryDenominationYear(country, denomination, year);
                    res.forEach(System.out::println);
                }

                case 8 -> {
                    System.out.print("Enter Country: ");
                    String country = sc.nextLine();
                    System.out.print("Enter Acquire Date (yyyy-mm-dd): ");
                    Date date = Date.valueOf(sc.nextLine());
                    List<Coin> res = cm.searchByAcquireDateAndCountry(date, country);
                    res.forEach(System.out::println);
                }

                case 9 -> {
                    System.out.print("Enter Coin ID to Update: ");
                    int coinId = sc.nextInt();
                    System.out.print("Enter New Current Value: ");
                    double newVal = sc.nextDouble();
                    Coin updated = cm.updateCoin(coinId, newVal);
                    if (updated != null)
                        System.out.println("✅ Coin Updated:\n" + updated);
                }

                case 10 -> {
                    System.out.print("Enter Coin ID to Remove: ");
                    int removeId = sc.nextInt();
                    Coin removed = cm.removeCoin(removeId);
                    if (removed != null)
                        System.out.println("✅ Coin Removed:\n" + removed);
                }

                case 11 -> {
                    System.out.println("Thank you for using Coin Management System!");
                    System.exit(0);
                }

                default -> System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }
}
