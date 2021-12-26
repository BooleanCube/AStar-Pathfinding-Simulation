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

	public boolean isEqual(Node s) {
		return x == s.x && y == s.y;
	}

}
