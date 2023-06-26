package com.example.jsf.Bean.controller;
import com.example.jsf.Bean.dao.Project;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "projectDetailsBean")
public class ProjectDetailsBean {
    private Project currentProject;
    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        this.currentProject = (Project) session.getAttribute("currentProject");
    }
    public Project getCurrentProject() {
        return currentProject;
    }



}



