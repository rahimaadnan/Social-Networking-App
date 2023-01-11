import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

/**
 * This interface represents all the functionalities of a Vertex in a graph.
 * 
 * @param <T> our generic.
 * @author Rahima Adnan
 */
interface VertexInterface<T> {

    /**
     * Gets this vertex’s label.
     * 
     * @return the label.
     */
    public T getLabel();

    /**
     * Thid method gets the number of neighbors.
     * 
     * @return the number of neighbors of this vertex.
     */
    public int getNumberOfNeighbors();

    /**
     * Marks this vertex as visited.
     */
    public void visit();

    /**
     * Removes this vertex’s visited mark.
     */
    public void unvisit();

    /**
     * Check if is visted.
     * 
     * @return true if the vertex is visited, false otherwise.
     */
    public boolean isVisited();

    /**
     * Connects this vertex and endVertex with a weighted edge. The two vertices
     * cannot be the same, and must not already have this edge between them.
     * Two vertices are equal (same)if their labels are equal (same).
     * 
     * @param endVertex  the vertex to connect to.
     * @param edgeWeight the weight of the edge.
     * @return true if the connection is successful, false otherwise.
     */
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight);

    /**
     * Connects this vertex and endVertex with a unweighted edge. The two vertices
     * cannot be the same, and must not already have this edge between them.
     * Two vertices are equal (same)if their labels are equal (same).
     * 
     * @param endVertex the vertex to connect to.
     * @return true if the connection is successful, false otherwise.
     */
    public boolean connect(VertexInterface<T> endVertex);

    /**
     * Disconnects this vertex from a given vertex with a weighted edge, i.e.,
     * removes the edge.
     * The Edge should exist in order to be disconnected.
     * 
     * @param endVertex  the vertex to disconnect to.
     * @param edgeWeight the weight of the edge.
     * @return true if the disconnection is successful, false otherwise.
     */
    public boolean disconnect(VertexInterface<T> endVertex, double edgeWeight);

    /**
     * Disconnects this vertex from a given vertex with an unweighted edge.
     * The Edge should exist in order to be disconnected.
     * 
     * @param endVertex the vertex to disconnect to.
     * @return true if the disconnection is successful, false otherwise.
     */
    public boolean disconnect(VertexInterface<T> endVertex);

    /**
     * Creates an iterator of this vertex's neighbors by following all edges that
     * begin at this vertex.
     * 
     * @return vertex's neighbors by following all edges that begin at this vertex.
     */
    public Iterator<VertexInterface<T>> getNeighborIterator();

    /**
     * Creates an iterator of the weights of the edges this vertex's neighbors by
     * following all edges that begin at this vertex.
     * 
     * @return weights of the edges this vertex's neighbors by following all edges
     *         that begin at this vertex.
     */
    public Iterator<Double> getWeightIterator();

    /**
     * Sees whether this vertex has at least one neighbor.
     * 
     * @return true or false.
     */
    public boolean hasNeighbor();

    /**
     * Gets an unvisited neighbor, if any, of this vertex.
     * 
     * @return the unvisited neighbor.
     */
    public VertexInterface<T> getUnvisitedNeighbor();

    /**
     * Records the previous vertex on a path to this vertex.
     * 
     * @param predecessor the prevouis.
     */
    public void setPredecessor(VertexInterface<T> predecessor);

    /**
     * Gets the recorded predecessor of this vertex.
     * 
     * @return the prevouis.
     */
    public VertexInterface<T> getPredecessor();

    /**
     * Sees whether a predecessor was recorded for this vertex.
     * 
     * @return true or false
     */
    public boolean hasPredecessor();

    /**
     * Records the cost of a path to this vertex.
     * 
     * @param newCost the updated cost.
     */
    public void setCost(double newCost);

    /**
     * Gets the cost of a plan.
     * 
     * @return the cost of a path to this vertex.
     */
    public double getCost();

}
