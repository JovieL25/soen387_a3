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

    public boolean hasAncestor(Group ancestor) {
        for (Group temp = parent; temp != null; temp = temp.parent) {
            if (temp.name.equals(ancestor.name))
                return true;
        }

        return false;
    }

    public ArrayList<String> getGroupNames(ArrayList<Group> groups) {
        ArrayList<String> groupNames = new ArrayList<>();

        groupNames.add(this.name);

        for (Group group: groups) {
            if (group.hasAncestor(this)) {
                if (hasAncestor(group))
                    return null;

                groupNames.add(group.name);
            }
        }

        return groupNames;
    }
}