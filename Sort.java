import java.util.Iterator;
import java.util.LinkedList;

public class Sort extends Filter  {

    public void filterPosts() {

        String sortKeyword;
        LinkedList<Post> feed;

        // if sort filter is on
        if (GUI.keywordEntered) {

            sortKeyword = GUI.keyWord.toLowerCase();
            feed = PostFeed.getPostFeed();
            
            try {                       //try to find and then sort the posts

                
                LinkedList<Post> priority = new LinkedList();
                for (Post post : feed) {
                    
                    if (post.getText().toLowerCase().contains(sortKeyword) || 
                    post.getUserName().toLowerCase().contains(sortKeyword)) {

                        priority.add(post);
                        
                    }
                }
                feed.removeAll(priority);
                feed.addAll(0, priority);

            } catch (Exception e) {
                //TODO: handle exception
            }
        }

    }
}
