package com.example.model;
import java.sql.Date;
public class Post {

    private Integer postId;
    private Integer userId;
    private String title;
    private Date postDate;
    private Date updateDate;
    private String text;
    private Boolean updated;

    public Post(){}

    public Post(Integer postId, Integer userId, String title, Date postDate, Date updateDate, String text){
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.postDate = postDate;
        this.updateDate = updateDate;
        this.text = text;
        this.updated = false;
    }

    public Post(int userId, String title, String text) {
        this.userId = userId;
        this.title = title;
        this.text = text;
    }

    public Integer getPostId(){return this.postId;}

    public Integer getUserId(){return this.userId;}

    public String getTitle(){return this.title;}

    public Date getPostDate(){return this.postDate;}

    public Date getUpdateDate(){return this.updateDate;}

    public String getText(){return this.text;}

    public Boolean getUpdate(){return this.updated;}

    public void setPostId(Integer postId){this.postId = postId;}

    public void setUserId(Integer userId){this.userId = userId;}

    public void setTitle(String title){this.title = title;}

    public void setPostDate(Date postDate){this.postDate = postDate;}

    public void setUpdateDate(Date updateDate){this.updateDate = updateDate;}

    public void setText(String text){this.text = text;}

    public void setUpdated(Boolean updated){this.updated = updated;}

    public int compareTo(Post otherPost){return this.postDate.compareTo(otherPost.postDate);}
}






