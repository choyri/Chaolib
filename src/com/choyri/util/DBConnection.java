package com.choyri.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据库连接
 */
public class DBConnection {

    private Connection conn = null;

    // 数据库连接池
    public DBConnection() {
        DataSource ds = null;
        try {
            ds = (DataSource) (new InitialContext()).lookup("java:comp/env/jdbc/mariadb");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        try {
            conn = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    // 传统连接
//    public DBConnection() {
//        try {
//            conn = DriverManager.getConnection("jdbc:mariadb://localhost:3306/library", "root", "root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public Connection getConnection() {
        return conn;
    }

    public void close() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}