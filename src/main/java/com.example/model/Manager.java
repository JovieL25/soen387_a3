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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
//This is the business class

public class Manager {

    private static PostDaoImpl postImpl = new PostDaoImpl();

    public static ArrayList<Post> getAllPost() {
        return SortPosts(postImpl.getAllPost());
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

    public static User authenticate(String emailTest, String passwordTest, File usersFile) {
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
                // Node groupsNode = userElement.getElementsByTagName("groups").item(0);

                String id           = idNode.getTextContent();
                String name         = nameNode.getTextContent();
                String emailTrue    = emailNode.getTextContent();
                String passwordTrue = passwordNode.getTextContent();

                if (emailTrue.equals(emailTest) && passwordTrue.equals(XMLFile.hashPassword(passwordTest))) {
                    return new User(id, name, emailTrue, passwordTrue);
                }
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
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

    public static java.sql.Date parseDate(String date) {
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


}
