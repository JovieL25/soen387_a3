package com.example.model;

import Utils.PostComparator;
import com.example.daoimpl.PostDaoImpl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
//This is the business class
// work with post java beans
public class Manager {

    private static  PostDaoImpl postImpl = new PostDaoImpl();



    public static ArrayList<Post> getAllPost(){

        return SortPosts(postImpl.getAllPost());
    }



    public static ArrayList<Post> getPost(String userId_str, String startDate_str, String endDate_str, String hashTag){

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


        return SortPosts(postImpl.getPost(userId_str,startDate_str,endDate_str,hashTag));

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

    public static User login(String email, String password){
        return postImpl.getUser(email,password);
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

        Collections.sort(sortedArray,new PostComparator());
        Collections.reverse(sortedArray);

        return sortedArray;
    }



}
