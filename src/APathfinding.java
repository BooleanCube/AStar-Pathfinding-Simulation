import java.util.ArrayList;
import java.util.Collections;

public class APathfinding {
	private int size, diagonalMoveCost;
	private long runTime;
	private double kValue;
	private Frame frame;
	private Node startNode, endNode, par;
	private boolean diagonal, running, noPath, complete, trig;
	private ArrayList<Node> borders, open, closed, path;
	private Sort sort = new Sort();

	public APathfinding(int size) {
		this.size = size;
		diagonalMoveCost = (int) (Math.sqrt(2 * (Math.pow(size, 2))));
		kValue = Math.PI / 2;
		diagonal = true;
		trig = false;
		running = false;
		complete = false;
		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
	}

	public APathfinding(Frame frame, int size) {
		this.frame = frame;
		this.size = size;

		diagonalMoveCost = (int) (Math.sqrt(2 * (Math.pow(size, 2))));
		kValue = Math.PI / 2;
		diagonal = true;
		trig = false;
		running = false;
		complete = false;

		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
	}

	public APathfinding(Frame frame, int size, Node start, Node end) {
		this.frame = frame;
		this.size = size;
		startNode = start;
		endNode = end;

		diagonalMoveCost = (int) (Math.sqrt(2 * (Math.pow(size, 2))));
		diagonal = true;
		trig = false;
		running = false;
		complete = false;

		borders = new ArrayList<Node>();
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();
		path = new ArrayList<Node>();
	}

	public void start(Node s, Node e) {
		running = true;
		startNode = s;
		startNode.g = 0;
		endNode = e;

		// Adding the starting node to the closed list
		addClosed(startNode);

		long startTime = System.currentTimeMillis();

		findPath(startNode);

		complete = true;
		long endTime = System.currentTimeMillis();
		runTime = endTime - startTime;
		System.out.println("Completed: " + runTime + "ms");
	}
	
	public void setup(Node s, Node e) {
		running = true;
		startNode = s;
		startNode.g = 0;
		par = startNode;
		endNode = e;

		// Adding the starting node to the closed list
		addClosed(startNode);
	}

	public void setStart(Node s) {
		startNode = s;
		startNode.g = 0;
	}

	public void setEnd(Node e) {
		endNode = e;
	}

	public boolean isRunning() {
		return running;
	}
	
	public boolean isComplete() {
		return complete;
	}

	public Node getStart() {
		return startNode;
	}

	public Node getEnd() {
		return endNode;
	}
	
	public Node getPar() {
		return par;
	}
	
	public boolean isNoPath() {
		return noPath;
	}
	
	public boolean isDiagonal() {
		return diagonal;
	}
	
	public boolean isTrig() {
		return trig;
	}

	public void setDiagonal(boolean d) {
		diagonal = d;
	}
	
	public void setTrig(boolean t) {
		trig = t;
	}

	public void setSize(int s) {
		size = s;
		diagonalMoveCost = (int) (Math.sqrt(2 * (Math.pow(size, 2))));
	}

