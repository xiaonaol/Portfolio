package com.example.jsf.Bean.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;

@ManagedBean
@RequestScoped
public class RegisterBean {
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private String loggedInUserAccount;
    private String account;
    private String password;

    public String register() {
        if (isUserExists(account)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Error", "Account already exists"));
            return null;
        }else{
            this.loggedInUserAccount = account;
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("loggedInUserAccount",loggedInUserAccount);
        }

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "INSERT INTO user_info (account, password) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, account);
                stmt.setString(2, password);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return "portfolioCreate.xhtml?faces-redirect=true";
    }

    private boolean isUserExists(String account) {
        // Replace this code with your actual database validation logic

        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "SELECT * FROM user_info WHERE account = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, account);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return true; // User already exists
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // User does not exist
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
// Getters and setters
}
