package Twitter_Analysis;

import graph.Graph;

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
        boolean moved=true;
        HashSet<Cluster> clusters=new HashSet<>();

        randomClusterAllotment(clusters,user,k);

        while (moved) {
            resetCenters(clusters);

            moved=reallotNodesToClusters(clusters);
        }
        return clusters;
    }

    private boolean reallotNodesToClusters(HashSet<Cluster> clusters) {
        Boolean moved=false;
        for (Cluster cluster:clusters){
            for (GraphNode node: cluster.getAllotedVertices()) {
                float min=Float.MAX_VALUE;
                Cluster nearestCluster=null;

                for (Cluster otherCluster : clusters) {
                    float diff=Math.abs(otherCluster.getCenterWeight()-node.getWeight());
                    if (diff<min){
                        min=diff;
                        nearestCluster=otherCluster;
                    }
                }

                if (!nearestCluster.equals(cluster)){
                    nearestCluster.addNodeToCluster(node);
                    cluster.removeNodeFromCluster(node);
                    moved=true;
                }

            }
        }
        return moved;
    }

    private void resetCenters(HashSet<Cluster> clusters) {
        for (Cluster cluster: clusters) {
            cluster.readjustCenter();
        }
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
