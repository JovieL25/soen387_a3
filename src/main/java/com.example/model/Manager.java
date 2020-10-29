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


    public static Set<Post> getPost(String userId_str, String startDate_str, String endDate_str, String hashTag){

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


        return postImpl.getPost(userId_str,startDate_str,endDate_str,hashTag);

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

    public static boolean deletePost(Integer postId){

        if(postImpl.deletePost(postId)){
            return true;
        }
        else{
            return false;
        }
    }




    public static java.util.Date parseDate(String date) {
        try {
            java.util.Date utilDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            System.out.println("Wrong format for Date, should be yyyy-MM-dd");
            return null;
        }
    }

}
