import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

import java.util.NoSuchElementException; // Professor said this was allowed 

/**
 * This generic class implements the VertexInterface.
 * 
 * @param <T> our genric.
 * @author Rahima Adnan
 * 
 */
public class Vertex<T> implements VertexInterface<T> {
    /**
     * Represents the label of the vertex.
     */
    private T label;

    /**
     * Stores if the vertex is visited or not, true if visited.
     */
    private boolean visted;

    /**
     * Behind the curent.
     */
    private VertexInterface<T> previousVertex;

    /**
     * The cost.
     */
    private double cost;
    /**
     * List of edges to neighbors. Note that there is an Edge class used.
     */
    private List<Edge> edgeList;

    // INNER CLASS BELOW
    // ___________________________________________________________________________________
    /**
     * Private Inner Edge class.
     */
    private class Edge {
        /**
         * This is the weight of edge.
         */
        private double weightOfEdge;

        /**
         * Used to get the vertex.
         */
        private VertexInterface<T> vertex;

        /**
         * This is a simple constructor. Just intializing.
         * 
         * @param weightOfEdge the weight.
         * @param vertex       the end vertex.
         */
        private Edge(double weightOfEdge, VertexInterface<T> vertex) {
            this.vertex = vertex;
            this.weightOfEdge = weightOfEdge;
        }

        /**
         * Simple get method for the edges weight.
         * 
         * @return the weight.
         */
        private double getterEdgeWeight() {
            return weightOfEdge;
        }

        /**
         * Simple get method for the end Vertex.
         * 
         * @return the end vertex.
         */
        private VertexInterface<T> getterEndVertex() {
            return vertex;
        }

    }
    // ___________________________________________________________________________________

    /**
     * Simple constructor.
     * Initializes label to the given value, visited → false, cost → 0.0,
     * previousVertex →null, and the edgeList to a default list.
     * 
     * @param vertexLabel what we are setting the label value to.
     */
    public Vertex(T vertexLabel) {
        label = vertexLabel;
        visted = false;
        cost = 0.0;
        previousVertex = null;
        edgeList = new LinkedList<Edge>();
    }

    /**
     * Gets this vertex’s label.
     * 
     * @return the label.
     */
    public T getLabel() {
        return label;
    }

    /**
     * Marks this vertex as visited.
     */
    public void visit() {
        visted = true;
    }

    /**
     * Removes this vertex’s visited mark.
     */
    public void unvisit() {
        visted = false;
    }

    /**
     * Check if is visted.
     * 
     * @return true if the vertex is visited, false otherwise.
     */
    public boolean isVisited() {
        if (visted == true) {
            return true;
        }
        return false;
    }

    // INNER CLASS BELOW
    // ___________________________________________________________________________________
    /**
     * Private Inner Simple iterator class.
     */
    private class NeightborIterator implements Iterator<VertexInterface<T>> {

        /**
         * created to iterate over.
         */
        private Iterator<Edge> goer; // created to iterate over

        /**
         * Simple constructor.
         */
        private NeightborIterator() {
            goer = edgeList.iterator();// intialize
        }

        /**
         * To check if there is value next.
         * 
         * @return a boolean true or false.
         */
        public boolean hasNext() {
            return goer.hasNext(); // see java doc above
        }

        /**
         * This method grabs the next value.
         * 
         * @return the next value.
         */
        public VertexInterface<T> next() {
            VertexInterface<T> nexty = null; // holds the next value
            boolean caller = goer.hasNext(); // calling to check if there is next

            if (caller == false) { // nothing is next
                throw new NoSuchElementException(); // end and throw an error
            }

            else { // this means there is a next value
                Edge nextNeighEdge = goer.next(); // go to the next
                nexty = nextNeighEdge.getterEndVertex(); // grab the value
            }

            return nexty;
        }
    }
    // ___________________________________________________________________________________

    /**
     * Creates an iterator of this vertex's neighbors by following all edges that-.
     * begin at this vertex.
     * 
     * @return vertex's neighbors by following all edges that begin at this vertex.
     */
    public Iterator<VertexInterface<T>> getNeighborIterator() {
        return new NeightborIterator(); // simply call it
    }

