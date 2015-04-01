package com.josiah;

import java.sql.*;
import java.util.Scanner;

public class RubiksCube {

    private static String protocol = "jdbc:derby:";
    private static String dbName = "rubiksCubeDB";

    //  Database credentials - for embedded, usually defaults. A client-server DB would need to authenticate connections
    private static final String USER = "username";
    private static final String PASS = "password";

    public static void main(String[] args) {
        Statement statement = null;
        Connection conn = null;
        ResultSet rs = null;
        //count is just to keep track of the amount of added rows
        int count = 0;

        try {
            conn = DriverManager.getConnection(protocol + dbName + ";create=true", USER, PASS);
            statement = conn.createStatement();
            try {
            //create table here
            String createTableSQL = "CREATE TABLE Cubes (rubiksSolver varchar(100), timeTakenInSec double)";
            statement.executeUpdate(createTableSQL);
            System.out.println("Cubes table created");


            } catch (SQLException se) {
                System.out.println("Cubes table already exists");
            }

            //Adds data
            String prepStatInsert = "INSERT INTO Cubes Values ( ? , ? )";
            PreparedStatement psInsert = conn.prepareStatement(prepStatInsert);
            psInsert.setString(1, "Cubestormer II Robot");
            psInsert.setDouble(2, 5.270);
            psInsert.executeUpdate();
            count++;

            psInsert.setString(1, "Fakhri Raihaan (using his feet)");
            psInsert.setDouble(2, 27.93);
            psInsert.executeUpdate();
            count++;

            psInsert.setString(1, "Ruxin Liu (age 3)");
            psInsert.setDouble(2, 99.33);
            psInsert.executeUpdate();
            count++;

            psInsert.setString(1, "Mats Valk (human record holder)");
            psInsert.setDouble(2, 6.27);
            psInsert.executeUpdate();
            count++;

            //Scanner for user input
            Scanner sc = new Scanner(System.in);
            System.out.println("Would you like to add to the table?");
            String userAnswer = sc.nextLine();
            while (!userAnswer.equalsIgnoreCase("no")){
                //saves each part to it's correct position
                System.out.println("Who/what solved the Rubiks cube?");
                String one = sc.nextLine();
                System.out.println("How fast in seconds did they/it solve the Rubiks cube?");
                String temp = sc.nextLine();
                Double two = Double.parseDouble(temp);
                //asks if there is more data and
                System.out.println("Would you like to add another?");
                userAnswer = sc.nextLine();
                //puts the data into the correct spot and updates the table
                psInsert.setString(1, one);
                psInsert.setDouble(2, two);
                psInsert.executeUpdate();
                count++;
            }
            sc.close();
            System.out.println(count + " rows of data were added to the Cubes table\n");

            //Fetch all the data and display it.
            String fetchAllDataSQL = "SELECT * FROM Cubes";
            rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()) {
                String solver = rs.getString("rubiksSolver");
                double time = rs.getDouble("timeTakenInSec");
                System.out.println("Solved by: " + solver + "\n" + "Solved in: " + time + " seconds \n");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            //A finally block runs whether an exception is thrown or not. Close resources and tidy up whether this code worked or not.
            try {
                if (statement != null) {
                    statement.close();
                    System.out.println("Statement closed");
                }
            } catch (SQLException se){
                //Closing the connection could throw an exception too
                se.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();  //Close connection to database
                    System.out.println("Database connection closed");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("End of program");

    }
}
