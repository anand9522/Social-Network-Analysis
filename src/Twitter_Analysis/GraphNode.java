package Twitter_Analysis;

import graph.Graph;

import java.util.HashSet;

/**
 * Created by anand on 09/01/17.
 */
public class GraphNode {

    public GraphNode(int user_id){
        this.covered=false;
        this.user_id=user_id;
        this.unmarked_followers=0;
        following=new HashSet<>();
        followers=new HashSet<>();
    }
    //whether it has been reached through an influencer or not.
    private boolean covered;

    private int user_id;

    //Counting number of followers left to be covered
    private int unmarked_followers;

    //People whom this user follows
    private HashSet<GraphNode> following;

    //This users followers
    private HashSet<GraphNode> followers;

    public boolean isCovered() {
        return covered;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getUnmarked_followers() {
        return unmarked_followers;
    }

    public HashSet getFollowers(){
        return new HashSet(followers);
    }

    public HashSet getFollowing(){
        return new HashSet(following);
    }

    public void setUnmarkedFollowers(int unmarked_followers){
        this.unmarked_followers=unmarked_followers;
    }

    public void addFollowing(GraphNode person){
        following.add(person);
    }

    public void addFollower(GraphNode person){
        followers.add(person);
    }
}
