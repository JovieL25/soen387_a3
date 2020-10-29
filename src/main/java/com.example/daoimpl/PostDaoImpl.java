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
            String query = "INSERT INTO posts (user_id,title,post_date,update_date,text,updated) VALUES (?, ?, ?, ?, ?, ?)";
            // Passing Statement.RETURN_GENERATED_KEYS to make getGeneratedKeys() work
            PreparedStatement ps = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1,post.getUserId());
            ps.setString(2, post.getTitle());
            ps.setDate(3,new Date(System.currentTimeMillis()));
            ps.setDate(4, null);
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
            ps.setDate(4, new Date(System.currentTimeMillis()));
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

}
