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

    //Check for Node
    public boolean containsNode(int node){
        return graphNodeMap.containsKey(node);
    }

    public void addEdge(int from, int to){
        if (!graphNodeMap.containsKey(from)){
            GraphNode node = new GraphNode(from);
            graphNodeMap.put(from,node);
        }

        if (!graphNodeMap.containsKey(to)){
            GraphNode node = new GraphNode(to);
            graphNodeMap.put(to,node);
        }

        graphNodeMap.get(to).addFollower(graphNodeMap.get(from));
        graphNodeMap.get(from).addFollowing(graphNodeMap.get(to));
    }

    // Easier Question: k-means clustering. Though this turned out to be harder than the harder question :).
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
    // Harder Question: k-most Domination Set. this is a heuristic because optimal solution is NP- Complete and not feasible for large graph.
    public HashSet<GraphNode> getK_DominationSet(int k){
        HashSet<GraphNode> influencers=new HashSet<>();
        if (graphNodeMap.size()<k){
            return null;
        }

        while (influencers.size()<k) {
            int max_uncovered_followers = 0;
            GraphNode largest_influencer = null;
            for (GraphNode node : graphNodeMap.values()) {
                if (node.getUnmarked_followers() > max_uncovered_followers) {
                    max_uncovered_followers = node.getUnmarked_followers();
                    largest_influencer = node;
                }
            }
            if (max_uncovered_followers == 0) {
                break;
            }
            else {
                influencers.add(largest_influencer);
                recomputeUnmarkedFollowers(largest_influencer);
            }
        }
        return influencers;
    }

    private void recomputeUnmarkedFollowers(GraphNode largest_influencer) {
        largest_influencer.setCovered();
        for (GraphNode node: largest_influencer.getFollowers()){
            if (!node.isCovered()){
                node.setCovered();
                for (GraphNode following: node.getFollowing()){
                    following.decrementUnmarkedFollowers();
                }
            }
        }
    }


    private boolean reallotNodesToClusters(HashSet<Cluster> clusters) {
        Boolean moved=false;
        for (Cluster cluster:clusters){
            for (GraphNode node: cluster.getAllotedNodes()) {
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
