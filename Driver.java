public class Driver {
    public static void main(String[] args) {
        PostFeed.addPost(new Post("Hello", "Larry"));
        PostFeed.addPost(new Post("Hello", "Doug"));
        PostFeed.addPost(new Post("Goodbye", "Mark"));
        PostFeed.addPost(new Post("Goodbye", "Doug"));
        PostFeed.addPost(new Post("No repeat here", "Doug"));
        PostFeed.addPost(new Post("I'm all alone", "Cony"));

        Spam spam = new Spam(1);
        spam.filterPosts();
        System.out.println(PostFeed.getPostFeed());
    }
}
