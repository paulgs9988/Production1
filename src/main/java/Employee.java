/**

 * This class is for creating Employees and their associated information while
 * interacting with the system

 * An employee will enter his/her name and password and have a username and email
 * created

 * @author Paul Sullivan

 */


import java.lang.StringBuilder;
import java.util.regex.Pattern;

public class Employee {
    private StringBuilder name;
    private String username;
    private String password;
    private String email;

    /**

     * Constructor for an Employee Object

     * @param name Employee's Name
     * @param password desired password

     */
    public Employee(String name, String password) {
        //this.name = name;
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        this.name = sb;
        this.password = password;

        if (checkName()) {
            setUsername();
            setEmail();
        } else {
            this.username = "default";
            this.email = "user@oracleacademy.Test";
        }
        if (!isValidPassword()) {
            this.password = "pw";
        }
    }
    /**

     * Sets the username for the Employee

     */
    private void setUsername() {
        char firstInitial = name.charAt(0);
        int spaceIndex = name.indexOf(" ");
        String lastName = name.substring(spaceIndex + 1, name.length());
        String beforeLower = firstInitial + lastName;
        this.username = beforeLower.toLowerCase();
    }
    /**

     * Checks to make sure that a space has been entered between first and last name

     * @return a boolean value that is true if the name entered contains a space
     */
    private boolean checkName() {
        if (this.name.indexOf(" ") >= 0) {
            return true;
        } else {
            return false;
        }

    }
    /**

     * Sets the email address for the employee

     */
    private void setEmail() {
        int spaceIndex = name.indexOf(" ");
        int nameLength = name.length();
        String firstName = name.substring(0, spaceIndex).toLowerCase();
        String lastName = name.substring(spaceIndex + 1, name.length()).toLowerCase();
        this.email = firstName + "." + lastName + "@oracleacademy.Test";

    }
    /**

     * Determines whether or not the entered password is valid based on the criteria:
     * -Contains a lowercase letter
     * -Contains a capital letter
     * -Contains a special character

     * @return a bool value as to whether or not the password is valid

     */
    public boolean isValidPassword() {
        boolean hasSpecial = !password.matches("[A-Za-z0-9 ]*");
        boolean hasCapital = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");

        if (hasCapital && hasLower && hasSpecial) {
            return true;
        } else {
            return false;
        }
    }
    /**

     * This is a method to return a String that gives information about the AudioPlayer

     * @return A string with the following formatting:
     * Employee Details:
     * Name: Employee's Name
     * Username: Created Username
     * Email: created email
     * Initial Password: password entered

     */
    @Override
    public String toString() {
        return "Employee Details\n" + "Name : " + this.name + "\nUsername : " + this.username + "\nEmail : " + this.email + "\nInitial Password : " + this.password;
    }

}
