package Twitter_Analysis;

import java.util.*;

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
    public void addEdge(int from, int to){

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
        for (GraphNode node: graphNodeMap.get(user).getFollowers()) {
            if(clusters.size()<k) {
                Cluster c = new Cluster();
                c.setCenter(node);
                clusters.add(c);
            }
            else {
                float minDistance=Float.MAX_VALUE;
                Cluster closestCluster=null;
                for (Cluster c: clusters){
                    float distance=Math.abs(c.getCenterWeight()-node.getWeight());
                    if(distance<minDistance){
                        closestCluster=c;
                        minDistance=distance;
                    }
                }
                closestCluster.addNodeToCluster(node);
            }
        }

    }
}
