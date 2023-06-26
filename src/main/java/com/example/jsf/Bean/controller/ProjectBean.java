package com.example.jsf.Bean.controller;


import com.example.jsf.Bean.util.GetAsList;
import com.example.jsf.Bean.dao.Project;


import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



@ManagedBean(name = "projectBean")
@ViewScoped
public class ProjectBean {
    private static final String driverClass = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/portfolio?useSSL=false";
    private static final String JDBC_USERNAME = "root";
    private static final String JDBC_PASSWORD = "123456";
    private ArrayList<Project> projectList = new ArrayList<>();;
    private Project currentProject;
    private String loggedInUserAccount;
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.loggedInUserAccount = (String) session.getAttribute("loggedInUserAccount");
    }

    public ProjectBean() {
        projectList = fetchProjectsFromDatabase();
    }

    public ArrayList<Project> fetchProjectsFromDatabase() {
        ArrayList<Project> projects = new ArrayList<>();
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return projects;
        }
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)) {
            String query = "SELECT * FROM project_info";
            try (Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String id = rs.getString("id");
                    String title = rs.getString("title");
                    String summary = rs.getString("summary");
                    String description = rs.getString("description");
                    List<String> keywords = GetAsList.getAsList(rs.getString("keywords"));
                    String type = rs.getString("type");
                    List<String> collaborators = GetAsList.getAsList(rs.getString("collaborators"));
                    String link = rs.getString("link");
                    String account = rs.getString("account");
                    Project project = new Project(id, title, summary, description, keywords, type, collaborators, link, account);
                    projects.add(project);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }

    public List<Project> getUserProjects() {
        List<Project> userProjects = new ArrayList<>();
        for (Project project : projectList) {
            System.out.println(project);
            if (project.getAccount().equals(loggedInUserAccount)) {
                userProjects.add(project);
            }
        }
        return userProjects;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public Project getProjectByTitle(String title) {
        for (Project project : projectList) {
            if (project.getTitle().equals(title)) {
                return project;
            }
        }
        return null;
    }

    public String getSelectedProject(String title) {
        System.out.println(title);
        this.currentProject = getProjectByTitle(title);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("currentProject", currentProject);
        return "projectDetails?faces-redirect=true";
    }

    public Project getCurrentProject() {
        return currentProject;
    }


    public String getProjectTitle() {
        return(currentProject.getTitle());
    }

}


