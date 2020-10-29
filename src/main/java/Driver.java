import com.example.model.Manager;
import com.example.model.Post;

import java.sql.Date;
import java.util.Set;

public class Driver {

    public static void main(String[] args){

        System.out.println(Manager.parseDate("1998-12-14").getClass());

        Post p1 = new Post();
        p1.setUserId(1);
        p1.setTitle("title 1");
        p1.setText("text 1 #vegan");

        Manager.createPost(p1);

        Set<Post> posts = Manager.getPost("1","","","");
        for(Post p : posts){
            System.out.println(p.getPostId());
            System.out.println(p.getText());
            System.out.println(p.getPostDate());
        }

        posts =  Manager.getPost("","","","vegan");
        for(Post p : posts){
            System.out.println(p.getPostId());
            System.out.println(p.getText());
            System.out.println(p.getPostDate());
        }



        System.out.println(p1.getPostId());
        p1.setText("title 1 updated");
        Manager.updatePost(p1);


        Manager.deletePost(p1.getPostId());





    }
}
