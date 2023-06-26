package com.example.jsf.Bean.controller;

import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Portfolio;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean(name = "editPortfolioBean")
public class EditPortfolioBean {
    private Portfolio editPortfolio;
    private String editPId;
    private String editPName;
    private String editPClasses;
    private String editPinterest;
    private String editPExperience;
    private String editPSkill;
    private String editPCareer;
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    public EditPortfolioBean() {
        init();
    }

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.editPortfolio = (Portfolio) session.getAttribute("currentPortfolio");
        this.editPId = editPortfolio.getId();
        this.editPName = editPortfolio.getName();
        this.editPClasses = editPortfolio.getClasses();
        this.editPinterest = editPortfolio.getInterest();
        this.editPExperience = editPortfolio.getExperience();
        this.editPSkill = editPortfolio.getSkill();
        this.editPCareer = editPortfolio.getCareGoals();
    }
    public String save(boolean isChanged) {
        if (isChanged) {
            if (!isDataValid()) {
                return "editPortfolio.xhtml";
            } else {
                editPortfolio.setName(editPName);
                editPortfolio.setClasses(editPClasses);
                editPortfolio.setInterests(GetAsList.getAsList(editPinterest));
                editPortfolio.setInterest(editPortfolio.getInterests().toString());
                editPortfolio.setExperience(editPExperience);
                editPortfolio.setSkills(GetAsList.getAsList(editPSkill));
                editPortfolio.setSkill(editPortfolio.getSkills().toString());
                editPortfolio.setCareGoals(editPCareer);
                try {
                    Class.forName(driverClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return "editPortfolio.xhtml";
                }
                // Save the updated project details to the database
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    String query = "UPDATE portfolio_info SET name = ?,Classes = ?, interests = ?, experience = ?, skills = ?, careergoal = ? WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, editPName);
                        stmt.setString(2, editPClasses);
                        stmt.setString(3, editPortfolio.getInterest());
                        stmt.setString(4, editPExperience);
                        stmt.setString(5, editPortfolio.getSkill());
                        stmt.setString(6, editPCareer);
                        stmt.setString(7, editPId);
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "homePage.xhtml";
    }
    private boolean isDataValid() {
        // 进行数据验证，判断保存的数据是否为空
        if (editPName == null || editPName.isEmpty()) {
            // 标题为空
            return false;
        }
        if (editPClasses == null || editPClasses.isEmpty()) {
            // 摘要为空
            return false;
        }
        if (editPinterest == null || editPinterest.isEmpty()) {
            // 标题为空
            return false;
        }
        if (editPExperience == null || editPExperience.isEmpty()) {
            // 摘要为空
            return false;
        }
        if (editPSkill == null || editPSkill.isEmpty()) {
            // 标题为空
            return false;
        }
            if (editPCareer== null || editPCareer.isEmpty()) {
            // 摘要为空
            return false;
        }
        return true; // 所有字段都不为空，数据有效
    }

    public String getEditPId() {
        return editPId;
    }

    public void setEditPId(String editPId) {
        this.editPId = editPId;
    }

    public String getEditPName() {
        return editPName;
    }

    public void setEditPName(String editPName) {
        this.editPName = editPName;
    }

    public String getEditPClasses() {
        return editPClasses;
    }

    public void setEditPClasses(String editPClasses) {
        this.editPClasses = editPClasses;
    }

    public String getEditPinterest() {
        return editPinterest;
    }

    public void setEditPinterest(String editPinterest) {
        this.editPinterest = editPinterest;
    }

    public String getEditPExperience() {
        return editPExperience;
    }

    public void setEditPExperience(String editPExperience) {
        this.editPExperience = editPExperience;
    }

    public String getEditPSkill() {
        return editPSkill;
    }

    public void setEditPSkill(String editPSkill) {
        this.editPSkill = editPSkill;
    }

    public String getEditPCareer() {
        return editPCareer;
    }

    public void setEditPCareer(String editPCareer) {
        this.editPCareer = editPCareer;
    }

    @Override
    public String toString() {
        return "EditPortfolioBean{" +
                "editPId='" + editPId + '\'' +
                ", editPName='" + editPName + '\'' +
                ", editPClasses='" + editPClasses + '\'' +
                ", editPinterest='" + editPinterest + '\'' +
                ", editPExperience='" + editPExperience + '\'' +
                ", editPSkill='" + editPSkill + '\'' +
                ", editPCareer='" + editPCareer + '\'' +
                '}';
    }
}
