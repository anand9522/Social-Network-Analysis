package Twitter_Analysis;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by anand on 12/01/17.
 */
public class TwitterGraph {

    //int corresponding to GraphNode
    private HashMap<Integer,GraphNode> graphNodeMap;

    public TwitterGraph(){
        graphNodeMap=new HashMap<>();
    }
    //
    public void addEdge(){

    }

    public HashSet<Cluster> getCommunities(int user, int k){
        if(graphNodeMap.get(user).getFollowers().size()<k){
            return null;
        }
        HashSet<Cluster> clusters=new HashSet<>();
        randomClusterAllotment(clusters,user,k);

        return clusters;
    }

    public void randomClusterAllotment(HashSet<Cluster> clusters, int user, int k){
        HashSet<GraphNode> alloted_centers=new HashSet<>();
        Set<GraphNode> user_followers=new TreeSet<>(graphNodeMap.get(user).getFollowers());
        for (int i = 0; i < k; i++) {
            Cluster c=new Cluster();
        }
    }
}
