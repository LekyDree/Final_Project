import java.util.ArrayList;

public class Driver {
    public static void main(String[] args)
    {
        PostFeed.addPost(new Post("Hello", "Larry"));
        PostFeed.addPost(new Post("Hello", "Doug"));
        PostFeed.addPost(new Post("Goodbye", "Mark"));
        PostFeed.addPost(new Post("Goodbye", "Doug"));
        PostFeed.addPost(new Post("No repeat here", "Doug"));
        PostFeed.addPost(new Post("I'm all alone", "Cony"));

        Spam spam = new Spam(1);
        spam.filterPosts();
        System.out.println(PostFeed.getPostFeed());


        /* Post post1 = new Post("asshole yo", "holden");
        Post post2 = new Post("yo what is up", "grape");
        Post post3 = new Post("poopee pool shit fucking", "hawkeye");
        Post post4 = new Post("my cum tatstes good :)", "weirdo");
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
        posts.add(post4);
        ArrayList<String> wordz = new ArrayList<String>();
        //wordz.add("pee");
        wordz.add("cum");
        BanList bl = new BanList(wordz, true);
        System.out.println("START!!!!!!!!!");
        bl.filterPosts(posts);
        posts.forEach(post -> System.out.println(post));
        */

    }
  
}

