package com.example.model;

import javax.servlet.http.Part;
import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public interface UserManager {
    public  ArrayList<Post> getAllPost();

    public  ArrayList<Post> getAllPost(String number_str);

    public  ArrayList<Post> getPost(String userId_str, String startDate_str, String endDate_str, String hashTag, String number_str);

    public  boolean createPost(Post post);

    public  Post getPost(int postId);

    public  boolean updatePost(Post post);

    public  boolean deletePost(int postId);

    public  User login(String email, String password);

    public  String loadGroups(File groupsFile);

    public  String loadMemberships(File membershipsFile);

    public  User authenticate(String emailTest, String passwordTest, File usersFile) throws Exception;

    public  boolean insertFile(Part part, int postId);

    public  boolean updateFile(Part part, int postId);

    public  File selectFile(int postId);

    public  boolean uploadFile(String filePath, String postId_str);

    public  boolean changeFile(String filePath, String postId_str);

    public  boolean deleteFile(int postId);

    public  Post getLastPost();
}
