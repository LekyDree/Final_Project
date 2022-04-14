import java.util.ArrayList;

public class Driver {
    public static void main(String[] args)
    {
        Post post1 = new Post("pee", "holden");
        Post post2 = new Post("hi poo lol", "grape");
        Post post3 = new Post("poopee pool fucking", "hawkeye");
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        ArrayList<String> wordz = new ArrayList<String>();
        wordz.add("pee");
        wordz.add("poo");
        BanList bl = new BanList(wordz, true);
        bl.filterPosts(posts);


    }
    
}
