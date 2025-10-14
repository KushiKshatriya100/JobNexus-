////package com.parent.util;
////
////import java.sql.Connection;
////import java.sql.DriverManager;
////import java.sql.SQLException;
////
////public class DBConnection {
////    private static final String URL = "jdbc:mysql://localhost:3306/job_portal";
////    private static final String USER = "root";
////    private static final String PASSWORD = "root";
////
////    public static Connection getConnection() throws SQLException {
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 8+
////            return DriverManager.getConnection(URL, USER, PASSWORD);
////        } catch (ClassNotFoundException e) {
////            throw new SQLException(e);
////        }
////    }
////}
//
//
//package com.parent.util;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBConnection {
//
//    private static final String URL = System.getenv("DB_URL") != null ?
//            System.getenv("DB_URL") :
//            "jdbc:mysql://localhost:3306/railway";
//
//    private static final String USER = System.getenv("DB_USER") != null ?
//            System.getenv("DB_USER") :
//            "root";
//
//    private static final String PASSWORD = System.getenv("DB_PASS") != null ?
//            System.getenv("DB_PASS") :
//            "root";
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            return DriverManager.getConnection(URL, USER, PASSWORD);
//        } catch (ClassNotFoundException e) {
//            throw new SQLException("MySQL Driver not found", e);
//        }
//    }
//}



package com.parent.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // ✅ Single combined DB_URL (username/password included)
    // ✅ Fallback for local development
    private static final String URL = System.getenv("DB_URL") != null ?
            System.getenv("DB_URL") :
            "jdbc:mysql://root:root@localhost:3306/job_portal";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // ✅ Use only URL, username/password embedded in JDBC URL
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }
}
