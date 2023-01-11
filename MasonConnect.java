import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.sql.Array;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Is a graph of profiles that are connected together to create the social
 * network of users.
 * Each user is represented by a profile object.
 * 
 * @author Rahima Adnan
 */
public class MasonConnect {
    /**
     * A graph of profiles.
     */
    private Graph<Profile> newGraph; // graph of profiles

    /**
     * Initializes the social networking app.
     */
    public MasonConnect() {
        newGraph = new Graph<>(); // intalize
    }

    /**
     * This adds a user to our graph.
     * 
     * @param p is the user
     */
    public void addUser(Profile p) {
        newGraph.addVertex(p); // simply add
    }

    /**
     * Removes an existing user from the social network.
     * 
     * @param p the user you want to remove.
     * @return the user you removed. If the user does not exist, it returns null
     */
    public Profile removeUser(Profile p) {

        if (exists(p) == false) { // this person does not exists
            return null;
        }

        newGraph.removeVertex(p);
        return p;
    }

    /**
     * Creates a friendship between two users on MasonConnect.
     * 
     * @param a the first user.
     * @param b the second user.
     * @return If the friendship is created successfully, it returns true, false
     *         otherwise.
     */
    public boolean createFriendship(Profile a, Profile b) {

        if(exists(a) == false || exists(b) == false || a == null || b == null){ // check if the user exists or is null 
            return false;
        }
       
        if (hasFriendship(a, b) == true) { // check if the freindship already exists
            return false;
        }

        

        a.addFriend(b);
        b.addFriend(a);
        return newGraph.addEdge(a, b);
    }

    /**
     * Removes a friendship between two users on MasonConnect.
     * 
     * @param a the first user.
     * @param b the second user.
     * @return if the friendship is discontinued successfully, it returns true,
     *         false otherwise.
     * 
     */
    public boolean removeFriendship(Profile a, Profile b) {
        // System.out.println("this profile a " + a + "this is profile b " + b);

        if (hasFriendship(a, b) == false) { // check if they are friends in the in the first place
            return false;
        }

        a.unFriend(a);
        b.unFriend(b);
        return newGraph.removeEdge(a, b);
    }

    /**
     * Checking if there is an edge.
     * 
     * @param a the first user.
     * @param b the second user.
     * @return Returns true if there is friendship between Profiles a and b, false
     *         otherwise.
     */
    public boolean hasFriendship(Profile a, Profile b) {
        return newGraph.hasEdge(a, b);

    }

    /**
     * This method displays each profile's information and friends, starting from
     * the startPoint profile.
     * 
     * @param startPoint the orgin.
     */
    public void traverse(Profile startPoint) {
        Queue<Profile> newQueue = newGraph.getBreadthFirstTraversal(startPoint);
        // int count = newQueue.size();
        // int i =0;
        while (true) {
            Profile current = newQueue.poll();
            if (current == null) {
                break;
            }
            current.display();
            // i++;
        }

    }

    /**
     * Checking if the user exists.
     * 
     * @param user who we are checking.
     * @return true if a user with the given profile exists in MasonConnect, false
     *         otherwise.
     * 
     */
    public boolean exists(Profile user) {
        if (newGraph.vertices.get(user) == null) {
            return false; // it doesnt exist
        }
        return true;
        // List<VertexInterface<Profile>> holder = newGraph.getVertices(); // get all
        // the vertices
        // return holder.contains(user);
    }

    /**
     * Getting friend suggestion.
     * 
     * @param user who we are checking for.
     * @return a list of Profiles, who are friends with one or more of the profile's
     *         friends (but not currently the profile's friend)
     *         It returns null, if the user does not exist or if it does not have
     *         any friend suggestions
     */

    public List<Profile> friendSuggestion(Profile user) {

        if (exists(user) == false) {
            return null; // It returns null, if the user does not exist
        }

        HashMap<Profile, Profile> some;
        some = new HashMap<>();

        ArrayList<Profile> ogUserfriends = user.getFriendProfiles(); // first get the orginal users friends

        some.put(user, user); // add the user
        for (int x = 0; x < ogUserfriends.size(); x++) { // add to the hash map 
            some.put(ogUserfriends.get(x), ogUserfriends.get(x));
        }

        ArrayList<Profile> result = new ArrayList<>(); // this is what we will return

        for (int i = 0; i < ogUserfriends.size(); i++) {
            Profile current = ogUserfriends.get(i); // get a friend
            ArrayList<Profile> currentFriends = current.getFriendProfiles(); // get there friends
            if (currentFriends.isEmpty() == false) { // check if they have friends
                for (int j = 0; j < currentFriends.size(); j++) { // loop through that list
                    Profile temp = currentFriends.get(j); // grab to compare
                    if (some.containsKey(temp) == false
                            && (!temp.equals(user))) { // check if they are friends, no duplicates, and if they are not  the user themselves.
                                                      
                        result.add(temp);
                        some.put(temp, temp);
                    }
                }
            }

        }

        if (result.size() == 0) { // no friend suggestions
            return null;
        }

        return result;

    }

