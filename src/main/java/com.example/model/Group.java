package com.example.model;

import java.util.ArrayList;

public class Group {
    private final String name;

    private Group parent;

    public Group(String name) {
        this.name = name;

        this.parent = null;
    }

    public String getName() {
        return name;
    }

    public Group getParent() {
        return parent;
    }

    public void setParent(Group parent) {
        this.parent = parent;
    }

    public ArrayList<String> getGroupNames() {
        ArrayList<String> groupNames = new ArrayList<>();

        for (Group temp = this; temp != null; temp = temp.parent)
            groupNames.add(temp.name);

        return groupNames;
    }
}