/**
 * @author UCSD MOOC development team
 * 
 * Utility class to add vertices and edges to a graph
 *
 */
package util;

import Twitter_Analysis.Graph;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class GraphLoader {
    /**
     * Loads graph with data from a file.
     * The file should consist of lines with 2 integers each, corresponding
     * to a "from" vertex and a "to" vertex.
     */ 
    public static void loadGraph(Graph g, String filename) {
        Scanner sc;
        try {
            sc = new Scanner(new File(filename));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        // Iterate over the lines in the file, adding new
        // vertices as they are found and connecting them with edges.
        while (sc.hasNextInt()) {
            int v1 = sc.nextInt();
            int v2 = sc.nextInt();

            g.addEdge(v1, v2);
        }
        
        sc.close();
    }
}