    /**
     * Checking connection level.
     * 
     * @param a user 1.
     * @param b user 2.
     * @return Returns the friendship distance between two profiles.
     */
    public int friendshipDistance(Profile a, Profile b) {

        if (exists(a) == true && exists(b) == true) { // both users exist

            if (a.equals(b)) { // the same user see @1863
                return 0;
            }

            Stack<Profile> path1 = new Stack<>();
            int result = newGraph.getShortestPath(a, b, path1); // call shortest path

            if (result == Integer.MAX_VALUE) { // if it eqauls max int that means a path does not exist,
                return -1;
            }

            else {
                return result;
            }

        }

        return -1;

    }

    /**
     * This is the main method.
     * 
     * @param args not used.
     */
    public static void main(String[] args) {
        System.out.println("Creating profiles and the network.");
        MasonConnect m = new MasonConnect();

        Profile malcom = new Profile();
        malcom.setName("Malcom", "X");
        malcom.setStatus("My name is Malcom.");

        Profile fannie = new Profile();
        fannie.setName("Fannie-lou ", "Hamer");
        fannie.setStatus("My name is Fannie.");

        Profile brown = new Profile();
        brown.setName("John", "Brown");
        brown.setStatus("My name is John Brown!");

        Profile lewis = new Profile();
        lewis.setName("John", "Lewis");
        lewis.setStatus("My name is also John.");

        m.addUser(malcom);
        m.addUser(fannie);
        m.addUser(brown);
        m.addUser(lewis);

        malcom.display();
        fannie.display();
        brown.display();
        lewis.display();

        System.out.println("-------------------------------------\n");
        System.out.println("Creating friendships.\n");

        m.createFriendship(malcom, fannie);
        m.createFriendship(fannie, brown);
        m.createFriendship(lewis, fannie);
        m.createFriendship(brown, lewis);

        // displaying MasonConnect
        m.traverse(malcom);

        System.out.println("-------------------------------------\n");
        System.out.println("Changing statuses / names.\n");

        lewis.setStatus("Just got married!");
        fannie.setStatus("Now Mrs. Smith!");
        fannie.setName("Fannie", "Smith");

        fannie.display();
        lewis.display();

        System.out.println("-------------------------------------\n");
        System.out.println("Checking Friendships .\n");

        Profile friendless1 = new Profile();
        friendless1.setName("Nameless ", " 1");
        friendless1.setStatus("My name is nameless1!");
        System.out.println(m.exists(friendless1));
        m.addUser(friendless1);
        System.out.println(m.exists(brown));
        System.out.println(m.hasFriendship(friendless1, fannie));
        System.out.println(m.hasFriendship(fannie, brown));
        System.out.println(m.hasFriendship(malcom, lewis));
        System.out.println(m.hasFriendship(friendless1, fannie));
        System.out.println(m.hasFriendship(brown, lewis));
        System.out.println(m.exists(friendless1));
        System.out.println(m.hasFriendship(lewis, fannie));

        System.out.println("---------------Suggestion----------------------------------");
        Profile friendless2 = new Profile();
        friendless2.setName("Nameless ", " 2");
        friendless2.setStatus("My name is nameless2!");
        m.addUser(friendless2);
        m.createFriendship(friendless1, friendless2);
        m.createFriendship(friendless2, lewis);
        System.out.println(m.friendSuggestion(friendless2));
        System.out.println(m.friendSuggestion(brown));

        System.out.println(m.friendSuggestion(lewis));

        System.out.println("---------------Distance----------------------------------");
        System.out.println(m.friendshipDistance(fannie, brown));

        // System.out.println(m.exists(brown));
        System.out.println(m.friendshipDistance(fannie, friendless1));
        System.out.println(m.friendshipDistance(fannie, lewis));
        System.out.println(m.friendshipDistance(malcom, friendless2));
        m.removeUser(friendless2);
        m.removeFriendship(fannie, brown);

        System.out.println(m.friendshipDistance(malcom, friendless1));
        System.out.println(m.friendshipDistance(malcom, friendless2));
        System.out.println(m.friendshipDistance(fannie, brown));


        //System.out.println("BOOOOOOBB45345");
    } // end main
}

