package com.example.jsf.Bean.controller;

import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Project;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ManagedBean(name = "editBean")
public class EditBean {
    private Project editProject;
    private String editId;
    private String editTitle;
    private String editSummary;
    private String editDescription;
    private String editKeyword;
    private String editType;
    private String editCollaborator;
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";

    public EditBean() {
        init();
    }

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.editProject = (Project) session.getAttribute("currentProject");
        this.editId = editProject.getId();
        this.editTitle = editProject.getTitle();
        this.editSummary = editProject.getSummary();
        this.editDescription = editProject.getDescription();
        this.editKeyword = editProject.getKeyword();
        this.editType = editProject.getType();
        this.editCollaborator = editProject.getCollaborator();
    }

    public String save(boolean isChanged) {
        if (isChanged) {
            if (!isDataValid()) {
                return "edit.xhtml";
            } else {
                editProject.setTitle(editTitle);
                editProject.setSummary(editSummary);
                editProject.setDescription(editDescription);
                editProject.setKeywords(GetAsList.getAsList(editKeyword));
                editProject.setKeyword(editProject.getKeywords().toString());
                editProject.setType(editType);
                editProject.setCollaborators(GetAsList.getAsList(editCollaborator));
                editProject.setCollaborator(editProject.getCollaborators().toString());
                try {
                    Class.forName(driverClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    return "edit.xhtml";
                }
                // Save the updated project details to the database
                try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
                    String query = "UPDATE project_info SET title = ?, summary = ?, description = ?, keywords = ?, type = ?, collaborators = ? WHERE id = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setString(1, editTitle);
                        stmt.setString(2, editSummary);
                        stmt.setString(3, editDescription);
                        stmt.setString(4, editProject.getKeyword());
                        stmt.setString(5, editType);
                        stmt.setString(6, editProject.getCollaborator());
                        stmt.setString(7, editId);
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return "projectDetails.xhtml";
    }

    private boolean isDataValid() {
        // 进行数据验证，判断保存的数据是否为空
        if (editTitle == null || editTitle.isEmpty()) {
            // 标题为空
            return false;
        }
        if (editSummary == null || editSummary.isEmpty()) {
            // 摘要为空
            return false;
        }
        if (editDescription == null || editDescription.isEmpty()) {
            // 标题为空
            return false;
        }
        if (editKeyword == null || editKeyword.isEmpty()) {
            // 摘要为空
            return false;
        }
        if (editType == null || editType.isEmpty()) {
            // 标题为空
            return false;
        }
        if (editCollaborator == null || editCollaborator.isEmpty()) {
            // 摘要为空
            return false;
        }
        return true; // 所有字段都不为空，数据有效
    }

    public String getEditId() {
        return editId;
    }

    public void setEditId(String editId) {
        this.editId = editId;
    }

    public String getEditTitle() {
        return editTitle;
    }

    public void setEditTitle(String editTitle) {
        this.editTitle = editTitle;
    }

    public String getEditSummary() {
        return editSummary;
    }

    public void setEditSummary(String editSummary) {
        this.editSummary = editSummary;
    }

    public String getEditDescription() {
        return editDescription;
    }

    public void setEditDescription(String editDescription) {
        this.editDescription = editDescription;
    }

    public String getEditType() {
        return editType;
    }

    public void setEditType(String editType) {
        this.editType = editType;
    }

    public String getEditKeyword() {
        return editKeyword;
    }

    public void setEditKeyword(String editKeyword) {
        this.editKeyword = editKeyword;
    }

    public String getEditCollaborator() {
        return editCollaborator;
    }

    public void setEditCollaborator(String editCollaborator) {
        this.editCollaborator = editCollaborator;
    }

    @Override
    public String toString() {
        return "EditBean{" +
                "editProject=" + editProject +
                ", editId=" + editId +
                ", editTitle='" + editTitle + '\'' +
                ", editSummary='" + editSummary + '\'' +
                ", editDescription='" + editDescription + '\'' +
                ", editKeyword='" + editKeyword + '\'' +
                ", editType='" + editType + '\'' +
                ", editCollaborator='" + editCollaborator + '\'' +
                '}';
    }
}
