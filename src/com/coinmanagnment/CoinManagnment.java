package com.coinmanagnment;

import java.sql.*;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoinManagnment {

    private Connection con;
    private ArrayList<Coin> coins = new ArrayList<>();

    public CoinManagnment() {
        try {
            this.con = Connectivity.getConnectivity();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFromDatabase() throws SQLException {
        String sql = "SELECT *  FROM coins";

        Statement stmt = null;
        Coin coin;
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            {
                while (rs.next()) {
                    coin = new Coin(rs.getString("country"), rs.getInt("denomination"), rs.getInt("currentValue"), rs.getInt("yearOfMinting"), rs.getDate("acquireDate"));
                    coins.add(coin);

                }
            }


        } catch (SQLException e) {
            System.out.println("Error,PRoblrm in Loading Database");

        }
    }

    public Coin addCoin(Coin coin) throws SQLException {

        String insertQuery = "insert into coins (country,denomination, yearOfMinting, currentValue, acquireDate) values (?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, coin.getCountry());
        ps.setInt(2, coin.getDenomination());
        ps.setDouble(3, coin.getYom());
        ps.setInt(4, coin.getCurrent_Value());
        ps.setDate(5, coin.getAcquried_date());
        int res = ps.executeUpdate();

        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        int generatedCoinID = generatedKeys.getInt(1);
        coin.setCoin_ID(generatedCoinID);

        //on successfully add i want is to send meesage to user with all coin details
        //sendSms(number, "coin added" + coin)

        //  System.out.println(coin);
        if (res == 1) {
            loadFromDatabase();
            return coin;
        }
        return null;
    }

    // searchbycountry
    public List<Coin> searchByCountry(String countryname) {
        return (List<Coin>) coins.stream().filter(coin -> coin.getCountry().equalsIgnoreCase(countryname)).toList();
    }

    //searchby yearofminting
    public List<Coin> searchByYearOfMinting(int year) {
        return coins.stream().filter(coin -> coin.getYom() == year).toList();

    }

    //  searchby currentvalue
    public List<Coin> searchByCurrentValue(int currentvalue) {
        return coins.stream().filter(coin -> coin.getCurrent_Value() == currentvalue).toList();
    }

    // search deno and country
    public List<Coin> searchByDenominationAndCountry(int deno, String country) {
        return coins.stream().filter(coin -> coin.getDenomination() == deno && coin.getCountry().equalsIgnoreCase(country)).toList();
    }

    //country + year
    public List<Coin> searchByCountryAndYear(String country, int year) {
        return coins.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getYom() == year).toList();
    }

    //Country + denomination + year
    public List<Coin> searchByCountryDenominationYear(String country, int denomination, int year) {
        return coins.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getDenomination() == denomination && c.getYom() == year).toList();
    }

    //Acquired Date + Country
    public List<Coin> searchByAcquireDateAndCountry(Date acquireDate, String country) {
        return coins.stream().filter(c -> c.getCountry().equalsIgnoreCase(country) && c.getAcquried_date() == acquireDate).toList();
    }

    public Coin updateCoin(int coinId, double currVal) {
        String selectQuery = "SELECT * FROM coins WHERE coinid = ?";
        String updateQuery = "UPDATE coins SET currentvalue = ? WHERE coinid = ?";

        try (
                PreparedStatement selectStmt = con.prepareStatement(selectQuery);
                PreparedStatement updateStmt = con.prepareStatement(updateQuery)
        ) {
            // Step 1: Check if the coin exists
            selectStmt.setInt(1, coinId);
            ResultSet rs = selectStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("❌ No coin found with ID: " + coinId);
                return null;
            }

            // Step 2: Create Coin object from current DB data
            Coin coin = new Coin(

                    rs.getString("country"),
                    rs.getInt("denomination"),
                    rs.getInt("currentvalue"),  // This is the OLD value
                    rs.getInt("yearofminting"),
                    rs.getDate("acquiredate")
            );

            // Step 3: Update the current value in the database
            updateStmt.setDouble(1, currVal);
            updateStmt.setInt(2, coinId);

            int result = updateStmt.executeUpdate();

            if (result == 1) {
                // Step 4: Update the currentValue in the Java object as well
                coin.setCurrent_Value((int) currVal);

                // Optional: Reload full database if you’re keeping a local cache
                loadFromDatabase();

                return coin; // ✅ Return updated coin
            } else {
                System.out.println("⚠️ Update failed for coin ID: " + coinId);
            }

        } catch (SQLException e) {
            System.err.println("❌ SQL Error: " + e.getMessage());
        }

        return null;
    }

      public Coin removeCoin(int coinId)  {
          String selectQue = "SELECT * FROM coins WHERE coinId = ?";
          String deleteQue = "DELETE FROM coins WHERE coinId = ?";

          PreparedStatement ss = null;
          try {
              ss = con.prepareStatement(selectQue);
              PreparedStatement ds = con.prepareStatement(deleteQue);

              ss.setInt(1, coinId);
              ResultSet rs = ss.executeQuery();

              if (!rs.next()) {
                  System.out.println("❌ No coin found with ID: " + coinId);
                  return null;
              }

              Coin coin = new Coin(rs.getString("country"),
                      rs.getInt("denomination"),
                      rs.getInt("currentvalue"),  // This is the OLD value
                      rs.getInt("yearofminting"),
                      rs.getDate("acquiredate")
              );
              ds.setInt(1, coinId);
              int res3 = ds.executeUpdate();
              if (res3 == 1) {
                  loadFromDatabase();
                  //return coin;
              }
              return coin;



          } catch (SQLException ex) {
              System.out.println("UNABLE TO REMOVE COIN " + coinId);
              //throw new RuntimeException(ex);

          }
          return null;
      }




}