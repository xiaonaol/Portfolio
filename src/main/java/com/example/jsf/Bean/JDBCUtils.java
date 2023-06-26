package com.example.jsf.Bean;

import java.sql.*;


public class JDBCUtils {
    private JDBCUtils(){}
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/portfolio";
    private static final String username = "root";
    private static final String password = "123456";
    private static Connection conn;

    static{
        try{
            Class.forName(driverClass);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static void close(Connection conn, Statement stat, ResultSet rs) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Connection conn, Statement stat) {
        close(conn,stat,null);
    }
}
