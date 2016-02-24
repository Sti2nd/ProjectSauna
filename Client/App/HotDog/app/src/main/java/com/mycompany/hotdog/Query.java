package com.mycompany.hotdog;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

/**
 * Represents a query.
 */
public class Query {
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    public Query(Connection connection, Statement statement, ResultSet resultSet) {
        this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}