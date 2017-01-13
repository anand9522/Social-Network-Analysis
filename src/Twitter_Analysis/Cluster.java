package Twitter_Analysis;

import java.util.HashSet;

/**
 * Created by anand on 13/01/17.
 */

public class Cluster {
    private HashSet<GraphNode> allotedNodes;
    private GraphNode center;
    private float mean;
    private float sum;

    public Cluster() {
        allotedNodes=new HashSet<>();
        center=null;
        mean=0;
        sum=0;
    }

    public HashSet<GraphNode> getAllotedNodes() {
        return new HashSet<>(allotedNodes);
    }

    public void addNodeToCluster(GraphNode node){
        allotedNodes.add(node);
        sum+=node.getWeight();
        recomputeMean();
    }

    public void setCenter(GraphNode center){
        this.center=center;
    }

    public float getCenterWeight(){
        return center.getWeight();
    }

    public void removeNodeFromCluster(GraphNode node){
        allotedNodes.remove(node);
        sum-=node.getWeight();
        recomputeMean();
    }

    private void recomputeMean(){
        mean=sum/allotedNodes.size();
    }

    public void readjustCenter(){
        float closest=getCenterWeight();
        GraphNode closest_to_mean=center;
        for (GraphNode node:allotedNodes){
            float diff=Math.abs(node.getWeight()-mean);
            if (diff<closest){
                closest=diff;
                closest_to_mean=node;
            }
        }
        center=closest_to_mean;

    }
}
