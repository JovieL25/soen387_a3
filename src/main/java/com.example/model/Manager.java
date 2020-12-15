package com.example.model;

import Utils.PostComparator;
import Utils.XMLFile;
import com.example.daoimpl.PostDaoImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
//This is the business class

public class Manager{
    private static ArrayList<Group> groups;
    private static ArrayList<String> usersArray;
    private static HashMap<String, HashSet<String>> memberships;

    private static UserManager userManagerImpl = UserManagerFactory.getInstance().create();

    private static PostDaoImpl postImpl = new PostDaoImpl();

    public static ArrayList<Post> getAllPost() {
        return SortPosts(postImpl.getAllPost());
    }

    public static Post getLastPost(){
        Post post = postImpl.getLastPost();
        if(post!=null) return post;
        else return null;
    }

    public static ArrayList<Post> getAllPost(String number_str){
        if(number_str.equals(""))
        {
            return SortPosts(postImpl.getAllPost());
        }
        else
            {
                int number = Integer.parseInt(number_str);

                ArrayList<Post> posts = SortPosts(postImpl.getAllPost());
                int trim = posts.size() - number;
                if(trim > 0)

                {
                    for(int i = 0 ; i < trim ; i ++)
                    {
                        int last_index = posts.size() - 1;
                        posts.remove(last_index);
                    }
                }

                return posts;
            }

    }


    // each parameter can be left as empty
    public static ArrayList<Post> getPost(String userId_str, String startDate_str, String endDate_str, String hashTag, String number_str){

        if(userId_str.equals("")){
            userId_str = null;
        }
        if(startDate_str.equals("")){
            startDate_str = null;
        }
        if(endDate_str.equals("")){
            endDate_str = null;
        }
        if(hashTag.equals("")){
            hashTag = null;
        }
        if(number_str.equals(""))
        {
            return SortPosts(postImpl.getPost(userId_str,startDate_str,endDate_str,hashTag));
        }
        else
            {
                int number = Integer.parseInt(number_str);

                ArrayList<Post> posts = SortPosts(postImpl.getPost(userId_str,startDate_str,endDate_str,hashTag));
                int trim = posts.size() - number;
                if(trim > 0)

                {
                    for(int i = 0 ; i < trim ; i ++)
                    {
                        int last_index = posts.size() - 1;
                        posts.remove(last_index);
                    }
                }

                return posts;
            }


    }

    public static boolean createPost(Post post){
        if(postImpl.createPost(post)){
            return true;
        }
        else{
            return false;
        }
    }

    public static Post getPost(int postId) {
        return postImpl.selectPost(postId);
    }