    // ___________________________________________________________________________________________________________________
    /**
     * Helper method to check if there is an edge between two unweigheted vertices.
     * @param endVertex where to end. 
     * @return true or false to see if it was sucessful.
     * 
     */
    private boolean unWeightedhelper(VertexInterface<T> endVertex) {
        boolean result = false;
        Iterator<VertexInterface<T>> newIterator = getNeighborIterator(); // get the iterator
        while (newIterator.hasNext() && !result) { // keep going till the we reach the end
            VertexInterface<T> nextDoor = newIterator.next(); // grab the vertex
            if (endVertex.equals(nextDoor)) { // FOUND AN EXISTING EDGE
                result = true; // result becomes true as a path already exists
                break;
            }
        }
        return result;
    }
    /**
     * Helper method to check if there is an edge between two weigheted vertices.
     * @param endVertex where to end.
     * @param edgeWeight the weight. 
     * @return true or false to see if it was sucessful.
     */
    private boolean weightedhelper(VertexInterface<T> endVertex, double edgeWeight) {
        boolean result = false;
        Iterator<VertexInterface<T>> newIterator = getNeighborIterator(); // get the iterator
        Iterator<Double> weightIt = getWeightIterator(); // get the iterator

        while (newIterator.hasNext() && weightIt.hasNext() && !result) { // keep going till the we reach the end
            VertexInterface<T> nextDoor = newIterator.next(); // grab the vertex
            Double nextDoorweight = weightIt.next();

            if (endVertex.equals(nextDoor) && edgeWeight == nextDoorweight) { // FOUND AN EXISTING EDGE
                result = true; // result becomes true as a path already exists
                break;
            }
        }
        return result;
    }

    // ___________________________________________________________________________________________________________________

