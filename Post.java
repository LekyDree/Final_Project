//Created by Holden
public class Post
{
    private String text;
    private String userName;

    /**
     * initialies Post
     * @param text
     * @param userName
     */
    public Post(String text, String userName)
    {
        this.text = text;
        this.userName = userName;
    }


    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * returns a string in the format "userName: text"
     * @return String
     */
    public String toString()
    {
        return userName + ": " + text;
    }


}