    public static boolean updatePost(Post post){
        if(postImpl.updatePost(post)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean deletePost(int postId){
        if(postImpl.deletePost(postId)){
            return true;
        }
        else{
            return false;
        }
    }

    //login uses the passed email and password and search the user.xml file
    //with the same credentials, when they are match, this method returns this '
    //user object, otherwise it returns null
    public static User login(String email, String password){
        return postImpl.getUser(email,password);
    }

    public static String loadGroups(File groupsFile) {

        return userManagerImpl.loadGroups(groupsFile);
//        System.out.println("loadGroups("+groupsFile+")");
//        groups = new ArrayList<>();
//
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//
//            Document document = documentBuilder.parse(groupsFile);
//            document.getDocumentElement().normalize();
//
//            NodeList groupsNodeList = document.getElementsByTagName("group");
//            for (int i = 0; i < groupsNodeList.getLength(); i++) {
//                Node groupNode = groupsNodeList.item(i);
//
//                Element groupElement = (Element)groupNode;
//
//                Node nameNode   = groupElement.getElementsByTagName("name").item(0);
//                Node parentNode = groupElement.getElementsByTagName("parent").item(0);
//
//                String name   = nameNode.getTextContent();
//                String parent = parentNode.getTextContent();
//
//                ArrayList<String> groupNamesTest = new ArrayList<>();
//                for (Group group: groups)
//                    groupNamesTest.add(group.getName());
//
//                if (!parent.equals("") && !groupNamesTest.contains(parent)) {
//                    return "Parent \"" + parent + "\" does not exist.";
//                }
//                Group group = new Group(name);
//
//                for (Group group_: groups) {
//                    if (group_.getName().equals(parent))
//                        group.setParent(group_);
//                }
//
//                groups.add(group);
//            }
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return null;
    }

    public static String loadMemberships(File membershipsFile) {
        return userManagerImpl.loadMemberships(membershipsFile);
//        memberships = new HashMap<>();
//        usersArray = new ArrayList<String>();
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//
//            Document document = documentBuilder.parse(membershipsFile);
//            document.getDocumentElement().normalize();
//
//            NodeList membershipNodeList = document.getElementsByTagName("membership");
//            for (int i = 0; i < membershipNodeList.getLength(); i++) {
//                Node membershipNode = membershipNodeList.item(i);
//
//                Element membershipElement = (Element)membershipNode;
//
//                Node userNameNode = membershipElement.getElementsByTagName("user-name").item(0);
//
//                String userName = userNameNode.getTextContent();
//
//                HashSet<String> groupNames = new HashSet<>();
//
//                NodeList userNamesNodeList = membershipElement.getElementsByTagName("user-name");
//                for (int j = 0; j < userNamesNodeList.getLength(); j++) {
////                    System.out.print("u"+i + " ");
//                    Node u = userNamesNodeList.item(j);
//
//                    String uStr = u.getTextContent();
//                    usersArray.add(uStr);
//                    if(uStr.trim().equals("")){
//                        return "There is an empty user in the groups file.";
//                    }
//                }
//
//                NodeList groupNamesNodeList = membershipElement.getElementsByTagName("group-name");
//                for (int j = 0; j < groupNamesNodeList.getLength(); j++) {
////                    System.out.print("g"+i + " ");
//                    Node groupNameNode = groupNamesNodeList.item(j);
//
//                    String groupName = groupNameNode.getTextContent();
//                    if(groupName.trim().equals("")){
//                        return "There is an empty group in the groups file.";
//                    }
//
//                    ArrayList<String> groupNamesTest = new ArrayList<>();
//                    for (Group group: groups) {
//                        groupNamesTest.add(group.getName());
//                    }
//
//                    if (!groupNamesTest.contains(groupName))
//                        return "Undefined group \"" + groupName + "\" in the groups file.";
//
//                    for (Group group: groups) {
//                        if (group.getName().equals(groupName)) {
//                            ArrayList<String> tempGroupNames = group.getGroupNames(groups);
//
//                            if (tempGroupNames == null)
//                                return "Circular parent-child definition detected.";
//
//                            groupNames.addAll(tempGroupNames);
//                        }
//                    }
//                }
//
//                memberships.put(userName, groupNames);
//
//            }
//
////            System.out.println("loadMemberships.memberships:"+memberships);
////            System.out.println("loadMemberships.usersArray:"+usersArray);
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        for (String userName: memberships.keySet()) {
//            System.out.print(userName + ": ");
//
//            for (String groupName: memberships.get(userName))
//                System.out.print(groupName + " ");
//
//            System.out.println();
//        }
//
//        return null;
    }

    public static User authenticate(String emailTest, String passwordTest, File usersFile) throws Exception {
        return userManagerImpl.authenticate(emailTest, passwordTest, usersFile);
//        try {
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//
//            Document document = documentBuilder.parse(usersFile);
//            document.getDocumentElement().normalize();
//
//            NodeList usersNodeList = document.getElementsByTagName("user");
//            for (int i = 0; i < usersNodeList.getLength(); i++) {
//                Node userNode = usersNodeList.item(i);
//
//                Element userElement = (Element)userNode;
//
//                Node idNode       = userElement.getElementsByTagName("id").item(0);
//                Node nameNode     = userElement.getElementsByTagName("name").item(0);
//                Node emailNode    = userElement.getElementsByTagName("email").item(0);
//                Node passwordNode = userElement.getElementsByTagName("password").item(0);
//
//                String id           = idNode.getTextContent();
//                String name         = nameNode.getTextContent();
//                String emailTrue    = emailNode.getTextContent();
//                String passwordTrue = passwordNode.getTextContent();
//
//                if (emailTrue.equals(emailTest) && passwordTrue.equals(XMLFile.hashPassword(passwordTest))) {
//                    if(memberships.get(name)==null){
////                        System.out.println("Membership for " + name + " does not existed.");
//                        throw new Exception("Membership for " + name + " does not existed.");
//                    } else{
//                        return new User(id, name, emailTrue, passwordTrue, memberships.get(name));
//                    }
//
//                }
//            }
//        }
//        catch (Exception exception) {
//            exception.printStackTrace();
//            throw exception;
//        }
//
//        return null;
    }

    public static boolean insertFile(Part part, int postId) {
        return postImpl.insertFile(part, postId);
    }

    public static boolean updateFile(Part part, int postId) {
        return postImpl.updateFile(part, postId);
    }

    public static File selectFile(int postId) {
        File file = null;

        try {
            file = postImpl.selectFile(postId);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return file;
    }

    public static boolean uploadFile(String filePath, String postId_str){
        Integer postId = Integer.parseInt(postId_str);

        return postImpl.uploadFile(filePath,postId);
    }

    public static boolean changeFile(String filePath, String postId_str){
        Integer postId = Integer.parseInt(postId_str);

        return postImpl.changeFile(filePath,postId);
    }

    public static boolean deleteFile(int postId) {
        return postImpl.deleteFile(postId);
    }

    public  java.sql.Date parseDate(String date) {
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println("Wrong format for Date, should be yyyy-MM-dd");
            return null;
        }
    }

    private static ArrayList<Post> SortPosts(Set<Post> rawPosts){
        ArrayList<Post> sortedArray = new ArrayList<>();

        for(Post p : rawPosts){
            sortedArray.add(p);
        }

        Collections.sort(sortedArray, new PostComparator());
        Collections.reverse(sortedArray);

        return sortedArray;
    }



    public static void main(String[] args)
    {
        System.out.println("!");
    }

}