    /**
     * Connects this vertex and endVertex with a weighted edge. The two vertices
     * cannot be the same, and must not already have this edge between them.
     * Two vertices are equal (same)if their labels are equal (same).
     * 
     * @param endVertex  the vertex to connect to.
     * @param edgeWeight the weight of the edge.
     * @return true if the connection is successful, false otherwise.
     */

    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) { // FLAG

        if (endVertex.getLabel().equals(label)) { // Two vertices are equal (same)if their labels are equal (same).
            return false;
        }

        boolean ans = false;
        boolean helps = weightedhelper(endVertex, edgeWeight); // check if the path exists
        if (helps == false) { // the path doesnt exist
            edgeList.add(new Edge(edgeWeight, endVertex)); // create a new edge and add!
        }
        return ans;

    }

    /**
     * Connects this vertex and endVertex with a unweighted edge. The two vertices
     * cannot be the same, and must not already have this edge between them.
     * Two vertices are equal (same)if their labels are equal (same).
     * 
     * @param endVertex the vertex to connect to.
     * @return true if the connection is successful, false otherwise.
     */
    public boolean connect(VertexInterface<T> endVertex) {
        if (endVertex.getLabel().equals(label)) { // Two vertices are equal (same)if their labels are equal (same).
            return false;
        }

        boolean ans = false;
        boolean helps = unWeightedhelper(endVertex); // check if the path exists
        if (helps == false) { // the path doesnt exist
            edgeList.add(new Edge(0, endVertex)); // create a new edge and add!
        }
        return ans;
    }

    /**
     * Sees whether this vertex has at least one neighbor.
     * 
     * @return true or false.
     */
    public boolean hasNeighbor() {
        boolean ans = !edgeList.isEmpty(); // as long as the list isnt empty there must be a neighbor
        return ans;
    }

    /**
     * Records the cost of a path to this vertex.
     * 
     * @param newCost the updated cost.
     */
    public void setCost(double newCost) {
        cost = newCost; // just change intialize the value;
    }

    /**
     * Gets the cost of a plan.
     * 
     * @return the cost of a path to this vertex.
     */
    public double getCost() {
        return cost; // just call cost
    }

    /**
     * Creates an iterator of the weights of the edges this vertex's neighbors by
     * following all edges that begin at this vertex.
     * 
     * @return weights of the edges this vertex's neighbors by following all edges
     *         that begin at this vertex.
     */
    public Iterator<Double> getWeightIterator() {
        return new WeightIterator();
    }

    // INNER CLASS BELOW
    // ___________________________________________________________________________________
    /**
     * Private Inner Simple iterator class.
     */
    private class WeightIterator implements Iterator<Double> {
        /**
         * Created to loop over the edgelist.
         */
        private Iterator<Edge> goer; // created to iterate over

        /**
         * Simple constructor.
         */
        private WeightIterator() {
            goer = edgeList.iterator();// intialize
        }

        /**
         * To check if there is value next.
         * 
         * @return a boolean true or false
         */
        public boolean hasNext() {
            return goer.hasNext(); // see java doc above
        }

        /**
         * This method grabs the next value.
         * 
         * @return the next value.
         */
        public Double next() {
            Double nexty = 0.0; // holds the next value
            boolean caller = goer.hasNext(); // calling to check if there is next

            if (caller == false) { // nothing is next
                throw new NoSuchElementException(); // end and throw an error
            }

            else { // this means there is a next value
                Edge nextNeighEdge = goer.next(); // go to the next
                nexty = nextNeighEdge.getterEdgeWeight(); // grab the value
            }

            return nexty;
        }

        // @2037 no need to implement remove 
        // ___________________________________________________________________________________

    }

    /**
     * Gets an unvisited neighbor, if any, of this vertex.
     * 
     * @return the unvisited neighbor.
     */
    public VertexInterface<T> getUnvisitedNeighbor() {
        VertexInterface<T> answer = null;
        Iterator<VertexInterface<T>> newIterator = getNeighborIterator(); // get the iterator

        while (answer == null && newIterator.hasNext()) { // keep going till you hit the end
            VertexInterface<T> nextDoor = newIterator.next(); // grab the vertex to compare
            if (nextDoor.isVisited() == false) { // has not been seen yet
                answer = nextDoor; // grab the value
                break; // stop the loop
            }
        }
        return answer;
    }

    /**
     * Records the previous vertex on a path to this vertex.
     * 
     * @param predecessor the prevouis.
     */
    public void setPredecessor(VertexInterface<T> predecessor) {
        predecessor = previousVertex; // simply initalize
    }

    /**
     * Gets the recorded predecessor of this vertex.
     * 
     * @return the prevouis.
     */
    public VertexInterface<T> getPredecessor() {
        return previousVertex; // just return the value
    }

    /**
     * Sees whether a predecessor was recorded for this vertex.
     * 
     * @return true or false
     */
    public boolean hasPredecessor() {
        if (previousVertex != null) { // check if the previousVertex is null or not.
            return true;
        }
        return false;
    }

    /**
     * Disconnects this vertex from a given vertex with a weighted edge, i.e.,
     * removes the edge.
     * The Edge should exist in order to be disconnected.
     * 
     * @param endVertex  the vertex to disconnect to.
     * @param edgeWeight the weight of the edge.
     * @return true if the disconnection is successful, false otherwise.
     */
    public boolean disconnect(VertexInterface<T> endVertex, double edgeWeight) {
        // if(weightedhelper(endVertex, edgeWeight) == false){ // this edge doesnt exist
        // automatic false
        // return false;
        // }

        for (int i = 0; i < edgeList.size(); i++) { // loop through the edgelist
            Edge current = edgeList.get(i); // grab the speciifc node
            if (current.getterEdgeWeight() == edgeWeight && current.getterEndVertex().equals(endVertex)) { // see if its in it and matches  etc.
                                                                                                          
                                                                                                           
                edgeList.remove(i); // if so simply remove
                return true; // remove was sucessful
            }
        }
        return false;
    }

    /**
     * Disconnects this vertex from a given vertex with an unweighted edge.
     * The Edge should exist in order to be disconnected.
     * 
     * @param endVertex the vertex to disconnect to.
     * @return true if the disconnection is successful, false otherwise.
     */
    public boolean disconnect(VertexInterface<T> endVertex) {
        // if(unWeightedhelper(endVertex) == false){ // this edge doesnt exist automatic
        // false
        // return false;
        // }

        for (int i = 0; i < edgeList.size(); i++) { // loop through the edgelist
            Edge current = edgeList.get(i); // grab the speciifc node
            if (current.getterEndVertex().equals(endVertex)) { // see if its in it and matches etc.
                edgeList.remove(i); // if so simply remove
                return true; // remove was sucessful
            }
        }
        return false;
    }

    /**
     * Thid method gets the number of neighbors.
     * 
     * @return the number of neighbors of this vertex.
     */
    public int getNumberOfNeighbors() {
        return edgeList.size(); // just the sizwe
    }

    /**
     * Just for checking.
     * Ignore just my testing.
     * 
     * @param args not used.
     */
    public static void main(String[] args) {
        Vertex<Integer> vertex1 = new Vertex<Integer>(12);
        System.out.println("The label should be 12 it is: " + vertex1.getLabel()); // print 12

        vertex1.setCost(10.15);
        System.out.println("The label should be 10.15 it is: " + vertex1.getCost()); // print 10.15

        Vertex<Integer> vertex2 = new Vertex<Integer>(11);
        vertex2.setCost(9.15);

        Vertex<Integer> vertex3 = new Vertex<Integer>(23);
        vertex3.setCost(90.15);

        System.out.println(vertex1.connect(vertex2, 100.11));
        System.out.println(vertex1.connect(vertex3, 103.11));
        System.out.println(vertex1.connect(vertex3, 103.11));

        System.out.println(vertex1.disconnect(vertex2, 100.11));
        System.out.println(vertex1.disconnect(vertex2, 106.11));

        // neighbors

        System.out.println("bob " + vertex1.getNumberOfNeighbors());
        System.out.println(vertex1);

        // vertex1.setPredecessor(vertex2);
        // System.out.println("this is the predecessor " + vertex1.getPredecessor());

    }

}