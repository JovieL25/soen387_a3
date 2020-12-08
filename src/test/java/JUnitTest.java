import com.example.daoimpl.PostDaoImpl;
import com.example.db.DBConnection;
import com.example.model.Manager;
import com.example.model.Post;
import com.example.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import java.sql.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JUnitTest {

    DownloadServlet downloadServlet = new DownloadServlet();
    Manager manager = new Manager();

    @Test public void testServlet() throws Exception{
        DownloadServlet request = mock(DownloadServlet.class);
    }

    @Test public void shall_connect_to_database(){
        DBConnection dbconnection = new DBConnection();
        System.out.println(dbconnection.getConnection());
        assertNotNull(dbconnection.getConnection());
    }

    @Test public void get_all_post(){
        ArrayList<Post> posts = manager.getAllPost();
        assertNotNull(posts);
    }

    @Test public void shall_create_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","Sample Group");
        assertEquals(1,post.getUserId());
        assertEquals("Sample Title", post.getTitle());
        assertEquals("Sample Text",post.getText());
        assertEquals("Sample Group",post.getGroup());
        manager.createPost(post);
        Post lastPost = manager.getLastPost();
        assertTrue(lastPost.equals(post,"no_postDate"));
    }

    @Test public void shall_update_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","Sample Group");
        manager.createPost(post);
        post.setText("Edited Text");
        post.setTitle("Edited Title");
        manager.updatePost(post);
        Post lastPost = manager.getLastPost();
        assertEquals("Edited Text", lastPost.getText());
        assertEquals("Edited Title", lastPost.getTitle());
    }

    @Test public void shall_delete_a_post(){
        Post post = new Post(1,"Sample Title", "Sample Text","Sample Group");
        manager.createPost(post);
        int id = manager.getLastPost().getPostId();
        assertTrue(manager.deletePost(id));
        assertNotEquals(id, manager.getLastPost().getPostId());
    }

    @Test public void shall_(){

    }

    @Test public void shall_get_last_post(){
        Post post = new Post(1,"Sample Last Title", "Sample Last Text","Sample Last Group");
        manager.createPost(post);
        Post lastPost = manager.getLastPost();
        assertTrue(lastPost.equals(post,"no_postDate"));
    }

    @Test public void shall_login(){
        User u = manager.login("tianmingchen@gmail.com","12345");
        System.out.println(u);
    }
}