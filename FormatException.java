public class FormatException extends RuntimeException 
{
    public FormatException() 
        { } 

    public FormatException(String message)
    {
        super(message);
    }
    
    public String toString() 
    { 
       return "File Formatted Improperly";
    }
}