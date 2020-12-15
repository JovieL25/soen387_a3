import com.example.daoimpl.PostDaoImpl;
import com.example.db.DBConnection;
import com.example.model.Manager;
import com.example.model.Post;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    DownloadServlet downloadServlet = new DownloadServlet();
//    Manager manager = new Manager();
    PostDaoImpl postDaoImple = new PostDaoImpl();

    @Test public void shall_connect_to_database(){
        DBConnection dbconnection = new DBConnection();
        System.out.println(dbconnection.getConnection());
        assertNotNull(dbconnection.getConnection());
    }

    @Test public void get_all_post(){
        ArrayList<Post> posts = Manager.getAllPost();
        System.out.println(posts.size());
        for(Post p : posts){
            System.out.println(p);
        }
        assertNotNull(posts);

        Set<Post> posts2 = postDaoImple.getAllPost();
        System.out.println(posts2.size());
        for(Post p : posts2){
            System.out.println(p);
        }

    }

    @Test public void shall_create_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","admins");
        assertEquals(1,post.getUserId());
        assertEquals("Sample Title", post.getTitle());
        assertEquals("Sample Text",post.getText());
        assertEquals("Sample Group",post.getGroup());
        Manager.createPost(post);
        Post lastPost = Manager.getLastPost();
        assertTrue(lastPost.equals(post,"no_postDate"));
    }

    @Test public void shall_update_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","admins");
        Manager.createPost(post);
        post.setText("Edited Text");
        post.setTitle("Edited Title");
        Manager.updatePost(post);
        Post lastPost = Manager.getLastPost();
        assertEquals("Edited Text", lastPost.getText());
        assertEquals("Edited Title", lastPost.getTitle());
    }

    @Test public void shall_delete_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","admins");
        Manager.createPost(post);
        int id = Manager.getLastPost().getPostId();
        assertTrue(Manager.deletePost(id));
        assertNotEquals(id, Manager.getLastPost().getPostId());
    }

    @Test public void shall_get_last_post(){
        Post post = new Post(1,"Sample Last Title", "Sample Last Text","admins");
        Manager.createPost(post);
        System.out.println(post);
        Post lastPost = Manager.getLastPost();
        System.out.println(lastPost);
        assertTrue(lastPost.equals(post,"no_postDate"));
    }

    @Test public void shall_login(){
        User u = Manager.login("tianmingchen@gmail.com","12345");
        //System.out.println(u);
    }
}