import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import com.example.model.Manager;
import com.example.model.Post;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;

public class Driver {

    public static void main(String[] args){
//
//        System.out.println(Manager.parseDate("1998-12-14").getClass());
//
//        Post p1 = new Post();
//        p1.setUserId(1);
//        p1.setTitle("title 1");
//        p1.setText("text 1 #vegan");
//
//        Manager.createPost(p1);
//
//        Set<Post> posts = Manager.getPost("1","","","");
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }
//
//        posts =  Manager.getPost("","","","vegan");
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }
//
//
//
//        System.out.println(p1.getPostId());
//        p1.setText("title 1 updated");
//        Manager.updatePost(p1);
//
//
//        Manager.deletePost(p1.getPostId());
//        Post p1 = new Post();
//        Post p2 = new Post();
//        Post p3 = new Post();
//        p1.setPostDate(Manager.parseDate("2000-01-01"));
//        p2.setPostDate(Manager.parseDate("2000-01-02"));
//        p3.setPostDate(Manager.parseDate("2000-01-03"));
//
//        ArrayList<Post> posts = new ArrayList<Post>();
//        posts.add(p1);
//        posts.add(p3);
//        posts.add(p2);
//
//        Collections.sort(posts,new PostComparator());
//        Collections.reverse(posts);
//
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }


//        ArrayList<Post> sortedPosts = Manager.getAllPost();

//        ArrayList<Post> posts = Manager.getAllPost();
//
//        for(Post p : posts){
//            System.out.println(p.getPostId());
//            System.out.println(p.getText());
//            System.out.println(p.getPostDate());
//        }

    }
}
