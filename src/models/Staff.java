package src.models;

public class Staff {
    private int staffID;
    private String username; 
    private String firstName;
    private String lastName;

    public Staff() {
        
    }

    public Staff(int staffID, String username, String firstName, String lastName) {
        this.staffID = staffID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    @Override
    public String toString() {
        return "Staff{" + "username=" + username + '}';
    }
}
