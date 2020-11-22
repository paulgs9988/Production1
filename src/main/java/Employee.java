import java.lang.StringBuilder;
import java.util.regex.Pattern;

public class Employee {
    private StringBuilder name;
    private String username;
    private String password;
    private String email;

    public Employee(String name, String password){
        //this.name = name;
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        this.name = sb;
        this.password = password;

        if(checkName()){
            setUsername();
            setEmail();
        }
        else{
            this.username = "default";
            this.email = "user@oracleacademy.Test";
        }
        if(!isValidPassword()){
            this.password = "pw";
        }
    }

    private void setUsername(){
        char firstInitial = name.charAt(0);
        int spaceIndex = name.indexOf(" ");
        String lastName = name.substring(spaceIndex+1,name.length());
        String beforeLower = firstInitial+lastName;
        this.username = beforeLower.toLowerCase();
    }

    private boolean checkName(){
        if(this.name.indexOf(" ") >= 0){
            return true;
        }
        else{
            return false;
        }

    }

    private void setEmail(){
        int spaceIndex = name.indexOf(" ");
        int nameLength = name.length();
        String firstName = name.substring(0,spaceIndex).toLowerCase();
        String lastName = name.substring(spaceIndex+1,name.length()).toLowerCase();
        this.email = firstName+"."+lastName+"@oracleacademy.Test";

    }

    private boolean isValidPassword(){
        boolean hasSpecial   = !password.matches("[A-Za-z0-9 ]*");
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");

        if(hasCapital && hasLower && hasSpecial){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String toString(){
        return "Employee Details\n"+"Name : "+this.name+"\nUsername : "+this.username+"\nEmail : "+this.email+"\nInitial Password : "+this.password;
    }

}
