package com.parent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    /*
     ✅ Logic:
     1. Pehle Render ke Environment Variables use honge
     2. Agar wo null hue (local testing ke time), to local fallback use hoga
     3. Railway credentials yahan hardcoded nahi hain — safe for GitHub
    */

    private static final String URL =
            System.getenv("DB_URL") != null
                    ? System.getenv("DB_URL")
                    : "jdbc:mysql://localhost:3306/job_portal";

    private static final String USER =
            System.getenv("DB_USER") != null
                    ? System.getenv("DB_USER")
                    : "root";

    private static final String PASSWORD =
            System.getenv("DB_PASS") != null
                    ? System.getenv("DB_PASS")
                    : "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Database connected successfully!");
            return connection;
        } catch (ClassNotFoundException e) {
            throw new SQLException("⚠️ MySQL Driver not found", e);
        } catch (SQLException e) {
            System.err.println("❌ Database connection error: " + e.getMessage());
            throw e;
        }
    }
}
//package com.parent.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//
//    /*
//     * ✅ Logic:
//     * 1. First preference → Environment variables (Render / production)
//     * 2. Fallback → Hardcoded Railway connection (for free plan or local use)
//     * 3. Safe for GitHub (no real password stored in public repo)
//     */
//
//    // Prefer environment variable if available
//    private static final String URL = System.getenv("DB_URL") != null ?
//            System.getenv("DB_URL") :
//            "jdbc:mysql://containers-asia-southeast1-eqsg3a.railway.app:3306/railway";
//
//    private static final String USER = System.getenv("DB_USER") != null ?
//            System.getenv("DB_USER") :
//            "root";
//
//    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ?
//            System.getenv("DB_PASSWORD") :
//            "eEoehITTzgqjPQrdwMVPWjPqllQIJmve";
//
//    // Method to establish connection
//    public static Connection getConnection() throws SQLException {
//        try {
//            // Load MySQL driver (optional in modern JDBC, but safe)
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.err.println("⚠️ MySQL JDBC Driver not found!");
//        }
//
//        // Create and return connection
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//
//    // For quick local testing
//    public static void main(String[] args) {
//        try (Connection conn = getConnection()) {
//            if (conn != null) {
//                System.out.println("✅ Database connected successfully!");
//            } else {
//                System.out.println("❌ Failed to connect to database.");
//            }
//        } catch (SQLException e) {
//            System.err.println("❌ Database connection error: " + e.getMessage());
//        }
//    }
//}
//
