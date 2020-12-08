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
    private String group;

    public Post(){}

    public Post(Integer postId, Integer userId, String title, Date postDate, Date updateDate, String text, String group){
        this.postId = postId;
        this.userId = userId;
        this.title = title;
        this.postDate = postDate;
        this.updateDate = updateDate;
        this.text = text;
        this.updated = false;
        this.group = group;
    }

    public Post(int userId, String title, String text, String group) {
        this.userId = userId;
        this.title = title;
        this.text = text;
        this.group = group;
    }

    public Integer getPostId(){return this.postId;}

    public Integer getUserId(){return this.userId;}

    public String getTitle(){return this.title;}

    public Date getPostDate(){return this.postDate;}

    public Date getUpdateDate(){return this.updateDate;}

    public String getText(){return this.text;}

    public Boolean getUpdated(){return this.updated;}

    public void setPostId(Integer postId){this.postId = postId;}

    public void setUserId(Integer userId){this.userId = userId;}

    public void setTitle(String title){this.title = title;}

    public void setPostDate(Date postDate){this.postDate = postDate;}

    public void setUpdateDate(Date updateDate){this.updateDate = updateDate;}

    public void setText(String text){this.text = text;}

    public void setUpdated(Boolean updated){this.updated = updated;}

    public int compareTo(Post otherPost){return this.postDate.compareTo(otherPost.postDate);}

    public void setGroup(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String toXmlString() {
        return "<post>" + "\n" +
               "  <post-id>" + postId + "</post-id>" + "\n" +
               "  <user-id>" + userId + "</user-id>" + "\n" +
               "  <group>" + group + "</group>" + "\n" +
               "  <title>" + title + "</title>" + "\n" +
               "  <post-date>" + postDate + "</post-date>" + "\n" +
               "  <update-date>" + updateDate + "</update-date>" + "\n" +
               "  <text>" + "\n" +
               "  " + text + "\n" +
               "  </text>" + "\n" +
               "</post>";
    }

    public boolean equals(Object obj, String param) {
        if (obj==null || this==null || this.getClass() != obj.getClass()) return false;
        else {
            Post p = (Post) obj;
            return (this.getUserId()==p.getUserId()
                    && this.getTitle().equals(p.getTitle())
                    && (param.equals("no_postDate") || this.getPostDate() == p.getPostDate())
                    && this.getText().equals(p.getText())
                    && this.getGroup().equals(p.getGroup()));
        }
    }

    @Override
    public String toString() {
        return "[userId:"+userId+", title:"+title+", postDate:" + postDate
                + ", updateDate:"+updateDate+", text:"+text+", updated:"+updated
                + ", group:" + group;
    }
}






