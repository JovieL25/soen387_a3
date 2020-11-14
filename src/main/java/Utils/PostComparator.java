package Utils;

import com.example.model.Post;

import java.util.Comparator;

public class PostComparator implements Comparator<Post> {

    public int compare(Post o1, Post o2) {
        if (o1.getPostDate().compareTo(o2.getPostDate()) == 0) {
            return o1.getPostId() - o2.getPostId();
        }

        return o1.getPostDate().compareTo(o2.getPostDate());
    }
}
