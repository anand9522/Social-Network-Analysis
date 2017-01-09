package Twitter_Analysis;

import java.util.HashSet;

/**
 * Created by anand on 09/01/17.
 */
public class GraphNode {

    //whether it has been reached through an influencer or not.
    private boolean covered;

    //Counting number of followers left to be covered
    private int unmarked_followers;

    //People whom this user follows
    private HashSet<GraphNode> following;

    //This users followers
    private HashSet<GraphNode> followers;


}