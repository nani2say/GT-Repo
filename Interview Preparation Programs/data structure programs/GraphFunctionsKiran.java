package trees;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class GraphFunctionsKiran {
	ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();

	public void createGraph() {

		GraphNode g1 = new GraphNode(1);
		GraphNode g2 = new GraphNode(2);
		GraphNode g3 = new GraphNode(3);
		GraphNode g4 = new GraphNode(4);
		GraphNode g5 = new GraphNode(5);
		GraphNode g6 = new GraphNode(6);

		g1.adjacencyList.add(g2);
		// g1.adjacencyList.add(g5);
		g1.adjacencyList.add(g3);

		// g2.adjacencyList.add(g1);
		g2.adjacencyList.add(g4);
		// g2.adjacencyList.add(g3);

		g3.adjacencyList.add(g1);
		g3.adjacencyList.add(g4);
		g3.adjacencyList.add(g2);

		// g4.adjacencyList.add(g3);
		g4.adjacencyList.add(g5);
		g4.adjacencyList.add(g2);

		g5.adjacencyList.add(g1);

		g6.adjacencyList.add(g5);

		nodes.add(g1);
		nodes.add(g6);
		nodes.add(g2);
		nodes.add(g3);
		nodes.add(g4);
		nodes.add(g5);

	}

	public void dfs() {
		for (int i = 0; i < nodes.size(); i++) {
			if (!nodes.get(i).isVisited) {
				System.out.print(nodes.get(i).value + " ");
				recurseDFS(nodes, i);
			}
		}
	}

	public void recurseDFS(ArrayList<GraphNode> nodes1, int j) {
		LinkedList<GraphNode> ll = nodes.get(j).adjacencyList;
		for (int i = 0; i < ll.size(); i++)
		{
			if (!ll.get(i).isVisited) 
			{
				ll.get(i).isVisited = true;
				System.out.print(ll.get(i).value + " ");
				recurseDFS(nodes1, i);
			}
		}
	}

	public void bfs() {
		// Uses Queue
		Queue<GraphNode> q = new LinkedList<GraphNode>();
		GraphNode g1 = nodes.get(0);

		q.add(g1);
		g1.isVisited = true;
		while (!q.isEmpty()) {
			GraphNode g2 = q.remove();
			// not visited node
			System.out.print(g2.value + " ");

			for (int i = 0; i < g2.adjacencyList.size(); i++) {
				if (!g2.adjacencyList.get(i).isVisited) {
					g2.adjacencyList.get(i).isVisited = true;
					q.add(g2.adjacencyList.get(i));
				}
			}

		}

	}

	public static void main(String args[]) {
		GraphFunctionsKiran gf = new GraphFunctionsKiran();
		gf.createGraph();

		// gf.dfs();

		gf.bfs();

	}
}