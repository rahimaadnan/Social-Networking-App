import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
//import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;
//import java.util.PriorityQueue; // we can use it @1649

/**
 * This class is the implentation of the graph interface.
 * 
 * @param <T> our generic.
 */
public class Graph<T> implements GraphInterface<T> {
    /**
     * This keep tracks of how many edges we have.
     */
    private int countsEdges;

    /**
     * Creating a graph data structure.
     * In which the key is the label.
     * The value is the vertex itself.
     */
    protected HashMap<T, Vertex<T>> vertices; // @1776 protected is allowed

    // A dictionary of key (Vertex label), value (Vertex) pair.
    /**
     * Simple constructor.
     */
    public Graph() {
        vertices = new HashMap<>(); // initializes the graph with an empty graph structure.
        countsEdges = 0;
    }

    /**
     * This method Adds a given vertex to this graph. If vertexLabel is null, it
     * returns false.
     * 
     * @param vertexLabel the label of the vertex we are adding.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addVertex(T vertexLabel) {
        if (vertexLabel == null) { // the label cant be null
            return false;
        }

        vertices.put(vertexLabel, new Vertex<>(vertexLabel)); // simply add
        return true;
    }

    /**
     * Removes a vertex with the given vertexLabel from this graph and returns the
     * removed vertex. If vertex does not exist, it will return null.
     * 
     * @param vertexLabel the label of the vertex we are removing.
     * @return the removed vertex.
     */
    public VertexInterface<T> removeVertex(T vertexLabel) {
        if (vertexLabel == null) { // cant remove a null value
            return null;
        }

        boolean checks = vertices.containsKey(vertexLabel); // check if the key exists
        if (checks == false) { // its not in the graph in the first place
            return null;
        }

        // System.out.println("this is the label " + vertexLabel);
        VertexInterface<T> returns = vertices.get(vertexLabel); // for returning
        vertices.remove(vertexLabel); // we can just remove with the label

        // Vertex<T> returns = vertices.get(vertexLabel);
        // vertices.remove(vertexLabel, new Vertex<>(vertexLabel));

        return returns;

    }

    /**
     * Checks if a weighted pair has an edge.
     * 
     * @param begin      our start.
     * @param end        our end.
     * @param edgeWeight our weight.
     * @return if a path exist.
     */
    private boolean weightedhasEdge(T begin, T end, double edgeWeight) {
        boolean returner = false;

        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        if (bvertex == null || evertex == null) { // first check if these value are null or not
            return false;
        }
        Iterator<VertexInterface<T>> loopy = bvertex.getNeighborIterator(); // to loop over
        Iterator<Double> weightloopy = bvertex.getWeightIterator(); // to loop over
        while (loopy.hasNext() && weightloopy.hasNext() && !returner) { // keep going till there is nothing next
            VertexInterface<T> nextDoor = loopy.next(); // grab the vertex to compare
            Double nextDoorWeight = weightloopy.next(); // grab the vertex.weight to compare
            if (nextDoor.equals(evertex) && edgeWeight == nextDoorWeight) { // IT WAS FOUND
                returner = true; // meaning there is a connection
                break; // stop the loop we found a connection.
            }
        }

        return returner;
    }

    /**
     * Adds a weighted edge between two given distinct vertices that are currently
     * in this graph.
     * 
     * @param begin      where to start.
     * @param end        where to end.
     * @param edgeWeight weight of the edge.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addEdge(T begin, T end, double edgeWeight) {
        boolean returns = false; // to check if it was added sucessfully and for returning.
        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        boolean checks = weightedhasEdge(begin, end, edgeWeight); // call to see if there as edge

        if (checks == true) { // this means edge exists between two given vertices.
            returns = false;
            return returns;
        }

        // but we have to check if bVertex and eVertex are null.
        if (bvertex == null || evertex == null) {
            returns = false;
            return returns;
        }

        if (bvertex.equals(evertex)) { // For this project, a vertex cannot create an edge with itself. @1657
            returns = false;
            return returns;
        }

        // now we are able to add
        else {
            // it goes both ways
            bvertex.connect(evertex, edgeWeight);
            evertex.connect(bvertex, edgeWeight);

            countsEdges += 2; // as its undirected
            returns = true;
            return returns;
        }

    }

    /**
     * Adds an unweighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must not already be in the graph.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addEdge(T begin, T end) {
        boolean returns = false; // to check if it was added sucessfully and for returning.
        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        // first check if this edge already occurs
        boolean checks = hasEdge(begin, end); // call hasEdge

        if (checks == true) { // this means edge exists between two given vertices.
            returns = false;
            return returns;

        }

        // but we have to check if bVertex and eVertex are null.
        if (bvertex == null || evertex == null) {
            returns = false;
            return returns;
        }

        if (bvertex.equals(evertex)) { // For this project, a vertex cannot create an edge with itself. @1657
            returns = false;
            return returns;
        }

        // now we are able to add
        else {
            // it goes both ways
            bvertex.connect(evertex);
            evertex.connect(bvertex);

            countsEdges += 2; // as its undirected
            returns = true;
            return returns;
        }
    }

    /**
     * Removes a weighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must already be in the graph.
     * 
     * @param begin      where to start.
     * @param end        where to end.
     * @param edgeWeight weight of the edge.
     * @return true if the removal is successful, false otherwise.
     */
    public boolean removeEdge(T begin, T end, double edgeWeight) {
        boolean checks = weightedhasEdge(begin, end, edgeWeight); // call to see if there as edge
        if (checks == false) { // an edge doesnt exist between them so we cant remove anything
            return false;
        }

        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        // but we have to check if bVertex and eVertex are null.
        if (bvertex == null || evertex == null) {
            return false;
        }

        else {
            // remove it both ways
            bvertex.disconnect(evertex, edgeWeight);
            evertex.disconnect(bvertex, edgeWeight);
            countsEdges -= 2; // as its undirected
            return true;
        }

    }

