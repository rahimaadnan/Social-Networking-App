import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents the profiles of the users of the MasonConnect.
 * 
 * @author Rahima Adnan
 */
public class Profile {

    /**
     * A String value that represents the full name of the user.
     */
    private String name;
    /**
     * A String that the user uses to specify their status.
     */
    private String status;
    /**
     * A arraylist of profiles that stores friends of the user.
     */
    private ArrayList<Profile> friendProfiles; // okay????

    /**
     * This is a simple constructor.
     */
    public Profile() {
        // itializes all the String attributes to empty strings and a default arraylist.
        name = null;
        status = null;
        friendProfiles = new ArrayList<>();
    }

    /**
     * Initializes the attributes with the accepted values.(constructor).
     * 
     * @param name           the name.
     * @param status         the status.
     * @param friendProfiles other friends.
     */
    public Profile(String name, String status, ArrayList<Profile> friendProfiles) {
        // initializes the attributes with the accepted valued and the last attribute
        // with a default arraylist object.
        this.name = name;
        this.status = status;
        this.friendProfiles = friendProfiles;
    }
    
    /**
     * Another constuctor. Initializes name and status. 
     * @param name the name of the user.
     * @param status the status of the user.
     */
    public Profile(String name, String status){
        //initializes the attributes with the accepted valued and the last attribute with a default arraylist object.
        this.name = name;
        this.status = status;
        friendProfiles = new ArrayList<>(); // creating a deafult list 

    }

    /**
     * The setter method for the name attribute that accepts the first and last
     * name-.
     * -of the user and set the name attribute with firstName +” “ +lastName (Note
     * the space between the two names).
     * 
     * @param firstName users first name.
     * @param lastName  this is the users last name.
     */
    public void setName(String firstName, String lastName) {
        name = firstName + " " + lastName;
    }

    /**
     * The getter method for the name attribute.
     * 
     * @return a string value of the name
     */
    public String getName() {
        return name; // simply call name
    }

    /**
     * This simply sets the status.
     * 
     * @param status the new status.
     */
    public void setStatus(String status) {
        // String newStat = status; // created this to avoid a warning
        this.status = status;
    }

    /**
     * Get method for the status attribute.
     * @return the status. 
     */
    public String getStatus() {
        return status; // simply call status
    }

    /**
     * Get a string that represents the profile of the user.
     * 
     * @return "Name: " + name + "\n\tStatus: " + status + \n\tNumber of friend
     *         profiles: " + friend’s number + "\n"
     */
    public String toString() {
        // Just following the format
        String result = "";
        result += "Name: ";
        result += name;
        result += "\n\tStatus: ";
        result += status;
        result += "\n\tNumber of friend profiles: ";
        result += getFriendProfiles().size();
        // result+= friendProfiles.size(); // number of friends
        result += "\n";

        return result;
    }

    /**
     * Displays the profile and the friends profiles.
     */
    public void display() {
        String get = toString(); // get the first part of display
        get += "Friends: ";
        for (int k = 0; k < friendProfiles.size(); k++) {
            get += "\n\t"; // format
            get += friendProfiles.get(k).getName(); // grab the name
        }
        System.out.println(get);
    }

    /**
     * Gets the friend profiles arraylist.
     * 
     * @return the arraylist.
     */
    public ArrayList<Profile> getFriendProfiles() {
        return friendProfiles;
    }

    /**
     * This adds friend.
     * 
     * @param user the friend we want to add
     */
    public void addFriend(Profile user) {
        if (friendProfiles.contains(user) == false) { // no duplicates @1783
            friendProfiles.add(user); // simply add the user.
        }

    }

    /**
     * Removes an existing friend from the list of friends.
     * 
     * @param user who we want to remove
     * @return true if the removal of the profile is successful, false otherwise.
     */
    public boolean unFriend(Profile user) {
        if (friendProfiles.contains(user) == true) { // existing friend
            friendProfiles.remove(user); // unfriend
        }
        return false; // this friend does not exist to remove
    }

    /**
     * Just for checking.
     * Ignore just my testing.
     * 
     * @param args not used.
     */
    public static void main(String[] args) {
        Profile user1 = new Profile();
        user1.setName("Carl", "Bom");
        user1.status = "widow";

        Profile pam = new Profile();
        pam.name = "pam";
        pam.status = "single";

        user1.addFriend(pam);

        user1.display();
        user1.unFriend(pam);
        user1.display();
    }

}
