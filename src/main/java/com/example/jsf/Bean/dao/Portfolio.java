package com.example.jsf.Bean.dao;

import java.util.List;

public class Portfolio {
    private String id;
    private String name;
    private String Classes;
    private List<String> interests;
    private String interest;
    private String experience;
    private List<String> skills;
    private String skill;
    private String careGoals;
    private String account;
    public Portfolio(){

    }

    public Portfolio(String id, String name, String Classes, List<String> interests,
                     String experience, List<String> skills,  String careGoals,String account) {
        this.id = id;
        this.name = name;
        this.Classes = Classes;
        this.interests = interests;
        this.experience = experience;
        this.skills = skills;
        this.careGoals = careGoals;
        this.account = account;
        setInterest(interests.toString());
        setSkill(skills.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getClasses() {
        return Classes;
    }

    public void setClasses(String Classes) {
        this.Classes = Classes;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest.substring(1, interest.length() - 1);
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill.substring(1, skill.length() - 1);
    }

    public String getCareGoals() {
        return careGoals;
    }

    public void setCareGoals(String careGoals) {
        this.careGoals = careGoals;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
