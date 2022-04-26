import java.io.IOException;

/**
* Custom exception for when file of incorrect format is entered into the program
* @author Holden Fellenger
* @version 1.0
*/
public class FormatException extends IOException 
{
    public FormatException() 
        { } 

    public FormatException(String message)
    {
        super(message);
    }
    

    /**
    * @return String explaining that file is formatted incorrectly 
    */
    public String toString() 
    { 
       return "File Formatted Improperly";
    }
}