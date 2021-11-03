package dbtestingshayaan;

import java.sql.*;

public class DatabaseConnectivity {

    public static void main(String[] args) throws SQLException {

        String url = "jdbc:mysql://sql5.freesqldatabase.com:3306/sql5447070"; //URL of database to be connected
        Connection myConn = DriverManager.getConnection(url, "sql5447070", "AxQ1YdG6MP"); //Connect to database (Requires JDBC) [Default username:root, pw empty]
        Statement statement= myConn.createStatement(); //Create a Statement object to run SQL statements on DB

        String query0="CREATE TABLE IF NOT EXISTS STUDENT ("+  // Initial query to create table if not already present in DB
                "name VARCHAR(30) NOT NULL," +
                "regno INT(8) PRIMARY KEY," +
                "branch VARCHAR(30) NOT NULL" +
                ")";

        statement.executeUpdate(query0); //executeUpdate(statement) is used to run DDL (e.g. CREATE) or DML (e.g INSERT) commands


        String query1 = "INSERT INTO STUDENT VALUES (?, ?, ?)";
        PreparedStatement preStat = myConn.prepareStatement(query1); //PreparedStatement is a subclass of Statement that supports data substitution and can execute a statement multiple times
        preStat.setString(1, "Betty"); //Using the setter methods to substitute values corresponding to the ?s
        preStat.setInt(2, 87375493);
        preStat.setString(3, "BOC");
        preStat.executeUpdate(); //Executing the statement using executeUpdate()


        String query2 = "SELECT * FROM STUDENT;";

        ResultSet result = statement.executeQuery(query2); //executeQuery(statement) is used to run DQL command (i.e. SELECT) and returns a ResultSet object

        while(result.next()) { //Now iterating over the ResultSet object to print the results of the query. next() returns false after all rows exhausted, else returns true
            int regno = result.getInt("regno"); //Getters extract corresponding data from column names
            String name = result.getString("name");
            String branch = result.getString("branch");
            System.out.println("Name - " + name);
            System.out.println("Branch - " + branch);
            System.out.println("Registration number - " + regno);
        }
    }
}