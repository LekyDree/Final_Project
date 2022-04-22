import java.io.BufferedReader;
//import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
    
public class ReadFile {
    private String file;//we can use this as a default instruction file

    public ReadFile(String file){
        this.file = file;
    }

    public ReadFile(){
        file = "src/input.txt";
    }
    public String getFile(){
        return file;
    }

    public void setFile(String newFile){
        file = newFile;
    }
    public void OpenFile()
    {
        try (Reader myReader = new BufferedReader(new FileReader(file))) {//risky code so try catch. Buffered is faster

			String thisLine; 
			while ((thisLine = ((BufferedReader) myReader).readLine()) != null) {
				System.out.println(thisLine);
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
    

}

