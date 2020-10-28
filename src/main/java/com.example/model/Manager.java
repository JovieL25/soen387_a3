package com.example.model;

import com.example.daoimpl.PostDaoImpl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
//This is the business class
// work with post java beans
public class Manager {

    private static  PostDaoImpl postImpl = new PostDaoImpl();

    public static Set<Post> getAllPost(){

        return postImpl.getAllPost();
    }

    //don't use this method yet
    public static Set<Post> getPost(String userId_str, String startDate_str, String endDate_str, String hashTag){
// problematic
//        Integer userId;
//        Date startDate, endDate;
//        if(userId_str.equals("") || userId_str == null){
//            userId_str = null;
//        }
//        else{
//            userId = Integer.parseInt(userId_str);
//        }
//
//        if(startDate_str.equals("") || startDate_str == null){
//            startDate_str = null;
//        }
//        else{
//            startDate = parseDate(startDate_str);
//        }
        return null;

    }

    public static boolean createPost(Post post){
        if(postImpl.createPost(post)){
            return true;
        }
        else{
            return false;
        }

    }

    public static boolean updatePost(Post post){
        if(postImpl.updatePost(post)){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean deletePost(String postId_str){
        Integer postId = Integer.parseInt(postId_str);
        if(postImpl.deletePost(postId)){
            return true;
        }
        else{
            return false;
        }
    }




    private static java.util.Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            System.out.println("Wrong format for Date, should be yyyy-MM-dd");
            return null;
        }
    }

}
