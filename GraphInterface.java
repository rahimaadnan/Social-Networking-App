import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This interface represents all the functionalities of the graph data structure
 * you are going to use in this project.
 * 
 * @param <T> our generic.
 * @author Rahima Adnan
 */
interface GraphInterface<T> {
    /**
     * This method Adds a given vertex to this graph. If vertexLabel is null, it
     * returns false.
     * 
     * @param vertexLabel the label of the vertex we are adding.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addVertex(T vertexLabel);

    /**
     * Removes a vertex with the given vertexLabel from this graph and returns the
     * removed vertex. If vertex does not exist, it will return null.
     * 
     * @param vertexLabel the label of the vertex we are removing.
     * @return the removed vertex.
     */
    public VertexInterface<T> removeVertex(T vertexLabel);

    /**
     * Adds a weighted edge between two given distinct vertices that are currently
     * in this graph.
     * 
     * @param begin      where to start.
     * @param end        where to end.
     * @param edgeWeight weight of the edge.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addEdge(T begin, T end, double edgeWeight);

    /**
     * Adds an unweighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must not already be in the graph.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return boolean to see if it was added sucessfully.
     */
    public boolean addEdge(T begin, T end);

    /**
     * Removes a weighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must already be in the graph.
     * 
     * @param begin      where to start.
     * @param end        where to end.
     * @param edgeWeight weight of the edge.
     * @return true if the removal is successful, false otherwise.
     */
    public boolean removeEdge(T begin, T end, double edgeWeight);

    /**
     * Removes an unweighted edge between two given distinct vertices that are
     * currently in this graph. The desired edge must already be in the graph.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return true if the removal is successful, false otherwise.
     */
    public boolean removeEdge(T begin, T end);

    /**
     * Sees whether an undirected edge exists between two given vertices.
     * 
     * @param begin where to start.
     * @param end   where to end.
     * @return true if it exists, false if not.
     */
    public boolean hasEdge(T begin, T end);

    /**
     * This method gets the number of vertices in this graph.
     * 
     * @return the number of vertices
     */
    public int getNumberOfVertices();

    /**
     * This method gets the number of undirected edges in this graph.
     * 
     * @return the number of edges.
     */
    public int getNumberOfEdges();

    /**
     * Check if empty.
     * 
     * @return this method returns true, if this graph is empty, false otherwise.
     */
    public boolean isEmpty();

    /**
     * Gets vertices.
     * 
     * @return his method returns the list of all vertices in the graph. If the
     *         graph is empty, it returns null.
     */
    public List<VertexInterface<T>> getVertices();

    /**
     * This method clears the graph.
     */
    public void clear();

    /**
     * Performs a breadth- first traversal of a graph.
     * 
     * @param origin where to start.
     * @return the queue that contains the result.
     */
    public Queue<T> getBreadthFirstTraversal(T origin);

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
    public int getShortestPath(T origin, T destination, Stack<T> path);

}
