package com.example.daoimpl;

import Utils.XMLFile;
import com.example.db.DBConnection;
import com.example.dao.PostDAO;
import com.example.model.Post;
import com.example.model.User;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



import javax.servlet.http.Part;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("SqlNoDataSourceInspection")
public class PostDaoImpl implements PostDAO {
    @Override
    public Set<Post> getAllPost() {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");

            Set posts = new HashSet();

            while(rs.next()) {
                Post post = extractPostFromResultSet(rs);
                posts.add(post);
            }

            return posts;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Post selectPost(int postId) {
        int userId = 0;
        String title = null;
        String text = null;
        Date postDate = null;
        Date modifiedDate = null;
        String groups=null;

        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM posts WHERE post_id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, postId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId       = resultSet.getInt("user_id");
                title        = resultSet.getString("title");
                text         = resultSet.getString("text");
                postDate     = resultSet.getDate("post_date");
                modifiedDate = resultSet.getDate("update_date");
                groups = resultSet.getString("groups");
                connection.close();
            }
            else {
                connection.close();

                return null;
            }
        }
        catch (SQLException exception) {
            exception.printStackTrace();
        }

        return new Post(postId, userId, title, postDate, modifiedDate, text,groups);
    }

    @Override
    public Set<Post> getPost(String userId, String startDate, String endDate, String hashTag) {

        Connection connection = DBConnection.getConnection();

        boolean addWhere = false;

        String query = "SELECT * FROM posts";

        if(userId != null){
            query += " WHERE user_id = " + Integer.valueOf(userId);
            addWhere = true;
        }

        if(startDate != null && !addWhere){
            query += " WHERE post_date >= '" + startDate+"'";
            addWhere = true;
        }
        else if(startDate != null){
            query += " AND post_date >= '" + startDate+"'";
        }

        if(endDate != null && !addWhere){
            query += " WHERE post_date <= '" + endDate+"'";
            addWhere = true;
        }
        else if(endDate != null){
            query += " AND post_date <= '" + endDate+"'";
        }


        if(hashTag != null && !addWhere){
            query += " WHERE text LIKE \"%#" + hashTag + "%\"";
            addWhere = true;
        }
        else if(hashTag != null){
            query += " AND text LIKE \"%#" + hashTag + "%\"";
        }
        System.out.println(query);

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Set posts = new HashSet();

            while(rs.next())
            {
                Post post = extractPostFromResultSet(rs);
                posts.add(post);
            }

            return posts;

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return null;
    }


    @Override
    public boolean createPost(Post post) {
        Connection connection = DBConnection.getConnection();

        try{
            String query = "INSERT INTO posts (user_id,title,post_date,update_date,text,updated,groups) VALUES (?, ?, ?, ?, ?, ?,?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            System.out.println();

            ps.setInt(1,post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setDate(3,new Date(System.currentTimeMillis()));
            ps.setDate(4, null);
            ps.setString(5, post.getText());
            ps.setBoolean(6,false);
            ps.setString(7,post.getGroups());
            int i = ps.executeUpdate();

            if(i == 1) {

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // generatedKeys() checkout this method it's doing interesting job
                        post.setPostId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }

                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {

            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean updatePost(Post post) {

        Connection connection = DBConnection.getConnection();

        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET user_id=?, title=?,post_date=?,update_date=?,text=?,updated=?,groups=? WHERE post_id=?");

            ps.setInt(1,post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setDate(3, post.getPostDate());
            ps.setDate(4, new Date(System.currentTimeMillis()));
            ps.setString(5, post.getText());
            ps.setBoolean(6,true);
            ps.setString(7,post.getGroups());
            ps.setInt(8,post.getPostId());

            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deletePost(Integer postId) {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            int i = stmt.executeUpdate("DELETE FROM posts WHERE post_id=" + postId);

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }
    @Override
    public User getUser(String email, String password){

        try{
            File fXmlFile = new File("user.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("user");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);


                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    if(eElement.getElementsByTagName("email").item(0).getTextContent().equals(email)
                            && eElement.getElementsByTagName("password").item(0).getTextContent().equals(XMLFile.hashPassword(password)))
                    {
                        System.out.println("we have a match email address and password");
                        User user = new User();
                        user.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
                        user.setUserId(eElement.getAttribute("id"));
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public boolean insertFile(Part part, int postId) {
        Connection connection = DBConnection.getConnection();
        InputStream input = null;

        try{
            String query = "INSERT INTO files (content, file_name, file_size, media_type, post_id) VALUES (?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            input = part.getInputStream();

            System.out.println(input == null);

            double fileSize = (double)part.getSize();
            String fullFileName = part.getSubmittedFileName();
            String[] strs = fullFileName.split("\\.");

            String fileName = strs[0];
            String mediaType = strs[1];

            ps.setBinaryStream(1,input);
            ps.setString(2, fileName);
            ps.setDouble(3, fileSize);
            ps.setString(4, mediaType);
            ps.setInt(5, postId);


            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;

    }

    @Override
    public boolean updateFile(Part part, int postId) {
        Connection connection = DBConnection.getConnection();
        InputStream input = null;

        try{
            String query = "UPDATE files SET content=?, file_name=?, file_size=?, media_type=? WHERE post_id=?";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            input = part.getInputStream();

            double fileSize = (double)part.getSize();
            String fullFileName = part.getSubmittedFileName();
            String[] strs = fullFileName.split("\\.");

            String fileName = strs[0];
            String mediaType = strs[1];

            ps.setBinaryStream(1,input);
            ps.setString(2, fileName);
            ps.setDouble(3, fileSize);
            ps.setString(4, mediaType);
            ps.setInt(5, postId);


            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public File selectFile(int postId) throws SQLException {
        byte[] bytes = null;
        String baseName = null;
        String extension = null;

        try {
            Connection connection = DBConnection.getConnection();

            String query = "SELECT * FROM files WHERE post_id=?";

            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, postId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                bytes = resultSet.getBytes("content");

                baseName  = resultSet.getString("file_name");
                extension = resultSet.getString("media_type");

                connection.close();
            }
            else {
                connection.close();

                return null;
            }
        }
            catch (SQLException exception) {
            exception.printStackTrace();
        }

        File file = new File(baseName + "." + extension);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            fileOutputStream.write(bytes);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return file;
    }

    @Override
    public boolean uploadFile(String filePath, Integer postId) {
        Connection connection = DBConnection.getConnection();
        FileInputStream input = null;


        try{
            String query = "INSERT INTO files (content,file_name,file_size,media_type,post_id) VALUES (?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            File theFile = new File("user.xml");
            input = new FileInputStream(theFile);
            Double fileSize = (double) theFile.length();
            String fullFileName = theFile.getName();
            System.out.println(fullFileName);
            String[] strs = fullFileName.split("\\.");

            String fileName = strs[0];
            String mediaType = strs[1];

            System.out.println(fileSize);
            System.out.println(fileName);
            System.out.println(mediaType);

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            ps.setBinaryStream(1,input);
            ps.setString(2, fileName);
            ps.setDouble(3,fileSize);
            ps.setString(4, mediaType);
            ps.setInt(5, postId);


            int i = ps.executeUpdate();

            if(i == 1) {
                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;

    }

    @Override
    public boolean changeFile(String filePath, Integer postId) {
        Connection connection = DBConnection.getConnection();
        FileInputStream input = null;


        try{
            String query = "UPDATE files SET content=?, file_name=?,file_size=?,media_type=? WHERE post_id=?";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            File theFile = new File("lol.txt");
            input = new FileInputStream(theFile);
            Double fileSize = (double) theFile.length();
            String fullFileName = theFile.getName();
            System.out.println(fullFileName);
            String[] strs = fullFileName.split("\\.");

            String fileName = strs[0];
            String mediaType = strs[1];

            System.out.println(fileSize);
            System.out.println(fileName);
            System.out.println(mediaType);

            System.out.println("Reading input file: " + theFile.getAbsolutePath());

            ps.setBinaryStream(1,input);
            ps.setString(2, fileName);
            ps.setDouble(3,fileSize);
            ps.setString(4, mediaType);
            ps.setInt(5, postId);

            changePostStatus(postId);


            int i = ps.executeUpdate();

            if(i == 1) {


                return true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            try {
                connection.close();
            } catch(SQLException e){
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteFile(Integer postId) {
        Connection connection = DBConnection.getConnection();

        try{
            String query = ("DELETE FROM files WHERE post_id=" + postId);
            System.out.println(query);
            PreparedStatement ps = connection.prepareStatement(query);

            int i = ps.executeUpdate();

            if(i == 1) {


                return true;
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            return false;
        }

    }

    private Post extractPostFromResultSet(ResultSet rs) throws SQLException {

        Post post = new Post();

        post.setPostId(rs.getInt("post_id"));
        post.setUserId(rs.getInt("user_id"));
        post.setTitle( rs.getString("title") );
        post.setPostDate(rs.getDate("post_date"));
        post.setUpdateDate(rs.getDate("update_date"));
        post.setText(rs.getString("text"));
        post.setUpdated(rs.getBoolean("updated"));

        return post;
    }

    private boolean changePostStatus(Integer postId){
        Connection connection = DBConnection.getConnection();

        try{
            String query = ("UPDATE posts SET updated=true WHERE post_id=" + postId);
            System.out.println(query);
            PreparedStatement ps = connection.prepareStatement(query);

            int i = ps.executeUpdate();

            if(i == 1) {


                return true;
            }

        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        finally {
            return false;
        }
    }

    public static void main(String[] args){
        PostDaoImpl p1 = new PostDaoImpl();

        p1.deleteFile(12);


    }

}
