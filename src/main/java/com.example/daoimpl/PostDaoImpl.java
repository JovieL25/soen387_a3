package com.example.daoimpl;

import com.example.db.DBConnection;
import com.example.dao.PostDAO;
import com.example.model.Post;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class PostDaoImpl implements PostDAO {

    @Override
    public Set<Post> getAllPost() {
        Connection connection = DBConnection.getConnection();

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM posts");

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

//    @Override
//    public Set<Post> getPost(){
//
//    }

    @Override
    public Set<Post> getPost(Integer userId, Date startDate, Date endDate, String hashTag) {
        // still a lot of problem
        // 1. can't compare date
        // how to find hashtag in its text string
        Connection connection = DBConnection.getConnection();

        boolean addWhere = false;

        String query = "SELECT * FROM posts";

        if(userId != null){
            query += " WHERE user_id = " + userId;
            addWhere = true;
        }

        if(startDate != null && !addWhere){
            query += " WHERE post_date >= " + startDate;
            addWhere = true;
        }
        else if(startDate != null){
            query += " AND post_date >= " + startDate;
        }

        if(endDate != null && !addWhere){
            query += " WHERE post_date <= " + endDate;
            addWhere = true;
        }
        else if(endDate != null){
            query += " AND post_date <= " + endDate;
        }




        if(hashTag != null && !addWhere){
            query += " WHERE text = " + hashTag;
            addWhere = true;
        }
        else if(hashTag != null){
            query += " AND text = " + hashTag;
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
            String query = "INSERT INTO posts (user_id,title,post_date,update_date,text,updated) VALUES (?, ?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1,post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setDate(3, post.getPostDate());
            ps.setDate(4, post.getUpdateDate());
            ps.setString(5, post.getText());
            ps.setBoolean(6,false);

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
            PreparedStatement ps = connection.prepareStatement("UPDATE posts SET user_id=?, title=?,post_date=?,update_date=?,text=?,updated=? WHERE post_id=?");

            ps.setInt(1,post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setDate(3, post.getPostDate());
            ps.setDate(4, post.getUpdateDate());
            ps.setString(5, post.getText());
            ps.setBoolean(6,true);
            ps.setInt(7,post.getPostId());

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

    public static void main(String[] args){
       PostDaoImpl postImpl = new PostDaoImpl();
//
//        Post p1 = new Post();
//        p1.setUserId(1);
//        p1.setPostDate(new Date(System.currentTimeMillis()));
//        p1.setTitle("bonjour");
//        p1.setText("bonjour text");
//
//        postImpl.createPost(p1);
////getAllPost
//        Set<Post> posts = postImpl.getAllPost();
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }
////getPost()
//        postImpl.getPostByUserId(12,new Date(System.currentTimeMillis()),"bounjour");
//        postImpl.getPostByUserId(12,null,null,"bounjour");
//        postImpl.getPostByUserId(null,new Date(System.currentTimeMillis()),null,"bounjour");
//        postImpl.getPostByUserId(null,new Date(System.currentTimeMillis()),null,null);
//        postImpl.getPostByUserId(12,null,null,null);
//        postImpl.getPostByUserId(null,new Date(System.currentTimeMillis()),new Date(System.currentTimeMillis()),null);
//        Set<Post> posts = postImpl.getPost(1,new Date(System.currentTimeMillis()),null,null);
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }
////update
//        Post p1 = new Post();
//        p1.setPostId(10);
//        p1.setUserId(1);
//        p1.setPostDate(new Date(System.currentTimeMillis()));
//        p1.setTitle("bonjour updated");
//        p1.setText("bonjour text");
//
//        postImpl.updatePost(p1);
////delete
        postImpl.deletePost(8);



    }
}
