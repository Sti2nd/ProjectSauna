package com.mycompany.hotdog;

import com.mycompany.hotdog.Query;

import java.sql.*;
import java.sql.ResultSet;

/**
 * Provides essential methods for database connections.
 */
public class DBConnector {

    /**
     * Establishes a connection to the database.
     * @return a connection or null if no connection was established.
     */
    public static Connection getConnection() {
        String url = "jdbc:mysql://mysql.stud.ntnu.no/roberei_hotdog_db";
        String username = "roberei_hotdog";
        String password = "pekka";
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Makes a query.
     * @param query a query instance
     * @return a query or null.
     */
    public static Query makeQuery(String query) {
        Connection connection = null;
        Statement st = null;
        ResultSet result = null;
        Query queryObject = null;

        try {
            connection = getConnection();
            st = connection.createStatement();
            result = st.executeQuery(query);
            queryObject = new Query(connection, st, result);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return queryObject;
    }

    /**
     * Makes a statement
     * @param statement the statement
     */
    public static void makeStatement(String statement) {
        Connection connection = null;
        Statement st = null;
        try {
            connection = getConnection();
            st = connection.createStatement();
            st.executeUpdate(statement);
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (st != null) {
                    st.close();
                }
                if (connection != null) {
                    connection.close();
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}