package com.nbs.simplefootballapp.presentation.viewmodel;

public class Team {
    private String id;

    private String name;

    private String badge;

    private String description;

    private String manager;

    public Team(String id, String name, String badge, String description, String manager) {
        this.id = id;
        this.name = name;
        this.badge = badge;
        this.description = description;
        this.manager = manager;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }
}
