
/**
* A representation of a post that holds its username and text
* @author Holden Fellenger
* @version 1.0
*/
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

    /**
    * returns text
    * @return String text
    */
    public String getText() {
        return this.text;
    }

    /**
    * sets text
    * @param String text
    */
    public void setText(String text) {
        this.text = text;
    }

    /**
    * returns userName
    * @return String userName
    */
    public String getUserName() {
        return this.userName;
    }

    /**
    * sets userName
    * @param String userName
    */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * returns a string in the format "userName: text"
     * @return String
     */
    public String toString()
    {
        return userName + text;
    }


}