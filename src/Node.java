
/* Node.java is an object used for every node (or block)
 * on the grid for path finding. Including walls, open, closed
 * path, start and end nodes. This class allows each node to
 * store its position, calculations (f, g, h), parents, and
 * determine equalities to other nodes.
 * by Devon Crawford
 */
public class Node {
	public int x, y, g, h, f;
	public Node parent;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setXY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static boolean isEqual(Node f, Node s) {
		if (f.x == s.x && f.y == s.y) return true;
		return false;
	}
}
