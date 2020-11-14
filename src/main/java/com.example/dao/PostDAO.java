package com.example.dao;

import com.example.model.Post;
import com.example.model.User;

import javax.servlet.http.Part;
import java.io.File;
import java.sql.SQLException;
import java.util.Set;

public interface PostDAO {

    Set<Post> getAllPost();

    Post selectPost(int postId);

    Set<Post> getPost(String userId,String startDate,String endDate,String hashTag);

    boolean createPost(Post post);

    boolean updatePost(Post post);

    boolean deletePost(Integer postId);

    User getUser(String email, String password);

    boolean insertFile(Part part, int postId);

    boolean updateFile(Part part, int postId);

    File selectFile(int postId) throws SQLException;

    boolean uploadFile(String filePath, Integer postId);

    boolean changeFile(String filePath, Integer postId);

    boolean deleteFile(Integer postId);







}
