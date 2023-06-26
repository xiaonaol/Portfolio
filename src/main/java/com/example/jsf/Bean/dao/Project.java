package com.example.jsf.Bean.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Project {
    private String id;
    private String title;
    private String summary;
    private String description;
    private List<String> keywords;
    private String keyword;
    private String type;
    private List<String> collaborators;
    private String collaborator;
    private String link;
    private String account;

    public Project() { }

    public Project(String id,String title, String summary, String description, List<String> keywords, String type,
                   List<String> collaborators, String link, String account) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.description = description;
        this.keywords = keywords;
        this.type = type;
        this.collaborators = collaborators;
        this.link = link;
        this.account = account;
        setKeyword(keywords.toString());
        setCollaborator(collaborators.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<String> collaborators) {
        this.collaborators = collaborators;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword.substring(1, keyword.length() - 1);
    }

    public String getCollaborator() {
        return collaborator;
    }

    public void setCollaborator(String collaborator) {
        this.collaborator = collaborator.substring(1, collaborator.length() - 1);
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", keywords=" + keywords +
                ", keyword='" + keyword + '\'' +
                ", type='" + type + '\'' +
                ", collaborators=" + collaborators +
                ", collaborator='" + collaborator + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
