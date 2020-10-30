package Utils;

import com.example.model.Post;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {

    public int compare(Post o1, Post o2) {
        return o1.getPostDate().compareTo(o2.getPostDate());
    }
}
