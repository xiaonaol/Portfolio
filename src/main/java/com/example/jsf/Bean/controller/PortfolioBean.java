package com.example.jsf.Bean.controller;
import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Portfolio;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
@ManagedBean(name = "portfolioBean")
@ViewScoped
public class PortfolioBean {
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private ArrayList<Portfolio> portfolioList = new ArrayList<>();
    private String loggedInUserAccount;
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.loggedInUserAccount = (String) session.getAttribute("loggedInUserAccount");
    }
    public PortfolioBean() {
        portfolioList = fetchPortfoliosFromDatabase();
    }
    private Portfolio currentPortfolio;

    public ArrayList<Portfolio> fetchPortfoliosFromDatabase() {
        ArrayList<Portfolio> portfolios = new ArrayList<>();
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return portfolios;
        }
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "SELECT * FROM portfolio_info";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    String Classes = rs.getString("Classes");
                    List<String> interests = GetAsList.getAsList(rs.getString("interests"));
                    String experience = rs.getString("experience");
                    List<String> skills = GetAsList.getAsList(rs.getString("skills"));
                    String careergoal = rs.getString("careergoal");
                    String account= rs.getString("account");

                    Portfolio portfolio = new Portfolio(id, name, Classes, interests, experience, skills, careergoal, account);
                    portfolios.add(portfolio);
                    System.out.println(portfolio.getName());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }

    public List<Portfolio> getPortfolioList() {
        return portfolioList;
    }

    public List<Portfolio> getUserPortfolios() {
        List<Portfolio> userPortfolios = new ArrayList<>();
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getAccount().equals(loggedInUserAccount)) {
                userPortfolios.add(portfolio);
            }
        }
        return userPortfolios;
    }
    public Portfolio getPortfolioById(String id) {
        for (Portfolio portfolio : portfolioList) {
            if (portfolio.getId().equals(id)){
                return portfolio;
            }
        }
        return null;
    }
    public String getSelectedPortfolio(String id) {
        System.out.println(id);
        this.currentPortfolio = getPortfolioById(id);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("currentPortfolio", currentPortfolio);
        return "editPortfolio.xhtml?faces-redirect=true";
    }

    public Portfolio getCurrentPortfolio() {
        return currentPortfolio;
    }

}
