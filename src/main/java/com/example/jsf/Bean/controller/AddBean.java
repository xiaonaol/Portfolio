package com.example.jsf.Bean.controller;

import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Project;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;

@ManagedBean(name = "addBean")
public class AddBean {
    private Project addProject;
    private String addId;
    private String addTitle;
    private String addSummary;
    private String addDescription;
    private String addKeyword;
    private String addType;
    private String addCollaborator;
    private String addAccount;
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
    public AddBean() {
        addProject = new Project();
    }

    public String getAddId() {
        return addId;
    }

    public void setAddId(String addId) {
        this.addId = addId;
    }

    public String getAddTitle() {
        return addTitle;
    }

    public void setAddTitle(String addTitle) {
        this.addTitle = addTitle;
    }

    public String getAddSummary() {
        return addSummary;
    }

    public void setAddSummary(String addSummary) {
        this.addSummary = addSummary;
    }

    public String getAddDescription() {
        return addDescription;
    }

    public void setAddDescription(String addDescription) {
        this.addDescription = addDescription;
    }

    public String getAddType() {
        return addType;
    }

    public void setAddType(String addType) {
        this.addType = addType;
    }

    public String getAddKeyword() {
        return addKeyword;
    }

    public void setAddKeyword(String addKeyword) {
        this.addKeyword = addKeyword;
    }

    public String getAddCollaborator() {
        return addCollaborator;
    }

    public void setAddCollaborator(String addCollaborator) {
        this.addCollaborator = addCollaborator;
    }
    @Override
    public String toString() {
        return "AddBean{" +
                "addProject=" + addProject +
                ", addId=" + addId +
                ", addTitle='" + addTitle + '\'' +
                ", addSummary='" + addSummary + '\'' +
                ", addDescription='" + addDescription + '\'' +
                ", addKeyword='" + addKeyword + '\'' +
                ", addType='" + addType + '\'' +
                ", addCollaborator='" + addCollaborator + '\'' +
                '}';
    }

    private boolean isIdAlreadyExists(String id) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "SELECT COUNT(*) FROM project_info WHERE id = ?";
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

    public String add(boolean isChanged) {
        if (isChanged) {
            if (!isDataValid()) {
                return "addProject.xhtml";
            } else {
                addProject.setId(addId);
                addProject.setTitle(addTitle);
                addProject.setSummary(addSummary);
                addProject.setDescription(addDescription);
                addProject.setKeywords(GetAsList.getAsList(addKeyword));
                addProject.setKeyword(addProject.getKeywords().toString());
                addProject.setType(addType);
                addProject.setCollaborators(GetAsList.getAsList(addCollaborator));
                addProject.setCollaborator(addProject.getCollaborators().toString());
                addProject.setAccount(loggedInUserAccount);
                try {
                    Class.forName(driverClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return "projectAdd.xhtml";
                }
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    String query = "insert project_info SET id=?,title = ?, summary = ?, description = ?, keywords = ?, type = ?, collaborators = ?,account=? ";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, addProject.getId());
                        stmt.setString(2, addProject.getTitle());
                        stmt.setString(3, addProject.getSummary());
                        stmt.setString(4, addProject.getDescription());
                        stmt.setString(5, addProject.getKeyword());
                        stmt.setString(6, addProject.getType());
                        stmt.setString(7, addProject.getCollaborator());
                        stmt.setString(8, addProject.getAccount());
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
        if (addId == null || addId.isEmpty()) {
            return false;
        }
        if (addTitle == null || addTitle.isEmpty()) {
            return false;
        }
        if (addSummary == null || addSummary.isEmpty()) {
            return false;
        }
        if (addDescription == null || addDescription.isEmpty()) {
            return false;
        }
        if (addKeyword == null || addKeyword.isEmpty()) {
            return false;
        }
        if (addType == null || addType.isEmpty()) {
            return false;
        }
        if (addCollaborator == null || addCollaborator.isEmpty()) {
            return false;
        }
        if (isIdAlreadyExists(addId)) {
            return false;
        }
        return true;
    }
}

