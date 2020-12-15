package com.example.impl;

import Utils.XMLFile;
import com.example.model.Group;
import com.example.model.User;
import com.example.model.UserManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class UserManagerImpl implements UserManager {

    private static ArrayList<Group> groups;
    private static ArrayList<String> usersArray;
    private static HashMap<String, HashSet<String>> memberships;


    @Override
    public String loadGroups(File groupsFile) {
        groups = new ArrayList<>();

        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(groupsFile);
            document.getDocumentElement().normalize();

            NodeList groupsNodeList = document.getElementsByTagName("group");
            for (int i = 0; i < groupsNodeList.getLength(); i++) {
                Node groupNode = groupsNodeList.item(i);

                Element groupElement = (Element)groupNode;

                Node nameNode   = groupElement.getElementsByTagName("name").item(0);
                Node parentNode = groupElement.getElementsByTagName("parent").item(0);

                String name   = nameNode.getTextContent();
                String parent = parentNode.getTextContent();

                ArrayList<String> groupNamesTest = new ArrayList<>();
                for (Group group: groups)
                    groupNamesTest.add(group.getName());

                if (!parent.equals("") && !groupNamesTest.contains(parent)) {
                    return "Parent \"" + parent + "\" does not exist.";
                }
                Group group = new Group(name);

                for (Group group_: groups) {
                    if (group_.getName().equals(parent))
                        group.setParent(group_);
                }

                groups.add(group);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    @Override
    public String loadMemberships(File membershipsFile) {
        memberships = new HashMap<>();
        usersArray = new ArrayList<String>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(membershipsFile);
            document.getDocumentElement().normalize();

            NodeList membershipNodeList = document.getElementsByTagName("membership");
            for (int i = 0; i < membershipNodeList.getLength(); i++) {
                Node membershipNode = membershipNodeList.item(i);

                Element membershipElement = (Element)membershipNode;

                Node userNameNode = membershipElement.getElementsByTagName("user-name").item(0);

                String userName = userNameNode.getTextContent();

                HashSet<String> groupNames = new HashSet<>();

                NodeList userNamesNodeList = membershipElement.getElementsByTagName("user-name");
                for (int j = 0; j < userNamesNodeList.getLength(); j++) {
//                    System.out.print("u"+i + " ");
                    Node u = userNamesNodeList.item(j);

                    String uStr = u.getTextContent();
                    usersArray.add(uStr);
                    if(uStr.trim().equals("")){
                        return "There is an empty user in the groups file.";
                    }
                }

                NodeList groupNamesNodeList = membershipElement.getElementsByTagName("group-name");
                for (int j = 0; j < groupNamesNodeList.getLength(); j++) {
//                    System.out.print("g"+i + " ");
                    Node groupNameNode = groupNamesNodeList.item(j);

                    String groupName = groupNameNode.getTextContent();
                    if(groupName.trim().equals("")){
                        return "There is an empty group in the groups file.";
                    }

                    ArrayList<String> groupNamesTest = new ArrayList<>();
                    for (Group group: groups) {
                        groupNamesTest.add(group.getName());
                    }

                    if (!groupNamesTest.contains(groupName))
                        return "Undefined group \"" + groupName + "\" in the groups file.";

                    for (Group group: groups) {
                        if (group.getName().equals(groupName)) {
                            ArrayList<String> tempGroupNames = group.getGroupNames(groups);

                            if (tempGroupNames == null)
                                return "Circular parent-child definition detected.";

                            groupNames.addAll(tempGroupNames);
                        }
                    }
                }

                memberships.put(userName, groupNames);

            }

//            System.out.println("loadMemberships.memberships:"+memberships);
//            System.out.println("loadMemberships.usersArray:"+usersArray);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        for (String userName: memberships.keySet()) {
            System.out.print(userName + ": ");

            for (String groupName: memberships.get(userName))
                System.out.print(groupName + " ");

            System.out.println();
        }

        return null;
    }

    @Override
    public User authenticate(String emailTest, String passwordTest, File usersFile) throws Exception {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

            Document document = documentBuilder.parse(usersFile);
            document.getDocumentElement().normalize();

            NodeList usersNodeList = document.getElementsByTagName("user");
            for (int i = 0; i < usersNodeList.getLength(); i++) {
                Node userNode = usersNodeList.item(i);

                Element userElement = (Element)userNode;

                Node idNode       = userElement.getElementsByTagName("id").item(0);
                Node nameNode     = userElement.getElementsByTagName("name").item(0);
                Node emailNode    = userElement.getElementsByTagName("email").item(0);
                Node passwordNode = userElement.getElementsByTagName("password").item(0);

                String id           = idNode.getTextContent();
                String name         = nameNode.getTextContent();
                String emailTrue    = emailNode.getTextContent();
                String passwordTrue = passwordNode.getTextContent();

                if (emailTrue.equals(emailTest) && passwordTrue.equals(XMLFile.hashPassword(passwordTest))) {
                    if(memberships.get(name)==null){
//                        System.out.println("Membership for " + name + " does not existed.");
                        throw new Exception("Membership for " + name + " does not existed.");
                    } else{
                        return new User(id, name, emailTrue, passwordTrue, memberships.get(name));
                    }

                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }

        return null;
    }
}
