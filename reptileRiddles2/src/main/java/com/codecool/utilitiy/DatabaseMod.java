package com.codecool.utilitiy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseMod {

    public void PostgresTruncateMultipleTables() {

        String DB_URL = "jdbc:postgresql://localhost:5000/riddle";
        String DB_USER = "postgres";
        String DB_PASSWORD = "postgres";
        
        String truncateSQL = "TRUNCATE TABLE answer, quiz, task, user_entity CASCADE";

            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement statement = connection.createStatement()) {

                statement.executeUpdate(truncateSQL);

                System.out.println("Tables Successfully emptied.");

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
}
