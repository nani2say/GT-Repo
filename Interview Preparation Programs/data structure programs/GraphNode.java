package trees;

import java.util.LinkedList;

public class GraphNode {
	int value;
	boolean isVisited;
	LinkedList<GraphNode> adjacencyList = new LinkedList<GraphNode>();

	public GraphNode(int value) {
		this.value = value;
		isVisited = false;
	}

	public GraphNode(int value, LinkedList<GraphNode> adjacency) {
		this.value = value;
		this.isVisited = false;
		this.adjacencyList = adjacency;
	}

}