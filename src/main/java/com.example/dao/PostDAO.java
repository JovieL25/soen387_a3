package com.example.dao;

import com.example.model.Post;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Set;

public interface PostDAO {

    Set<Post> getAllPost();

    Set<Post> getPost(Integer userId,Date startDate,Date endDate,String hashTag);



    boolean createPost(Post post);

    boolean updatePost(Post post);

    boolean deletePost(Integer postId);




}