    /**
     * Removes an unweighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must already be in the graph.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return true if the removal is successful, false otherwise.
     */
    public boolean removeEdge(T begin, T end) {
        boolean checks = hasEdge(begin, end);
        if (checks == false) { // an edge doesnt exist between them so we cant remove anything
            return false;
        }

        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        // but we have to check if bVertex and eVertex are null.
        if (bvertex == null || evertex == null) {
            return false;
        }

        else {
            // remove it both ways
            bvertex.disconnect(evertex);
            evertex.disconnect(bvertex);
            countsEdges -= 2; // as its undirected
            return true;
        }

    }

    /**
     * Sees whether an undirected edge exists between two given vertices.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return true if it exists, false if not.
     */
    public boolean hasEdge(T begin, T end) {
        boolean returner = false;
        Vertex<T> bvertex = vertices.get(begin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(end); // get the value associated with the end key

        if (bvertex == null || evertex == null) { // first check if these value are null or not
            return false;
        }
        Iterator<VertexInterface<T>> loopy = bvertex.getNeighborIterator(); // to loop over
        while (loopy.hasNext() && !returner) { // keep going till we there is nothing next
            VertexInterface<T> nextDoor = loopy.next(); // grab the vertex to compare
            if (nextDoor.equals(evertex)) { // IT WAS FOUND
                returner = true; // meaning there is a connection
                break; // stop the loop we found a connection.
            }
        }

        return returner;
    }

    /**
     * This method gets the number of vertices in this graph.
     * 
     * @return the number of vertices
     */
    public int getNumberOfVertices() {
        return vertices.size(); // simply get the size of the structure.
    }

    /**
     * This method gets the number of undirected edges in this graph.
     * 
     * @return the number of edges.
     */
    public int getNumberOfEdges() {
        return countsEdges; // just get our variable.
    }

    /**
     * Check if empty.
     * 
     * @return this method returns true, if this graph is empty, false otherwise.
     */
    public boolean isEmpty() {
        return vertices.isEmpty(); // call isEmpty on the hashmap
    }

    /**
     * Gets vertices.
     * 
     * @return his method returns the list of all vertices in the graph. If the
     *         graph is empty, it returns null.
     */
    public List<VertexInterface<T>> getVertices() {
        if (vertices.size() == 0) { // there is nothing in the graph
            return null;
        }
        List<VertexInterface<T>> holder = new ArrayList<>(); // create a newlist

        for (T label : vertices.keySet()) { // go through every key in this hashmap
            VertexInterface<T> temp = vertices.get(label); // get the vertex using the label
            holder.add(temp); // add to the list
        }
        return holder; // return the list we created

    }

    /**
     * This method clears the graph.
     */
    public void clear() {
        vertices.clear(); // clear the hashmap
        countsEdges = 0; // reset the counter
    }

    /**
     * Performs a breadth- first traversal of a graph.
     * 
     * @param origin where to start.
     * @return the queue that contains the result.
     */
    public Queue<T> getBreadthFirstTraversal(T origin) {
        Queue<T> result = new LinkedList<>(); // for the resulting travesl order
        Queue<T> vistedQueue = new LinkedList<>(); // hold the verteces that are visted

        if (vertices.containsKey(origin) == false) { // if the orgin doesnt exist return an empty queue see @1664
            return result; // empty queue
        }

        if (vertices.size() == 0) { // there is nothing to traverse
            return result; // empty queue
        }

        // first lets add orgin to the queues
        result.add(origin);
        vistedQueue.add(origin);

        Vertex<T> orginVertex = vertices.get(origin); // create the vertex
        orginVertex.visit(); // mark it as visted

        while (!vertices.isEmpty()) { // now lets loop through

            T orginal = vistedQueue.poll(); // grab the head and remove
            Vertex<T> vertex1 = vertices.get(orginal); // get the value associated with the begin key

            if (vertex1 == null) { // if the vertex is null we have reached the end
                break; // STOP
            }

            Iterator<VertexInterface<T>> newIterator = vertex1.getNeighborIterator(); // for looping

            while (newIterator.hasNext()) { // keep going till we reach the end
                VertexInterface<T> nextDoor = newIterator.next(); // grab the vertex
                if (nextDoor.isVisited() == false) { // as long as we havent seen it yet
                    result.add(nextDoor.getLabel()); // add it to our order
                    vistedQueue.add(nextDoor.getLabel()); // as we already visted
                    nextDoor.visit(); // mark as seen
                }
            }

        }

        // now reset the vertices
        // make a copy to alter
        Queue<T> resultcopy = new LinkedList<>();
        resultcopy.addAll(result);

        while (true) {
            T temp = resultcopy.poll(); // grab a value
            if (temp == null) {
                break; // stop the loop
            }

            VertexInterface<T> currentVertex = vertices.get(temp); // get its vertex

            if (currentVertex == null) { // check if its null
                break;
            }
            currentVertex.unvisit(); // mark as univisted and reset

        }

        return result;

    }

    /**
     * Get the shortest distance between the origin and destination.
     * 
     * @param origin      where to start.
     * @param destination where we want to go.
     * @param path        the sequence.
     * @return the shortest distance between the origin and destination. If a path
     *         does not exist, it returns the maximum integer (to simulate
     *         infinity).
     */
    public int getShortestPath(T origin, T destination, Stack<T> path) { // NOT DONE

        int len = 0; // the length

        Vertex<T> bvertex = vertices.get(origin); // get the value associated with the begin key
        Vertex<T> evertex = vertices.get(destination); // get the value associated with the end key

        if (bvertex == null || evertex == null) {
            return Integer.MAX_VALUE; // does not exist, it returns the maximum integer (to simulate Ifinity).
        }

        if (vertices.isEmpty() == true) { // make sure there is soemething even in the graph
            return Integer.MAX_VALUE; // does not exist, it returns the maximum integer (to simulate Ifinity).
        }

        Queue<T> getbftdes = getBreadthFirstTraversal(destination);

        Queue<T> getbftorg = getBreadthFirstTraversal(origin);

        int desCount = 0;
        int orgCount = 0;

        if (getbftdes.contains(origin) == false || getbftdes.contains(destination) == false
                || getbftorg.contains(origin) == false || getbftorg.contains(destination) == false) {
            return Integer.MAX_VALUE; // does not exist, it returns the maximum integer (to simulate Ifinity).
        }

        // lets check getbftdes first
        Stack<T> pathdes = new Stack<>();
        while (true) {
            T temp = getbftdes.poll(); // grab
            pathdes.push(temp);
            if (temp.equals(origin)) {
                break;
            }
            desCount++;
        }

        Stack<T> pathorg = new Stack<>();
        while (true) {
            T temp1 = getbftorg.poll(); // grab
            pathorg.push(temp1);
            if (temp1.equals(destination)) {
                break;
            }
            orgCount++;

        }

        // now compare them and see which one is the smaller and return the smallest
        // ammount

        if (orgCount == desCount) { // if they are the same?
            len = desCount;
            path.addAll(pathdes);
        }

        if (orgCount < desCount) {
            len = orgCount;
            path.addAll(pathorg);
        }
        if (desCount < orgCount) {
            len = desCount;
            path.addAll(pathdes);
        }

        // System.out.println("this is path " + path);

        return len;
    }

    /**
     * Just for checking.
     * 
     * @param args not used.
     */
    public static void main(String[] args) {
        Graph<Integer> graph1 = new Graph<Integer>();
        graph1.addVertex(1);
        graph1.addVertex(2);
        graph1.addVertex(3);
        graph1.addVertex(4);
        graph1.addVertex(5);
        graph1.addVertex(6);
        graph1.addVertex(7);
        graph1.addVertex(8);
        graph1.addVertex(9);

        graph1.addEdge(1, 2);
        graph1.addEdge(1, 2, 31.78);
        graph1.addEdge(1, 2, 51.78);
        graph1.addEdge(1, 3);
        graph1.addEdge(1, 4);
        graph1.addEdge(1, 5);
        graph1.addEdge(2, 6);
        graph1.addEdge(2, 7);
        graph1.addEdge(3, 8);
        graph1.addEdge(8, 9);

        System.out.println(graph1.getNumberOfEdges());
        // System.out.println(graph1.hasEdge(1, 2));
        // System.out.println(graph1.hasEdge(1, 99));

        System.out.println(graph1.removeEdge(1, 2, 31.78));
        System.out.println(graph1.getNumberOfEdges());
        System.out.println(graph1.removeEdge(1, 2));
        System.out.println(graph1.getNumberOfEdges());
        System.out.println(graph1.getNumberOfVertices());
        System.out.println(graph1.removeVertex(3));
        System.out.println(graph1.getNumberOfVertices());

        // System.out.println("this is the number of edges " +
        // graph1.getNumberOfEdges());
        // System.out.println("this is the number of vertices " +
        // graph1.getNumberOfVertices());

        // System.out.println(graph1.getBreadthFirstTraversal(8));

        // System.out.println(graph1.getShortestPath(1, 2, path));
        // System.out.println(graph1.getShortestPath(1, 6, path));

        // System.out.println(graph1.addEdge(1, 2)); // true
        // System.out.println(graph1.addEdge(1, 2, 76.56)); // true
        // System.out.println(graph1.addEdge(1, 2, 76.16)); // true

        // System.out.println("this is the size " + graph1.getNumberOfEdges());

        // System.out.println(graph1.addEdge(1, 2)); // false
        // System.out.println(graph1.addEdge(1, 2, 76.56)); // false

        // System.out.println(graph1.removeEdge(1, 2)); // true
        // System.out.println(graph1.removeEdge(1, 2, 76.56)); // true
        // System.out.println("this is the size " + graph1.getNumberOfEdges());

        // if(graph1.weightedhasEdge(1, 2, 76.56) == false){
        // System.out.println("YAYYAYAYY1");
        // }

        /*
         * graph1.addVertex(3);
         * graph1.addVertex(4);
         * graph1.addVertex(5);
         * graph1.addVertex(6);
         * graph1.addVertex(7);
         * graph1.addVertex(8);
         * 
         * 
         * if(graph1.getNumberOfVertices() ==8 ){
         * System.out.println("get number of vertices is working");
         * }
         * System.out.println("-----------------------------------------");
         * 
         * if(graph1.addEdge(1, 2) == true){
         * System.out.println("Add EDGE IS WORKING");
         * }
         * if(graph1.addEdge(5, 6) == true){
         * System.out.println("Add EDGE IS WORKING");
         * }
         * 
         * if(graph1.getNumberOfEdges() == 4){
         * System.out.println("NUMBER OF EDGES IS WORKING");
         * }
         * 
         * if(graph1.addEdge(100, 2) == false){
         * System.out.println("ADD EDGE IS WORKING ");
         * }
         * 
         * if(graph1.addEdge(1, 2, 98.09) == false){
         * System.out.println("ADD EDGE IS WORKING");
         * }
         * 
         * System.out.println("-----------------------------------------");
         * 
         * if(graph1.hasEdge(1, 2) == true){
         * System.out.println("HAS EDGE IS WORKING");
         * }
         * 
         * if(graph1.hasEdge(5 , 6) == true){
         * System.out.println("HAS EDGE IS WORKING");
         * }
         * 
         * if(graph1.hasEdge(100, 2) == false){
         * System.out.println("HAS EDGE IS  WORKING");
         * }
         * 
         * if(graph1.hasEdge(3, 1) == false){
         * System.out.println("HAS EDGE IS  WORKING");
         * }
         * 
         * 
         * System.out.println("-----------------------------------------");
         * 
         * if(graph1.removeEdge(1, 2, 89.78) == true){ // SHOULD THIS BE TRUE?????
         * System.out.println("REMOVE EDGE IS WORKING");
         * }
         * 
         * System.out.println(graph1.hasEdge(1, 2) );
         * 
         * //if(graph1.removeEdge(1, 4) == false){ // SHOULD THIS BE TRUE?????
         * //System.out.println("REMOVE EDGE IS WORKING");
         * //}
         * //if(graph1.removeEdge(100, 4) == false){ // SHOULD THIS BE TRUE?????
         * //System.out.println("REMOVE EDGE IS WORKING");
         * // }
         * 
         * if(graph1.getNumberOfEdges() == 2){
         * System.out.println("REMOVE EDGE IS WORKING wooo");
         * }
         * 
         * //if(graph1.hasEdge(1, 2) == false){
         * //System.out.println("REMOVE EDGE IS WORKING woo34o");
         * //}
         * 
         * 
         */

    }

}