	public void findPath(Node parent) {
		Node openNode = null;
		if (diagonal) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i == 1 && j == 1) continue;
					int possibleX = (parent.x - size) + (size * i);
					int possibleY = (parent.y - size) + (size * j);
					int crossBorderX = parent.x + (possibleX - parent.x);
					int crossBorderY = parent.y + (possibleY - parent.y);
					if (searchBorder(crossBorderX, parent.y) != -1 | searchBorder(parent.x, crossBorderY) != -1 && ((j == 0 | j == 2) && i != 1)) continue;
					calculateNodeValues(possibleX, possibleY, openNode, parent);
				}
			}
		} 
		else if (!trig) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if((i == 0 && j == 0) || (i == 0 && j == 2) || 
						(i == 1 && j == 1) || (i == 2 && j == 0) ||
						(i == 2 && j == 2)) {
						continue;
					}
					int possibleX = (parent.x - size) + (size * i);
					int possibleY = (parent.y - size) + (size * j);
					calculateNodeValues(possibleX, possibleY, openNode, parent);
				}
			}
		}
		else {
			for (int i = 0; i < 4; i++) {
				int possibleX = (int) Math.round(parent.x + (-size * Math.cos(kValue * i)));
				int possibleY = (int) Math.round(parent.y + (-size * Math.sin(kValue * i)));
				calculateNodeValues(possibleX, possibleY, openNode, parent);
			}
		}
		parent = lowestFCost();
		if (parent == null) {
			System.out.println("END> NO PATH");
			noPath = true;
			running = false;
			frame.repaint();
			return;
		}
		if (Node.isEqual(parent, endNode)) {
			endNode.parent = parent.parent;
			connectPath();
			running = false;
			complete = true;
			frame.repaint();
			return;
		}
		removeOpen(parent);
		addClosed(parent);
		if (diagonal) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i == 1 && j == 1) continue;
					int possibleX = (parent.x - size) + (size * i);
					int possibleY = (parent.y - size) + (size * j);
					Node openCheck = getOpenNode(possibleX, possibleY);
					if (openCheck != null) {
						int distanceX = parent.x - openCheck.x;
						int distanceY = parent.y - openCheck.y;
						int newG = parent.g;
						if (distanceX != 0 && distanceY != 0) newG += diagonalMoveCost;
						else newG += size;
						if (newG < openCheck.g) {
							int s = searchOpen(possibleX, possibleY);
							open.get(s).parent = parent;
							open.get(s).g = newG;
							open.get(s).f = open.get(s).g + open.get(s).h;
						}
					}
				}
			}
		}
		if(!frame.showSteps()) findPath(parent);
		else par = parent;
	}

	public void calculateNodeValues(int possibleX, int possibleY, Node openNode, Node parent) {
		if (possibleX < 0 | possibleY < 0 | possibleX >= frame.getWidth() | possibleY >= frame.getHeight()) return;
		if (searchBorder(possibleX, possibleY) != -1 | searchClosed(possibleX, possibleY) != -1 | searchOpen(possibleX, possibleY) != -1) return;
		openNode = new Node(possibleX, possibleY);
		openNode.parent = parent;
		int GxMoveCost = openNode.x - parent.x;
		int GyMoveCost = openNode.y - parent.y;
		int gCost = parent.g;
		if (GxMoveCost != 0 && GyMoveCost != 0) gCost += diagonalMoveCost;
		else gCost += size;
		openNode.g = gCost;
		int HxDiff = Math.abs(endNode.x - openNode.x);
		int HyDiff = Math.abs(endNode.y - openNode.y);
		int hCost = HxDiff + HyDiff;
		openNode.h = hCost;
		int fCost = gCost + hCost;
		openNode.f = fCost;
		addOpen(openNode);
	}

	public void connectPath() {
		if (path.size() == 0) {
			Node parentNode = endNode.parent;
			while (!Node.isEqual(parentNode, startNode)) {
				addPath(parentNode);
				for (int i = 0; i < getClosedList().size(); i++) {
					Node current = getClosedList().get(i);
					if (Node.isEqual(current, parentNode)) {
						parentNode = current.parent;
						break;
					}
				}
			}
			Collections.reverse(path);
		}
	}

	public void addBorder(Node node) {
		if (borders.size() == 0) borders.add(node);
		else if (!checkBorderDuplicate(node)) borders.add(node);
	}

	public void addOpen(Node node) {
		if (open.size() == 0) open.add(node);
		else if (!checkOpenDuplicate(node)) open.add(node);
	}

	public void addClosed(Node node) {
		if (closed.size() == 0) closed.add(node);
		else if (!checkClosedDuplicate(node)) closed.add(node);
	}

	public void addPath(Node node) {
		if (path.size() == 0) path.add(node);
		else path.add(node);
	}

	public void removePath(int location) {
		path.remove(location);
	}

	public void removeBorder(int location) {
		borders.remove(location);
	}

	public void removeOpen(int location) {
		open.remove(location);
	}

	public void removeOpen(Node node) {
		for (int i = 0; i < open.size(); i++) if (node.x == open.get(i).x && node.y == open.get(i).y) open.remove(i);
	}

	public void removeClosed(int location) {
		closed.remove(location);
	}

	public boolean checkBorderDuplicate(Node node) {
		for (int i = 0; i < borders.size(); i++) if (node.x == borders.get(i).x && node.y == borders.get(i).y) return true;
		return false;
	}

	public boolean checkOpenDuplicate(Node node) {
		for (int i = 0; i < open.size(); i++) if (node.x == open.get(i).x && node.y == open.get(i).y) return true;
		return false;
	}

	public boolean checkClosedDuplicate(Node node) {
		for (int i = 0; i < closed.size(); i++) if (node.x == closed.get(i).x && node.y == closed.get(i).y) return true;
		return false;
	}

	public int searchBorder(int xSearch, int ySearch) {
		int location = -1;
		for (int i = 0; i < borders.size(); i++) {
			if (borders.get(i).x == xSearch && borders.get(i).y == ySearch) {
				location = i;
				break;
			}
		}
		return location;
	}

	public int searchClosed(int xSearch, int ySearch) {
		int location = -1;
		for (int i = 0; i < closed.size(); i++) {
			if (closed.get(i).x == xSearch && closed.get(i).y == ySearch) {
				location = i;
				break;
			}
		}
		return location;
	}

	public int searchOpen(int xSearch, int ySearch) {
		int location = -1;
		for (int i = 0; i < open.size(); i++) {
			if (open.get(i).x == xSearch && open.get(i).y == ySearch) {
				location = i;
				break;
			}
		}
		return location;
	}

	public Node lowestFCost() {
		if (open.size() > 0) {
			sort.bubbleSort(open);
			return open.get(0);
		}
		return null;
	}

	public ArrayList<Node> getBorderList() {
		return borders;
	}

	public ArrayList<Node> getOpenList() {
		return open;
	}

	public Node getOpen(int location) {
		return open.get(location);
	}

	public ArrayList<Node> getClosedList() {
		return closed;
	}

	public ArrayList<Node> getPathList() {
		return path;
	}
	
	public long getRunTime() {
		return runTime;
	}
	
	public void reset() {
		while(open.size() > 0) {
			open.remove(0);
		}
		
		while(closed.size() > 0) {
			closed.remove(0);
		}
		
		while(path.size() > 0) {
			path.remove(0);
		}
		noPath = false;
		running = false;
		complete = false;
	}

	public Node getOpenNode(int x, int y) {
		for (int i = 0; i < open.size(); i++) {
			if (open.get(i).x == x && open.get(i).y == y) {
				return open.get(i);
			}
		}
		return null;
	}

	public void printBorderList() {
		for (int i = 0; i < borders.size(); i++) {
			System.out.print(borders.get(i).x + ", " + borders.get(i).y);
			System.out.println();
		}
		System.out.println("===============");
	}

	public void printOpenList() {
		for (int i = 0; i < open.size(); i++) {
			System.out.print(open.get(i).x + ", " + open.get(i).y);
			System.out.println();
		}
		System.out.println("===============");
	}

	public void printPathList() {
		for (int i = 0; i < path.size(); i++) {
			System.out.print(i + ": " + path.get(i).x + ", " + path.get(i).y + ": " + path.get(i).f);
			System.out.println();
		}
		System.out.println("===============");
	}
}
