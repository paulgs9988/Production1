import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {

  public String password;

  public ReadFile() {
    this.password = "";
  }

  public void getPassword() {
    try {
      File myObj = new File("filename.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        this.password = myReader.nextLine();
        //return data;
        //this.password = data;
        //System.out.println(data);
      }
      myReader.close();
    } catch (
        FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  public String returnPassword() {
    return this.password;
  }
}

