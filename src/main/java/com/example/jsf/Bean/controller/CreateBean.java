package com.example.jsf.Bean.controller;


import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Portfolio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;

@ManagedBean(name = "createBean")
public class CreateBean {
    private Portfolio createPortfolio;
    private String createId;
    private String createName;
    private String createClasses;
    private String createInterest;
    private String createExperience;
    private String createSkill;
    private String createCareerGoals;
    private String createAccount;
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private String loggedInUserAccount;

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.loggedInUserAccount = (String) session.getAttribute("loggedInUserAccount");
    }

    public CreateBean() {
        createPortfolio= new Portfolio();
    }
    public String create(boolean isChanged) {
        if (isChanged) {
            if (!isDataValid()) {
                return "createPortfolio.xhtml";
            } else {
                createPortfolio.setId(createId);
                createPortfolio.setName(createName);
                createPortfolio.setClasses(createClasses);
                createPortfolio.setInterests(GetAsList.getAsList(createInterest));
                createPortfolio.setInterest(createPortfolio.getInterests().toString());
                createPortfolio.setExperience(createExperience);
                createPortfolio.setSkills(GetAsList.getAsList(createSkill));
                createPortfolio.setSkill(createPortfolio.getSkills().toString());
                createPortfolio.setAccount(loggedInUserAccount);
                try {
                    Class.forName(driverClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return "projectAdd.xhtml";
                }
                // Save the updated project details to the database
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    String query = "insert portfolio_info SET id=?,name = ?,Classes = ?, interests = ?, experience = ?, skills = ?, careergoal = ?,account=? ";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, createPortfolio.getId());
                        stmt.setString(2, createPortfolio.getName());
                        stmt.setString(3, createPortfolio.getClasses());
                        stmt.setString(4, createPortfolio.getInterest());
                        stmt.setString(5, createPortfolio.getExperience());
                        stmt.setString(6, createPortfolio.getSkill());
                        stmt.setString(7, createPortfolio.getCareGoals());
                        stmt.setString(8,createPortfolio.getAccount());
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "login.xhtml";
    }
// ...其他代码...

    private boolean isDataValid() {
        if (createId == null || createId.isEmpty()) {
            return false;
        }
        if (createName == null || createName.isEmpty()) {
            return false;
        }
        if (createClasses == null || createClasses.isEmpty()) {
            return false;
        }
        if (createInterest == null || createInterest.isEmpty()) {
            return false;
        }
        if (createExperience == null || createExperience.isEmpty()) {
            return false;
        }
        if (createSkill == null || createSkill.isEmpty()) {
            return false;
        }
        if (createCareerGoals == null || createCareerGoals.isEmpty()) {
            return false;
        }

        if (isIdAlreadyExists(createId)) {
            return false;
        }
        return true;
    }

    private boolean isIdAlreadyExists(String id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM portfolio_info WHERE id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int count = rs.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateClasses() {
        return createClasses;
    }

    public void setCreateClasses(String createClasses) {
        this.createClasses = createClasses;
    }

    public String getCreateInterest() {
        return createInterest;
    }

    public void setCreateInterest(String createInterest) {
        this.createInterest = createInterest;
    }

    public String getCreateExperience() {
        return createExperience;
    }

    public void setCreateExperience(String createExperience) {
        this.createExperience = createExperience;
    }

    public String getCreateSkill() {
        return createSkill;
    }

    public void setCreateSkill(String createSkill) {
        this.createSkill = createSkill;
    }

    public String getCreateCareerGoals() {
        return createCareerGoals;
    }

    public void setCreateCareerGoals(String createCareerGoals) {
        this.createCareerGoals = createCareerGoals;
    }

    public String getCreateAccount() {
        return createAccount;
    }

    public void setCreateAccount(String createAccount) {
        this.createAccount = createAccount;
    }

    @Override
    public String toString() {
        return "CreateBean{" +
                "createId='" + createId + '\'' +
                ", createName='" + createName + '\'' +
                ", createClasses='" + createClasses + '\'' +
                ", createInterest='" + createInterest + '\'' +
                ", createExperience='" + createExperience + '\'' +
                ", createSkill='" + createSkill + '\'' +
                ", createCareerGoals='" + createCareerGoals + '\'' +
                ", createAccount='" + createAccount + '\'' +
                '}';
    }
}
